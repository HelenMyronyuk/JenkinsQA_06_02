package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.users.ManageUsersPage;

public class SearchPage extends BaseMainHeaderPage<SearchPage> {

    @FindBy(xpath = "//div[@class='error']")
    private WebElement error;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorText() {
        return getWait2().until(ExpectedConditions.visibilityOf(error)).getText();
    }

    public FreestyleProjectPage clickSearchResult(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='?q=" + name + "']"))).click();

        return new FreestyleProjectPage(getDriver());
    }
}
