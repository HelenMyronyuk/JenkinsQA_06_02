package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseSubmenuPage<Self extends BaseSubmenuPage<?>> extends BaseMainHeaderPage<Self> {

    @FindBy(xpath = "//h1")
    private WebElement heading;

    public BaseSubmenuPage(WebDriver driver) {
        super(driver);
    }

    public abstract String callByMenuItemName();

    public String getHeading(){
        return heading.getText();
    }
}
