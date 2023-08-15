package school.redrover.model.externalPages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseModel;

public class DocBookPipelinePage extends BaseModel {

    @FindBy(id = "pipeline-1")
    private WebElement title;

    public DocBookPipelinePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get 'Pipeline' Title")
    public String getTextPipelineTitle() {
        return title.getText();
    }
}
