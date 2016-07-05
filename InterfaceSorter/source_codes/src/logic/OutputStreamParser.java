package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OutputStreamParser {
	
	public OutputStreamParser(){}
	
	public List<String> parseOutputStream(String outputStream, List<String> processes, List<String> interfaces){
		List <String> neededInterfaces = new ArrayList<>();
		String[] splitResults = outputStream.split("\\n");
		for(String string: splitResults){
			for(String process : processes){
				if(string.contains(process)){
					for(String interfaceItem  : interfaces){
						if(string.contains(interfaceItem)){
							neededInterfaces.add(string);
							break;
						}
					}
				}
			}
		}		
		return neededInterfaces;		
	}

}
