package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobsconfig.MultiConfigurationProjectConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class MultiConfigurationProjectTest extends BaseTest {

    private static final String NAME = "MULTI_CONFIGURATION_NAME";
    private static final String NEW_NAME = "MULTI_CONFIGURATION_NEW_NAME";
    private static final String DESCRIPTION = "Description";
    private static final String NEW_DESCRIPTION = "New Description";

    @Test
    public void testCreateFromCreateAJob() {
        MainPage mainPage = new MainPage(getDriver())
                .clickCreateAJob()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.jobIsDisplayed(NAME), "error was not show name project");
    }

    @Test
    public void testCreateFromCreateAJobArrow() {
        boolean multiConfigNameIsAppeared = new MainPage(getDriver())
                .clickCreateAJobArrow()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(multiConfigNameIsAppeared, "Error! Job Is Not Displayed");
    }

    @Test
    public void testCreateFromNewItem() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        Assert.assertEquals(new MultiConfigurationProjectPage(getDriver()).getJobName().substring(8, 32), NAME);

        new MainPage(getDriver())
                .getHeader()
                .clickLogo();

        Assert.assertEquals(new MainPage(getDriver()).getJobName(NAME), NAME);
    }

    @Test
    public void testCreateFromPeoplePage() {
        MainPage projectPeoplePage = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectPeoplePage.jobIsDisplayed(NAME));
    }

    @Test
    public void testCreateFromBuildHistoryPage() {
        MainPage newProjectFromBuildHistoryPage = new BuildHistoryPage(getDriver())
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(newProjectFromBuildHistoryPage.jobIsDisplayed(NAME));
    }

    @Test
    public void testCreateFromManageJenkinsPage() {
        boolean jobIsDisplayed = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(jobIsDisplayed, "Error: the MultiConfiguration Project's name is not displayed on Dashboard");
    }

    @Test
    public void testCreateFromMyViewsCreateAJob() {
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJob()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(NAME), "Error: the MultiConfiguration Project's name is not displayed on Dashboard from Home page");
        Assert.assertTrue(projectName.clickMyViewsSideMenuLink()
                .jobIsDisplayed(NAME), "Error: the MultiConfiguration Project's name is not displayed on Dashboard from MyViews page");
    }

    @Test
    public void testCreateFromMyViewsCreateAJobArrow() {
        MainPage mainPage = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJobArrow()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
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
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(NAME), "Error: the project name is not displayed");
    }

    @Test
    public void testCreateWithExistingName() {
        final String errorMessageName = "A job already exists with the name " + "‘" + NAME + "’";

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        CreateItemErrorPage errorPage =
                TestUtils.createJobWithExistingName(this, NAME, TestUtils.JobType.MultiConfigurationProject);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), errorMessageName);
    }

    @DataProvider(name = "invalid-characters")
    public static Object[][] provideUnsafeCharacters() {
        return new Object[][]{{'!'}, {'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'&'},
                {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'},
                {'<'}, {'>'}, {'/'}, {'?'}};
    }

    @Test(dataProvider = "invalid-characters")
    public void testCreateUsingInvalidData(char invalidCharacters) {
        NewJobPage newJobPage = TestUtils.createFolderUsingInvalidData
                (this, invalidCharacters + "MyProject", TestUtils.JobType.MultiConfigurationProject);

        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "error OK button is enabled");
        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» ‘" + invalidCharacters + "’" + " is an unsafe character");
    }

    @Test
    public void testCreateWithEmptyName() {
        final String expectedError = "» This field cannot be empty, please enter a valid name";

        String actualError = new MainPage(getDriver())
                .clickCreateAJobArrow()
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(actualError, expectedError);
    }

    @Test
    public void testCreateWithSpaceInsteadOfName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        CreateItemErrorPage errorPage =
                TestUtils.createJobWithSpaceInsteadName(this, TestUtils.JobType.MultiConfigurationProject);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "No name is specified");
    }

    @Test
    public void testCreateWithDotInsteadOfName() {
        NewJobPage newJobPage = new MainPage(getDriver())
                .clickCreateAJob()
                .enterItemName(".")
                .selectJobType(TestUtils.JobType.MultiConfigurationProject);

        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» “.” is not an allowed name");
        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "error OK button is enabled");
    }

    @Test
    public void testCreateWithLongName() {
        String longName = RandomStringUtils.randomAlphanumeric(256);

        String errorMessage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(longName)
                .selectJobAndOkAndGoToBugPage(TestUtils.JobType.MultiConfigurationProject)
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test
    public void testCheckExceptionOfNameToMultiConfiguration() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String exceptionMessage = new MainPage(getDriver())
                .clickNewItem()
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(exceptionMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testRenameFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String newNameProject = new MainPage(getDriver())
                .dropDownMenuClickRename(NAME, new MultiConfigurationProjectPage(getDriver()))
                .enterNewName(NEW_NAME)
                .clickRenameButton()
                .getHeader()
                .clickLogo()
                .getJobName(NEW_NAME);

        Assert.assertEquals(newNameProject, NEW_NAME);
    }

    @Test
    public void testRenameFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String newName = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickRename()
                .enterNewName(NEW_NAME)
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(newName, "Project " + NEW_NAME);
    }

    @Test
    public void testRenameToTheCurrentNameAndGetError() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String errorMessage = new MainPage(getDriver())
                .dropDownMenuClickRename(NAME, new MultiConfigurationProjectPage(getDriver()))
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
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickRename()
                .enterNewName(invalidData)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "‘" + expectedResult + "’ is an unsafe character");
    }

    @Test
    public void testCreateBuildNowFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        Assert.assertEquals(new MainPage(getDriver()).getJobBuildStatusByWeatherIcon(NAME), "Not built");

        String jobBuildStatus = new MainPage(getDriver())
                .clickJobDropdownMenuBuildNow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .getJobBuildStatus();

        Assert.assertEquals(jobBuildStatus, "Success");
    }

    @Test
    public void testCreateBuildNowFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "build not created");
    }

    @Test
    public void testCreateBuildNowFromArrow() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "Build is not created");
    }

    @Test
    public void testAddDisplayNameForBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String buildHeaderText = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDisplayName("DisplayName")
                .clickSaveButton()
                .getBuildHeaderText();

        Assert.assertTrue(buildHeaderText.contains("DisplayName"),
                "Error: The Display Name for the Build has not been changed.");
    }

    @Test
    public void testPreviewDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String previewText = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditDescription()
                .enterDescriptionText(DESCRIPTION)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewText, DESCRIPTION);
    }

    @Test
    public void testEditDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.FreestyleProject, true);

        String newBuildDescription = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildFromSideMenu(NAME, 1)
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .clickEditDescription()
                .clearDescriptionText()
                .enterDescriptionText(NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(newBuildDescription, NEW_DESCRIPTION);
    }

    @Test
    public void testBuildChangesFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

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
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String text = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickChangesViaLastBuildDropDownMenu()
                .getTextOfPage();

        Assert.assertTrue(text.contains("No changes."));
    }

    @Test
    public void testConsoleOutputFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean consoleOutput = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .openBuildsDropDownMenu()
                .clickConsoleOutputType()
                .isDisplayedBuildTitle();

        Assert.assertTrue(consoleOutput, "Console output page is not displayed");
    }

    @Test
    public void testConsoleOutputFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MultiConfigurationProjectPage multiConfigJob = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()));

        String lastBuildNumber = multiConfigJob
                .getLastBuildNumber();

        ConsoleOutputPage consoleOutput = multiConfigJob
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
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean consoleOutputTitleDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickLastBuildLink()
                .clickConsoleOutput()
                .isDisplayedBuildTitle();

        Assert.assertTrue(consoleOutputTitleDisplayed, "Error: Console Output Title is not displayed!");
    }

    @Test
    public void testEditBuildInformationFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

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
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String titleEditBuildPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickEditBuildInformFromProjectPage()
                .getTitle();

        Assert.assertEquals(titleEditBuildPage, "Edit Build Information");
    }

    @Test
    public void testEditBuildInformationFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String editBuildInformPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .editBuildInfoPermalinksLastBuildDropDown()
                .getTextFromBreadCrumb();

        Assert.assertEquals(editBuildInformPage, "Edit Build Information");
    }

    @Test
    public void testEditBuildInformationFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String editBuildInformPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .getTextFromBreadCrumb();

        Assert.assertEquals(editBuildInformPage, "Edit Build Information");
    }

    @Test
    public void testPreviewDescriptionFromEditInformationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String previewDescriptionText = new MultiConfigurationProjectPage(getDriver())
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
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String descriptionText = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
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
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean noBuildsMessage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildDropdownMenuDeleteBuild("#1")
                .clickDelete(new MultiConfigurationProjectPage(getDriver()))
                .isNoBuildsDisplayed();

        Assert.assertTrue(noBuildsMessage, "Error");
    }

    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String previewDescription = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickAddEditDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewDescription, DESCRIPTION);
    }

    @Test
    public void testAddDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String getDescription = new MultiConfigurationProjectPage(getDriver())
                .clickAddEditDescription(DESCRIPTION)
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(getDescription, DESCRIPTION);
    }

    @Test
    public void testDisableFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MultiConfigurationProjectPage disabled = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDisable();

        Assert.assertEquals(disabled.getDisabledMessageText(), "This project is currently disabled");
        Assert.assertEquals(disabled.getEnableButtonText(), "Enable");
    }

    @Test
    public void testCheckDisableIconOnDashboard() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String statusIcon = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDisable()
                .getHeader()
                .clickLogo()
                .getJobBuildStatusIcon(NAME);

        Assert.assertEquals(statusIcon, "Disabled");
    }

    @Test
    public void testBuildNowOptionNotPresentInDisabledProject() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        List<String> dropDownMenuItems = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDisable()
                .getHeader()
                .clickLogo()
                .getListOfProjectMenuItems(NAME);

        Assert.assertFalse(dropDownMenuItems.contains("Build Now"), "'Build Now' option is present in drop-down menu");
    }

    @Test
    public void testCheckGeneralParametersDisplayedAndClickable() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MultiConfigurationProjectConfigPage parameter = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())));

        boolean checkboxesVisibleClickable = true;
        for (int i = 4; i <= 8; i++) {
            WebElement checkbox = parameter.getCheckboxById(i);
            if (!checkbox.isDisplayed() || !checkbox.isEnabled()) {
                checkboxesVisibleClickable = false;
                break;
            }
        }
        Assert.assertTrue(checkboxesVisibleClickable);
    }

    @Test
    public void testEnableFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String projectPage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDisable()
                .getHeader()
                .clickLogo()
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickEnable()
                .getHeader()
                .clickLogo()
                .getJobBuildStatusIcon(NAME);

        Assert.assertEquals(projectPage, "Not built");
    }

    @Test
    public void testNavigateToChangesPageFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String text =
                new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickChangeOnLeftSideMenu()
                .getTextOfPage();

        Assert.assertTrue(text.contains("No builds."),
                "In theMultiConfiguration project Changes chapter, not displayed status of the latest build.");
    }
    @Test
    public void testDisableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MultiConfigurationProjectConfigPage statusSwitchButton = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .switchCheckboxDisable();

        String availableMode = statusSwitchButton
                .getTextEnabled();

        MainPage mainPage = statusSwitchButton
                .clickSaveButton()
                .getHeader()
                .clickLogo();

        Assert.assertEquals(availableMode, "Enabled");
        Assert.assertEquals(mainPage.getJobBuildStatusIcon(NAME), "Disabled");
        Assert.assertFalse(mainPage.isScheduleBuildOnDashboardAvailable(NAME), "Error: disabled project cannot be built");
    }

    @Test
    public void testEnableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String enabledButtonText = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .getTextEnabled();

        Assert.assertEquals(enabledButtonText, "Enabled");
    }

    @Test
    public void testConfigureOldBuildForProject() {
        final int displayedDaysToKeepBuilds = 5;
        final int displayedMaxNumOfBuildsToKeep = 7;

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MultiConfigurationProjectConfigPage multiConfigurationProjectConfigPage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .clickOldBuildCheckBox()
                .enterDaysToKeepBuilds(displayedDaysToKeepBuilds)
                .enterMaxNumOfBuildsToKeep(displayedMaxNumOfBuildsToKeep)
                .clickSaveButton()
                .clickConfigure();

        Assert.assertEquals(Integer.parseInt(
                multiConfigurationProjectConfigPage.getDaysToKeepBuilds("value")), displayedDaysToKeepBuilds);
        Assert.assertEquals(Integer.parseInt(
                multiConfigurationProjectConfigPage.getMaxNumOfBuildsToKeep("value")), displayedMaxNumOfBuildsToKeep);
    }

    @Test
    public void testAddingAProjectOnGithubToTheMultiConfigurationProject() {
        final String gitHubUrl = "https://github.com/ArtyomDulya/TestRepo";
        final String expectedNameRepo = "Sign in";

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String actualNameRepo = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
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
    public void testThisProjectIsParameterizedOptionsCollectToList() {
        List<String> expectedOptionsProjectIsParameterizedList = List.of("Boolean Parameter", "Choice Parameter",
                "Credentials Parameter", "File Parameter", "Multi-line String Parameter", "Password Parameter",
                "Run Parameter", "String Parameter");

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        List<String> actualOptionsProjectIsParameterizedList = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .checkProjectIsParametrized()
                .openAddParameterDropDown()
                .getAllOptionsOfAddParameterDropdown();

        Assert.assertEquals(actualOptionsProjectIsParameterizedList, expectedOptionsProjectIsParameterizedList);
    }

    @Test
    public void testAddBuildStepsOptionsCollectToList() {
        List<String> expectedOptionsInBuildStepsSection = List.of("Execute Windows batch command", "Execute shell",
                "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets", "Run with timeout",
                "Set build status to \"pending\" on GitHub commit");

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        List<String> actualOptionsInBuildStepsSection = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .openBuildStepOptionsDropdown()
                .getOptionsInBuildStepDropdown();

        Assert.assertEquals(actualOptionsInBuildStepsSection, expectedOptionsInBuildStepsSection);
    }

    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean projectIsPresent = new MainPage(getDriver())
                .dropDownMenuClickDelete(NAME)
                .dismissAlert()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(projectIsPresent, "Error: the name of the MultiConfiguration project is not shown");
    }

    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean isProjectPresent = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDeleteAndCancel()
                .getHeader()
                .clickLogo()
                .verifyJobIsPresent(NAME);

        Assert.assertTrue(isProjectPresent, "error! project is not displayed!");
    }

    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        List<String> deleteProject = new MainPage(getDriver())
                .dropDownMenuClickDelete(NAME)
                .acceptAlert()
                .getJobList();

        Assert.assertEquals(deleteProject.size(), 0);
    }

    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MainPage deletedProjPage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDeleteAndAlert();

        Assert.assertEquals(deletedProjPage.getTitle(), "Dashboard [Jenkins]");
        Assert.assertEquals(deletedProjPage.getWelcomeText(), "Welcome to Jenkins!");
    }
}
