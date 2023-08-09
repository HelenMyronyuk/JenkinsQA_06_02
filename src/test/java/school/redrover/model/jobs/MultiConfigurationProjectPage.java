package school.redrover.model.jobs;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.jobsConfig.MultiConfigurationProjectConfigPage;
import school.redrover.model.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage<MultiConfigurationProjectPage> {

    @FindBy(xpath = "//div[@id='matrix']//span[@class='build-status-icon__outer']/child::*")
    private WebElement jobBuildStatus;

    @FindBy(xpath = "//div[@class='build-icon']")
    private WebElement lastBuildIcon;

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the 'Configure' button on side menu")
    @Override
    public MultiConfigurationProjectConfigPage clickConfigure() {
        setupClickConfigure();

        return new MultiConfigurationProjectConfigPage(this);
    }

    @Step("Get Build Status of Job")
    public String getJobBuildStatus() {
        WebElement buildStatus = getWait5().until(ExpectedConditions.visibilityOf(jobBuildStatus));
        new Actions(getDriver()).moveToElement(buildStatus).perform();
        getWait5().until(ExpectedConditions.not(ExpectedConditions.attributeContains(buildStatus, "tooltip", "progress")));

        return buildStatus.getAttribute("tooltip");
    }

    @Step("Get 'true' if Build Icon is displayed on the present")
    public boolean isLastBuildIconDisplayed() {
        return getWait2().until(ExpectedConditions.visibilityOf(lastBuildIcon)).isDisplayed();
    }

    @Step("Make refresh page")
    public MultiConfigurationProjectPage refreshPage() {
        getDriver().navigate().refresh();

        return this;
    }
}

