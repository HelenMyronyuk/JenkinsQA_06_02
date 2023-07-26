package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseSubmenuPage;

public class PipelineStepsPage extends BaseSubmenuPage<PipelineStepsPage> {

    @FindBy(xpath = "//a[contains(text(),'Pipeline Steps')]")
    private WebElement titlePipelineStep;

    public PipelineStepsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return null;
    }

    public String getTitlePipelineFromBreadcrumb(){
        return getWait5().until(ExpectedConditions.visibilityOf(titlePipelineStep)).getText();
    }
}
