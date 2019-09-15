package essentials.utilities;

import essentials.language.LanguageConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtilities {
	private TimeUtilities() {}

	public static String timeToString(LocalDateTime from, LocalDateTime until) {
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
}
