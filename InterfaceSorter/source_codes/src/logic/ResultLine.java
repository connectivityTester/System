package logic;
public class ResultLine implements Comparable<ResultLine>{
	
	private String nativeLine;
	private String[] parts;

	public ResultLine(String line){
		this.setNativeLine(line);
		this.setParts(line.split(" "));
	}

	public String getNativeLine() 					{ return nativeLine;			}
	public String[] getParts() 						{ return parts;					}
	
	public void setNativeLine(String nativeLine) 	{ this.nativeLine = nativeLine;	}
	public void setParts(String[] parts) 			{ this.parts = parts;			}

	@Override
	public int compareTo(ResultLine resultLine) {
		String currentLine = this.parts[parts.length-2] +" "+ this.parts[parts.length-1];
		String compareLine = resultLine.getParts()[resultLine.parts.length-2]+" "+resultLine.getParts()[resultLine.parts.length-1];
		return currentLine.compareTo(compareLine);
	}
	
	public String getInterfaceName()				{ return parts[parts.length-2];	
		}	
	public String getVersion()						{ return parts[parts.length-1];	}
	public String getApplication()					{ return parts[4];				}

}
