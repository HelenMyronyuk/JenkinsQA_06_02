package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;

public class MovePage<JobTypePage extends BasePage<?, ?>> extends BaseMainHeaderPage<MovePage<JobTypePage>> {

    @FindBy(name = "Submit")
    private WebElement moveButton;

    @FindBy(name = "destination")
    private WebElement destinationFolder;

    private final JobTypePage jobTypePage;

    public MovePage(JobTypePage jobTypePage) {
        super(jobTypePage.getDriver());

        this.jobTypePage = jobTypePage;
    }
    @Step("select destination for move a folder")
    public MovePage<JobTypePage> selectDestinationFolder(String folderName) {
        new Select(getWait5().until(ExpectedConditions.elementToBeClickable(destinationFolder))).selectByValue("/" + folderName);

        return this;
    }

    @Step("Click 'Move' button")
    public JobTypePage clickMoveButton() {
        moveButton.click();

        return jobTypePage;
    }
}
