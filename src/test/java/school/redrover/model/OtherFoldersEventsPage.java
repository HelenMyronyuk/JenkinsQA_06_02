package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;

public class OtherFoldersEventsPage extends BaseMainHeaderPage<ScanOtherFoldersLogPage> {

    @FindBy(xpath = "//h1")
    private WebElement title;

    public OtherFoldersEventsPage(WebDriver driver) {
        super(driver);
    }

    public String getTextFromTitle() {
        return title.getText();
    }
}