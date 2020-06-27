/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etsy.engine;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

/**
 *
 * @author Tejas Shah
 */
public class WebOperation {

    public WebDriver driver;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    public WebOperation(WebDriver driver, ExtentHtmlReporter htmlReporter, ExtentReports extent, ExtentTest test) {
        this.driver = driver;
        this.htmlReporter = htmlReporter;
        this.extent = extent;
        this.test = test;

    }
    
   

    //public void startExecution(WebDriver driver, String action, String locatorName, String locatorValue, String value) {
    public void perform(Properties prop, String operation, String objectName, String objectType, String value) throws Exception {

        switch (operation.toUpperCase()) {
            case "CLICK":  
                
//Perform click
                driver.findElement(this.getObject(prop, objectName, objectType)).click();
                    
//Log in extent report if all steps for click happened properly
                 test.log(Status.INFO, objectName+" Was Clicked");
                break;

            case "SENDKEYS": 
                
//Set text on control              
                driver.findElement(this.getObject(prop, objectName, objectType)).sendKeys(value);
                
//Log in extent report if all steps for sendkeys happened properly
                test.log(Status.INFO, value+" Was Entered For "+objectName);
                break;

            case "GOTOURL":
 
//Get url of application              
                driver.get(prop.getProperty(value));
                
//Log in extent report if all steps for opening url happened properly
                test.log(Status.INFO, "URL Has Passed");
                break;

            case "SELECT":
                Select dropDown = new Select(driver.findElement(this.getObject(prop, objectName, objectType)));
                driver.findElement(this.getObject(prop, objectName, objectType)).click();
                Thread.sleep(1000);
                dropDown.selectByVisibleText(value);
                       
//Log in extent report if all steps for selecting value happened properly
                test.log(Status.INFO, value+" Has Been Selected");
                break;

            case "WAIT":
                Thread.sleep(2000);
                break;

//Check If Proper Page Is Loded
            case "CHECKURL":
                String currentURL = driver.getCurrentUrl();
                boolean CHECKURL = currentURL.equals(value);
                if (!CHECKURL) {
                    Assert.fail();
                    
//Log in extent report if all steps for checking url did not happened properly
                    test.log(Status.INFO, "Incorrect Page Loaded: " + currentURL);
                } else{
                    
//Log in extent report if all steps for checking url happened properly
                    test.log(Status.INFO, "Correct Page Was Loaded");
                }
                break;

//Check If Proper Page Is Loded
            case "CHECKTEXT":
                String getText = driver.findElement(this.getObject(prop, objectName, objectType)).getText();
                boolean CHECKTEXT = getText.equals(value);
                if (!CHECKTEXT) {
                    Assert.fail();
                    //Log in extent report if all steps for checking message did not happened properly
                    test.log(Status.INFO, "Incorrect Message Displayed: " + getText);
                } else{
                    //Log in extent report if all steps for checking a message happened properly
                    test.log(Status.INFO, "Correct Message Is Displayed");
                }
                break;

//Check If Proper Page Is Loded
            case "CHECKOBJECT":

                boolean checkObject = driver.findElement(this.getObject(prop, objectName, objectType)).isDisplayed();
                if (!checkObject) {
                    Assert.fail();
                    //Log in extent report if all steps for checking object did not happened properly
                    test.log(Status.INFO, "Object On Present: " + checkObject);
                }else{
                    //Log in extent report if all steps for checking object happened properly
                    test.log(Status.INFO, "Object Is properly Displayed");
                }
                break;

            default:
                break;
        }
    }

    private By getObject(Properties prop, String objectName, String objectType) throws Exception {

//Find by xpath
        if (objectType.equalsIgnoreCase("XPATH")) {

// Gets valure from object repositery base on oject name
            return By.xpath(prop.getProperty(objectName));
        }

//Find by id
        if (objectType.equalsIgnoreCase("ID")) {  

// Gets valure from object repositery base on oject name
            return By.id(objectName);
        } 

//find by class
        else if (objectType.equalsIgnoreCase("CLASSNAME")) {
            
// Gets valure from object repositery base on oject name
            return By.className(prop.getProperty(objectName));

        } 
        
//find by name
        else if (objectType.equalsIgnoreCase("NAME")) {
    
// Gets valure from object repositery base on oject name
            return By.name(prop.getProperty(objectName));

        }
        
//Find by css
        else if (objectType.equalsIgnoreCase("CSS")) {
            
// Gets valure from object repositery base on oject name
            return By.cssSelector(prop.getProperty(objectName));

        } 

//find by link
        else if (objectType.equalsIgnoreCase("LINK")) {
    
// Gets valure from object repositery base on oject name
            return By.linkText(prop.getProperty(objectName));

        } 

//find by partial link
        else if (objectType.equalsIgnoreCase("PARTIALLINK")) {    
            
// Gets valure from object repositery base on oject name
            return By.partialLinkText(prop.getProperty(objectName));

        } else {
            
// If Wrong Object Type Is Present 
            throw new Exception("Wrong object type");
        }
    }
    
    
}
