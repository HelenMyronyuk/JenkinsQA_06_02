package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class LogRecordersPage extends BaseSubmenuPage<LogRecordersPage> {

    public LogRecordersPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "System Log";
    }
}
