package school.redrover.model.jobsSidemenu;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class ChangesPage extends BaseMainHeaderPage<ChangesPage> {

    @FindBy(xpath = "//div[@id='main-panel']")
    private WebElement mainPanel;

    public ChangesPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Heading text from Changes page")
    public String getTextOfPage() {
        return getWait5().until(ExpectedConditions.visibilityOf(mainPanel)).getText();
    }
}
