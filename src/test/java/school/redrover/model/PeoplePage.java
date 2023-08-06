package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.users.UserPage;

import java.util.ArrayList;
import java.util.List;

public class PeoplePage extends BaseMainHeaderPage<PeoplePage> {

    @FindBy(css = ".task-link-wrapper>a[href$='newJob']")
    private WebElement newItem;

    @FindBy(xpath = "//a[@class='sortheader'][contains(text(), 'User ID')]")
    private WebElement userIDButton;

    @FindBy(xpath = "//a[@class='sortheader'][contains(text(), 'User ID')]/span")
    private WebElement userIDButtonArrow;

    @FindBy(xpath = "//a[@class='sortheader'][contains(text(), 'Name')]")
    private WebElement nameButton;

    @FindBy(xpath = "//h1")
    private WebElement pageTitle;

    @FindBy(xpath = "//table[@id = 'people']/tbody")
    private WebElement user;

    @FindBy(xpath = "//div[@class='jenkins-icon-size']//ol/li")
    private List<WebElement> iconButtons;

    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    public UserPage clickUserName(String newUserName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath("//a[@href='/user/" + newUserName + "/']")))).click();

        return new UserPage(getDriver());
    }
    @Step("Check if User '{newUserName}' was deleted")
    public boolean checkIfUserWasDeleted(String newUserName) {
        return ExpectedConditions.not(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//a[@href='/user/" + newUserName + "/']")))
                .apply(getDriver());
    }
    @Step("Click on the 'New Item' button on the People page")
    public NewJobPage clickNewItem() {
        newItem.click();

        return new NewJobPage(getDriver());
    }

    @Step("Click on the 'User ID' button on the People page")
    public PeoplePage clickUserIDButton() {
        userIDButton.click();

        return new PeoplePage(getDriver());
    }

    @Step("Click on the 'Name' button on the People page")
    public PeoplePage clickNameButton() {
        nameButton.click();

        return new PeoplePage(getDriver());
    }

    @Step("Check if the 'User ID' button without arrow")
    public boolean isUserIDButtonWithoutArrow() {
        return userIDButtonArrow.getText().isEmpty();
    }

    @Step("Check if the 'User ID' button with up arrow")
    public boolean isUserIDButtonWithUpArrow() {
        return userIDButtonArrow.getText().trim().contains("↑");
    }

    @Step("Check if the 'User ID' button with down arrow")
    public boolean isUserIDButtonWithDownArrow() {
        return userIDButtonArrow.getText().trim().contains("↓");
    }

    @Step("Check if User '{userName}' was added")
    public boolean checkIfUserWasAdded(String userName, String userFullName) {
        return user.getText().contains(userName) && user.getText().contains(userFullName);
    }

    public static List<String> listText(List<WebElement> elementList) {
        List<String> stringList = new ArrayList<>();
        for (WebElement element : elementList) {
            stringList.add(element.getText());
        }

        return stringList;
    }

    @Step("Get Icon buttons List")
    public List<String> getIconButtonsList() {
        return listText(iconButtons);
    }

    @Step("Get Heading text from People Page")
    public String getPageTitle() {
        return pageTitle.getText();
    }
}
