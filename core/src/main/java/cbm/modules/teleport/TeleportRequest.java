package cbm.modules.teleport;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import components.utils.tuple.Tuple;

public class TeleportRequest extends Tuple<UUID, UUID> implements Delayed {
	protected LocalDateTime ended;
	
	public TeleportRequest(UUID object1, UUID object2) {
		super(object1, object2);
		ended = LocalDateTime.now().plusMinutes(5);
	}

	@Override
	public int compareTo(Delayed o) {
		var a = ended;
		var b = ((TeleportRequest) o).ended;
		
		if(a.isBefore(b))
			return (int) (a.until(b, ChronoUnit.MILLIS) * -1);
		return (int) b.until(a, ChronoUnit.MILLIS);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return LocalDateTime.now().until(ended, ChronoUnit.MILLIS);
	}
}
