package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class ErrorNodePage extends BaseMainHeaderPage<ErrorNodePage> {

    @FindBy(xpath = "//h1")
    private WebElement error;

    @FindBy(xpath = "//p")
    private WebElement errorMessage;

    public ErrorNodePage(WebDriver driver){
        super(driver);
    }

    public String getErrorMessage() {
        getWait2().until(ExpectedConditions
                .textToBePresentInElement(error, "Error"));

        return errorMessage.getText();
    }
}
