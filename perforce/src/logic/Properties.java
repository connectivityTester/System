package logic;

import java.io.File;
import java.io.FileInputStream;

public enum Properties {	
	
	USER("user"),
	PASSWORD("password"),
	DOMAIN_WORKSPACE("domain_workspace"),
	TCFG_WORKSPACE("tcfg_workspace"),
	BRANCH("branch"),
	LABEL_PREFIX("label_prefix"),
	SYNC_MANAGER_PATH("sync_manager_path"),
	TCFG_PATH("tcfg_path"),
	APPS_PATH("apps_path"),
	SCP_PATH("scp_path"),
	BINARIES_DEPOT("binaries_depot");
	
	public static final String CONFIG_FILE = "config.ini";

	static {
		java.util.Properties props = new java.util.Properties();
		try {
			props.load(new FileInputStream(new File(CONFIG_FILE)));
		} catch (Exception e) {
			e.printStackTrace();
		}		
		for(Properties property : values()){
			property.setData(props.getProperty(property.getName()));
		}
	};

	Properties(final String name){
		this.name = name;
	}
	

	private final String name;
	private String data;
	
	public String getName()					{ return this.name; }
	public String getData()					{ return this.data; }
	public String toString()				{ return this.data;	}
	public void setData(final String data)	{ this.data = data; }	
}
