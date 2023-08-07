package school.redrover.model.users;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.DeletePage;
import school.redrover.model.MainPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.interfaces.IDescription;

import java.util.List;

public class UserPage extends BaseSubmenuPage<UserPage> implements IDescription<UserPage> {

    @FindBy(xpath = "//div[contains(text(), 'Jenkins User ID:')]")
    private WebElement actualNameUser;

    @FindBy(className = "task")
    private List<WebElement> tasks;

    @FindBy(css = "[href$='/configure']")
    private WebElement configureSideMenu;

    public UserPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Manage Users";
    }

    @Step("Click 'Delete User' button from UserPage")
    public DeletePage<MainPage> clickDeleteUserBtnFromUserPage(String newUserName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/user/" + newUserName + "/delete']"))).click();

        return new DeletePage(new MainPage(getDriver()));
    }

    @Step("Get User name  '{USER_NAME}' ")
    public String getActualNameUser() {
        return getWait2().until(ExpectedConditions.visibilityOf(actualNameUser)).getText();
    }

    @Step("Get 'Jenkins User ID:' text is not displayed!' ")
    public boolean isUserPageAvailable() {
        return getWait2().until(ExpectedConditions.visibilityOf(actualNameUser)).getText().contains("Jenkins User ID:");
    }

    @Step("Select item from the side Menu ")
    public <SidePage extends BaseMainHeaderPage<?>> SidePage selectItemFromTheSideMenu(String itemName, SidePage sidePage) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/" + itemName + "')]"))).click();

        return sidePage;
    }

    @Step("Click 'Configure' button on SideMenu")
    public UserConfigPage clickConfigureSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(configureSideMenu)).click();

        return new UserConfigPage(new UserPage(getDriver()));
    }
}
