package com.epam.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

	@FindBy(id="username")
	private WebElement userNameField;
	
	@FindBy(id="passwd")
	private WebElement passwordField;
	
	@FindBy(id=".save")
	private WebElement saveButton;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public MailPage login(String login, String password) {
		userNameField.sendKeys(login);
		passwordField.sendKeys(password);
		saveButton.click();
		return new MailPage(driver);
	}

}
