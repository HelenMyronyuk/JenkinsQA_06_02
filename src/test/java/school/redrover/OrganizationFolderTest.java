package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.jobs.OrganizationFolderPage;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobsConfig.OrganizationFolderConfigPage;
import school.redrover.model.jobsConfig.PipelineConfigPage;
import school.redrover.model.views.MyViewsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class OrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = "OrgFolder";
    private static final String ORGANIZATION_FOLDER_RENAMED = "OrgFolderNew";
    private static final String PRINT_MESSAGE_PIPELINE_SYNTAX = "TEXT";
    private static final String DESCRIPTION_TEXT = "DESCRIPTION_TEXT";
    private static final String DISPLAY_NAME = "This is Display Name of Folder";

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating Organization Folder project by clicking 'Create a job' button")
    @Test
    public void testCreateFromCreateAJob() {
        MainPage mainPage = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .clickOkButton(new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.projectStatusTableIsDisplayed());
        Assert.assertEquals(mainPage.getJobName(ORGANIZATION_FOLDER_NAME), ORGANIZATION_FOLDER_NAME);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify job creation when clicking on 'New Item' button")
    @Test
    public void testCreateFromNewItem() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        boolean actualNewFolderName = new MainPage(getDriver())
                .jobIsDisplayed(ORGANIZATION_FOLDER_NAME);

        Assert.assertTrue(actualNewFolderName, "error was not show name folder");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating Organization Folder project by clicking 'New Item' button from People Page")
    @Test
    public void testCreateFromPeoplePage() {
        MainPage projectPeoplePage = new MainPage(getDriver())
                .clickPeopleFromSideMenu()
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .clickOkButton(new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectPeoplePage.jobIsDisplayed(ORGANIZATION_FOLDER_NAME));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating Organization Folder project by clicking 'New Item' button from Build History Page")
    @Test
    public void testCreateFromBuildHistoryPage() {
        boolean newProjectFromBuildHistoryPage = new MainPage(getDriver())
                .clickBuildsHistoryFromSideMenu()
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .clickOkButton(new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(ORGANIZATION_FOLDER_NAME);

        Assert.assertTrue(newProjectFromBuildHistoryPage, "Error: the Organization Folder name is not displayed on Dashboard");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating Organization Folder project by clicking 'New Item' button from Manage Jenkins Page")
    @Test
    public void testCreateFromManageJenkinsPage() {
        List<String> organizationFolderName = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .clickOkButton(new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .clickSaveButton()
                .getBreadcrumb()
                .clickDashboardButton()
                .getJobList();

        Assert.assertTrue(organizationFolderName.contains(ORGANIZATION_FOLDER_NAME));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating Organization Folder project by clicking 'New Item' button from My Views Page")
    @Test
    public void testCreateFromMyViewsNewItem() {
        String newOrganizationFolderName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewItemFromSideMenu()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .clickOkButton(new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .clickSaveButton()
                .getJobName();

        boolean newOrganizationFolderNameIsDisplayed = new OrganizationFolderPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu("My Views", new MyViewsPage(getDriver()))
                .jobIsDisplayed(newOrganizationFolderName);

        Assert.assertEquals(newOrganizationFolderName, ORGANIZATION_FOLDER_NAME);
        Assert.assertTrue(newOrganizationFolderNameIsDisplayed);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating Organization Folder project by clicking 'Create a Job' button from My Views Page")
    @Test
    public void testCreateFromMyViewsCreateAJob() {
        MainPage mainPage = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJobAndArrow()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .clickOkButton(new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.jobIsDisplayed(ORGANIZATION_FOLDER_NAME), "Error: the Organization Folder's name is not displayed on Dashboard from Home page");
        Assert.assertTrue(mainPage.clickMyViewsSideMenuLink()
                .jobIsDisplayed(ORGANIZATION_FOLDER_NAME), "Error: the Organization Folder's name is not displayed on Dashboard from MyViews page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating Organization Folder project with existing name")
    @Test
    public void testCreateWithExistingName() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);
        CreateItemErrorPage errorPage =
                TestUtils.createJobWithExistingName(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "A job already exists with the name ‘" + ORGANIZATION_FOLDER_NAME + "’");
    }

    @DataProvider(name = "wrong-character")
    public Object[][] provideWrongCharacters() {
        return new Object[][]{{"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {">"}, {"["}, {"]"}};
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating Organization Folder project with name using unsafe characters")
    @Test(dataProvider = "wrong-character")
    public void testCreateUsingInvalidData(String invalidData) {
        NewJobPage newJobPage = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(invalidData)
                .selectJobType(TestUtils.JobType.OrganizationFolder);

        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "Save button is enabled");
        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» ‘" + invalidData + "’ is an unsafe character");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating Organization Folder project with empty name")
    @Test
    public void testCreateWithEmptyName() {
        final String expectedError = "» This field cannot be empty, please enter a valid name";

        String actualError = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(actualError, expectedError);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message on Error Page while creating Organization Folder project with space instead name")
    @Test
    public void testCreateWithSpaceInsteadOfName() {
        CreateItemErrorPage errorPage =
                TestUtils.createJobWithSpaceInsteadName(this, TestUtils.JobType.OrganizationFolder);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "No name is specified");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating Organization Folder project with dot instead name")
    @Test
    public void testCreateWithDotInsteadOfName() {
        String errorMessage = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(".")
                .getItemInvalidMessage();

        Assert.assertEquals(errorMessage, "» “.” is not an allowed name");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating Organization Folder project with long name")
    @Test
    public void testCreateWithLongName() {
        String longName = RandomStringUtils.randomAlphanumeric(256);
        String errorMessage = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(longName)
                .selectJobAndOkAndGoToBugPage(TestUtils.JobType.OrganizationFolder)
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be renamed from drop down menu on the Main page")
    @Test
    public void testRenameFromDropDownMenu() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String actualRenamedName = new MainPage(getDriver())
                .dropDownMenuClickRename(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .enterNewName(ORGANIZATION_FOLDER_RENAMED)
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(actualRenamedName, ORGANIZATION_FOLDER_RENAMED);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be renamed from side menu on the Project page")
    @Test
    public void testRenameFromSideMenu() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String actualRenamedFolderName = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickRename()
                .enterNewName(ORGANIZATION_FOLDER_RENAMED)
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(actualRenamedFolderName, ORGANIZATION_FOLDER_RENAMED);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Organization Folder project from drop-down menu with existing name")
    @Test
    public void testRenameToTheCurrentNameAndGetError() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String errorMessage = new MainPage(getDriver())
                .dropDownMenuClickRename(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .enterNewName(ORGANIZATION_FOLDER_NAME)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "The new name is the same as the current name.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Organization Folder project with invalid data")
    @Test(dataProvider = "wrong-character")
    public void testRenameWithInvalidData(String invalidData) {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickRename()
                .enterNewName(invalidData)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        switch (invalidData) {
            case "&" -> Assert.assertEquals(actualErrorMessage, "‘&amp;’ is an unsafe character");
            case "<" -> Assert.assertEquals(actualErrorMessage, "‘&lt;’ is an unsafe character");
            case ">" -> Assert.assertEquals(actualErrorMessage, "‘&gt;’ is an unsafe character");
            default -> Assert.assertEquals(actualErrorMessage, "‘" + invalidData + "’ is an unsafe character");
        }
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Organization Folder project with '.' name'")
    @Test
    public void testRenameWithDotName() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String errorMessage = new MainPage(getDriver())
                .dropDownMenuClickRename(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .enterNewName(".")
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "“.” is not an allowed name");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Configuration Page from side menu for Organization Folder Project")
    @Test
    public void testConfigureProject() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, false);

        String configurationHeaderText = new OrganizationFolderPage(getDriver())
                .clickConfigureProject()
                .getPageHeaderText();

        Assert.assertEquals(configurationHeaderText, "Configuration");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Scan Organization Folder Page" +
            " by click 'Re-run the Folder Computation'")
    @Test
    public void testRerunFolderComputation() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String headerScanOrganizationFolder = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickRerunTheFolderComputation()
                .getPageHeaderText();

        Assert.assertEquals(headerScanOrganizationFolder, "Scan Organization Folder");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Pipeline Page" +
            " by click 'Creating a Jenkins Pipeline'")
    @Test
    public void testCreatingJenkinsPipeline() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, false);
        String linkBookCreatingPipeline = new OrganizationFolderPage(getDriver())
                .getTextCreatingJenkinsPipeline();

        String pipelineOneTutorial = new OrganizationFolderPage(getDriver())
                .clickCreatingAJenkinsPipelineLinkOnProjectPage()
                .getTextPipelineTitle();

        Assert.assertEquals(linkBookCreatingPipeline, "Creating a Jenkins Pipeline");
        Assert.assertEquals(pipelineOneTutorial, "Pipeline");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Branches and Pull Requests Page" +
            " by click 'Creating Multibranch Projects'")
    @Test
    public void testCreateMultibranchProject() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, false);

        String createMultibranchProject = new OrganizationFolderPage(getDriver())
                .clickMultibranchProject()
                .getBranchesAndPullRequestsTutorial();

        Assert.assertEquals(createMultibranchProject, "Branches and Pull Requests");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Scan Organization Folder Log Page " +
            "from side menu for Organization Folder Project")
    @Test
    public void testScanOrgFolderLog() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String titleScanOrgFolderLogPage = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickScanLog()
                .getTextFromTitle();

        Assert.assertEquals(titleScanOrgFolderLogPage, "Scan Organization Folder Log");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Organization Folder Events Page" +
            " from side menu for Organization Folder Project")
    @Test
    public void testOrganizationFolderEvents() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String eventTitle = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickEventsLink()
                .getPageHeaderText();

        Assert.assertEquals(eventTitle, "Organization Folder Events");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Pipeline Syntax Page" +
            " from side menu for Organization Folder Project and added option 'echo: Print Message'")
    @Test
    public void testOrganizationFolderConfigPipelineSyntax() {
        final String expectedText = "echo '" + PRINT_MESSAGE_PIPELINE_SYNTAX + "'";
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String pipelineSyntax = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickPipelineSyntax()
                .clickPrintMessageOption()
                .enterMessage(PRINT_MESSAGE_PIPELINE_SYNTAX)
                .clickGeneratePipelineScriptButton()
                .getTextPipelineScript();

        Assert.assertEquals(pipelineSyntax, expectedText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Credentials Page " +
            "from side menu for Organization Folder Project")
    @Test
    public void testCredentials() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String titleCredentials = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickCredentials()
                .getTitleText();

        Assert.assertEquals(titleCredentials, "Credentials");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence Preview of description for Organization Folder Project from the Project page")
    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String previewText = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION_TEXT)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewText, DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be disable by click on the 'Disable Organization Folder' button on the Project page")
    @Test
    public void testDisableFromProjectPage() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String disabledText = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickDisableEnableButton()
                .getTextFromDisableMessage();

        Assert.assertEquals(disabledText.substring(0, 46), "This Organization Folder is currently disabled");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to enable disabled Organization Folder Project from Project Page")
    @Test
    public void testEnableFromProjectPage() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String disableButton = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickDisableEnableButton()
                .clickDisableEnableButton()
                .getDisableButtonText();

        boolean iconOrgFolder = new OrganizationFolderPage(getDriver())
                .isMetadataFolderIconDisplayed();

        Assert.assertEquals(disableButton, "Disable Organization Folder");
        Assert.assertTrue(iconOrgFolder, "the dispayеd icon OrganizationFolder exists");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Configuration Page from drop-down menu for Organization Folder Project")
    @Test
    public void testAccessConfigurationPageFromDropDown() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String getHeaderText = new MainPage(getDriver())
                .clickConfigureDropDown(
                        ORGANIZATION_FOLDER_NAME, new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .getHeaderText();

        Assert.assertEquals(getHeaderText, "Configuration");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Configuration Page from side menu menu for Organization Folder Project")
    @Test
    public void testAccessConfigurationPageFromSideMenu(){
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String getHeaderText = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .getHeaderText();

        Assert.assertEquals(getHeaderText, "Configuration");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be disable from Configuration page")
    @Test
    public void testDisableFromConfigurationPage() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String disabledText = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .clickDisableEnable()
                .clickSaveButton()
                .getTextFromDisableMessage();

        Assert.assertTrue(disabledText.contains("This Organization Folder is currently disabled"));
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be enable from Configuration page")
    @Test
    public void testEnableFromConfigurationPage() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String enableOrgFolder = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .clickDisableEnable()
                .clickSaveButton()
                .clickConfigure()
                .clickDisableEnable()
                .clickSaveButton()
                .getDisableButtonText();

        Assert.assertEquals(enableOrgFolder.trim(), "Disable Organization Folder");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence Preview of description for Organization Folder Project from the Configuration page")
    @Test
    public void testPreviewDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String previewText = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION_TEXT)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewText, DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("The 'Description' can be added to the Organization Folder from Configuration page")
    @Test
    public void testAddDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String textFromDescription = new MainPage(getDriver())
                .clickConfigureDropDown(ORGANIZATION_FOLDER_NAME, new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .addDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getAddedDescriptionFromConfig();

        Assert.assertEquals(textFromDescription, DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("The 'Display name' can be added to the Organization Folder from Configuration page")
    @Test
    public void testAddDisplayName() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        OrganizationFolderPage orgFolderPage = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .enterDisplayName(DISPLAY_NAME)
                .clickSaveButton();

        Assert.assertEquals(orgFolderPage.getJobName(), DISPLAY_NAME);
        Assert.assertEquals(orgFolderPage.getHeader().clickLogo().getJobName(ORGANIZATION_FOLDER_NAME), DISPLAY_NAME);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("The 'Display name' can be deleted to the Organization Folder from Configuration page")
    @Test
    public void testDeleteDisplayName() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String orgFolderName = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .enterDisplayName(DISPLAY_NAME)
                .clickSaveButton()
                .clickConfigure()
                .clearDisplayName()
                .clickSaveButton()
                .getJobName();

        Assert.assertEquals(orgFolderName, ORGANIZATION_FOLDER_NAME);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("The 'Appearance' icon can be added to the Organization Folder from Configuration page")
    @Test
    public void testAppearanceIconHasChanged() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        boolean defaultIconDisplayed = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .clickAppearance()
                .selectDefaultIcon()
                .clickSaveButton()
                .isDefaultIconDisplayed();

        Assert.assertTrue(defaultIconDisplayed, "The appearance icon was not changed to the default icon");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("The child health metrics can be added to Organization folder")
    @Test
    public void testAddHealthMetricsFromSideMenu() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        boolean isHealthMetricsAdded = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .addHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .healthMetricIsVisible();

        Assert.assertTrue(isHealthMetricsAdded, "Health Metric is not displayed");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Health metrics recursive can be added")
    @Test
    public void testHealthMetricsRecursive() {
        String pipelineName = "pipeline Test";
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME,TestUtils.JobType.OrganizationFolder, true);

        String weatherReport = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .clickHealthMetrics()
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickNewItemFromSideMenu()
                .enterItemName(pipelineName)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .hoverOverWeather(pipelineName)
                .getTooltipDescription();

        Assert.assertEquals(weatherReport, "Build stability: No recent builds failed.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Check Health Metric can be deleted")
    @Test
    public void testDeleteHealthMetricsSideMenu() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        boolean healthMetricIsNotVisible = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .addHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .removeHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .isHealthMetricInvisible();

        Assert.assertTrue(healthMetricIsNotVisible, "Health metrics is disabled");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The 'Script Path' can be edited to the Organization Folder from Configuration page")
    @Test
    public void testConfigureProjectsEditScriptPath() {
        final String scriptPath = "Test Script Path";
        TestUtils.createJob(this,ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String organizationFolderProjectIsPresent = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .clickProjectsSideMenu()
                .enterScriptPath(scriptPath)
                .clickSaveButton()
                .clickConfigure()
                .clickProjectsSideMenu()
                .getScriptPath();
        Assert.assertEquals(organizationFolderProjectIsPresent, scriptPath);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that deleting 'Organization Folder' can be canceled from drop-down menu on the Main page")
    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        boolean isOrganisationFolderDisplayed = new MainPage(getDriver())
                .dropDownMenuClickDeleteFolders(ORGANIZATION_FOLDER_NAME)
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(ORGANIZATION_FOLDER_NAME);

        Assert.assertTrue(isOrganisationFolderDisplayed, "Organisation Folder`s name is not displayed");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that deleting 'Organization Folder' can be canceled from side menu on the Project page")
    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        boolean isOrganisationFolderDisplayed = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickDeleteJobLocatedOnFolderPage()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(ORGANIZATION_FOLDER_NAME);

        Assert.assertTrue(isOrganisationFolderDisplayed, "Organisation Folder`s name is not displayed");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be deleted with the 'Delete' option from drop-down menu on the Main page")
    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, "OrgFolder", TestUtils.JobType.OrganizationFolder, true);

        boolean welcomeToJenkinsIsDisplayed = new MainPage(getDriver())
                .openJobDropDownMenu("OrgFolder")
                .dropDownMenuClickDeleteFolders("OrgFolder")
                .clickYesButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeToJenkinsIsDisplayed, "error, Welcome to Jenkins! is not displayed");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be deleted with the 'Delete' button from side menu on the Project page")
    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, "OrgFolder", TestUtils.JobType.OrganizationFolder, true);

        String welcomeText = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickDeleteJobLocatedOnMainPage()
                .clickYesButton()
                .getWelcomeText();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }
}
