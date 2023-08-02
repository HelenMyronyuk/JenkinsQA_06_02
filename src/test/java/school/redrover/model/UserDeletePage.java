package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class UserDeletePage extends BaseMainHeaderPage<UserDeletePage> {

    @FindBy(name = "Submit")
    private WebElement yesButton;

    public UserDeletePage(WebDriver driver) {
        super(driver);
    }

    public MainPage clickOnYesButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(yesButton)).click();

        return new MainPage(getDriver());
    }
}
