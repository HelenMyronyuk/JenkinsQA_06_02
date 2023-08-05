package school.redrover.model.jobsConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.baseConfig.BaseConfigFreestyleAndMulticonfigProjectsPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;

public class MultiConfigurationProjectConfigPage extends BaseConfigFreestyleAndMulticonfigProjectsPage<MultiConfigurationProjectConfigPage, MultiConfigurationProjectPage> {

    public MultiConfigurationProjectConfigPage(MultiConfigurationProjectPage multiConfigurationProjectPage) {
        super(multiConfigurationProjectPage);
    }
    @Step("Get checkbox '{id}' on Multi-configuration project config page ")
    public WebElement getCheckboxById(int id){
        return getDriver().findElement(By.id("cb" + id));
    }
}
