package school.redrover.model.jobs;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.ConsoleOutputPage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.model.base.BaseProjectPage;

public class FreestyleProjectPage extends BaseProjectPage<FreestyleProjectPage> {

    @FindBy(id = "description-link")
    private WebElement addDescriptionButton;

    @FindBy(xpath = "//*[@id='description']/form/div[2]/button")
    private WebElement saveDescriptionButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//a[@class = 'textarea-show-preview']")
    private WebElement previewButton;

    @FindBy(xpath = "//*[@class = 'textarea-preview']")
    private WebElement descriptionPreviewButton;

    @FindBy(css = "[href*='build?']")
    private WebElement buildNowButtonSideMenu;

    @FindBy(xpath = "//a[@tooltip='Success > Console Output']")
    private WebElement buildIconStatus;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public FreestyleProjectConfigPage clickConfigure() {
        setupClickConfigure();
        return new FreestyleProjectConfigPage(this);
    }

    public FreestyleProjectPage clickAddDescription() {
        addDescriptionButton.click();
        return this;
    }

    public FreestyleProjectPage clickSaveDescription() {
        saveDescriptionButton.click();
        return this;
    }

    public FreestyleProjectPage addDescription(String description) {
        descriptionTextArea.sendKeys(description);
        return this;
    }

    public FreestyleProjectPage removeOldDescriptionAndAddNew (String description) {
        descriptionTextArea.clear();
        descriptionTextArea.sendKeys(description);
        return this;
    }

    public FreestyleProjectPage clickPreviewButton () {
        previewButton.click();
        return this;
    }

    public String getPreviewDescription () {
        return descriptionPreviewButton.getText();
    }

    public FreestyleProjectPage clickBuildNowButtonSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildNowButtonSideMenu)).click();
        return this;
    }

    public ConsoleOutputPage clickBuildIconStatus() {
        getWait5().until(ExpectedConditions.visibilityOf(buildIconStatus));
        getWait5().until(ExpectedConditions.elementToBeClickable(buildIconStatus)).click();
        return new ConsoleOutputPage(getDriver());
    }
}
