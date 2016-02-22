package packers;

import java.util.List;
import com.google.gson.Gson;

public class JsonPacker implements iPacker {

	@Override
	public String packTraces(int deviceId, List<String> traces) {
		CommunicationMessage [] messages = new CommunicationMessage[traces.size()];
		for(int i = 0; i < traces.size(); ++i){
			messages[i] = new CommunicationMessage("TEXT", deviceId, traces.get(i));
		}
		return new Gson().toJson(messages, CommunicationMessage[].class);
	}

}
