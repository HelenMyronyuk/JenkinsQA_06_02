package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class SystemInformationPage extends BaseSubmenuPage<SystemInformationPage> {

    public SystemInformationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Call name by menu Item")
    @Override
    public String callByMenuItemName() {
        return "System Information";
    }
}
