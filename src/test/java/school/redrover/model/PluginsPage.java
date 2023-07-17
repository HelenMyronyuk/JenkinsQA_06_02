package school.redrover.model;

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

    public String getPageTitle() {
        return pageTitle.getText();
    }
}
