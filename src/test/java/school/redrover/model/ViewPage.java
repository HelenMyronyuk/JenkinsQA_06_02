package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseDashboardPage;

public class ViewPage extends BaseDashboardPage<ViewPage> implements IDescription<ViewPage> {

    @FindBy(xpath = "//a[@href='delete']")
    private WebElement deleteView;

    @FindBy(xpath = "//a[contains(@href, '/configure')]")
    private WebElement editViewSideMenu;

    @FindBy(name = "Submit")
    private WebElement yesButton;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigPage clickEditListView(String nameProject) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//*[@href='/view/%s/configure']", nameProject.replaceAll(" ","%20"))))).click();

        return new ListViewConfigPage(new ViewPage(getDriver()));
    }

    public DeletePage<MainPage> clickDeleteView() {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteView)).click();

        return new DeletePage<>(new MainPage(getDriver()));
    }

    public ViewPage clickEditView() {
        editViewSideMenu.click();

        return this;
    }

    public ViewPage clickDeleteViewSideMenu() {
        deleteView.click();
        yesButton.click();

        return this;
    }

    public ViewPage selectView(String viewName) {
        getWait2().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//a[contains(@href, '/user/admin/my-views/view/"+ viewName +"')]")))).click();

        return this;
    }
}
