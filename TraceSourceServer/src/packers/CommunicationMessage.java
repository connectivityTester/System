package packers;

public class CommunicationMessage {
	
	private String type;
	private int id;
	private String data;
	
	public CommunicationMessage(String type, int id, String data) {
		super();
		this.type = type;
		this.id = id;
		this.data = data;
	}
	
	public String getType() 		{	return type;	 	}
	public int getId() 				{	return id;			}
	public String getData() 		{	return data;		}
	
	public void setId(int id) 		{	this.id = id;		}
	public void setType(String type){	this.type = type;	}
	public void setData(String data){	this.data = data;	}
}
