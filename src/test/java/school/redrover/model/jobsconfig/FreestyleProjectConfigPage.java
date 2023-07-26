package school.redrover.model.jobsconfig;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.base.BaseConfigProjectsPage;
import school.redrover.runner.TestUtils;

public class FreestyleProjectConfigPage extends BaseConfigProjectsPage<FreestyleProjectConfigPage, FreestyleProjectPage> {

    @FindBy(xpath = "(//button[contains(text(),'Advanced')])[3]")
    private WebElement advancedDropdownMenu;

    @FindBy(xpath = "//input[@name='blockBuildWhenUpstreamBuilding']")
    private WebElement trueBlockBuildWhenUpstreamProjectIsBuilding;

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement displayNameField;

    public FreestyleProjectConfigPage(FreestyleProjectPage freestyleProjectPage) {
        super(freestyleProjectPage);
    }

    public FreestyleProjectConfigPage clickAdvancedDropdownMenu() {
        getWait10().until(ExpectedConditions.elementToBeClickable(advancedDropdownMenu)).click();
        TestUtils.scrollToElementByJavaScript(this, advancedDropdownMenu);
        return this;
    }

    public boolean getTrueBlockBuildWhenUpstreamProjectIsBuilding() {
        return trueBlockBuildWhenUpstreamProjectIsBuilding.isSelected();
    }

    public FreestyleProjectConfigPage setDisplayName(String displayName) {
        displayNameField.sendKeys(displayName);
        return this;
    }
}
