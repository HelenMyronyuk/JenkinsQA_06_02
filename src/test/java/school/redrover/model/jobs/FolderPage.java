package school.redrover.model.jobs;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.interfaces.IDashboardTable;
import school.redrover.model.interfaces.IViewBar;
import school.redrover.model.jobsConfig.FolderConfigPage;
import school.redrover.model.base.BaseJobPage;

public class FolderPage extends BaseJobPage<FolderPage> implements IDashboardTable<FolderPage>, IViewBar {

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

    @Step("Click on the 'Configure' link on the Folder page")
    @Override
    public FolderConfigPage clickConfigure() {
        setupClickConfigure();

        return new FolderConfigPage(new FolderPage(getDriver()));
    }

    @Step("Click on the 'New Item' link on the Folder page")
    public NewJobPage clickNewItem() {
        newItemButton.click();

        return new NewJobPage(getDriver());
    }

    @Step("Click on the 'Delete Job' link on the Folder page and go to Main Page")
    public DeletePage<MainPage> clickDeleteJobThatIsMainPage() {
        deleteButton.click();

        return new DeletePage<>(new MainPage(getDriver()));
    }

    @Step("Get Folder description text on the Folder page")
    public String getFolderDescription() {
        if (!folderDescription.getText().isEmpty()) {
            getWait5().until(ExpectedConditions.visibilityOf(folderDescription));
        }
        return folderDescription.getText();
    }

    @Step("Get Heading text")
    public String getTitle() {
        return getDriver().getTitle();
    }
}
