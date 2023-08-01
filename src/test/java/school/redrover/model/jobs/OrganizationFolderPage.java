package school.redrover.model.jobs;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;

import school.redrover.model.base.BaseOtherFoldersPage;
import school.redrover.model.jobsConfig.OrganizationFolderConfigPage;

public class OrganizationFolderPage extends BaseOtherFoldersPage<OrganizationFolderPage> {

    @FindBy(xpath = "//span[(text() = 'Re-run the Folder Computation')]")
    private WebElement rerunFolderComputationLink;

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public OrganizationFolderConfigPage clickConfigure() {
        setupClickConfigure();

        return new OrganizationFolderConfigPage(this);
    }

    @Override
    public OrganizationFolderConfigPage clickConfigureProject() {
        setupClickConfigureProject();

        return new OrganizationFolderConfigPage(this);
    }

    public ScanOtherFoldersPage clickRerunTheFolderComputation() {
        getWait5().until(ExpectedConditions.elementToBeClickable(rerunFolderComputationLink)).click();

        return new ScanOtherFoldersPage(getDriver());
    }
}