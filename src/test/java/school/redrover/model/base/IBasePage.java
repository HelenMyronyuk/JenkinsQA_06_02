package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface IBasePage {

    WebDriver getDriver();
    WebDriverWait getWait2();

    WebDriverWait getWait5();

    WebDriverWait getWait10();
}
