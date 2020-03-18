package vn.easycredit.request;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.easycredit.common.Constant;
import vn.easycredit.common.HttpStatusCodeEnum;
import vn.easycredit.common.RedirectPageEnum;
import vn.easycredit.common.Utils;
import vn.easycredit.response.InquiryResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnquiryEligibleRequest {

	@JsonProperty("national_id")
	@SerializedName("national_id")
	private String nationalId;

	@JsonProperty("date_of_birth")
	@SerializedName("date_of_birth")
	private String dateOfBirth;

	@JsonProperty("user_name")
	@SerializedName("user_name")
	private String userName;

	@JsonProperty("login_id")
	@SerializedName("login_id")
	private String loginId;

	public InquiryResponse validate() {
		Utils utils = new Utils();
		if (StringUtils.isEmpty(this.nationalId) || StringUtils.isEmpty(this.dateOfBirth) || StringUtils.isEmpty(this.userName) || StringUtils.isEmpty(this.loginId)) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.INPUT_EMPTY,
					Constant.INPUT_EMPTY, RedirectPageEnum.INQUIRY.getCode());
		} else if (!utils.checkNationalIdMatch(this.nationalId)) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.ERROR_NATIONAL_ID, "", RedirectPageEnum.INQUIRY.getCode());
		} else if (this.nationalId.length() != 9 && this.nationalId.length() != 12) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.ERROR_NATIONAL_ID_LENGTH, "", RedirectPageEnum.INQUIRY.getCode());
		}
		Date parseDateOfBirth = utils.parseDate(this.dateOfBirth);
		if (StringUtils.isEmpty(parseDateOfBirth)) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.ERROR_DATE_OF_BIRTH,
					"", RedirectPageEnum.INQUIRY.getCode());
		}
		LocalDate dob = parseDateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (null == dob) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.ERROR_DATE_OF_BIRTH,
					"", RedirectPageEnum.INQUIRY.getCode());
		}else if (LocalDate.now().minusYears(18).isBefore(dob) || dob.getYear() < 1900) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.ERROR_RANGE_DATE_OF_BIRTH, "", RedirectPageEnum.INQUIRY.getCode());
		}
		return null;
	}
}
