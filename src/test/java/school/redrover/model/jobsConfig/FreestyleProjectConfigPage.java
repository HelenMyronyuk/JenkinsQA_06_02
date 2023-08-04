package school.redrover.model.jobsConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.baseConfig.BaseConfigFreestyleAndMulticonfigProjectsPage;
import school.redrover.model.jobs.FreestyleProjectPage;

public class FreestyleProjectConfigPage extends BaseConfigFreestyleAndMulticonfigProjectsPage<FreestyleProjectConfigPage, FreestyleProjectPage> {

    @FindBy(xpath = "//input[@name='blockBuildWhenUpstreamBuilding']")
    private WebElement trueBlockBuildWhenUpstreamProjectIsBuilding;

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement displayNameField;

    public FreestyleProjectConfigPage(FreestyleProjectPage freestyleProjectPage) {
        super(freestyleProjectPage);
    }

    @Step("Get a mark from the option 'Block build when upstream project is building' in the 'General' " +
            "section on the Configuration page")
    public boolean getTrueBlockBuildWhenUpstreamProjectIsBuilding() {
        return trueBlockBuildWhenUpstreamProjectIsBuilding.isSelected();
    }

    @Step("Input the name '{displayName}' into the 'Display name' field")
    public FreestyleProjectConfigPage setDisplayName(String displayName) {
        displayNameField.sendKeys(displayName);

        return this;
    }
}
