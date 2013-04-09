package com.epam.training.core;


import java.io.File;

import org.testng.annotations.Test;

/**
 * Warning!!!
 * This file is performs actions ONLY with "testdata File", that means this class MUST NOT contain ACTIONS with OTHER FILE  
 * Sheets names and indexes
 * Version info 0
 * Test data -- 1
 */
public class TestDataProvider {
	
	private static File workbook;
//	public TestDataProvider(){
//		String testDataFileProperty = "./src/test/resources/testdata.xlsx";
//		workbook = new File(testDataFileProperty);
//		System.out.println(testDataFileProperty);
//	}

	/**
	 * Test Data -- 1
	 * @param startRowIndex
	 * @return
	 */
	public static String[][] getTestData (int startRowIndex){	
		String testDataFileProperty = "./src/test/resources/testdata.xlsx";
		workbook = new File(testDataFileProperty);
//		System.out.println(testDataFileProperty);
		return BaseExcelDataProvider.loadData(workbook, 0, 4, startRowIndex);
	}
	
	public static String[][] getTestData (){
		return getTestData(1);
	}
		
	@Test
	public static void testGetTestData() {
		String[][] result = TestDataProvider.getTestData(1);
		System.out.println(result[0][0]);
		System.out.println(result[0][1]);
		System.out.println(result[0][2]);
		System.out.println("==========================================");
	}
}