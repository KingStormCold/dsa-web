package vn.easycredit.common;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class LogUtilities {
	public <T> String convertObjectToJsonLog(T object) {
		Gson builder = new GsonBuilder().registerTypeAdapter(Date.class, new CustomDateJsonSerializer())
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		return builder.toJson(object);
	}
}
