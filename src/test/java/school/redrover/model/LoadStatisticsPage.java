package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class LoadStatisticsPage extends BaseSubmenuPage<LoadStatisticsPage> {

    public LoadStatisticsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Load Statistics";
    }
}
