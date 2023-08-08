package school.redrover.model.builds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class UserBuildsPage extends BaseMainHeaderPage<UserBuildsPage> {

    public UserBuildsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isBuildFroUserPresent(String projectName) {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@class='jenkins-table__link model-link' and contains(@href, '" + projectName + "')]"))).isDisplayed();
    }
}
