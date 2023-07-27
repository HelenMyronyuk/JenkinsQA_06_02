package school.redrover.model.jobsconfig;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.baseConfig.BaseConfigWithoutPipelineProjectPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;

public class MultiConfigurationProjectConfigPage extends BaseConfigWithoutPipelineProjectPage<MultiConfigurationProjectConfigPage, MultiConfigurationProjectPage> {

    public MultiConfigurationProjectConfigPage(MultiConfigurationProjectPage multiConfigurationProjectPage) {
        super(multiConfigurationProjectPage);
    }

    public WebElement getCheckboxById(int id){
        return getDriver().findElement(By.id("cb" + id));
    }
}
