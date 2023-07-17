package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseSubmenuPage;

public class ScriptConsolePage extends BaseSubmenuPage<ScriptConsolePage> {

    public ScriptConsolePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Script Console";
    }
}
