package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.JenkinsVersionPage;
import school.redrover.model.MainPage;
import school.redrover.model.component.MainBreadcrumbComponent;
import school.redrover.model.component.MainHeaderComponent;

public abstract class BaseMainHeaderPage<Self extends BaseMainHeaderPage<?>> extends BasePage<MainHeaderComponent<Self>, MainBreadcrumbComponent<Self>> {

    @FindBy(xpath = "//h1")
    private WebElement header;

    @FindBy(xpath = "//a[contains(text(), 'Jenkins')]")
    private WebElement jenkinsVersionLink;

    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    private WebElement deleteButton;

    public BaseMainHeaderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MainHeaderComponent<Self> getHeader() {
        return new MainHeaderComponent<>( (Self)this);
    }

    @Override
    public MainBreadcrumbComponent<Self> getBreadcrumb() {
        return new MainBreadcrumbComponent<>( (Self)this);
    }

    public String getPageHeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(header)).getText();
    }

    public JenkinsVersionPage clickJenkinsVersionLink() {
        jenkinsVersionLink.click();
        return new JenkinsVersionPage(getDriver());
    }
}
