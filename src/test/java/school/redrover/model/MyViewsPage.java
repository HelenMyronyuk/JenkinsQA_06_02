package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseDashboardPage;
import school.redrover.model.interfaces.IDashboardTable;
import school.redrover.model.interfaces.IDescription;
import school.redrover.model.interfaces.IViewBar;

import java.util.ArrayList;
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

    public MyViewConfigPage clickEditView() {
        getWait5().until(ExpectedConditions.elementToBeClickable(configureView)).click();

        return new MyViewConfigPage(new ViewPage(getDriver()));
    }

    public DeletePage<MyViewsPage> clickDeleteViewButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteViewButton)).click();

        return new DeletePage<>(this);
    }
}
