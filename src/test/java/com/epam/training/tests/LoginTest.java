package com.epam.training.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.epam.training.base.BaseTest;
import com.epam.training.core.Utils;

public class LoginTest extends BaseTest {
	
	@Test/*(groups="smoke")*/
	public void testLogin() throws Exception{		
		driver.findElement(By.xpath("//a[em[text()='Mail']]")).click();
		driver.findElement(By.id("username")).sendKeys(Utils.getProperty("username"));
		driver.findElement(By.id("passwd")).sendKeys(Utils.getProperty("password"));
		driver.findElement(By.id(".save")).click();
	}
}