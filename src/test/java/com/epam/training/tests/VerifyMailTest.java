package com.epam.training.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.training.base.BaseLogin;
import com.epam.training.base.BaseTest;
import com.epam.training.core.TestDataProvider;

public class VerifyMailTest extends BaseLogin {
	
	@Test/*(groups="smoke")*/
	public void verifyMailTest() throws InterruptedException{
//		driver.findElement(By.xpath("//a[em[text()='Mail']]")).click();
//		driver.findElement(By.id("username")).sendKeys("fortrainingepam");
//		driver.findElement(By.id("passwd")).sendKeys("qweasdzxc");
//		driver.findElement(By.id(".save")).click();
//		Thread.sleep(2000);
		Assert.assertEquals(driver.findElement(By.className("from")).getText(), TestDataProvider.getTestData()[0][1]/*"Training EPAM"*/, "Incorrect sender");
		Assert.assertEquals(driver.findElement(By.className("subj")).getText(), TestDataProvider.getTestData()[0][2]/*"Test mail"*/, "Incorrect title");
		driver.findElement(By.xpath("//span[@id='btn-ml-cbox']//input")).click();
		driver.findElement(By.xpath("//div[@id='toolbar']//a[text()='Delete']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='empty-folder-msg']/p")).getText(), TestDataProvider.getTestData()[0][3]/*"There are no emails in your Inbox folder."*/, "Email is not empty");
	}
}
