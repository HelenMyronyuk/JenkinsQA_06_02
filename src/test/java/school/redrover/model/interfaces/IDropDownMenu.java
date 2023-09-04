package school.redrover.model.interfaces;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.IBasePage;

public interface IDropDownMenu extends IBasePage {

    @Step("Get the option name")
    default String callByMenuItemName() {
        return "";
    }

    @Step("Get the text of the alert window")
    default String getAssertTextFromPage() {
        return getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//h1"))).getText();
    }
}
