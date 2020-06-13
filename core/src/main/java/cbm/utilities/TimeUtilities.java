package cbm.utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import cbm.language.LanguageConfig;

public class TimeUtilities {
	private TimeUtilities() {}

	public static String timeToString(LocalDateTime from, LocalDateTime until) {
		if(from == null || until == null) return null;
		
		StringBuilder builder = new StringBuilder();

		long year = from.until(until, ChronoUnit.YEARS);
		if (year != 0)
			builder.append(year).append(' ').append(LanguageConfig.getString("time.years")).append(' ');

		long month = from.until(until, ChronoUnit.MONTHS);
		if (month != 0)
			builder.append(month).append(' ').append(LanguageConfig.getString("time.months")).append(' ');

		long days = from.until(until, ChronoUnit.DAYS);
		if (days != 0)
			builder.append(days).append(' ').append(LanguageConfig.getString("time.day-short")).append(' ');

		long hours = from.until(until, ChronoUnit.HOURS);
		if (hours != 0)
			builder.append(hours).append(' ').append(LanguageConfig.getString("time.hour-short")).append(' ');

		long minutes = from.until(until, ChronoUnit.MINUTES);
		if (minutes != 0)
			builder.append(minutes).append(' ').append(LanguageConfig.getString("time.minute-short")).append(' ');

		long seconds = from.until(until, ChronoUnit.SECONDS);
		if (seconds != 0)
			builder.append(seconds).append(' ').append(LanguageConfig.getString("time.second-short")).append(' ');

		return builder.toString();
	}
	
	public static String timeToString(LocalDateTime time) {
		if(time == null) return null;
		
		LocalDateTime now = LocalDateTime.now();
		StringBuilder builder = new StringBuilder();
		
		if (time.getYear() != now.getYear())
			builder.append(time.getYear()).append(' ').append(LanguageConfig.getString("time.year")).append(' ');

		if (time.getMonthValue() != now.getMonthValue())
			builder.append(time.getMonthValue()).append(' ').append(LanguageConfig.getString("time.month")).append(' ');

		if (time.getDayOfMonth() != now.getDayOfMonth())
			builder.append(time.getDayOfMonth()).append(' ').append(LanguageConfig.getString("time.day-short")).append(' ');

		builder.append(time.getHour()).append(' ').append(LanguageConfig.getString("time.hour-short")).append(' ');
		builder.append(time.getMinute()).append(' ').append(LanguageConfig.getString("time.minute-short")).append(' ');
		builder.append(time.getSecond()).append(' ').append(LanguageConfig.getString("time.second-short")).append(' ');

		return builder.toString();
	}
	
	public static LocalDateTime parseAddTime(String timeString) {
		LocalDateTime time = LocalDateTime.now();
		
		Map<Character, Integer> map = new HashMap<>();
		StringBuilder value = null;
		
		for(char c : timeString.toCharArray()) {
			if(Character.isDigit(c)) {
				if(value == null)
					value = new StringBuilder();
				
				value.append(c);
			} else {
				if(value == null || value.length() <= 0) continue;
				
				switch (c) {
					case 'y':
					case 'M':
					case 'd':
					case 'w':
						
					case 'h':
					case 'm':
					case 's':
						map.put(c, Integer.parseInt(value.toString()));
						break;
				}
				
				value = null;
			}
		}
		
		if(map.containsKey('y'))
			time = time.plusYears(map.get('y'));
		if(map.containsKey('M'))
			time = time.plusMonths(map.get('M'));
		if(map.containsKey('d'))
			time = time.plusDays(map.get('d'));
		if(map.containsKey('w'))
			time = time.plusWeeks(map.get('w'));
		
		if(map.containsKey('h'))
			time = time.plusHours(map.get('h'));
		if(map.containsKey('m'))
			time = time.plusMinutes(map.get('m'));
		if(map.containsKey('s'))
			time = time.plusSeconds(map.get('s'));
		
		return time;
	}
}
