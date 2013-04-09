package com.epam.training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage{
	
	@FindBy(xpath="//a[em[text()='Mail']]")
	WebElement mailButton;
	
	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public LoginPage openLoginPage() {
		mailButton.click();
		return new LoginPage(driver);
		
	}

}
