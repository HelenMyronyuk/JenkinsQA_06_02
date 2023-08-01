package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.jobs.*;
import school.redrover.model.jobsConfig.FolderConfigPage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.model.base.BaseJobPage;
import school.redrover.model.jobsConfig.PipelineConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.*;

public class FolderTest extends BaseTest {

    private static final String NAME = "FolderName";
    private static final String RENAME = "Folder";
    private static final String DESCRIPTION = "Created new folder";
    private static final String DESCRIPTION_2 = "Created new Description";
    private static final String DISPLAY_NAME = "NewFolder";

    private void createdJobInFolder(String jobName, String folderName, TestUtils.JobType jobType, BaseConfigPage<?, ?> jobConfigPage) {
        new MainPage(getDriver())
                .clickJobName(folderName, new FolderPage(getDriver()))
                .clickNewItem()
                .enterItemName(jobName)
                .selectJobType(jobType)
                .clickOkButton(jobConfigPage)
                .getHeader()
                .clickLogo();
    }

    @Test
    public void testCreateFromCreateAJob() {
        MainPage mainPage = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Folder)
                .clickOkButton(new FolderConfigPage(new FolderPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.jobIsDisplayed(NAME), "Error: was not show name folder");
        Assert.assertTrue(mainPage.isIconFolderDisplayed(), "Error: was not shown icon folder");
    }

    @Test
    public void testCreateFromNewItem() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        Assert.assertTrue(new MainPage(getDriver()).jobIsDisplayed(NAME), "Error: was not show name folder");
        Assert.assertTrue(new MainPage(getDriver()).isIconFolderDisplayed(), "Error: was not shown icon folder");
    }

    @Test
    public void testCreateFromPeoplePage() {
        MainPage projectName = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Folder)
                .clickOkButton(new FolderConfigPage(new FolderPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(NAME), "Error: the folder name is not displayed");
        Assert.assertTrue(projectName.isIconFolderDisplayed(), "Error: the folder icon is not displayed");
    }

    @Test
    public void testCreateFromBuildHistoryPage() {
        MainPage mainPage = new MainPage(getDriver())
                .clickBuildsHistoryButton()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Folder)
                .clickOkButton(new FolderConfigPage(new FolderPage(getDriver())))
                .clickSaveButton()
                .getBreadcrumb()
                .clickDashboardButton();

        Assert.assertTrue(mainPage.jobIsDisplayed(NAME), "Error: was not show name folder");
        Assert.assertTrue(mainPage.isIconFolderDisplayed(), "Error: was not shown icon folder");
    }

    @Test
    public void testCreateFromManageJenkinsPage() {
        MainPage mainPage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Folder)
                .clickOkButton(new FolderConfigPage(new FolderPage(getDriver())))
                .clickSaveButton()
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.jobIsDisplayed(NAME), "Error: was not show name folder");
        Assert.assertTrue(mainPage.isIconFolderDisplayed(), "Error: was not shown icon folder");
    }

    @Test
    public void testCreateFromMyViewsNewItem(){
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Folder)
                .clickOkButton(new FolderConfigPage(new FolderPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(NAME), "Error: the folder name is not displayed");
        Assert.assertTrue(projectName.clickMyViewsSideMenuLink()
                .jobIsDisplayed(NAME), "Error: the Folder's name is not displayed on Dashboard from MyViews page");
    }

    @Test
    public void testCreateFromMyViewsCreateAJob(){
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJobAndArrow()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Folder)
                .clickOkButton(new FolderConfigPage(new FolderPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(NAME), "Error: the Folder's name is not displayed on Dashboard from Home page");
        Assert.assertTrue(projectName.clickMyViewsSideMenuLink()
                .jobIsDisplayed(NAME), "Error: the Folder's name is not displayed on Dashboard from MyViews page");
    }

    @Test
    public void testCreateWithExistingName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        CreateItemErrorPage errorPage = TestUtils.createJobWithExistingName(this, NAME, TestUtils.JobType.Folder);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "A job already exists with the name ‘" + NAME + "’");
    }

    @DataProvider(name = "invalid-data")
    public Object[][] provideInvalidData() {
        return new Object[][]{{"!"}, {"#"}, {"$"}, {"%"}, {"&"}, {"*"}, {"/"}, {":"},
                {";"}, {"<"}, {">"}, {"?"}, {"@"}, {"["}, {"]"}, {"|"}, {"\\"}, {"^"}};
    }

    @Test(dataProvider = "invalid-data")
    public void testCreateUsingInvalidData(String invalidData) {
        final String expectedErrorMessage = "» ‘" + invalidData + "’ is an unsafe character";

        NewJobPage newJobPage = TestUtils.createFolderUsingInvalidData(this, invalidData, TestUtils.JobType.Folder);

        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "error OK button is enabled");
        Assert.assertEquals(newJobPage.getItemInvalidMessage(), expectedErrorMessage);
    }

    @Test
    public void testCreateWithEmptyName() {
        final String expectedError = "» This field cannot be empty, please enter a valid name";

        String actualError = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .selectJobType(TestUtils.JobType.Folder)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(actualError, expectedError);
    }

    @Test
    public void testCreateWithSpaceInsteadOfName() {
        CreateItemErrorPage errorPage =
                TestUtils.createJobWithSpaceInsteadName(this, TestUtils.JobType.Folder);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "No name is specified");
    }

    @Test
    public void testCreateWithDotInsteadOfName() {
        NewJobPage newJobPage = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(".")
                .selectJobType(TestUtils.JobType.Folder);

        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» “.” is not an allowed name");
        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "error OK button is enabled");
    }

    @Test
    public void testCreateWithLongName() {
        String longName = RandomStringUtils.randomAlphanumeric(256);

        String errorMessage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(longName)
                .selectJobAndOkAndGoToBugPage(TestUtils.JobType.Folder)
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test
    public void testRenameFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        boolean newNameIsDisplayed = new MainPage(getDriver())
                .dropDownMenuClickRename(NAME, new FolderPage(getDriver()))
                .enterNewName(RENAME)
                .clickRenameButton()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(RENAME);

        Assert.assertTrue(newNameIsDisplayed, "Error: was not show new name folder");
    }

    @Test
    public void testRenameFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);
        FolderPage folderPage = new FolderPage(getDriver())
                .clickRename()
                .enterNewName(RENAME)
                .clickRenameButton();

        Assert.assertEquals(folderPage.getJobName(), RENAME);
        Assert.assertEquals(folderPage.getTitle(), "All [" + RENAME + "] [Jenkins]");
    }

    @Test
    public void testRenameToTheCurrentNameAndGetError() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);
        CreateItemErrorPage createItemErrorPage = new FolderPage(getDriver())
                .clickRename()
                .enterNewName(NAME)
                .clickRenameButtonAndGoError();

        Assert.assertEquals(createItemErrorPage.getHeaderText(), "Error");
        Assert.assertEquals(createItemErrorPage.getErrorMessage(), "The new name is the same as the current name.");
    }

    @Test(dataProvider = "invalid-data")
    public void testRenameWithInvalidData(String invalidData) {
        final String expectedErrorMessage = "‘" + invalidData + "’ is an unsafe character";

        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickRename()
                .enterNewName(invalidData)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        switch (invalidData) {
            case "&" -> Assert.assertEquals(actualErrorMessage, "‘&amp;’ is an unsafe character");
            case "<" -> Assert.assertEquals(actualErrorMessage, "‘&lt;’ is an unsafe character");
            case ">" -> Assert.assertEquals(actualErrorMessage, "‘&gt;’ is an unsafe character");
            default -> Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        }
    }

    @Test
    public void testRenameWithDotName() {
        final String expectedErrorMessage = "“.” is not an allowed name";

        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);

        String actualErrorMessage = new FolderPage(getDriver())
                .clickRename()
                .enterNewName(".")
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void testAccessConfigurationPageFromDashboard() {
        final String breadcrumb = "Dashboard > " + NAME + " > Configuration";
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        FolderConfigPage folderConfigPage = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new FolderConfigPage(new FolderPage(getDriver())));

        Assert.assertEquals(folderConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(folderConfigPage.getHeaderText(), "Configuration");
    }

    @Test
    public void testAccessConfigurationPageFromSideMenu() {
        final String breadcrumb = "Dashboard > " + NAME + " > Configuration";
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);
        FolderConfigPage folderConfigPage = new FolderPage(getDriver())
                .clickConfigure();

        Assert.assertEquals(folderConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(folderConfigPage.getHeaderText(), "Configuration");
    }

    @Test
    public void testAddDisplayName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);
        String jobName = new FolderPage(getDriver())
                .clickConfigure()
                .enterDisplayName(DISPLAY_NAME)
                .clickSaveButton()
                .getJobName();

        Assert.assertEquals(jobName, DISPLAY_NAME);
    }

    @Test
    public void testDeleteDisplayName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);
        String folderName = new FolderPage(getDriver())
                .clickConfigure()
                .enterDisplayName(DISPLAY_NAME)
                .clickSaveButton()
                .clickConfigure()
                .clearDisplayName()
                .clickSaveButton()
                .getJobName();

        Assert.assertEquals(folderName, NAME);
    }

    @Test
    public void testAddDescriptionFromConfigurationPage(){
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);
        String descriptionText = new FolderPage(getDriver())
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickSaveButton()
                .getFolderDescription();

        Assert.assertEquals(descriptionText,DESCRIPTION);
    }

    @Test
    public void testPreviewDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        String previewText = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewText, DESCRIPTION);
    }

    @Test
    public void testDeleteDescriptionFromConfigPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        String actualDescription = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new FolderConfigPage(new FolderPage(getDriver())))
                .addDescription(DESCRIPTION)
                .clearDescriptionArea()
                .clickSaveButton()
                .getFolderDescription();

        Assert.assertTrue(actualDescription.isEmpty());
    }

    @Test
    public void testAddHealthMetricsFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        boolean isHealthMetricsAdded =  new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .addHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .healthMetricIsVisible();

        Assert.assertTrue(isHealthMetricsAdded, "Health Metric is not displayed");
    }

    @Test
    public void testHealthMetricWithRecursive() {
        String pipelineName = "BadPipe";
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        new MainPage(getDriver()).
                clickJobName(NAME, new FolderPage(getDriver()));
        String tooltipDescription = new FolderPage(getDriver())
                .clickConfigure()
                .addHealthMetrics()
                .clickSaveButton()
                .clickNewItem()
                .selectJobType(TestUtils.JobType.Pipeline)
                .enterItemName(pipelineName)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .inputInScriptField("Broken")
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .hoverOverWeather(NAME)
                .getTooltipDescription();

        Assert.assertEquals(tooltipDescription,
                "Worst health: " + NAME + " » " +  pipelineName + ": Build stability: All recent builds failed.");
    }

    @Test
    public void testDeleteHealthMetrics() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        boolean healthMetric = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
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

        Assert.assertTrue(healthMetric, "The deleted health metric is visible!");
    }

    @Ignore
    @Test
    public void testAddedPipelineLibrary() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        String defaultVersion = "main";
        String repoUrl = "https://github.com/darinpope/github-api-global-lib.git";

        boolean isVersionValidated = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .inputNameLibrary()
                .inputDefaultVersion(defaultVersion)
                .pushSourceCodeManagementButton()
                .chooseOptionGitHub()
                .inputLibraryRepoUrl(repoUrl)
                .pushApply()
                .refreshPage()
                .libraryDefaultVersionValidated();

        Assert.assertTrue(isVersionValidated, "Cannot validate default version");
    }

    @Test
    public void testAddDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        FolderPage folderPage = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription();

        Assert.assertEquals(folderPage.getDescriptionText(), DESCRIPTION);
        Assert.assertEquals(folderPage.getDescriptionButton(), "Edit description");
    }

    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        String previewText = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .clickAddOrEditDescription()
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewText, DESCRIPTION);
    }

    @Test
    public void testEditDescription() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        String newDescription = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(DESCRIPTION_2)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(newDescription, DESCRIPTION_2);
    }

    @Test
    public void testCreateJobsInFolder() {
        Map<String, BaseJobPage<?>> jobMap = TestUtils.getJobMap(this);

        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        for (Map.Entry<String, BaseJobPage<?>> entry : TestUtils.getJobMap(this).entrySet()) {
            createdJobInFolder(entry.getKey(), NAME, TestUtils.JobType.valueOf(entry.getKey()),
                    new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())));
        }

        List<String> createdJobList = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .getJobList();

        List<String> jobNameList = new ArrayList<>(jobMap.keySet());

        Assert.assertEquals(jobNameList.size(), createdJobList.size());
        Assert.assertTrue(createdJobList.containsAll(jobNameList));
    }

    @DataProvider(name = "jobType")
    public Object[][] JobTypes() {
        return new Object[][]{
                {TestUtils.JobType.FreestyleProject},
                {TestUtils.JobType.Pipeline},
                {TestUtils.JobType.MultiConfigurationProject},
                {TestUtils.JobType.Folder},
                {TestUtils.JobType.MultibranchPipeline},
                {TestUtils.JobType.OrganizationFolder}};
    }

    @Test(dataProvider = "jobType")
    public void testMoveJobToFolderFromDropDownMenu(TestUtils.JobType jobType) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        TestUtils.createJob(this, jobType.name(), jobType, true);

        boolean isJobDisplayed = new MainPage(getDriver())
                .dropDownMenuClickMove(jobType.name(), new FolderPage(getDriver()))
                .selectDestinationFolder(NAME)
                .clickMoveButton()
                .getHeader()
                .clickLogo()
                .clickJobName(NAME, new FolderPage(getDriver()))
                .jobIsDisplayedF(jobType.name());

        Assert.assertTrue(isJobDisplayed, "Job is not present in Folder");
    }

    @Test(dataProvider = "jobType")
    public void testMoveJobsToFolderFromSideMenu(TestUtils.JobType jobType) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        TestUtils.createJob(this, jobType.name(), jobType, true);

        boolean isJobDisplayed = new MainPage(getDriver())
                    .clickJobName(jobType.name(), new FolderPage(getDriver()))
                    .clickMoveOnSideMenu()
                    .selectDestinationFolder(NAME)
                    .clickMoveButton()
                    .getHeader()
                    .clickLogo()
                    .clickJobName(NAME, new FolderPage(getDriver()))
                    .jobIsDisplayedF(jobType.name());

        Assert.assertTrue(isJobDisplayed, "Job is not present in Folder");
    }

    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        boolean folderIsDisplayed = new MainPage(getDriver())
                .dropDownMenuClickDeleteFolders(NAME)
                .getBreadcrumb()
                .clickDashboardButton()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(folderIsDisplayed, "error was not show name folder");
    }

    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        boolean folderIsDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickDeleteJobThatIsMainPage()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(folderIsDisplayed, "error was not show name folder");
    }

    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);
        MainPage welcomeIsDisplayed = new MainPage(getDriver())
                .dropDownMenuClickDeleteFolders(NAME)
                .clickYesButton();

        Assert.assertTrue(welcomeIsDisplayed.isWelcomeDisplayed());
        Assert.assertEquals(welcomeIsDisplayed.clickMyViewsSideMenuLink().getStatusMessageText(), "This folder is empty");
    }

    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        boolean welcomeIsDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickDeleteJobThatIsMainPage()
                .clickYesButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeIsDisplayed, "error was not show Welcome to Jenkins!");
    }
}
