package school.redrover.model.jobsConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.jobs.OrganizationFolderPage;
import school.redrover.model.base.baseConfig.BaseConfigFoldersPage;

public class OrganizationFolderConfigPage extends BaseConfigFoldersPage<OrganizationFolderConfigPage, OrganizationFolderPage> {

    @FindBy(xpath = "//label[@data-title='Disabled']")
    private WebElement disableEnableFromConfig;

    @FindBy(xpath = "//button[@data-section-id='appearance']")
    private WebElement appearanceButton;

    @FindBy(xpath = "//div[@class='jenkins-form-item has-help']/div/select")
    private WebElement defaultIcon;

    public OrganizationFolderConfigPage(OrganizationFolderPage organizationFolderPage) {
        super(organizationFolderPage);
    }

    @Step("Click on the 'Disable/Enable' button on Configuration page")
    public OrganizationFolderConfigPage clickDisableEnable() {
        disableEnableFromConfig.click();

        return this;
    }

    @Step("Click on the 'Appearance' button on the Configuration page to select the option")
    public OrganizationFolderConfigPage clickAppearance() {
        getWait5().until(ExpectedConditions.elementToBeClickable(appearanceButton)).click();

        return this;
    }

    @Step("Select an option the 'Default Icon' from the Configuration page")
    public OrganizationFolderConfigPage selectDefaultIcon() {
        new Select(getWait5().until(ExpectedConditions.elementToBeClickable(defaultIcon)))
                .selectByVisibleText("Default Icon");

        return this;
    }
}
