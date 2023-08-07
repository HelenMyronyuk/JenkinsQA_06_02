package school.redrover.model.jobsSidemenu;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class FullStageViewPage extends BaseMainHeaderPage<FullStageViewPage> {
    @FindBy(xpath = "//h2")
    private WebElement header;

    public FullStageViewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Heading text")
    @Override
    public String getPageHeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(header)).getText();
    }

}
