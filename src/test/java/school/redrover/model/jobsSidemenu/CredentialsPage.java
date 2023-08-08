package school.redrover.model.jobsSidemenu;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.GlobalCredentialsPage;
import school.redrover.model.base.BaseSubmenuPage;

public class CredentialsPage extends BaseSubmenuPage<CredentialsPage> {

    @FindBy(xpath = "//h1")
    private WebElement pageHeader;

    @FindBy(xpath = "//table[2]//a[@href='/manage/credentials/store/system/domain/_/']")
    private WebElement globalStoresScopedToJenkinsLink;

    public CredentialsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Manage Credentials";
    }

    @Step("Get Heading text from Credentials page")
    public String getTitleText() {
        return getWait2().until(ExpectedConditions.visibilityOf(pageHeader)).getText();
    }

    @Step("Click '(global)' link in 'Stores Scoped to Jenkins' table")
    public GlobalCredentialsPage clickGlobalStoresScopedToJenkinsLink() {
        new Actions(getDriver()).moveToElement(globalStoresScopedToJenkinsLink).click().perform();
        return new GlobalCredentialsPage(getDriver());
    }
}
