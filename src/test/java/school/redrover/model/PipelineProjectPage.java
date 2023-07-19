package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;

public class PipelineProjectPage extends BaseMainHeaderPage<PipelineProjectPage> {

    @FindBy(id = "pipeline-1")
    private WebElement title;

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getTextPipelineTitle() {

        return title.getText();
    }
}
