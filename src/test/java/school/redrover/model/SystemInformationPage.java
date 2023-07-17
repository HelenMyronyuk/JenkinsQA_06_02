package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class SystemInformationPage extends BaseSubmenuPage<SystemInformationPage> {

    public SystemInformationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "System Information";
    }
}
