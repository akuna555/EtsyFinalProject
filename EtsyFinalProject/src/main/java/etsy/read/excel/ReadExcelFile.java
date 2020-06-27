/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etsy.read.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Tejas Shah
 */
public class ReadExcelFile {
    
    public Sheet readExcel(String filePath, String sheetName) throws IOException{
    

//Create a object of File class to open xlsx file
    File file = new File(filePath);
    

//Create an object of FileInputStream class to read excel file
    FileInputStream inputStream = new FileInputStream(file);
    
    //Workbook excelWorkbook = null;
    
    //create object of XSSFWorkbook class
    Workbook excelWorkbook = new XSSFWorkbook(inputStream);
    
    //Read sheet inside the workbook by its name
    Sheet excelSheet = excelWorkbook.getSheet(sheetName);
    
    return excelSheet;
    
    }
}
