package school.redrover.model.builds;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseSubmenuPage;

public class ChangesBuildPage extends BaseSubmenuPage<ChangesBuildPage> {

    public ChangesBuildPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "changes";
    }

    @Step("Get text 'Changes' ")
    public String getTextChanges() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='jenkins-icon-adjacent']"))).getText();
    }
}