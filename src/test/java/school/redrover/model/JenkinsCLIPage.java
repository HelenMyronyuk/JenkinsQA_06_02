package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class JenkinsCLIPage extends BaseSubmenuPage<JenkinsCLIPage> {
    public JenkinsCLIPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Jenkins CLI";
    }
}
