/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etsy.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/*
 * @author Tejas Shah
 */
public class Base {

    public Properties prop;

    public Properties getObjectRepository() {

        prop = new Properties();

        try {
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\etsy\\config\\object.properties");

            prop.load(ip);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    public static String takeScreenshot(WebDriver driver){
    
    TakesScreenshot ts = (TakesScreenshot) driver;
    File src = ts.getScreenshotAs(OutputType.FILE);
    String path = System.getProperty("user.dir") + "\\Screenshots\\" + System.currentTimeMillis() + ".png";
    
    File destination = new File(path);
    
    try {
    FileUtils.copyFile(src, destination);
    } catch (IOException ex) {
    System.out.println("Capture Failed" +ex.getMessage());
    }
    
    return path;
    }
}
