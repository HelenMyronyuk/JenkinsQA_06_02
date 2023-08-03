package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class SearchPage extends BaseMainHeaderPage<SearchPage> {

    @FindBy(xpath = "//div[@class='error']")
    private WebElement error;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorText() {
        return getWait2().until(ExpectedConditions.visibilityOf(error)).getText();
    }
}
