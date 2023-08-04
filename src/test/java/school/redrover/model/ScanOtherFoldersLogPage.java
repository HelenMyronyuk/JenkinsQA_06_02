package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;

public class ScanOtherFoldersLogPage extends BaseMainHeaderPage<ScanOtherFoldersLogPage> {

    @FindBy(xpath = "//span[@class='jenkins-icon-adjacent']")
    private WebElement title;

    public ScanOtherFoldersLogPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get title 'Scan Organization Folder Log' ")
    public String getTextFromTitle() {
        return title.getText();
    }
}

