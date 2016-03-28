package reports;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;

import test.Test;
import types.ActionResultTypes;
import types.FailReaction;
import types.LogLevels;
import utils.Logger;
import utils.Utils;

public class ExcelReport extends Report{
	
	private static final byte blueColor = (byte) 255;
	private static final byte greenColor = (byte)106;
	private static final byte redColor = (byte)181;
	private int currentRowNumber = 0;
	private final HSSFWorkbook document;
	private final HSSFSheet mainSheet;
	
	public ExcelReport() {
		this.document = new HSSFWorkbook();
		this.mainSheet = document.createSheet("Test Results");
	}

	@Override
	public void setDataToFile(final List<Test> executedTests) {
		Utils.requireNonNull(executedTests);
		
		executedTests.forEach(test ->{
			this.createTestHeader(this.mainSheet, test.getTestName());
			this.setData(this.mainSheet, test);
			this.currentRowNumber++;
		});
		this.mainSheet.autoSizeColumn(0, true);
		this.mainSheet.autoSizeColumn(1, true);
		this.mainSheet.autoSizeColumn(2, true);
		
	}

	@Override
	public void saveDataToFile(final String pathTofile) throws FileNotFoundException, Exception {
		Utils.requireNonNull(pathTofile);
		
		FileOutputStream outputStream = new FileOutputStream(
				new File(Report.reportFilesDirectory.getAbsolutePath()
						+ "/" + pathTofile));
		this.document.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
	private void createTestHeader(final HSSFSheet sheet, final String testName){
		Utils.requireNonNull(sheet, testName);
		
		CellRangeAddress mergedCells = new CellRangeAddress(this.currentRowNumber, this.currentRowNumber, 0, 2);
		sheet.addMergedRegion(mergedCells);
		HSSFCell headerCell = sheet.createRow(this.currentRowNumber).createCell(0);
		headerCell.setCellValue(testName);

		HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, mergedCells, sheet, this.document);
		HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, mergedCells, sheet, this.document);
		
		HSSFCellStyle cellStyle = this.document.createCellStyle();
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        HSSFFont hSSFFont = this.document.createFont();
        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont.setFontHeightInPoints((short) 12);
        cellStyle.setFont(hSSFFont);
        headerCell.setCellStyle(cellStyle);
		this.currentRowNumber++;
		
		HSSFRow columnNames = sheet.createRow(this.currentRowNumber);
		HSSFCell commandCell = columnNames.createCell(0);
		commandCell.setCellValue("Command");
		
		HSSFCellStyle columnsCellStyle = this.document.createCellStyle();
		columnsCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		columnsCellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		columnsCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont columnsCellFont = this.document.createFont();
        columnsCellFont.setFontHeightInPoints((short) 10);
        columnsCellStyle.setFont(columnsCellFont);
        columnsCellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        columnsCellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        columnsCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        columnsCellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        columnsCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        commandCell.setCellStyle(columnsCellStyle);
        
		HSSFCell finishCell = columnNames.createCell(1);
		finishCell.setCellValue("Finished with result");
		finishCell.setCellStyle(columnsCellStyle);
		
		HSSFCell reasonCell = columnNames.createCell(2);
		reasonCell.setCellValue("Reason");
		reasonCell.setCellStyle(columnsCellStyle);
		this.currentRowNumber++;
	}
	
	private void setData(final HSSFSheet sheet, final Test executedTest){
		Utils.requireNonNull(sheet, executedTest);
		
		executedTest.getTestActions().stream().forEach(action -> {
			HSSFRow currentCommandRow = sheet.createRow(this.currentRowNumber);
			HSSFCell currentCell = currentCommandRow.createCell(0);
			currentCell.setCellValue(action.getCommand().getCommandName());
			currentCell.setCellStyle(createStyle(HSSFColor.WHITE.index));
			currentCell = currentCommandRow.createCell(1);
			currentCell.setCellValue(action.getActionResult().getResultType().toString());
			if(action.getActionResult().getResultType() == ActionResultTypes.OK){				
				currentCell.setCellStyle(createStyle(HSSFColor.BRIGHT_GREEN.index));
			}
			else if(action.getFailReaction() == FailReaction.SKIP){
				HSSFColor skipColor = this.setColor(redColor, greenColor, blueColor);
				if(skipColor != null){
					currentCell.setCellStyle(createStyle(skipColor.getIndex()));
				}
				else{
					currentCell.setCellStyle(createStyle(HSSFColor.YELLOW.index));
				}
			}
			else{
				currentCell.setCellStyle(createStyle(HSSFColor.RED.index));
			}
			currentCell = currentCommandRow.createCell(2);
			currentCell.setCellValue(action.getActionResult().getReason());
			currentCell.setCellStyle(createStyle(HSSFColor.WHITE.index));
			this.currentRowNumber++;
		});
	}
	
	private HSSFColor setColor(final byte r, final byte g, final byte b){
		HSSFPalette palette = this.document.getCustomPalette();
		HSSFColor hssfColor = null;
		hssfColor= palette.findColor(r, g, b); 
		if (hssfColor == null ){
		    palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
		    hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
		}
		return hssfColor;
		}
	
	private HSSFCellStyle createStyle(final short color)
	{
		final HSSFCellStyle dataCellStyle = this.document.createCellStyle();
		dataCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		dataCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dataCellStyle.setFillForegroundColor(color);
		dataCellStyle.setWrapText(true);
		dataCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return dataCellStyle;
	}

	@Override
	public boolean openReportFile(final String pathTofile) {
		Utils.requireNonNull(pathTofile);
		
		boolean result = false;
		if(!result){
			Desktop desktop = Desktop.getDesktop();
			if (!desktop.isSupported(Desktop.Action.EDIT)) {
				Logger.log(LogLevels.ERROR, "ExcelReport", "Function openReportFile, desktop does not support \"edit\" function");
			}
			else{
				try {
					desktop.edit(new File(Report.reportFilesDirectory.getAbsolutePath()+ "\\" + pathTofile));
					result = true;
				} catch (IOException e) {
					Logger.log(LogLevels.ERROR, "ExcelReport", "Function openReportFile, desktop can't open file: "+Report.reportFilesDirectory.getAbsolutePath()+ "\\" + pathTofile);
				}
			}
		}
		return result;		  
	}

}
