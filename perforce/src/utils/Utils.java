package utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String getHarmanDate() {
		// example, 16203
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR) - 2000;
		String week = getHarmanWeek();
		int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return year + week + day;
	}

	public static String getHarmanWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.WEEK_OF_YEAR) - 1 + "";
	}
	
	public static String getUsualDate(){
		return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
	}
	
	public static String getData(String input, String regexp){
		Matcher matcher = Pattern.compile(regexp).matcher(input);
		matcher.find();
		return matcher.group(1);
	}
}
