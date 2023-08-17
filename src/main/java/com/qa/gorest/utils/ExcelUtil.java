package com.qa.gorest.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class ExcelUtil {
	/* 1: We will create a private final variable to read the path of the excel file from.
	 * 2: We will create a method to read the excel file. And we will dynamically pass a parameter to the method to read
	 *    any given excel sheet--> [String sheetName]. not a specific one, (that will make it hard coded)
	 * 3: We have to make connection with that particular sheet. how can we make that connection?
	 * 	For creating the connection, we will use FileInputStream class object. Give the object the path of the excel sheet 
	 * 	as parameter and surround it with a try-catch block if any kind of exception happens.
	 * 4: There is one more class in Apache POI named as "workbookFactory" use it with create() method and pass the File
	 * 	   InputStream object as parameter to it, and surround with catch block to capture any possible exception.
	 * 	The return type of create method is workbook. So, I Will create a private static workbook variable for it. and 
	 * 	store the create method withing a book.
	 */

	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/APITestData.xlsx";
	private static Workbook book;
	private static Sheet sheet; // while importing with Apache poi, always remember to import ss.usermodel ones.
	
	public static Object[][] getTestData(String sheetName) {
		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);
			data = new Object [sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for(int i = 0; i<sheet.getLastRowNum(); i++) {
				for(int j = 0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return data;
	}
}
