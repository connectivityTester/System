package test;

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

import buffers.BufferManager;
import common.SystemConstants;
import types.LogLevels;
import types.MessageLogTypes;
import types.ReportTypes;
import utils.Logger;
import xml.AbstractReader;
import xml.SystemConfig;
import xml.TestReader;

public class TestExecutor implements Runnable{
	
	private static final String ERROR_TITLE = "Error during test loading";
	private final static SimpleDateFormat format = new SimpleDateFormat("yyMMdd_HHmmss");
	private final List<Test> testToExecute;
	private final boolean doReport;
	private final AbstractReader reader = new TestReader(SystemConstants.testShemaPath);;
	
	public TestExecutor(final TreePath [] paths, final boolean withReport){
		utils.Utils.requireNonNull((Object []) paths);
		
		this.testToExecute = this.loadTestList(paths);
		this.doReport = withReport;
	}

	private List<Test> loadTestList(final TreePath [] paths) {
		utils.Utils.requireNonNull((Object []) paths);
		
		final List<Test> tests = new ArrayList<Test>();
		for(TreePath path : paths){
			TreeNode node = (TreeNode)path.getLastPathComponent();
			if(node.isLeaf()){
				String absolutePath = this.getAbsoluteTestFilePath(node);
				if(absolutePath.isEmpty()){
					continue;
				}
				Test test = createTest(node, absolutePath);
				if(test == null){
					return null;
				}
				test.setTestName(node.toString());
				tests.add(test);
			}
			else{
				List<TreeNode> children = this.getChildren(node);
				for(TreeNode child : children){
					Test test = createTest(child);
					if(test == null){
						return null;
					}
					test.setTestName(child.toString());
					tests.add(test);
				}
			}
		}
		return tests;
	}

	private Test createTest(final TreeNode child) {
		utils.Utils.requireNonNull(child);
		
		String pathToTest = this.getAbsoluteTestFilePath(child);
		Test test = null;
		try {
			test = (Test) this.reader.getContext(pathToTest);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null, "<html><body><p style='width: 150px;'>File: " + 
					pathToTest+".</p><p> "+e.getMessage()+
					". </p><p>Execution of tests was interrupted</p></body></html>", 
					ERROR_TITLE, 
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return test;
	}

	private Test createTest(TreeNode node, String absolutePath) {
		Test test = null;
		try {
			test = (Test) this.reader.getContext(absolutePath);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					null, "<html><body><p style='width: 150px;'>File: "+
					node.toString() + ".</p><p> "	+ e.getMessage()+
					". </p><p>Execution of tests was interrupted</p></body></html>", 
					ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return test;
	}
	
	
	@Override
	public void run() {
		BufferManager.getInstance().setIsAllowAddition(true);
		this.executeTests();	
		BufferManager.getInstance().setIsAllowAddition(false);
		BufferManager.getInstance().clearBuffers();
	}

	private void executeTests() {
		if(this.testToExecute == null){
			return;
		}	
		this.testToExecute.forEach(test -> test.executeTest());
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
