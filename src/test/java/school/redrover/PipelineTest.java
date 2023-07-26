package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobsconfig.PipelineConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Arrays;
import java.util.List;

public class PipelineTest extends BaseTest {

    private static final String NAME = "PIPELINE_NAME";
    private static final String NEW_NAME = "Pipeline Project";
    private static final String DESCRIPTION = "This is a test description";
    private static final String DISPLAYED_BUILD_NAME = "New Build Name";

    @Test
    public void testCreateFromCreateAJob() {
        MainPage mainPage = new MainPage(getDriver())
                .clickCreateAJob()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.projectStatusTableIsDisplayed());
        Assert.assertEquals(mainPage.getJobName(NAME), NAME);
    }

    @Test
    public void testCreateFromCreateAJobArrow() {
        boolean freestyleProjectNameIsAppeared = new MainPage(getDriver())
                .clickCreateAJobArrow()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(freestyleProjectNameIsAppeared, "Error! Job Is Not Displayed");
    }

    @Test
    public void testCreateFromNewItem() {
        String projectName = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .getJobName(NAME);

        Assert.assertEquals(projectName, NAME);
    }

    @Test
    public void testCreateFromPeoplePage() {
        MainPage projectPeoplePage = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectPeoplePage.jobIsDisplayed(NAME));
    }

    @Test
    public void testCreateFromBuildHistoryPage() {
        MainPage newProjectFromBuildHistoryPage = new BuildHistoryPage(getDriver())
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(newProjectFromBuildHistoryPage.jobIsDisplayed(NAME));
    }

    @Test
    public void testCreateFromManageJenkinsPage() {
        List<String> jobList = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .getJobList();

        Assert.assertTrue(jobList.contains(NAME));
    }

    @Test
    public void testCreateFromMyViewsCreateAJob() {
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJob()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(NAME), "Error: the Pipeline Project's name is not displayed on Dashboard from Home page");
        Assert.assertTrue(projectName.clickMyViewsSideMenuLink()
                .jobIsDisplayed(NAME), "Error: the FPipeline Project's name is not displayed on Dashboard from MyViews page");
    }

    @Test
    public void testCreateFromMyViewsCreateAJobArrow() {
        MainPage mainPage = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJobArrow()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.jobIsDisplayed(NAME));
        Assert.assertTrue(mainPage.clickMyViewsSideMenuLink().verifyJobIsPresent(NAME));
    }

    @Test
    public void testCreateFromMyViewsNewItem() {
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(NAME), "Error: the pipeline name is not displayed");
    }

    @Test
    public void testCreateWithExistingName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        NewJobPage newJobPage =
                TestUtils.createJobWithExistingNameWithoutClickOk(this, NAME, TestUtils.JobType.Pipeline);

        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» A job already exists with the name " + "‘" + NAME + "’");
        Assert.assertTrue(newJobPage.isOkButtonEnabled(), "error OK button is disabled");
    }

    @DataProvider(name = "invalid-characters")
    public Object[][] providerInvalidCharacters() {
        return new Object[][]{{"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {">"}, {"["}, {"]"}};
    }

    @Test(dataProvider = "invalid-characters")
    public void testCreateUsingInvalidDate(String invalidCharacters) {
        NewJobPage newJobPage =
                TestUtils.createFolderUsingInvalidData(this, invalidCharacters, TestUtils.JobType.Pipeline);

        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» ‘" + invalidCharacters + "’ is an unsafe character");
        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "error OK button is enabled");
    }

    @Test
    public void testCreateWithEmptyName() {
        final String expectedError = "» This field cannot be empty, please enter a valid name";

        String actualError = new MainPage(getDriver())
                .clickCreateAJobArrow()
                .selectJobType(TestUtils.JobType.Pipeline)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(actualError, expectedError);
    }

    @Test
    public void testCreateWithSpaceInsteadOfName() {
        CreateItemErrorPage createItemErrorPage =
                TestUtils.createJobWithSpaceInsteadName(this, TestUtils.JobType.Pipeline);

        Assert.assertEquals(createItemErrorPage.getHeaderText(), "Error");
        Assert.assertEquals(createItemErrorPage.getErrorMessage(), "No name is specified");
    }

    @Test
    public void testCreateWithDotInsteadOfName() {
        String getMessage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(".")
                .getItemInvalidMessage();

        Assert.assertEquals(getMessage, "» “.” is not an allowed name");
    }

    @Test
    public void testCreateWithLongName() {
        String longName = RandomStringUtils.randomAlphanumeric(256);

        String errorMessage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(longName)
                .selectJobAndOkAndGoToBugPage(TestUtils.JobType.Pipeline)
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test
    public void testCreateWithAllowedCharacters() {
        final String allowedChar = "_-+=”{},";

        String projectNameDashboard = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(allowedChar)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .getJobName(allowedChar);

        Assert.assertEquals(projectNameDashboard, allowedChar);
    }

    @Test
    public void testSortingPipelineProjectAlphabetically() {
        List<String> namesOfJobs = Arrays.asList("UProject", "SProject", "AProject");

        TestUtils.createJob(this, namesOfJobs.get(1), TestUtils.JobType.Pipeline, true);
        TestUtils.createJob(this, namesOfJobs.get(2), TestUtils.JobType.Pipeline, true);
        TestUtils.createJob(this, namesOfJobs.get(0), TestUtils.JobType.Pipeline, true);

        List<String> listNamesOfJobs = new MainPage(getDriver())
                .clickSortByName()
                .getJobList();

        Assert.assertEquals(listNamesOfJobs, namesOfJobs);
    }

    @Test
    public void testCreatingBasicPipelineProjectThroughJenkinsUI() {
        String resultOptionDefinitionFieldText = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .scrollToPipelineSection()
                .getOptionTextInDefinitionField();

        Assert.assertEquals(resultOptionDefinitionFieldText, "Pipeline script");
    }

    @Test
    public void testRenameFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String renamedPipeline = new MainPage(getDriver())
                .dropDownMenuClickRename(NAME.replaceAll(" ", "%20"), new PipelinePage(getDriver()))
                .enterNewName(NEW_NAME)
                .clickRenameButton()
                .getHeader()
                .clickLogo()
                .getJobName(NEW_NAME);

        Assert.assertEquals(renamedPipeline, NEW_NAME);
    }

    @Test
    public void testRenameFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String projectName = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickRename()
                .enterNewName(NEW_NAME)
                .clickRenameButton()
                .getHeader()
                .clickLogo()
                .getJobName(NEW_NAME);

        Assert.assertEquals(projectName, NEW_NAME);
    }

    @Test
    public void testRenameToTheCurrentNameAndGetError() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String errorMessage = new MainPage(getDriver())
                .dropDownMenuClickRename(NAME, new PipelinePage(getDriver()))
                .enterNewName(NAME)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "The new name is the same as the current name.");
    }

    @DataProvider(name = "wrong-character")
    public Object[][] provideWrongCharacters() {
        return new Object[][]{{"!", "!"}, {"@", "@"}, {"#", "#"}, {"$", "$"}, {"%", "%"}, {"^", "^"}, {"&", "&amp;"}, {"*", "*"},
                {"?", "?"}, {"|", "|"}, {">", "&gt;"}, {"<", "&lt;"}, {"[", "["}, {"]", "]"}};
    }

    @Test(dataProvider = "wrong-character")
    public void testRenameWithInvalidData(String invalidData, String expectedResult) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickRename()
                .enterNewName(invalidData)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "‘" + expectedResult + "’ is an unsafe character");
    }

    @Test
    public void testCreateBuildWithParameters() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        ConsoleOutputPage consoleOutputPage = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickTrend()
                .clickBuildIcon();

        Assert.assertTrue(consoleOutputPage.isDisplayedGreenIconV(), "Build failed");
        Assert.assertTrue(consoleOutputPage.isDisplayedBuildTitle(), "Not found build");
    }

    @Test
    public void testCreateBuildNowFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String createBuildNow = new MainPage(getDriver())
                .clickJobDropdownMenuBuildNow(NAME)
                .getHeader()
                .clickLogoWithPause()
                .getLastBuildIconStatus();

        Assert.assertEquals(createBuildNow, "Success");
    }

    @Test
    public void testCreateBuildNowFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "build not created");
    }

    @Test
    public void testCreateBuildNowFromArrow() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "Build is not created");
    }

    @Test
    public void testAddDisplayNameForBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        Boolean newDisplayedBuildName = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDisplayName(DISPLAYED_BUILD_NAME)
                .clickSaveButton()
                .getBuildHeaderText()
                .contains(DISPLAYED_BUILD_NAME);

        Assert.assertTrue(newDisplayedBuildName, "Added Name for the Build is not displayed");
    }

    @Test
    public void testPreviewDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String previewText = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditDescription()
                .enterDescriptionText(DESCRIPTION)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewText, DESCRIPTION);
    }

    @Test
    public void testAddDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String newBuildDescription = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickEditDescription()
                .enterDescriptionText(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(newBuildDescription, DESCRIPTION);
    }

    @Test
    public void testEditDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String newBuildDescription = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildFromSideMenu(NAME, 1)
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .clickEditDescription()
                .clearDescriptionText()
                .enterDescriptionText(NEW_NAME)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(newBuildDescription, NEW_NAME);
    }

    @Test
    public void testBuildChangesFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String titleChange = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .getHeader()
                .clickLogo()
                .openBuildDropDownMenu("#1")
                .clickChangesBuildFromDropDown()
                .getTextChanges();

        Assert.assertEquals(titleChange, "Changes");
    }

    @Test
    public void testBuildChangesFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String text = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickChangesViaLastBuildDropDownMenu()
                .getPageHeaderText();

        Assert.assertEquals(text, "Changes");
    }

    @Test
    public void testBuildChangesFromProjectPage() {
        final String title = "Changes";
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String changesTitle = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickChangesFromDropDownMenu()
                .getPageHeaderText();

        Assert.assertEquals(changesTitle, title);
    }

    @Test
    public void testConsoleOutputFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean consoleOutputTitle = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .openBuildsDropDownMenu()
                .clickConsoleOutputType()
                .isDisplayedBuildTitle();

        Assert.assertTrue(consoleOutputTitle, "Error: Console Output Title is not displayed!");
    }

    @Test
    public void testConsoleOutputFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean consoleOutput = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .openBuildsDropDownMenu()
                .clickConsoleOutputType()
                .isDisplayedBuildTitle();

        Assert.assertTrue(consoleOutput, "Console output page is not displayed");
    }

    @Test
    public void testConsoleOutputFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        PipelinePage pipelineJob = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()));

        String lastBuildNumber = pipelineJob
                .getLastBuildNumber();

        ConsoleOutputPage consoleOutput = pipelineJob
                .clickLastBuildLink()
                .clickConsoleOutput();

        String breadcrumb = consoleOutput
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertTrue(consoleOutput.isDisplayedBuildTitle(), "Console output page is not displayed");
        Assert.assertTrue(breadcrumb.contains(lastBuildNumber));
    }

    @Test
    public void testConsoleOutputFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean consoleOutputTitleDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickConsoleOutput()
                .isDisplayedBuildTitle();

        Assert.assertTrue(consoleOutputTitleDisplayed, "Error: Console Output Title is not displayed!");
    }

    @Test
    public void testEditBuildInformationFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String getTitle = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .getHeader()
                .clickLogo()
                .openBuildDropDownMenu("#1")
                .clickEditBuildInformFromDropDown()
                .getTitle();

        Assert.assertEquals(getTitle, "Edit Build Information");
    }

    @Test
    public void testEditBuildInformationFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String titleEditBuildPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickEditBuildInformFromProjectPage()
                .getTitle();

        Assert.assertEquals(titleEditBuildPage, "Edit Build Information");
    }

    @Test
    public void testEditBuildInformationFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String textPageFromBreadcrumb = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .editBuildInfoPermalinksLastBuildDropDown()
                .getTitle();

        Assert.assertEquals(textPageFromBreadcrumb, "Edit Build Information");
    }

    @Test
    public void testEditBuildInformationFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String testPageFromBreadcrumb = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .getTitle();

        Assert.assertEquals(testPageFromBreadcrumb, "Edit Build Information");
    }

    @Test
    public void testPreviewDescriptionFromEditInformationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        String previewDescriptionText = new PipelinePage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION)
                .clickPreviewButton()
                .getPreviewText();

        Assert.assertEquals(previewDescriptionText, DESCRIPTION);
    }

    @Test
    public void testAddDescriptionFromEditInformationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String descriptionText = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildFromSideMenu(NAME, 1)
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, DESCRIPTION);
    }

    @Test
    public void testDeleteBuildNowFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean noBuildsMessage = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildDropdownMenuDeleteBuild("#1")
                .clickDelete(new PipelinePage(getDriver()))
                .isNoBuildsDisplayed();

        Assert.assertTrue(noBuildsMessage, "Error");
    }

    @Test
    public void testDeleteBuildNowFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean noBuildsMessage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickDeleteBuild(new PipelinePage(getDriver()))
                .clickYesButton()
                .isNoBuildsDisplayed();

        Assert.assertTrue(noBuildsMessage, "error! No builds message is not display");
    }

    @Test
    public void testDeleteBuildNowFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean buildMessage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .deleteBuildPermalinksLastBuildDropDown()
                .clickYesButton()
                .isNoBuildsDisplayed();

        Assert.assertTrue(buildMessage, "error! No builds message is not display");
    }

    @Test
    public void testDeleteBuildNowFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean noBuildsMessage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickDeleteBuild(new PipelinePage(getDriver()))
                .clickYesButton()
                .isNoBuildsDisplayed();

        Assert.assertTrue(noBuildsMessage, "error! No builds message is not display");
    }

    @Test
    public void testReplayBuildFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String lastBuildNumber = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .openPermalinksLastBuildsDropDownMenu()
                .clickReplayFromDropDownMenu()
                .clickRunButton()
                .getLastBuildNumber();

        Assert.assertEquals(lastBuildNumber, "#2");
    }

    @Ignore
    @Test
    public void testReplayBuildFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String lastBuildNumber = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .openBuildsDropDownMenu()
                .clickReplayFromDropDownMenu()
                .clickRunButton()
                .getLastBuildNumber();

        Assert.assertEquals(lastBuildNumber, "#2");
    }

    @Test
    public void testReplayBuildFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String lastBuildNumber = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .clickReplay(new PipelinePage(getDriver()))
                .clickRunButton()
                .getLastBuildNumber();

        Assert.assertEquals(lastBuildNumber, "#3");
    }

    @Test
    public void testReplayBuildFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String lastBuildNumber = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickReplay(new PipelinePage(getDriver()))
                .clickRunButton()
                .getLastBuildNumber();

        Assert.assertEquals(lastBuildNumber, "#2");
    }

    @Test
    public void testWorkspacesBuildFromDropDown() {
        final String pageHeaderText = "Workspaces for " + NAME + " #1";

        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String actualPageHeaderText = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .getHeader()
                .clickLogo()
                .openLastBuildDropDownMenu()
                .clickWorkspacesLastBuildDropDown()
                .getHeaderTextFromWorkspacesBuildPage();

        Assert.assertEquals(actualPageHeaderText, pageHeaderText);
    }

    @Test
    public void testWorkspacesBuildFromProjectPage() {
        final String pageHeaderText = "Workspaces for " + NAME + " #1";

        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String actualPageHeaderText = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .openBuildsDropDownMenu()
                .clickWorkspaceButtonFromBuildDropDown()
                .getHeaderTextFromWorkspacesBuildPage();

        Assert.assertEquals(actualPageHeaderText, pageHeaderText);
    }

    @Test
    public void testWorkspacesBuildFromLastBuild() {
        final String pageHeaderText = "Workspaces for " + NAME + " #1";

        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String actualPageHeaderText = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .openPermalinksLastBuildsDropDownMenu()
                .clickWorkspaceButtonFromBuildDropDown()
                .getHeaderTextFromWorkspacesBuildPage();

        Assert.assertEquals(actualPageHeaderText, pageHeaderText);
    }

    @Test
    public void testMakeSeveralBuilds() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        List<String> buildNumberExpected = Arrays.asList("#1", "#2", "#3", "#4");

        List<String> buildNumber = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildNowFromSideMenu()
                .clickBuildNowFromSideMenu()
                .clickBuildNowFromSideMenu()
                .clickTrend()
                .getBuildNumbers(4);

        Assert.assertEquals(buildNumber, buildNumberExpected);
    }

    @Test
    public void testSelectHelloAndConsoleOutputSuccess() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String textSuccessBuild = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .clickScriptDropDownMenu()
                .selectHelloWord()
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .getConsoleOutputText();

        Assert.assertTrue(textSuccessBuild.contains("Finished: SUCCESS"), "Job does not finished success");
    }

    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String previewDescription = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickAddDescription()
                .enterDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewDescription, DESCRIPTION);
    }

    @Test
    public void testPipelineBuildNow() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String stageName = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .clickScriptDropDownMenu()
                .selectHelloWord()
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getStage();

        Assert.assertEquals(stageName, "Hello");
    }

    @Test
    public void testAddDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String resultDescriptionText = new PipelinePage(getDriver())
                .clickEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(resultDescriptionText, DESCRIPTION);
    }

    @Test
    public void testDisableFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String jobStatus = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickDisable()
                .getHeader()
                .clickLogo()
                .getJobBuildStatusIcon(NAME);

        Assert.assertEquals(jobStatus, "Disabled");
    }

    @Test
    public void testEnableFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String jobStatus = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickDisable()
                .getHeader()
                .clickLogo()
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickEnable()
                .getHeader()
                .clickLogo()
                .getJobBuildStatusIcon(NAME);

        Assert.assertEquals(jobStatus, "Not built");
    }

    @Test
    public void testProjectChangesFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String text = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickChangeOnLeftSideMenu()
                .getTextOfPage();

        Assert.assertTrue(text.contains("No changes in any of the builds"),
                "In the Pipeline Changes chapter, not displayed status of the latest build.");
    }

    @Test
    public void testPipelineBuildingAfterChangesInCode() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        BuildPage buildPage = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .clickPipelineLeftMenu()
                .clickScriptDropDownMenu()
                .selectHelloWord()
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink();

        Assert.assertTrue(buildPage.isDisplayedBuildTitle(), "Build #1 failed");
        Assert.assertTrue(buildPage.isDisplayedGreenIconV(), "Build #1 failed");
    }

    @Test
    public void testAccessConfigurationPageFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String configPageHeaderText = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new PipelineConfigPage(new PipelinePage(getDriver())))
                .getPageHeaderText();

        Assert.assertEquals(configPageHeaderText, "Configure");
    }

    @Test
    public void testAccessConfigurationPageFromSideMenu() {
        final String breadcrumb = "Dashboard > " + NAME + " > Configuration";
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);
        PipelineConfigPage pipelineConfigPage = new PipelinePage(getDriver())
                .clickConfigure();

        Assert.assertEquals(pipelineConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(pipelineConfigPage.getTitle(), "Configure");
    }

    @Test
    public void testDisableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean projectDisable = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .toggleDisableProject()
                .clickSaveButton()
                .clickConfigure()
                .isProjectDisable();

        Assert.assertFalse(projectDisable, "Pipeline is enabled");
    }

    @Test
    public void testEnableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String disableButtonText = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickDisable()
                .clickConfigure()
                .clickSwitchEnableOrDisable()
                .clickSaveButton()
                .getDisableButtonText();

        Assert.assertEquals(disableButtonText, "Disable Project");
    }

    @Test
    public void testEditDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);
        final String newDescription = "Edited description text";

        String jobDescription = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickSaveButton()
                .clickEditDescription()
                .clearDescriptionField()
                .enterDescription(newDescription)
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(jobDescription, newDescription);
    }

    @Test
    public void testPreviewDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String textPreview = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewText();
        Assert.assertEquals(textPreview, DESCRIPTION);
    }

    @Test
    public void testAddDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String jobDescription = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(jobDescription, DESCRIPTION);
    }

    @Test
    public void testDiscardOldBuildsIsChecked() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean discardOldBuildsCheckbox = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .selectDiscardOldBuildsandSave()
                .clickConfigure()
                .checkboxDiscardOldBuildsIsSelected();

        Assert.assertTrue(discardOldBuildsCheckbox);
    }

    @Test
    public void testDiscardOldBuildsPipeline() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        String jobName = new PipelinePage(getDriver())
                .clickConfigure()
                .clickDiscardOldBuildsCheckbox()
                .enterDaysToKeepBuilds("2")
                .enterMaxOfBuildsToKeep("30")
                .clickSaveButton()
                .getJobName();

        Assert.assertEquals(jobName, "Pipeline " + NAME);
    }

    @Test
    public void testDiscardOldBuildsParams() {
        final String days = "7";
        final String builds = "5";

        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        PipelineConfigPage pipelineConfigPage = new PipelinePage(getDriver())
                .clickConfigure()
                .clickDiscardOldBuildsCheckbox()
                .enterDaysToKeepBuilds(days)
                .enterMaxOfBuildsToKeep(builds)
                .clickSaveButton()
                .clickConfigure();

        Assert.assertEquals(pipelineConfigPage.getDaysToKeepBuilds(), days);
        Assert.assertEquals(pipelineConfigPage.getMaxNumbersOfBuildsToKeep(), builds);
    }

    @Test
    public void testDiscardOldBuilds0Days() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        String actualErrorMessage = new PipelinePage(getDriver())
                .clickConfigure()
                .clickDiscardOldBuildsCheckbox()
                .enterDaysToKeepBuilds("0")
                .enterMaxOfBuildsToKeep("")
                .getErrorMessageStrategyDays();

        Assert.assertEquals(actualErrorMessage, "Not a positive integer");
    }

    @Test
    public void testDiscardOldBuildsIsChecked0Builds() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        boolean notPositiveInteger = new PipelinePage(getDriver())
                .clickConfigure()
                .clickDiscardOldBuildsCheckbox()
                .enterDaysToKeepBuilds("0")
                .clickOutsideOfInputField()
                .isErrorMessageDisplayed();

        Assert.assertTrue(notPositiveInteger);
    }

    @Test
    public void testAddingAProjectOnGithubToThePipelineProject() {
        final String gitHubUrl = "https://github.com/ArtyomDulya/TestRepo";
        final String expectedNameRepo = "Sign in";

        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String actualNameRepo = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .clickGitHubProjectCheckbox()
                .inputTextTheInputAreaProjectUrlInGitHubProject(gitHubUrl)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .selectFromJobDropdownMenuTheGitHub(NAME);

        Assert.assertEquals(actualNameRepo, expectedNameRepo);
    }

    @Test
    public void testAddBooleanParameterWithDescription() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        final String name = "Pipeline Boolean Parameter";
        final String description = "Some boolean parameters here";
        final String parameterName = "Boolean Parameter";

        BuildWithParametersPage<PipelinePage> buildParametersPagePage = new PipelinePage(getDriver())
                .clickConfigure()
                .clickAndAddParameter(parameterName)
                .setBooleanParameterName(name)
                .setDefaultBooleanParameter()
                .setBooleanParameterDescription(description)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickBuildButton(new PipelinePage(getDriver()));

        Assert.assertEquals(buildParametersPagePage.getBooleanParameterName(), name);
        Assert.assertEquals(buildParametersPagePage.getBooleanParameterCheckbox(), "true");
        Assert.assertEquals(buildParametersPagePage.getParameterDescription(), description);
    }

    @Test
    public void testAddBooleanParameter() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        final String name = "Pipeline Boolean Parameter";
        final String parameterName = "Boolean Parameter";

        BuildWithParametersPage<PipelinePage> buildParametersPage = new PipelinePage(getDriver())
                .clickConfigure()
                .clickAndAddParameter(parameterName)
                .setBooleanParameterName(name)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickBuildButton(new PipelinePage(getDriver()));

        Assert.assertEquals(buildParametersPage.getBooleanParameterName(), name);
        Assert.assertNull(buildParametersPage.getBooleanParameterCheckbox());
    }

    @Test
    public void testThisProjectIsParameterizedCheckAllParameters() {
        List<String> expectedOptionsOfAddParameterDropdown = List.of(
                "Boolean Parameter", "Choice Parameter", "Credentials Parameter", "File Parameter",
                "Multi-line String Parameter", "Password Parameter", "Run Parameter", "String Parameter"
        );
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        List<String> actualOptionsOfAddParameterDropdown = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new PipelineConfigPage(new PipelinePage(getDriver())))
                .checkProjectIsParametrized()
                .openAddParameterDropDown()
                .getAllOptionsOfAddParameterDropdown();

        Assert.assertEquals(actualOptionsOfAddParameterDropdown, expectedOptionsOfAddParameterDropdown);
    }

    @Test
    public void testAddDisplayName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        PipelinePage pipelinePage = new PipelinePage(getDriver())
                .clickConfigure()
                .scrollAndClickAdvancedButton()
                .setDisplayName(NEW_NAME)
                .clickSaveButton();

        Assert.assertEquals(pipelinePage.getJobName(), "Pipeline " + NEW_NAME);
        Assert.assertEquals(pipelinePage.getProjectNameSubtitleWithDisplayName(), NAME);
        Assert.assertEquals(pipelinePage.getHeader().clickLogo().getJobName(NAME), NEW_NAME);
    }

    @Test
    public void testCreateNewPipelineWithScript() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean projectIsPresent = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .selectScriptedPipelineAndSubmit()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(projectIsPresent);
    }

    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean projectIsPresent = new MainPage(getDriver())
                .dropDownMenuClickDelete(NAME)
                .dismissAlert()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(projectIsPresent);
    }

    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean isProjectPresent = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickDeleteAndCancel()
                .getHeader()
                .clickLogo()
                .verifyJobIsPresent(NAME);

        Assert.assertTrue(isProjectPresent, "error! project is not displayed!");
    }

    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String welcomeText = new MainPage(getDriver())
                .dropDownMenuClickDelete(NAME)
                .acceptAlert()
                .getWelcomeText();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String welcomeText = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickDeleteAndAccept()
                .getWelcomeText();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }
}
