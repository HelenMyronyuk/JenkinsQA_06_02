package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;

public class ChangesBuildPage extends BaseMainHeaderPage <ChangesBuildPage> {

    public ChangesBuildPage(WebDriver driver) {
        super(driver);
    }
    public String getTextChanges() {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='jenkins-icon-adjacent']"))).getText();
    }
}