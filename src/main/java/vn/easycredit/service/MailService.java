package vn.easycredit.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.extern.slf4j.Slf4j;
import vn.easycredit.setting.EligibleMailSetting;

@Service
@Slf4j
public class MailService {
	public static final String CREATE_PASSWORD_EMAIL = "create-password";
	public static final String FORGOT_PASSWORD_EMAIL = "forgot-password";

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Autowired
	private EligibleMailSetting eligibleMailSetting;

	public void sendMail(final String emailAddress, final String token, final String fullName, final boolean forgetPassword) {
		try {
			log.info(forgetPassword ? "Send mail forget password ...." : "Send mail create password ....");
			MimeMessage message = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			String link = forgetPassword ? eligibleMailSetting.getLinkMailForget().concat(token) : eligibleMailSetting.getLinkMailCreate().concat(token);
			Map<String, Object> model = new HashMap<>();
			model.put("fullName", fullName);
			model.put("link", link);
			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process(forgetPassword ? FORGOT_PASSWORD_EMAIL : CREATE_PASSWORD_EMAIL, context);

			helper.setTo(emailAddress);
			helper.setText(html, true);
			helper.setSubject(forgetPassword ? eligibleMailSetting.getSubjectMailOfForgetPassword() : eligibleMailSetting.getSubjectMailOfCreatePassword());
			helper.setFrom(eligibleMailSetting.getFromMail());

			javaMailSender.send(message);
			log.info("DONE...");
		} catch (MessagingException e) {
			log.info(forgetPassword ? "Send mail forget password exception...." : "Send mail create password exception....");
			log.info(e.toString());
		}
	}
}
