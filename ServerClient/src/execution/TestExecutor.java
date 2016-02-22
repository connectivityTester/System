package execution;

import reports.ExcelReport;
import reports.Report;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import common.SystemConfig;
import common.ConfigXMLReader;
import test.Test;
import types.LogLevels;
import types.MessageLogTypes;
import types.ReportTypes;
import utils.Logger;

public class TestExecutor implements Runnable{
	
	private final static SimpleDateFormat format = new SimpleDateFormat("yyMMdd_HHmmss");
	private final List<Test> testToExecute;
	private final boolean doReport;
	
	public TestExecutor(TreePath [] paths, boolean withReport){
		this.testToExecute = this.loadTestList(paths);
		this.doReport = withReport;
	}

	private List<Test> loadTestList(TreePath [] paths) {
		List<Test> tests = new ArrayList<Test>();
		for(TreePath path : paths){
			TreeNode node = (TreeNode)path.getLastPathComponent();
			if(node.isLeaf()){
				String absolutePath = this.getAbsoluteTestFilePath(node);
				if(absolutePath.isEmpty()){
					continue;
				}
				Test test = null;
				try {
					test = ConfigXMLReader.readTest(absolutePath);
				} catch (Exception e) {
					StringBuilder message = new StringBuilder("<html><body><p style='width: 150px;'>File: ");
					message.append(node.toString());
					message.append(".</p><p> ");
					message.append(e.getMessage());
					message.append(". </p><p>Execution of tests was interrupted</p></body></html>");
					JOptionPane.showMessageDialog(null, message.toString(), "Error during test loading", JOptionPane.ERROR_MESSAGE);
					return null;
				}
				test.setTestName(node.toString());
				tests.add(test);
			}
			else{
				List<TreeNode> children = this.getChildren(node);
				for(TreeNode child : children){
					String pathToTest = this.getAbsoluteTestFilePath(child);
					Test test = null;
					try {
						test = ConfigXMLReader.readTest(pathToTest);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(
								null, 
								"<html><body><p style='width: 150px;'>File: "+pathToTest+".</p><p> "+e.getMessage()+". </p><p>Execution of tests was interrupted</p></body></html>", 
								"Error during test loading", 
								JOptionPane.ERROR_MESSAGE);
						return null;
					}
					test.setTestName(child.toString());
					tests.add(test);
				}
			}
		}
		return tests;
	}
	
	@Override
	public void run() {
		if(this.testToExecute == null){
			return;
		}	
		for(Test test : this.testToExecute){			
			test.executeTest();			
		}
		if(this.doReport){
			Logger.logToUser("Report generation was started", this, MessageLogTypes.HEADER);			
			this.generateReport(this.testToExecute, ReportTypes.EXCEL_REPORT);			
			Logger.logToUser("Report generation was finished", this, MessageLogTypes.HEADER);
		}		
	}

	private void generateReport(List<Test> executedTests, ReportTypes type) {
		Report report = null;
		switch(type){
			case EXCEL_REPORT:
				report = new ExcelReport();
				report.setDataToFile(executedTests);
				String fileReportPath = format.format(new Date()).toString()+".xls";
				try {
					report.saveDataToFile(fileReportPath);
				} catch (FileNotFoundException e) {
					Logger.logToUser("Report file can't be created", this, MessageLogTypes.ERROR);
					Logger.log(LogLevels.EXCEPTION, this, "Method generateReport, " + e.getStackTrace());
				} catch (Exception e) {
					Logger.logToUser("Data can't be written to file", this, MessageLogTypes.ERROR);
					Logger.log(LogLevels.EXCEPTION, this, "Method generateReport, " + e.getStackTrace());
				}
				if(!report.openReportFile(fileReportPath)){
					Logger.logToUser("Sorry, system can't open report", this, MessageLogTypes.SKIP);
					Logger.logToUser("Please open it via file manager", this, MessageLogTypes.SKIP);
				}				
				break;
		}		
	}
	
	private String getAbsoluteTestFilePath(TreeNode treePath){
		String absolutePath = treePath.toString();
		TreeNode tempNode = treePath;
		while(tempNode.getParent().getParent() != null){
			tempNode = tempNode.getParent();
			absolutePath = tempNode.toString() + "\\"+absolutePath;
		}
		return SystemConfig.getInstance().getTestRootDirectory()+"\\"+absolutePath;
	}
	
	private List<TreeNode> getChildren(TreeNode node){
		List<TreeNode> children = new ArrayList<TreeNode>();
		for(int i = 0; i < node.getChildCount(); ++i){
			if(node.getChildAt(i).isLeaf()){
				children.add(node.getChildAt(i));
			}
			else{
				children.addAll(this.getChildren(node.getChildAt(i)));
			}
		}
		return children;
	}

}
