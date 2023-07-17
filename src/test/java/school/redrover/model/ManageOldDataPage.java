package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class ManageOldDataPage extends BaseSubmenuPage<ManageOldDataPage> {
    public ManageOldDataPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Manage Old Data";
    }
}
