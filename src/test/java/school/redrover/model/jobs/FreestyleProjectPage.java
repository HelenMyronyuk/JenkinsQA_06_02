package school.redrover.model.jobs;

import org.openqa.selenium.*;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.model.base.BaseProjectPage;

public class FreestyleProjectPage extends BaseProjectPage<FreestyleProjectPage> {

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public FreestyleProjectConfigPage clickConfigure() {
        setupClickConfigure();

        return new FreestyleProjectConfigPage(this);
    }
}
