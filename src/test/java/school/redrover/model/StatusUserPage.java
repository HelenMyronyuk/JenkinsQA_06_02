package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class StatusUserPage extends BaseMainHeaderPage<StatusUserPage> implements IDescription<StatusUserPage>{

    @FindBy(css = "[href$='/configure']")
    private WebElement configureSideMenu;

    public StatusUserPage(WebDriver driver) {
        super(driver);
    }

    public UserConfigPage clickConfigureSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(configureSideMenu)).click();

        return new UserConfigPage(new StatusUserPage(getDriver()));
    }
}
