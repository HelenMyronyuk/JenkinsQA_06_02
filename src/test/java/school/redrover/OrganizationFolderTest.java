package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.jobs.OrganizationFolderPage;
import school.redrover.model.jobsConfig.OrganizationFolderConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class OrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = "OrgFolder";
    private static final String ORGANIZATION_FOLDER_RENAMED = "OrgFolderNew";
    private static final String PRINT_MESSAGE_PIPELINE_SYNTAX = "TEXT";
    private static final String DESCRIPTION_TEXT = "DESCRIPTION_TEXT";
    private static final String DISPLAY_NAME = "This is Display Name of Folder";

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

    @Test
    public void testCreateFromNewItem() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        boolean actualNewFolderName = new MainPage(getDriver())
                .jobIsDisplayed(ORGANIZATION_FOLDER_NAME);

        Assert.assertTrue(actualNewFolderName, "error was not show name folder");
    }

    @Test
    public void testCreateFromPeoplePage() {
        MainPage projectPeoplePage = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .clickOkButton(new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectPeoplePage.jobIsDisplayed(ORGANIZATION_FOLDER_NAME));
    }

    @Test
    public void testCreateFromBuildHistoryPage() {
        boolean newProjectFromBuildHistoryPage = new MainPage(getDriver())
                .clickBuildsHistoryButton()
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .clickOkButton(new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(ORGANIZATION_FOLDER_NAME);

        Assert.assertTrue(newProjectFromBuildHistoryPage, "Error: the Organization Folder name is not displayed on Dashboard");
    }

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

    @Test
    public void testCreateFromMyViewsNewItem() {
        String newOrganizationFolderName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewItem()
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

    @Test(dataProvider = "wrong-character")
    public void testCreateUsingInvalidData(String invalidData) {
        NewJobPage newJobPage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(invalidData)
                .selectJobType(TestUtils.JobType.OrganizationFolder);

        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "Save button is enabled");
        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» ‘" + invalidData + "’ is an unsafe character");
    }

    @Test
    public void testCreateWithEmptyName() {
        final String expectedError = "» This field cannot be empty, please enter a valid name";

        String actualError = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .selectJobType(TestUtils.JobType.OrganizationFolder)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(actualError, expectedError);
    }

    @Test
    public void testCreateWithSpaceInsteadOfName() {
        CreateItemErrorPage errorPage =
                TestUtils.createJobWithSpaceInsteadName(this, TestUtils.JobType.OrganizationFolder);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "No name is specified");
    }

    @Test
    public void testCreateWithDotInsteadOfName() {
        String errorMessage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(".")
                .getItemInvalidMessage();

        Assert.assertEquals(errorMessage, "» “.” is not an allowed name");
    }

    @Test
    public void testCreateWithLongName() {
        String longName = RandomStringUtils.randomAlphanumeric(256);
        String errorMessage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(longName)
                .selectJobAndOkAndGoToBugPage(TestUtils.JobType.OrganizationFolder)
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

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

    @Test
    public void testConfigureProject() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, false);

        String configurationHeaderText = new OrganizationFolderPage(getDriver())
                .clickConfigureProject()
                .getPageHeaderText();

        Assert.assertEquals(configurationHeaderText, "Configuration");
    }

    @Test
    public void testRerunFolderComputation() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String headerScanOrganizationFolder = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickRerunTheFolderComputation()
                .getPageHeaderText();

        Assert.assertEquals(headerScanOrganizationFolder, "Scan Organization Folder");
    }

    @Test
    public void testCreatingJenkinsPipeline() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, false);
        String linkBookCreatingPipeline = new OrganizationFolderPage(getDriver())
                .getTextCreatingJenkinsPipeline();

        String pipelineOneTutorial = new OrganizationFolderPage(getDriver())
                .clickPipelineOneTutorial()
                .getTextPipelineTitle();

        Assert.assertEquals(linkBookCreatingPipeline, "Creating a Jenkins Pipeline");
        Assert.assertEquals(pipelineOneTutorial, "Pipeline");
    }

    @Test
    public void testCreateMultibranchProject() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, false);

        String createMultibranchProject = new OrganizationFolderPage(getDriver())
                .clickMultibranchProject()
                .getBranchesAndPullRequestsTutorial();

        Assert.assertEquals(createMultibranchProject, "Branches and Pull Requests");
    }

    @Test
    public void testScanOrgFolderLog() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String titleScanOrgFolderLogPage = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickScanLog()
                .getTextFromTitle();

        Assert.assertEquals(titleScanOrgFolderLogPage, "Scan Organization Folder Log");
    }

    @Test
    public void testOrganizationFolderEvents() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String eventTitle = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickEventsLink()
                .getPageHeaderText();

        Assert.assertEquals(eventTitle, "Organization Folder Events");
    }

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

    @Test
    public void testCredentials() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String titleCredentials = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickCredentials()
                .getTitleText();

        Assert.assertEquals(titleCredentials, "Credentials");
    }

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

    @Test
    public void testDisableFromProjectPage() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String disabledText = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickDisableEnableButton()
                .getTextFromDisableMessage();

        Assert.assertEquals(disabledText.substring(0, 46), "This Organization Folder is currently disabled");
    }

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

    @Test
    public void testAccessConfigurationPageFromDropDown() {
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String getHeaderText = new MainPage(getDriver())
                .clickConfigureDropDown(
                        ORGANIZATION_FOLDER_NAME, new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())))
                .getHeaderText();

        Assert.assertEquals(getHeaderText, "Configuration");
    }

    @Test
    public void testAccessConfigurationPageFromSideMenu(){
        TestUtils.createJob(this, ORGANIZATION_FOLDER_NAME, TestUtils.JobType.OrganizationFolder, true);

        String getHeaderText = new MainPage(getDriver())
                .clickJobName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderPage(getDriver()))
                .clickConfigure()
                .getHeaderText();

        Assert.assertEquals(getHeaderText, "Configuration");
    }

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
