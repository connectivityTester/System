package controller;


import logic.Logic;
import gui.MainWindow;
import gui.MessageType;
/**
 * It is class-starter
 * It creates application logic and GUI and  
 * 		handles communication between each other.
 * 
 * @author dkuschevoy
 *
 */
public class Controller {
	
	private MainWindow gui;
	private Logic logic;
	
	public final static Controller controller = new Controller();
	
	public void init(){
		if(gui == null){
			gui = new MainWindow();
			gui.blockGUI(true);
			if(logic == null){
				logic = new Logic();
			}
			gui.blockGUI(false);
		}
	}

	public void showLogMessage(final String message, final MessageType type)	{	gui.showLogMessage(message, type);	}
	public void blockGUI(final boolean flag)									{	gui.blockGUI(flag);					}
	
	public void makeRebase(final String wicomeLabel, final boolean withSubmit)	{	logic.makeRebase(wicomeLabel, withSubmit);	}
	public void createLabel(final boolean withSubmit)							{	logic.createLabel(withSubmit);				}	
	public void makeTCFG(final String wicomeLabel, final boolean withSubmit)	{	logic.makeTCFG(wicomeLabel, withSubmit);	}	
	public void addFilesToLabel() 												{	logic.addFilesToLabel();					}
	public void getLatestDomainRevision() 										{	logic.getLatestDomainRevision();			}
	public void syncTCFGToHead() 												{	logic.syncTCFGToHead();						}
//	public void buildRebaseCL() 					{	logic.buildRebaseCL();			}
//	public void buildTCFGCL() 						{	logic.buildTCFGCL();			}
	
}