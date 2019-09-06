package essentials.utilities;

import essentials.language.LanguageConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtilities {
	private TimeUtilities() {}
	
	public static String timeToString(LocalDateTime from, LocalDateTime until) {
		StringBuilder builder = new StringBuilder();
		
		long year = from.until(until, ChronoUnit.YEARS);
		if(year != 0)
			builder.append(year + ' ' + LanguageConfig.getString("time.years") + ' ');
			
		long month = from.until(until, ChronoUnit.MONTHS);
		if(month != 0)
			builder.append(month + ' ' + LanguageConfig.getString("time.months") + ' ');
		
		long days = from.until(until, ChronoUnit.DAYS);
		if(days != 0)
			builder.append(days + ' ' + LanguageConfig.getString("time.day-short") + ' ');
		
		long hours = from.until(until, ChronoUnit.HOURS);
		if(hours != 0)
			builder.append(hours + ' ' + LanguageConfig.getString("time.hour-short") + ' ');
		
		long minutes = from.until(until, ChronoUnit.MINUTES);
		if(minutes != 0)
			builder.append(minutes + ' ' + LanguageConfig.getString("time.minute-short") + ' ');
		
		long seconds = from.until(until, ChronoUnit.SECONDS);
		if(seconds != 0)
			builder.append(seconds + ' ' + LanguageConfig.getString("time.second-short") + ' ');
		
		return builder.toString();
	}
}
