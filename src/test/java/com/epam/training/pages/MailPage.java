package com.epam.training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MailPage extends BasePage {

	@FindBy(id="main-btn-new")
	private WebElement newMailButton;
	
	@FindBy(id="to-field")
	private WebElement toField;
	
	@FindBy(id="subject-field")
	private WebElement subjectField;
	
	@FindBy(xpath="//div/span[@id='emoticon']//a[@title='Emoticons']")
	private WebElement emoticonsButton;
	
	@FindBy(xpath="//li[contains(@title, 'happy')]/a")
	private WebElement happySmileButton;
	
	@FindBy(id="btn-send")
	private WebElement sendButton;
	
	public MailPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void composeMail() {
		newMailButton.click();
	}

	public void enterRecipient(String recipient) {
		toField.sendKeys(recipient);
				
	}

	public void enterSubject(String subject) {
		subjectField.sendKeys(subject);
	}

	public void enterSmile() {
		emoticonsButton.click();
		happySmileButton.click();
	}

	public void send() {
		sendButton.click();
	}

}
