package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseDashboardPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;
import school.redrover.model.interfaces.IDashboardTable;
import school.redrover.model.interfaces.IDescription;
import school.redrover.runner.TestUtils;

public class ViewPage extends BaseDashboardPage<ViewPage> implements IDescription<ViewPage>, IDashboardTable<ViewPage> {

    @FindBy(xpath = "//a[@href='delete']")
    private WebElement deleteViewLink;

    @FindBy(xpath = "//a[contains(@href, '/configure')]")
    private WebElement editViewSideMenu;

    @FindBy(name = "Submit")
    private WebElement yesButton;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public <ViewConfigPage extends BaseMainHeaderPage<?>> ViewConfigPage clickEditView(TestUtils.ViewType viewType, Class<ViewConfigPage> clazz) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Edit View']/.."))).click();

        return (ViewConfigPage) viewType.createNextPage(getDriver());
    }

    public <RedirectPage extends BasePage<?,?>> DeletePage<RedirectPage> clickDeleteView(RedirectPage redirectPage) {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteViewLink)).click();

        return new DeletePage<>(redirectPage);
    }

    public ViewPage clickEditView() {
        editViewSideMenu.click();

        return this;
    }

    public ViewPage clickDeleteViewSideMenu() {
        deleteViewLink.click();
        yesButton.click();

        return this;
    }

    public ViewPage selectView(String viewName) {
        getWait2().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//a[contains(@href, '/user/admin/my-views/view/" + viewName + "')]")))).click();

        return this;
    }
}
