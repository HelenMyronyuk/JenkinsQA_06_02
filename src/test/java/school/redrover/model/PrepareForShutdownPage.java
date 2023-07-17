package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class PrepareForShutdownPage extends BaseSubmenuPage<PrepareForShutdownPage> {

    public PrepareForShutdownPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Prepare for Shutdown";
    }
}
