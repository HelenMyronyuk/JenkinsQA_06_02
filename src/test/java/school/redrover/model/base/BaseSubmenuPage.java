package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseSubmenuPage<Self extends BaseSubmenuPage<?>> extends BaseMainHeaderPage<Self> {

    @FindBy(xpath = "//h1")
    private WebElement heading;

    @FindBy(xpath = "//li[contains(text(),'Edit Build Information')]")
    private WebElement titleEditFromBreadCrumb;

    public BaseSubmenuPage(WebDriver driver) {
        super(driver);
    }

    public abstract String callByMenuItemName();

    public String getHeading(){
        return heading.getText();
    }

    @Step("Get text 'Edit Build Information' from BreadCrumb")
    public String getTextEditBuildInformFromBreadCrumb(){
            return getWait5().until(ExpectedConditions.visibilityOf(titleEditFromBreadCrumb)).getText();
    }
}
