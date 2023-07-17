package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class ConfigureCredentialProvidersPage extends BaseSubmenuPage<ConfigureCredentialProvidersPage> {

    public ConfigureCredentialProvidersPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Configure Credential Providers";
    }
}
