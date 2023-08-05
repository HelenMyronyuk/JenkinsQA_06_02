package school.redrover.model.builds;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseSubmenuPage;

public class EditBuildInformationPage extends BaseSubmenuPage<EditBuildInformationPage> {

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement buildDescriptionTextArea;

    @FindBy(xpath = "//*[@name = 'Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//input[@name='displayName']")
    private WebElement displayNameField;

    @FindBy(xpath = "//a[text()='Preview']")
    private WebElement previewButton;

    @FindBy(xpath = "//div[@class=\"textarea-preview\"]")
    private WebElement previewTextarea;

    @FindBy(xpath = "//li[contains(text(),'Edit Build Information')]")
    private WebElement titleEditFromBreadCrumb;


    public EditBuildInformationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "configure";
    }

    @Step("Click on the 'Save' button")
    public BuildPage clickSaveButton() {
        saveButton.click();

        return new BuildPage(getDriver());
    }

    @Step("Input the display name '{displayName} in the 'Display Name' field" +
            " on the Edit Build Information page")
    public EditBuildInformationPage enterDisplayName(String displayName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(displayNameField))
                .sendKeys(displayName);

        return this;
    }

    @Step("Input the description '{description} in the 'Description' field" +
            " on the Edit Build Information page")
    public EditBuildInformationPage enterDescription(String description) {
        getWait5().until(ExpectedConditions.visibilityOf(buildDescriptionTextArea)).clear();
        buildDescriptionTextArea.sendKeys(description);

        return this;
    }

    @Step("Click on the 'Preview' button on the Edit Build Information page")
    public EditBuildInformationPage clickPreviewButton() {
        previewButton.click();

        return this;
    }

    @Step("Get Preview text on the Edit Build Information page")
    public String getPreviewText() {
        return getWait5().until(ExpectedConditions.visibilityOf(previewTextarea)).getText();
    }

    @Step("Get Heading text on the Edit Build Information page")
    public String getHeaderText(){
        return getWait5().until(ExpectedConditions.visibilityOf(titleEditFromBreadCrumb)).getText();
    }

    @Step("Edit and input new display name '{displayName} in the 'Display Name' field" +
            " on the Edit Build Information page")
    public EditBuildInformationPage editDisplayName(String displayName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(displayNameField)).clear();
        displayNameField.sendKeys(displayName);

        return this;
    }
}
