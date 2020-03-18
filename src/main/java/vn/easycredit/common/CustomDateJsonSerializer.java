package vn.easycredit.common;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * validate input date
 * @author HienNT
 *
 */
@Slf4j
public class CustomDateJsonSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {
    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
    
    public static final Pattern DATE_PATTERN = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)+$");
	public static final Pattern DATE_TIME_PATTERN = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)\\s([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]+$");
    

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String asString = json.getAsString();
        if (StringUtils.isEmpty(asString)){
        	throw new JsonParseException("Could not parse to date: 'Date is empty'");
        }
        try {
            if (DATE_PATTERN.matcher(asString).matches()) {
            	
            	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");            	
            	df.setTimeZone(UTC_TIME_ZONE);
            	df.setLenient(false);
            	Date startDateJava = df.parse(asString);
            	
                return startDateJava;
            } else if (DATE_TIME_PATTERN.matcher(asString).matches()) {
                return getDateTimeFormat().parse(asString);
            } else {
                throw new JsonParseException("Could not parse to date: " + json);
            }
        } catch (ParseException e) {
            throw new JsonParseException("Could not parse to date: " + json, e);
        }
    }

    private static DateFormat getDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(UTC_TIME_ZONE);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat;
    }

    private static DateFormat getDateTimeFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        dateFormat.setTimeZone(UTC_TIME_ZONE);
        dateFormat.setLenient(false);
        return dateFormat;
    }

    public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
        Calendar calendar = Calendar.getInstance(UTC_TIME_ZONE);
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        String dateFormatted;
        if (hours == 0 && minutes == 0 && seconds == 0) {
            dateFormatted = getDateFormat().format(date);
        } else {
            dateFormatted = getDateTimeFormat().format(date);
        }
        return new JsonPrimitive(dateFormatted);
    }
}
