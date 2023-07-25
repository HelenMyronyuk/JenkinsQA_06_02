package school.redrover.model;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.*;

public class ReplayPage<ParentPage extends BasePage<?,?>> extends BaseMainHeaderPage<ReplayPage<ParentPage>>  {
    @FindBy(name = "Submit")
    private WebElement runButton;
    private final ParentPage parentPage;

    public ReplayPage(ParentPage parentPage) {
        super(parentPage.getDriver());
        this.parentPage = parentPage;
    }

    public ParentPage clickRunButton() {
        runButton.click();
        return parentPage;
    }
}
