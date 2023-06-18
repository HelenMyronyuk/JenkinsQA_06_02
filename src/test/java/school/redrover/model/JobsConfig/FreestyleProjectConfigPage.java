package school.redrover.model.JobsConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.Jobs.FreestyleProjectPage;
import school.redrover.model.base.BaseConfigProjectsPage;

public class FreestyleProjectConfigPage extends BaseConfigProjectsPage<FreestyleProjectConfigPage, FreestyleProjectPage> {

    public FreestyleProjectConfigPage(FreestyleProjectPage freestyleProjectPage) {
        super(freestyleProjectPage);
    }

    public FreestyleProjectConfigPage addBuildStepsExecuteShell(String buildSteps) {
        new Actions(getDriver())
                .scrollByAmount(0, 2000)
                .perform();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='yui-gen9-button']"))).click();
        getDriver().findElement(
                By.xpath("//*[@id='yui-gen24']")).click();

        new Actions(getDriver())
                .click(getDriver().findElement(By.xpath("//*[@name='description']")))
                .sendKeys(buildSteps)
                .perform();
        return this;
    }
}