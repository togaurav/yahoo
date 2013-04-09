package com.epam.training.tests;

import org.testng.annotations.Test;

import com.epam.training.base.BaseTest;
import com.epam.training.core.TestDataProvider;
import com.epam.training.core.Utils;
import com.epam.training.pages.HomePage;
import com.epam.training.pages.LoginPage;
import com.epam.training.pages.MailPage;

public class ComposeMailTest extends BaseTest {
	
	@Test/*(groups="smoke")*/
	public void composeMailTest() throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = homePage.openLoginPage();
		MailPage mailPage = loginPage.login(Utils.getProperty("username"), Utils.getProperty("password"));
		mailPage.composeMail();
		mailPage.enterRecipient(TestDataProvider.getTestData()[0][0]);
		mailPage.enterSubject(TestDataProvider.getTestData()[0][2]);
		mailPage.enterSmile();
		mailPage.send();
	}
}