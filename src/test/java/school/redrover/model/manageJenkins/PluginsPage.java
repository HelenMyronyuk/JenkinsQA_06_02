package school.redrover.model.manageJenkins;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseSubmenuPage;

public class PluginsPage extends BaseSubmenuPage<PluginsPage> {
    @FindBy(xpath = "//h1")
    private WebElement pageTitle;

    public PluginsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Manage Plugins";
    }

    @Step("Get Heading text from Plugins page")
    public String getPageTitle() {
        return pageTitle.getText();
    }
}
