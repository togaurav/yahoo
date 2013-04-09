package com.epam.training.base;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.epam.training.core.Utils;


public class BaseTest {

	protected WebDriver driver;
	
	@BeforeClass
	@Parameters({"timeout", "url"})
	public void setUp(int timeout, String url) throws IOException{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);//timeout
		driver.manage().window().maximize();
		driver.get(url);//url
	}
	
	@AfterClass
	public void tearDown() throws IOException{
		takeScreenshot();
		driver.quit();
	}
	
	protected FirefoxProfile generateFirefoxProfile() throws IOException {
        FirefoxProfile prf = new FirefoxProfile();
        File file = new File(".\\resources\\firebug-1.11.0.xpi");
        System.out.println(file.getAbsolutePath());
        prf.addExtension(file);
        prf.setPreference("extensions.firebug.currentVersion", "9.9.9");
        return (prf);
    }
	
	public void takeScreenshot() throws IOException{
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(".//output//screen.jpg"));
	}
}
