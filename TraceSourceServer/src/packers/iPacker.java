package packers;

import java.util.List;

public interface iPacker {
	String packTraces(int deviceId, List<String>traces);
}
