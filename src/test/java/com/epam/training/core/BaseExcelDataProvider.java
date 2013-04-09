package com.epam.training.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * Class that provides test data set from the excel file. Due to TestNG
 * restrictions {@link DataProvider} method must be static. Because of that all
 * methods that uses in {@link DataProvider} are static too.
 * 
 * @author Oleksandr_Kachur
 * 
 */
public class BaseExcelDataProvider {
	private static Logger logger = Logger.getLogger("ExcelDataProvider");

	private static final String XLS = "xls";
	private static final String XLSX = "xlsx";

	/**
	 * Gets data from the first sheet of workbook. Number of columns MUST be the
	 * same as number of method parameters
	 * 
	 * @param context
	 *            {@link ITestContext} of the current test run. This parameter
	 *            provides reference to the value of excel data file path.
	 * @param method
	 *            Method that had called this {@link DataProvider}. This
	 *            parameter provides number of the method's parameters. This
	 *            info is needed to get the same number of columns as method
	 *            parameters.
	 * @param filePath
	 *            Name of tag in testng.xml that stores data file path
	 * @return Data set from excel file which is represented as array of arrays
	 */
	public static String[][] baseDataProvider(String filePath, ITestContext context, Method method) {
		// Get number of parameters that callable method has
		int paramsCount = method.getParameterTypes().length;
		// Retrieve parameter value from testng.xml
		String dataFilePath = context.getCurrentXmlTest().getParameter(filePath);
		File workbook = new File(dataFilePath);
		// Get data from workbook. Number of columns MUST be the same as number
		// of method parameters
		String[][] data = loadData(workbook, 0, paramsCount);
		return data;
	}
	
	public static String[][] baseDataProvider(String filePath, int sheetIndex, ITestContext context, Method method) {
		// Get number of parameters that callable method has
		int paramsCount = method.getParameterTypes().length;
		// Retrieve parameter value from testng.xml
		String dataFilePath = context.getCurrentXmlTest().getParameter(filePath);
		File workbook = new File(dataFilePath);
		// Get data from workbook. Number of columns MUST be the same as number
		// of method parameters
		String[][] data = loadData(workbook, sheetIndex, paramsCount);
		return data;
	}

	/**
	 * Similar to the
	 * {@link BaseExcelDataProvider#baseDataProvider(String, ITestContext, Method)}
	 * method, but with current one it is possible to define sheet name from
	 * which this method receives data
	 * 
	 */
	public static String[][] baseDataProvider(String filePath, String sheetName, ITestContext context, Method m) {
		// Get number of parameters that callable method has
		int paramsCount = m.getParameterTypes().length;
		// Retrieve parameter value from testng.xml
		String dataFilePath = context.getCurrentXmlTest().getParameter(filePath);
		File workbook = new File(dataFilePath);
		// Get data from workbook. Number of columns MUST be the same as number
		// of method parameters
		String[][] data = loadData(workbook, sheetName, paramsCount);
		return data;
	}


	private static String[][] loadData(File xlFile, int sheetIndex, int values) {

		Workbook wb = createWorkbook(xlFile);
		Sheet sheet = wb.getSheetAt(sheetIndex);
		return loadData(sheet, values);
	}

	private static String[][] loadData(File xlFile, String sheetName, int values) {

		Workbook wb = createWorkbook(xlFile);
		Sheet sheet = wb.getSheet(sheetName);
		return loadData(sheet, values);
	}
	
	
	public static String[][] loadData(File xlFile, int sheetIndex, int values, int startRowIndex) {

		Workbook wb = createWorkbook(xlFile);
		Sheet sheet = wb.getSheetAt(sheetIndex);
		return loadData(sheet, values, startRowIndex);
	}

	public static String[][] loadData(File xlFile, String sheetName, int values, int startRowIndex) {

		Workbook wb = createWorkbook(xlFile);
		Sheet sheet = wb.getSheet(sheetName);
		return loadData(sheet, values, startRowIndex);
	}

	private static String[][] loadData(Sheet sheet, int values) {
		return loadData(sheet, values, 1);
	}

	private static String[][] loadData(Sheet sheet, int values, int startRowIndex) {
		List<String[]> data = new ArrayList<String[]>();
		Row row;
		Cell cell;
		int rows;
		if (startRowIndex > 1) {
			//We want to retrieve values only from one row!
			rows=startRowIndex+1;
		} else {
			// Calculate number of rows
			rows = sheet.getPhysicalNumberOfRows();
		}
//		logger.info("The total number of rows is " + rows);

		int cols = 0; // Number of columns
		int tmp = 0;

		for (int i = 0; i < 10 || i < rows; i++) {
			row = sheet.getRow(i);
			if (row != null) {
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols) cols = tmp;

			}
		}

		// now we have rows and columns
		// r1=1 excludes metadata
		for (int rowIndex = startRowIndex; rowIndex < rows; rowIndex++) {
			row = sheet.getRow(rowIndex);
			if (row != null) {
				String[] cells = new String[values];
				for (int columnIndex = 0; columnIndex < values; columnIndex++) {

					cell = row.getCell((short) columnIndex);
					// cell not empty
					if (cell != null && !cell.toString().equals("")) {
						if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
							cells[columnIndex] = String.valueOf(new BigDecimal(cell.getNumericCellValue())).replace(".0", "");						
						else
							cells[columnIndex] = cell.getStringCellValue();
						// end of row

					} else {
						// if we have only one parameter in the file we should
						// skip this step
						if (values > 1) {
							cells[columnIndex] = "";
						}
					}
					if (columnIndex == (values - 1)) {
						if (isCellsNotEmpty(cells)) {
							data.add(cells);
						}
					}
				}
			}
		}

		return data.toArray(new String[0][0]);
	}

	private static boolean isCellsNotEmpty(String[] cells) {
		for (int i = 0; i < cells.length; i++) {
			String value = cells[i];
			if (value != null && value != "" && value != "[]") return true;
		}
		return false;
	}

	private static String getFileExtension(File workbook) throws FileNotFoundException {
		if (workbook.canRead()) {
			String extension = null;
			String extensionSeparator = ".";

			String fileName = workbook.getName();
			int separatorPosition = fileName.lastIndexOf(extensionSeparator);
			extension = fileName.substring(separatorPosition + 1).toLowerCase();
			return extension;
		} else {
			throw new FileNotFoundException("Please check if specified file exists");
		}
	}

	private static Workbook createWorkbook(File workbook) {
		String extension = null;
		try {
			extension = getFileExtension(workbook);
			if (extension.equals(XLSX)) {
//				logger.info(workbook.getName() + " have Office Open XML file format");
				return new XSSFWorkbook(workbook.getAbsolutePath());
			} else if (extension.equals(XLS)) {
//				logger.info(workbook.getName() + " have Binary Interchange file format");
				return new HSSFWorkbook(new FileInputStream(workbook));
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.warning("Please provide correct filename! " + e);
			e.printStackTrace();
			return null;
		}

	}
}
