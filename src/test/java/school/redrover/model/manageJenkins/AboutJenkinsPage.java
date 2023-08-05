package school.redrover.model.manageJenkins;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class AboutJenkinsPage extends BaseSubmenuPage<AboutJenkinsPage> {
    public AboutJenkinsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "About Jenkins";
    }
}
