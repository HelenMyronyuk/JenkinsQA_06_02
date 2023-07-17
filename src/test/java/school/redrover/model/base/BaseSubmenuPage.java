package school.redrover.model.base;

import org.openqa.selenium.WebDriver;

public abstract class BaseSubmenuPage<Self extends BaseSubmenuPage<?>> extends BaseMainHeaderPage<Self> {

    public BaseSubmenuPage(WebDriver driver) {
        super(driver);
    }

    public abstract String callByMenuItemName();
}
