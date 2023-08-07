package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public abstract class BasePage<Header extends BaseComponent<?>, Breadcrumb extends BaseComponent<?>> extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get 'Header'")
    public abstract Header getHeader();

    @Step("Get 'Breadcrumb'")
    public abstract Breadcrumb getBreadcrumb();
}
