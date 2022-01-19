package cbm.modules.teleport;

import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import components.utils.tuple.Tuple;

public class TeleportRequest extends Tuple<UUID, UUID> implements Delayed {
	public TeleportRequest(UUID object1, UUID object2) {
		super(object1, object2);
	}

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}
}
