package school.redrover.model.views;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.DeletePage;
import school.redrover.model.base.BaseDashboardPage;
import school.redrover.model.interfaces.IDashboardTable;
import school.redrover.model.interfaces.IDescription;
import school.redrover.model.interfaces.IViewBar;

import java.util.List;

public class MyViewsPage extends BaseDashboardPage<MyViewsPage> implements IDescription<MyViewsPage>, IDashboardTable<MyViewsPage>, IViewBar {

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[contains(@href, '/configure')]")
    private WebElement configureView;

    @FindBy(xpath = "//h2")
    private WebElement statusMessage;

    @FindBy(xpath = "//div[@class='tabBar']/div/a")
    private List<WebElement> allViewLinks;

    @FindBy(xpath = "//a[@href = 'delete']")
    private WebElement deleteViewButton;

    public String getStatusMessageText() {

        return statusMessage.getText();
    }

    @Step("Click on the last created view name '{viewName}' button on the MyViews page")
    public MyViewsPage clickInactiveLastCreatedMyView(String viewName) {
        for(WebElement e : allViewLinks) {
            String link = e.getAttribute("href");
            String linkName = link.substring(link.length() - viewName.length() - 1, link.length() - 1);
            if(linkName.equals(viewName)) {
                getWait5().until(ExpectedConditions.elementToBeClickable(e)).click();

                return this;
            }
        }

        return this;
    }

    @Step("Click on the 'Edit View' button on the side menu")
    public MyViewConfigPage clickEditView() {
        getWait5().until(ExpectedConditions.elementToBeClickable(configureView)).click();

        return new MyViewConfigPage(new ViewPage(getDriver()));
    }

    @Step("Click on the 'Delete View' button on the side menu")
    public DeletePage<MyViewsPage> clickDeleteViewButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteViewButton)).click();

        return new DeletePage<>(this);
    }
}
