package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.interfaces.IAlert;

public abstract class BaseSubmenuPage<Self extends BaseSubmenuPage<?>> extends BaseMainHeaderPage<Self> implements IAlert<Self> {

    @FindBy(xpath = "//h1")
    private WebElement heading;

    @FindBy(xpath = "//li[contains(text(),'Edit Build Information')]")
    private WebElement titleEditFromBreadCrumb;

    public BaseSubmenuPage(WebDriver driver) {
        super(driver);
    }

    public abstract String callByMenuItemName();

    public String getHeading() {
        return heading.getText();
    }

    @Step("Get text 'Edit Build Information' from BreadCrumb")
    public String getTextEditBuildInformFromBreadCrumb() {
        return getWait5().until(ExpectedConditions.visibilityOf(titleEditFromBreadCrumb)).getText();
    }

    @Step("Get text from BreadCrumb")
    public String getTextFromBreadCrumb(String name) {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'" + name + "')]"))).getText();

    }

    @Step("Get the text of the alert window")
    public String getAssertTextFromPage() {
        return getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//h1"))).getText();
    }
}
