/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestNG;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import etsy.base.Base;
import etsy.engine.WebOperation;
import etsy.read.excel.ReadExcelFile;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author Tejas Shah
 */
public class HybridPreferenceTest {

    public WebDriver webdriver;
    public ReadExcelFile file;
    public WebOperation operation;
    public Sheet excelSheet;
    int rowCount;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    @BeforeTest
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Data\\chromedriver.exe");
        String reportName = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\Report\\"+reportName+".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Test(dataProvider = "hybridData")
    public void AccountTest(String fileLocation, String sheetName) throws IOException, Exception {

        file = new ReadExcelFile();
        Base object = new Base();
        Properties allObjects = object.getObjectRepository();

        excelSheet = file.readExcel(System.getProperty("user.dir") + "\\src\\"
                + "main\\java\\etsy\\excel\\files\\" + fileLocation, sheetName);

        rowCount = excelSheet.getLastRowNum();

        for (int i = 1; i < rowCount + 1; i++) {

            Row row = excelSheet.getRow(i);

            if (!row.getCell(0).toString().equalsIgnoreCase("NA")) {

                String testCaseName = row.getCell(0).toString();

                webdriver = new ChromeDriver();
                webdriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                test = extent.createTest(testCaseName);
                operation = new WebOperation(webdriver, htmlReporter, extent, test);
            }

//Perform operation with the help of WebObject.java
            operation.perform(allObjects, row.getCell(1).toString(), row.getCell(2).toString(),
                    row.getCell(3).toString(), row.getCell(4).toString());
        }

    }

    @DataProvider(name = "hybridData")
    public Object[][] getDataFromDataprovider() throws IOException {

        Object[][] object = null;

        file = new ReadExcelFile();

//Read keyword sheet
        excelSheet = file.readExcel(System.getProperty("user.dir") + "\\src\\"
                + "main\\java\\etsy\\excel\\files\\HybridData.xlsx", "PreferenceTest");

// Find number Of Rows
        rowCount = excelSheet.getLastRowNum();

        object = new Object[rowCount][2];

        for (int i = 0; i < rowCount; i++) {

//Loop over all the rows
            Row row = excelSheet.getRow(i + 1);

//Create a loop to print cell values in a row
            for (int j = 0; j < row.getLastCellNum(); j++) {

//Print excel data in console
                object[i][j] = row.getCell(j).toString();
            }
        }
        return object;
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Fail");
            String temp = Base.takeScreenshot(webdriver);
            test.fail(result.getName(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
            extent.flush();
            webdriver.quit();
        } else {
            test.pass("Test pass");
            extent.flush();
            webdriver.quit();
        }
    }
}
