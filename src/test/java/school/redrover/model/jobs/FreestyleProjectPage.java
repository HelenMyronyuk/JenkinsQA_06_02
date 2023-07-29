package school.redrover.model.jobs;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.ConsoleOutputPage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.model.base.BaseProjectPage;

public class FreestyleProjectPage extends BaseProjectPage<FreestyleProjectPage> {

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
