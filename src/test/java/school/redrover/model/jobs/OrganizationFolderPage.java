package school.redrover.model.jobs;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import school.redrover.model.base.BaseOtherFoldersPage;
import school.redrover.model.jobsConfig.OrganizationFolderConfigPage;
import school.redrover.model.jobsSidemenu.ScanOtherFoldersPage;

public class OrganizationFolderPage extends BaseOtherFoldersPage<OrganizationFolderPage> {

    @FindBy(xpath = "//span[(text() = 'Re-run the Folder Computation')]")
    private WebElement rerunFolderComputationLink;

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the 'Configure' button on side menu")
    @Override
    public OrganizationFolderConfigPage clickConfigure() {
        setupClickConfigure();

        return new OrganizationFolderConfigPage(this);
    }

    @Step("Click on the 'Configure' button on side menu")
    @Override
    public OrganizationFolderConfigPage clickConfigureProject() {
        setupClickConfigureProject();

        return new OrganizationFolderConfigPage(this);
    }

    @Step("Click on the 'Re-run the Folder Computation' link on the Organization Folder page")
    public ScanOtherFoldersPage clickRerunTheFolderComputation() {
        getWait5().until(ExpectedConditions.elementToBeClickable(rerunFolderComputationLink)).click();

        return new ScanOtherFoldersPage(getDriver());
    }
}