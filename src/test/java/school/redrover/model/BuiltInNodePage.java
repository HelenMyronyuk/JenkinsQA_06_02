package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;

public class BuiltInNodePage extends BaseMainHeaderPage<BuiltInNodePage> {

    @FindBy(xpath = "//h1")
    private WebElement title;

    public BuiltInNodePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get text from Header of Build In Node page")
    public String getHeaderText() {
        return title.getText();
    }
}
