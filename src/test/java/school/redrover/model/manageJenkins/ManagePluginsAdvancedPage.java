package school.redrover.model.manageJenkins;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;

public class ManagePluginsAdvancedPage extends BaseMainHeaderPage<ManagePluginsAdvancedPage> {

    @FindBy(className = "jenkins-help-button")
    private WebElement extraInfoServerIcon;

    @FindBy(css = ".help>div")
    private WebElement extraInfoServer;

    public ManagePluginsAdvancedPage(WebDriver driver) {super(driver);}

    @Step("Click on the 'Extra Info Server' icon in the 'Advanced settings' ")
    public ManagePluginsAdvancedPage clickExtraInfoServerIcon(){
        extraInfoServerIcon.click();

        return this;
    }

    @Step("Get text 'Extra Info Server' ")
    public String getExtraInfoServerTextBox(){
        return extraInfoServer.getText();
    }
}
