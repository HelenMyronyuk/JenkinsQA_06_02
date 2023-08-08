package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class ConfigureSystemPage extends BaseSubmenuPage<ConfigureSystemPage> {

    @FindBy(xpath = "//div[text()='SMTP server']/following-sibling::div/input")
    private WebElement smtpServerFieldExtendedEmailNotifications;

    @FindBy(xpath = "//div[text()='SMTP Port']/following-sibling::div/input")
    private WebElement smtpPortFieldExtendedEmailNotifications;

    @FindBy(xpath = "//div[@class='setting-main help-sibling']//button[contains(@class, 'advancedButton')]")
    private WebElement advancedButtonExtendedEmailNotifications;

    @FindBy(css = "div.credentials-select-control button")
    private WebElement addButtonExtendedEmailNotifications;

    @FindBy(xpath = "//span[@title='Jenkins Credentials Provider']")
    private WebElement jenkinsCredentialProvider;

    @FindBy(css = "#credentialsDialog_c input[name='_.username']")
    private WebElement usernameInputFieldInAddCredentialsPopUpWindow;

    @FindBy(css = "#credentialsDialog_c input[name='_.password']")
    private WebElement passwordInputFieldInAddCredentialsPopUpWindow;

    @FindBy(css = "#credentialsDialog_c button#credentials-add-submit-button")
    private WebElement addButtonAddCredentialsPopUpWindow;

    @FindBy(xpath = "//div[@class='setting-main help-sibling']//span[text()='Use SSL']")
    private WebElement useSSLCheckboxExtendedEmailNotifications;

    @FindBy(xpath = "//div[@class='setting-main help-sibling']//input[@name='_.useSsl']")
    private WebElement useSSLCheckboxExtendedEmailVerification;

    @FindBy(xpath = "//button[contains(text(), 'Default Triggers')]")
    private WebElement defaultTriggersButton;

    @FindBy(xpath = "//div[@class='setting-main']/span/label[@class='attach-previous ']")
    private List<WebElement> defaultTriggersList;

    @FindBy(xpath = "//label[text()='Always']/../input")
    private WebElement defaultTriggerAlwaysVerification;

    @FindBy(xpath = "//label[text()='Always']")
    private WebElement defaultTriggerAlwaysCheckbox;

    @FindBy(xpath = "//label[text()='Success']/../input")
    private WebElement defaultTriggerSuccessVerification;

    @FindBy(xpath = "//label[text()='Success']")
    private WebElement defaultTriggerSuccessCheckbox;

    @FindBy(xpath = "//input[@class='jenkins-input validated  '][@name='_.smtpHost']")
    private WebElement smtpServerFieldEmailNotifications;

    @FindBy(xpath = "(//button[contains(@class, 'advancedButton')])[last()]")
    private WebElement advancedButtonEmailNotifications;

    @FindBy(xpath = "//label[text()='Use SMTP Authentication']")
    private WebElement useSMTPAuthenticationCheckbox;

    @FindBy(xpath = "//label[text()='Use SMTP Authentication']/../input")
    private WebElement useSMTPAuthenticationVerification;

    @FindBy(xpath = "//div[@nameref='cb15']//input[@name='_.username']")
    private WebElement userNameSMTPAuthentication;

    @FindBy(xpath = "//div[@nameref='cb15']//input[@name='_.password']")
    private WebElement passwordSMTPAuthentication;

    @FindBy(xpath = "//div[@class='jenkins-form-item tr has-help jenkins-form-item--tight']//label/span[text()='Use SSL']")
    private WebElement useSSLEmailNotificationsCheckbox;

    @FindBy(xpath = "//div[@class='jenkins-form-item tr has-help jenkins-form-item--tight']//input[@name='_.useSsl']")
    private WebElement useSSLEmailCheckboxVerification;

    @FindBy(xpath = "//div[@class='jenkins-form-item tr  has-help']//input[@name='_.smtpPort']")
    private WebElement smtpPortFieldEmailNotifications;

    @FindBy(xpath = "//label[text()='Test configuration by sending test e-mail']")
    private WebElement testConfigurationBySendingTestEmailCheckbox;

    @FindBy(xpath = "//input[@name='sendTestMailTo']")
    private WebElement testEmailRecipientInputField;

    @FindBy(xpath = "//button[text()='Test configuration']")
    private WebElement testConfigurationButton;

    @FindBy(xpath = "//select[@name='_.credentialsId']")
    private WebElement credentialsDropdown;

    @FindBy(xpath = "//div[@class='jenkins-validate-button__container__status']//div[@class='ok']")
    private WebElement testConfigurationMessage;

    @FindBy(xpath = "//div[text()='Content Token Reference']")
    private WebElement contentTokenReference;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    @FindBy(css = "#footer")
    private WebElement footer;


    public ConfigureSystemPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Configure System";
    }

    @Step("Enter an email into 'SMTP server' field for 'Extended Email Notifications' section")
    public ConfigureSystemPage inputSmtpServerFieldExtendedEmailNotifications(String smtpServer) {
        TestUtils.scrollToElementByJavaScript(this, smtpServerFieldExtendedEmailNotifications);
        getWait10().until(ExpectedConditions.visibilityOf(smtpServerFieldExtendedEmailNotifications)).clear();
        smtpServerFieldExtendedEmailNotifications.sendKeys(smtpServer);

        return this;
    }

    @Step("Enter a port number into 'SMTP port' field for 'Extended Email Notifications' section")
    public ConfigureSystemPage inputSmtpPortFieldExtendedEmailNotifications(String smtpPort) {
        getWait2().until(ExpectedConditions.visibilityOf(smtpPortFieldExtendedEmailNotifications)).clear();
        smtpPortFieldExtendedEmailNotifications.sendKeys(smtpPort);

        return this;
    }

    @Step("Click 'Advanced' button under 'Extended Email Notifications' section")
    public ConfigureSystemPage clickAdvancedButtonExtendedEmailNotification() {
        advancedButtonExtendedEmailNotifications.click();

        return this;
    }

    @Step("Click 'Add Credentials' button under 'Advanced' settings")
    public ConfigureSystemPage clickAddCredentialButton() {
        getWait10().until(ExpectedConditions.elementToBeClickable(addButtonExtendedEmailNotifications)).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(jenkinsCredentialProvider)).click();

        return this;
    }

    @Step("Select created credentials")
    public ConfigureSystemPage selectCreatedCredentials(String email) {
        credentialsDropdown.click();
        new Select(credentialsDropdown).selectByVisibleText(email + "/******");

        return this;
    }

    @Step("Enter a user name into 'Add Credentials' pop-up window")
    public ConfigureSystemPage inputUsernameIntoAddCredentialPopUpWindow(String username) {
        getWait5().until(ExpectedConditions.visibilityOf(usernameInputFieldInAddCredentialsPopUpWindow)).sendKeys(username);

        return this;
    }

    @Step("Enter a password into 'Add Credentials' pop-up window")
    public ConfigureSystemPage inputPasswordIntoAddCredentialPopUpWindow(String password) {
        getWait5().until(ExpectedConditions.visibilityOf(passwordInputFieldInAddCredentialsPopUpWindow)).sendKeys(password);

        return this;
    }

    @Step("Click 'Add' button under Add Credentials")
    public ConfigureSystemPage clickAddButtonAddCredentialPopUp() {
        TestUtils.scrollWithPauseByActions(this, addButtonAddCredentialsPopUpWindow, 100);
        getWait5().until(ExpectedConditions.elementToBeClickable(addButtonAddCredentialsPopUpWindow)).click();

        return this;
    }

    @Step("Check 'Use SSL' checkbox")
    public ConfigureSystemPage checkUseSSLCheckbox() {
        TestUtils.scrollWithPauseByActions(this, useSSLCheckboxExtendedEmailNotifications, 100);
        getWait5().until(ExpectedConditions.visibilityOf(useSSLCheckboxExtendedEmailNotifications)).click();

        return this;
    }

    @Step("Click the 'Default Triggers' button")
    public ConfigureSystemPage clickDefaultTriggersButton() {
        TestUtils.scrollToElementByJavaScript(this, contentTokenReference);
        defaultTriggersButton.click();

        return this;
    }

    @Step("Check 'Always' checkbox under Default Triggers section")
    public ConfigureSystemPage checkAlwaysDefaultTriggers() {
        for (WebElement trigger : defaultTriggersList) {
            if (trigger.getText().equals("Always")) {
                trigger.click();
            }
        }

        return this;
    }

    @Step("Check 'Success' checkbox under Default Triggers section")
    public ConfigureSystemPage checkSuccessDefaultTriggers() {
        for (WebElement trigger : defaultTriggersList) {
            if (trigger.getText().equals("Success")) {
                trigger.click();
            }
        }

        return this;
    }

    @Step("Enter an email into 'SMTP server' field for 'Email Notifications' section")
    public ConfigureSystemPage inputSmtpServerFieldEmailNotifications(String smtpServer) {
        TestUtils.scrollToElementByJavaScript(this, smtpServerFieldEmailNotifications);
        getWait5().until(ExpectedConditions.visibilityOf(smtpServerFieldEmailNotifications)).clear();
        smtpServerFieldEmailNotifications.sendKeys(smtpServer);

        return this;
    }

    @Step("Click 'Advanced' button under 'Email Notifications' section")
    public ConfigureSystemPage clickAdvancedButtonEmailNotification() {
        advancedButtonEmailNotifications.click();

        return this;
    }

    @Step("Click 'Use SMTP Authentication' checkbox")
    public ConfigureSystemPage clickUseSMTPAuthenticationCheckbox() {
        getWait2().until(ExpectedConditions.elementToBeClickable(useSMTPAuthenticationCheckbox)).click();

        return this;
    }

    @Step("Enter a user name and password for 'SMTP Authentication'")
    public ConfigureSystemPage inputUserNameAndPasswordSMTPAuthentication(String username, String password) {
        getWait5().until(ExpectedConditions.visibilityOf(userNameSMTPAuthentication)).sendKeys(username);
        passwordSMTPAuthentication.sendKeys(password);

        return this;
    }

    @Step("Check 'Use SSl' checkbox for 'Email Notifications' section")
    public ConfigureSystemPage checkUseSSLCheckboxEmailNotifications() {
        TestUtils.scrollToElementByJavaScript(this, useSSLEmailNotificationsCheckbox);
        getWait5().until(ExpectedConditions.elementToBeClickable(useSSLEmailNotificationsCheckbox)).click();

        return this;
    }

    @Step("Enter a port number into 'SMTP Port' under 'Email Notifications' section")
    public ConfigureSystemPage inputSmtpPortEmailNotificationsField(String port) {
        smtpPortFieldEmailNotifications.clear();
        smtpPortFieldEmailNotifications.sendKeys(port);

        return this;
    }

    @Step("Check 'Test Configuration By Sending Test Email' checkbox")
    public ConfigureSystemPage checkTestConfigurationBySendingTestEmailCheckbox() {
        testConfigurationBySendingTestEmailCheckbox.click();

        return this;
    }

    @Step("Enter an email into 'Test Email Recipient' field")
    public ConfigureSystemPage inputEmailIntoTestEmailRecipientInputField(String email) {
        getWait2().until(ExpectedConditions.visibilityOf(testEmailRecipientInputField)).clear();
        testEmailRecipientInputField.sendKeys(email);

        return this;
    }

    @Step("Click 'Test Configuration' button")
    public ConfigureSystemPage clickTestConfigurationButton() {
        TestUtils.scrollToElementByJavaScript(this, footer);
        testConfigurationButton.click();

        return this;
    }

    @Step("Get the configuration message text")
    public String getConfigurationMessageText() {
        return getWait2().until(ExpectedConditions.visibilityOf(testConfigurationMessage)).getText();
    }

    @Step("Click 'Save' button")
    public MainPage clickSaveButton() {
        TestUtils.scrollToElementByJavaScript(this, saveButton);
        saveButton.click();

        return new MainPage(getDriver());
    }

    @Step("Uncheck 'Use SSL' checkbox under 'Extended Email Notifications'")
    public ConfigureSystemPage unCheckUseSSLCheckboxExtendedEmailNotifications() {
        TestUtils.scrollWithPauseByActions(this, useSSLCheckboxExtendedEmailNotifications, 100);
        if (useSSLCheckboxExtendedEmailVerification.isSelected()) {
            useSSLCheckboxExtendedEmailNotifications.click();
        }

        return this;
    }

    @Step("Uncheck 'Default Trigger Always' checkbox")
    public ConfigureSystemPage unCheckDefaultTriggerAlwaysCheckbox() {
        if (defaultTriggerAlwaysVerification.isSelected()) {
            defaultTriggerAlwaysCheckbox.click();
        }

        return this;
    }

    @Step("Uncheck 'Default Trigger Success' checkbox")
    public ConfigureSystemPage unCheckDefaultTriggerSuccessCheckbox() {
        if (defaultTriggerSuccessVerification.isSelected()) {
            defaultTriggerSuccessCheckbox.click();
        }

        return this;
    }

    @Step("Uncheck 'SMTP Authentication' checkbox")
    public ConfigureSystemPage unCheckSMTPAuthenticationCheckbox() {
        if (useSMTPAuthenticationVerification.isSelected()) {
            useSMTPAuthenticationCheckbox.click();
        }

        return this;
    }

    @Step("Uncheck 'Use SSL' checkbox under 'Email Notifications' section")
    public ConfigureSystemPage unCheckUseSSLCheckboxEmailNotifications() {
        if (useSSLEmailCheckboxVerification.isSelected()) {
            useSSLEmailNotificationsCheckbox.click();
        }

        return this;
    }

    @Step("Verifying that 'SMTP Port' field is displayed")
    public boolean isSmtpServerFieldExtendedEmailNotificationsEmpty() {
        return smtpPortFieldExtendedEmailNotifications.getText().isEmpty();
    }

    @Step("Verifying that 'SMTP Port' field under 'Extended Email Notifications' section is back to original")
    public boolean isSmtpPortFieldExtendedEmailNotificationsBackToOriginal() {
        return smtpPortFieldExtendedEmailNotifications.getAttribute("value").equals("25");
    }

    @Step("Verifying that 'Use SSL' checkbox is checked")
    public boolean isUseSSLCheckboxChecked() {
        return useSSLCheckboxExtendedEmailVerification.isSelected();
    }

    @Step("Verifying that 'Triggers Always' is checked")
    public boolean isTriggersAlwaysChecked() {
        return defaultTriggerAlwaysVerification.isSelected();
    }

    @Step("Verifying that 'SMTP Port' field is displayed")
    public boolean isTriggersSuccessChecked() {
        return defaultTriggerSuccessVerification.isSelected();
    }

    @Step("Verifying that 'SMTP Server' field under 'Email Notifications' section is empty")
    public boolean isSmtpServerFieldEmailNotificationsEmpty() {
        return smtpServerFieldEmailNotifications.getText().isEmpty();
    }

    @Step("Verifying that 'SMTP Authentication' checkbox is checked")
    public boolean isUseSMTPAuthenticationCheckboxChecked() {
        return useSMTPAuthenticationVerification.isSelected();
    }

    @Step("Verifying that 'Use SSL' checkbox under 'Email Notifications' section is checked")
    public boolean isUseSSLCheckboxEmailNotificationsChecked() {
        return useSSLEmailCheckboxVerification.isSelected();
    }

    @Step("Verifying that 'SMTP Port' field under 'Email Notifications' section is back to original")
    public boolean isSmtpPortFieldEmailNotificationsBackToOriginal() {
        return smtpPortFieldEmailNotifications.getAttribute("value").equals("25");
    }
}
