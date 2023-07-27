package school.redrover.model.jobsconfig;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.baseConfig.BaseConfigWithoutPipelineProjectPage;
import school.redrover.model.jobs.FreestyleProjectPage;

public class FreestyleProjectConfigPage extends BaseConfigWithoutPipelineProjectPage<FreestyleProjectConfigPage, FreestyleProjectPage> {



    @FindBy(xpath = "//input[@name='blockBuildWhenUpstreamBuilding']")
    private WebElement trueBlockBuildWhenUpstreamProjectIsBuilding;

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement displayNameField;

    public FreestyleProjectConfigPage(FreestyleProjectPage freestyleProjectPage) {
        super(freestyleProjectPage);
    }



    public boolean getTrueBlockBuildWhenUpstreamProjectIsBuilding() {
        return trueBlockBuildWhenUpstreamProjectIsBuilding.isSelected();
    }

    public FreestyleProjectConfigPage setDisplayName(String displayName) {
        displayNameField.sendKeys(displayName);
        return this;
    }
}
