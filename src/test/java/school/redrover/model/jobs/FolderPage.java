package school.redrover.model.jobs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.interfaces.IDashboardTable;
import school.redrover.model.jobsConfig.FolderConfigPage;
import school.redrover.model.base.BaseJobPage;

public class FolderPage extends BaseJobPage<FolderPage> implements IDashboardTable<FolderPage> {

    @FindBy(xpath = "//a[contains(@href, '/newJob')]")
    private WebElement newItemButton;

    @FindBy(xpath = "//a[contains(@href, '/newView')]")
    private WebElement newViewButton;

    @FindBy(xpath = "//a[contains(@href, '/delete')]")
    private WebElement deleteButton;

    @FindBy(id = "view-message")
    private WebElement folderDescription;

    public FolderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public FolderConfigPage clickConfigure() {
        setupClickConfigure();

        return new FolderConfigPage(new FolderPage(getDriver()));
    }

    public NewJobPage clickNewItem() {
        newItemButton.click();

        return new NewJobPage(getDriver());
    }

    public NewViewPage clickNewView() {
        newViewButton.click();

        return new NewViewPage(getDriver());
    }

    public DeletePage<MainPage> clickDeleteJobThatIsMainPage() {
        deleteButton.click();

        return new DeletePage<>(new MainPage(getDriver()));
    }

    public String getFolderDescription() {
        if (!folderDescription.getText().isEmpty()) {
            getWait5().until(ExpectedConditions.visibilityOf(folderDescription));
        }
        return folderDescription.getText();
    }

    public String getTitle() {
        return getDriver().getTitle();
    }
}
