package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class RestApiPage extends BaseMainHeaderPage<RestApiPage> {

    @FindBy(xpath = "//h1")
    private WebElement title;

    public RestApiPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get title text 'REST API' ")
    public String getRestApiPageTitle(){
       return getWait2().until(ExpectedConditions.elementToBeClickable(title)).getText();
    }
}
