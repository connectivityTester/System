package io;

import java.io.IOException;

public abstract class Reader {
	
	protected String filePath;
	
	public Reader(String filePath)	{ this.filePath = filePath; }
	public abstract Configuration readConfiguration() throws IOException;
	public abstract String getTargetIP();
}
