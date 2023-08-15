package school.redrover.model.externalPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseModel;

public class GitHubTestRepo extends BaseModel {
    public GitHubTestRepo(WebDriver driver) {
        super(driver);
    }

    public String getNameRepo() {
        return getDriver().findElement(By.xpath("//strong [@itemprop='name']/a")).getText();
    }
}
