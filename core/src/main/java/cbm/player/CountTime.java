package cbm.player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CountTime {
	private LocalDateTime current;

	public CountTime() {
		current = LocalDateTime.of(0, 1, 1, 0, 0, 0);
	}

	public CountTime(LocalDateTime localDateTime) {
		current = localDateTime;
	}

	public CountTime(String s) {
		setTime(s);
	}

	public void add(LocalDateTime von, LocalDateTime bis) {
		LocalDateTime tmp = LocalDateTime.from(von);
		addYear(tmp.until(bis, ChronoUnit.YEARS));
		addMonth(tmp.until(bis, ChronoUnit.MONTHS));
		addDay(tmp.until(bis, ChronoUnit.DAYS));

		addHour(tmp.until(bis, ChronoUnit.HOURS));
		addMinute(tmp.until(bis, ChronoUnit.MONTHS));
		addSecond(tmp.until(bis, ChronoUnit.SECONDS));
	}

	public int getYears() {
		return current.getYear();
	}

	public void addYear(long months) {
		current = current.plusMonths(months);
	}

	public int getMonth() { // Begint bei 1 und 12 Monate = 1 Year, daher - 1 um es zu korregieren
		return current.getMonthValue() - 1;
	}

	public void addMonth(long months) {
		current = current.plusMonths(months);
	}

	public int getDay() {
		return current.getDayOfMonth() - 1;
	}

	public void addDay(long days) {
		current = current.plusDays(days);
	}

	public int getHour() {
		return current.getHour();
	}

	public void addHour(long hours) {
		current = current.plusHours(hours);
	}

	public int getMinute() {
		return current.getMinute();
	}

	public void addMinute(long minutes) {
		current = current.plusMinutes(minutes);
	}

	public int getSecond() {
		return current.getSecond();
	}

	public void addSecond(long seconds) {
		current = current.plusSeconds(seconds);
	}

	public LocalDateTime getLocalDateTime() {
		return current;
	}

	public void setTime(String s) {
		if (s != null && !s.isEmpty()) {
			String[] times = s.split("-");

			try {
				current = LocalDateTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]) + 1, Integer.parseInt(times[2]) + 1, Integer.parseInt(times[3]), Integer.parseInt(times[4]));
				return;
			} catch (NumberFormatException ignored) {
			}
		}

		current = LocalDateTime.of(0, 1, 1, 0, 0, 0);
	}

	public String format(String format) {
		char selected = 0;
		int number = 0;

		StringBuilder builder = new StringBuilder();

		for (char c : format.toCharArray()) {
			switch (c) {
				case 'u':
				case 'y':
				case 'Y':
				case 'M':
				case 'd':
				case 'D':
				case 'h':
				case 'H':
				case 'm':
				case 's':
				case 'S':

					if (selected == c)
						number++;
					else {
						builder.append(getString(selected, number));

						selected = c;
						number = 1;
					}

					break;

				default:
					if (selected != 0) {
						builder.append(getString(selected, number));

						selected = 0;
						number = 1;
					}

					builder.append(c);

					break;
			}
		}

		if (selected != 0)
			builder.append(getString(selected, number));

		return builder.toString();
	}

	public String getString(char c, int amount) {
		if (amount < 1) return "";

		int n;

		switch (c) {
			case 'u':
			case 'y':
			case 'Y':

				n = getYears();
				break;

			case 'M':

				n = getMonth();
				break;

			case 'd':
			case 'D':

				n = getDay();
				break;

			case 'h':
			case 'H':

				n = getHour();
				break;

			case 'm':

				n = getMinute();
				break;

			case 's':
			case 'S':

				n = getSecond();
				break;

			default:
				return "";
		}

		int rest = amount - String.valueOf(n).length();
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < rest; i++)
			s.append("0");
		s.append(n);

		return s.toString();
	}

	@Override
	public String toString() {
		return getYears() + "-" + getMonth() + "-" + getDay() + "-" + getHour() + "-" + getMinute() + "-" + getSecond();
	}
}
