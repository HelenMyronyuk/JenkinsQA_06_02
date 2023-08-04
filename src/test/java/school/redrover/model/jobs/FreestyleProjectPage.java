package school.redrover.model.jobs;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.model.base.BaseProjectPage;

public class FreestyleProjectPage extends BaseProjectPage<FreestyleProjectPage> {

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the 'Configure' button on side menu")
    @Override
    public FreestyleProjectConfigPage clickConfigure() {
        setupClickConfigure();

        return new FreestyleProjectConfigPage(this);
    }
}
