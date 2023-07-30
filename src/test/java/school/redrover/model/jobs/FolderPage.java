package school.redrover.model.jobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.*;
import school.redrover.model.jobsConfig.FolderConfigPage;
import school.redrover.model.base.BaseJobPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FolderPage extends BaseJobPage<FolderPage> {

    @FindBy(xpath = "//a[contains(@href, '/newJob')]")
    private WebElement newItemButton;

    @FindBy(xpath = "//a[contains(@href, '/newView')]")
    private WebElement newViewButton;

    @FindBy(xpath = "//a[contains(@href, '/delete')]")
    private WebElement deleteButton;

    @FindBy(id = "view-message")
    private WebElement folderDescription;

    @FindBy(css = ".jenkins-table__link")
    private List<WebElement> jobList;

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
        return TestUtils.getText(this, folderDescription);
    }

    public List<String> getJobList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

    public boolean jobIsDisplayed(String viewName) {
        try {

            return getDriver().findElement(By.linkText(viewName)).isDisplayed();
        } catch (Exception e) {

            return false;
        }
    }
}
