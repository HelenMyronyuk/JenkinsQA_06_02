package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;

public class DocBookPipelinePage extends BaseMainHeaderPage<DocBookPipelinePage> {

    @FindBy(id = "pipeline-1")
    private WebElement title;

    public DocBookPipelinePage(WebDriver driver) {
        super(driver);
    }

    public String getTextPipelineTitle() {
        return title.getText();
    }
}
