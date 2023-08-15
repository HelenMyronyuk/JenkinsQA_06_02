package school.redrover.model.externalPages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class SearchBoxPage extends BaseMainHeaderPage<SearchBoxPage> {

    @FindBy(xpath = "//*[@id='search-box']")
    private WebElement pageHeaderText;

    public SearchBoxPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Title text")
    public String getTitleText() {
        return getWait2().until(ExpectedConditions.visibilityOf(pageHeaderText)).getText();
    }

}
