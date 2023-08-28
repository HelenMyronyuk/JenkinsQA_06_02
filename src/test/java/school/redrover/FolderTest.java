package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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

    @DataProvider(name = "invalid-data")
    public Object[][] provideInvalidData() {
        return new Object[][]{{"!"}, {"#"}, {"$"}, {"%"}, {"&"}, {"*"}, {"/"}, {":"},
                {";"}, {"<"}, {">"}, {"?"}, {"@"}, {"["}, {"]"}, {"|"}, {"\\"}, {"^"}};
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the Folder can be renamed from drop down menu on the Main page")
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

        Assert.assertTrue(newNameIsDisplayed, "The new name of the Folder is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the Folder can be renamed from side menu on the Project page")
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

    @Severity(SeverityLevel.NORMAL)
    @Description("Verification of impossibility to rename Folder project with existing name")
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

    @Severity(SeverityLevel.NORMAL)
    @Description("Verification of impossibility to rename Folder project with invalid data")
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

    @Severity(SeverityLevel.NORMAL)
    @Description("Verification of impossibility to rename Folder project with '.' name'")
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

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigation to Configuration Page from drop-down menu on Dashboard for Folder Project")
    @Test
    public void testAccessConfigurationPageFromDashboard() {
        final String breadcrumb = "Dashboard > " + NAME + " > Configuration";
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        FolderConfigPage folderConfigPage = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new FolderConfigPage(new FolderPage(getDriver())));

        Assert.assertEquals(folderConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(folderConfigPage.getHeaderText(), "Configuration");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigation to Configuration Page from side menu for Folder Project")
    @Test
    public void testAccessConfigurationPageFromSideMenu() {
        final String breadcrumb = "Dashboard > " + NAME + " > Configuration";
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);

        FolderConfigPage folderConfigPage = new FolderPage(getDriver())
                .clickConfigure();

        Assert.assertEquals(folderConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(folderConfigPage.getHeaderText(), "Configuration");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that Folder's 'Display name' can be added from Configuration page")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that Folder's 'Display name' can be deleted from Configuration page")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that Folder description is  added from the Configuration page")
    @Test
    public void testAddDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, false);

        String descriptionText = new FolderPage(getDriver())
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickSaveButton()
                .getFolderDescription();

        Assert.assertEquals(descriptionText, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that Folder description is previewed on the Configuration page")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that Folder's Description can be deleted from the Configuration page")
    @Test
    public void testDeleteDescriptionFromConfigPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        String actualDescription = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new FolderConfigPage(new FolderPage(getDriver())))
                .addDescription(DESCRIPTION)
                .clearDescriptionArea()
                .clickSaveButton()
                .getFolderDescription();

        Assert.assertTrue(actualDescription.isEmpty(), "The Folder's description is not empty");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the health metrics can be added to Folder from side menu")
    @Test
    public void testAddHealthMetricsFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        boolean isHealthMetricsAdded = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .addHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .healthMetricIsVisible();

        Assert.assertTrue(isHealthMetricsAdded, "The Health Metric is not displayed");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify tool tip Description after adding the health metrics With Recursive to Folder")
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
                "Worst health: " + NAME + " » " + pipelineName + ": Build stability: All recent builds failed.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that Folder's health metrics can be deleted through the Configuration page")
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

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that Pipeline Library can be added to Folder description through the Configuration page")
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
                .openSourceCodeManagementDropdown()
                .selectOptionGitHub()
                .inputLibraryRepoUrl(repoUrl)
                .pushApply()
                .refreshPage()
                .libraryDefaultVersionValidated();

        Assert.assertTrue(isVersionValidated, "Cannot validate default version");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that Folder description is  added from the Project page")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that Folder description is previewed on the Project page")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that Folder description can be edit")
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

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that Jobs can be created in Folder")
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
        Assert.assertTrue(createdJobList.containsAll(jobNameList), "The Folder page contains the list of all jobs");
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

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that created Projects can be moved to Folder from drop-down menu on the Main page")
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

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that created Projects can be moved to Folder from side menu on the Project page")
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

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that deleting Folder can be canceled from drop-down menu on the Main page")
    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        boolean folderIsDisplayed = new MainPage(getDriver())
                .dropDownMenuClickDeleteFolders(NAME)
                .getBreadcrumb()
                .clickDashboardButton()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(folderIsDisplayed, "The Folder's name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that deleting Folder can be canceled from side menu on the Project page")
    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        boolean folderIsDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickDeleteJobThatIsMainPage()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(folderIsDisplayed, "The Folder's name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the Folder can be deleted with the 'Delete' option from drop-down menu on the Main page")
    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        MainPage welcomeIsDisplayed = new MainPage(getDriver())
                .dropDownMenuClickDeleteFolders(NAME)
                .clickYesButton();

        Assert.assertTrue(welcomeIsDisplayed.isWelcomeDisplayed(), "'Welcome to Jenkins!' text is not displayed");
        Assert.assertEquals(welcomeIsDisplayed.clickMyViewsSideMenuLink().getStatusMessageText(), "This folder is empty");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the Folder can be deleted with the 'Delete' button from side menu on the Project page")
    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Folder, true);

        boolean welcomeIsDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new FolderPage(getDriver()))
                .clickDeleteJobThatIsMainPage()
                .clickYesButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeIsDisplayed, "'Welcome to Jenkins!' text is not displayed");
    }
}
