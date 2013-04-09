package com.epam.training.base;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;

import com.epam.training.core.Utils;

public class BaseLogin extends BaseTest {
	
	@BeforeMethod
	public void beforeTestLogin() throws InterruptedException{
		
		driver.findElement(By.xpath("//a[em[text()='Mail']]")).click();
		driver.findElement(By.id("username")).sendKeys(Utils.getProperty("username"));
		driver.findElement(By.id("passwd")).sendKeys(Utils.getProperty("password"));
		driver.findElement(By.id(".save")).click();
		Thread.sleep(2000);
	}

}
