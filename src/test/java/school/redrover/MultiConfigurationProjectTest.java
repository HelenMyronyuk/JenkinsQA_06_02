package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.builds.ChangesBuildPage;
import school.redrover.model.builds.ConsoleOutputPage;
import school.redrover.model.builds.EditBuildInformationPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobsConfig.MultiConfigurationProjectConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.function.Function;

public class MultiConfigurationProjectTest extends BaseTest {

    private static final String NAME = "MULTI_CONFIGURATION_NAME";
    private static final String NEW_NAME = "MULTI_CONFIGURATION_NEW_NAME";
    private static final String DESCRIPTION = "Description";
    private static final String NEW_DESCRIPTION = "New Description";
    private static final String GITHUB_REPOSITORY_URL = "https://github.com/nikabenz/sourceCodeManagementForJenkinsBuild";

    @DataProvider(name = "buildMenu")
    public Object[][] getBuildMenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesBuildPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) DeletePage::new, "Delete"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating MultiConfiguration project with empty name")
    @Test
    public void testCheckExceptionOfNameToMultiConfiguration() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String exceptionMessage = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(exceptionMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to rename MultiConfiguration project from drop-down menu")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to rename MultiConfiguration project from side menu")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename MultiConfiguration project from drop-down menu with existing name")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename MultiConfiguration project with unsafe data")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to build  MultiConfiguration project from drop-down menu")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to build  MultiConfiguration project from side menu")
    @Test
    public void testCreateBuildNowFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "The build is not created");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to build  MultiConfiguration project by clicking green arrow")
    @Test
    public void testCreateBuildNowFromArrow() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "The build is not created");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence display name for build of MultiConfiguration project")
    @Test
    public void testAddDisplayNameForBuild() {
        final String displayName = "DisplayName";
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        boolean buildHeaderText = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDisplayName(displayName)
                .clickSaveButton()
                .getBuildHeaderText()
                .contains(displayName);

        Assert.assertTrue(buildHeaderText, "The display name for the build has not been changed.");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence of preview description for build of MultiConfiguration project")
    @Test
    public void testPreviewDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String previewText = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewText, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification that description for build of MultiConfiguration project can be added")
    @Test
    public void testAddDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String descriptionText = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of possibility to rename description for build of MultiConfiguration project")
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
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(NEW_DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(newBuildDescription, NEW_DESCRIPTION);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to the options from the last build drop-down menu of MultiConfiguration project")
    @Test(dataProvider = "buildMenu")
    public void testNavigateToOptionsFromLastBuild(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDown, String expectedPage) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String lastBuildNumber = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .refreshPage()
                .getLastBuildNumber();

        String actualPage = new MultiConfigurationProjectPage(getDriver())
                .refreshPage()
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectOptionFromBuildDropDownList(pageFromDropDown.apply(getDriver()))
                .getAssertTextFromPage();

        String breadcrumb = pageFromDropDown.apply(getDriver())
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
        Assert.assertTrue(breadcrumb.contains(lastBuildNumber), "The full text of the breadcrumb does not contain the last build number");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to the build menu options from the MultiConfiguration Project Page")
    @Test(dataProvider = "buildMenu")
    public void testNavigateToOptionsFromProjectPage(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenu, String expectedPage) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

       String actualPage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .refreshPage()
                .openBuildsDropDownMenu()
                .selectOptionFromDropDownList(pageFromSideMenu.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to navigate to the MultiConfiguration Project Build pages from the build drop-down")
    @Test(dataProvider = "buildMenu")
    public void testNavigateToOptionsFromBuildPage(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenu, String expectedPage) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String actualPage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .refreshPage()
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectOptionFromBuildDropDownList(pageFromSideMenu.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence preview description of build from Edit Information Page for MultiConfiguration Project")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of description of build can be added from Edit Information Page for MultiConfiguration Project")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence description for MultiConfiguration Project")
    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String previewDescription = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(DESCRIPTION)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewDescription, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence description added from MultiConfiguration Project Page")
    @Test
    public void testAddDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String getDescription = new MultiConfigurationProjectPage(getDriver())
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(getDescription, DESCRIPTION);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to disable MultiConfiguration Project from Project Page")
    @Test
    public void testDisableFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MultiConfigurationProjectPage disabled = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDisable();

        Assert.assertEquals(disabled.getDisabledMessageText(), "This project is currently disabled");
        Assert.assertEquals(disabled.getEnableButtonText(), "Enable");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of disabled icon of MultiConfiguration Project on Dashboard")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to build for disabled MultiConfiguration Project")
    @Test
    public void testBuildNowOptionNotPresentInDisabledProject() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean dropDownMenuItemsContains = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDisable()
                .getHeader()
                .clickLogo()
                .getListOfProjectMenuItems(NAME)
                .contains("Build Now");

        Assert.assertFalse(dropDownMenuItemsContains, "'Build Now' option is present in drop-down menu");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification for general parameters are visible and clickable for MultiConfiguration Project drop-down menu")
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
        Assert.assertTrue(checkboxesVisibleClickable, "Not all checkboxes are visible and clickable");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to enable disabled MultiConfiguration Project from Project Page")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Changes Page from side menu for MultiConfiguration Project")
    @Test
    public void testNavigateToChangesPageFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean textContains = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickChangeOnLeftSideMenu()
                .getTextOfPage()
                .contains("No builds.");

        Assert.assertTrue(textContains,
                "In the MultiConfiguration project Changes chapter, not displayed status of the latest build.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Workspaces from Project Page for MultiConfiguration Project")
    @Test
    public void testNavigateToWorkspaceFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String workspacePage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickWorkspaceFromSideMenu()
                .getTextFromWorkspacePage();

        Assert.assertEquals(workspacePage, "Workspace of " + NAME + " on Built-In Node");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Configuration Page from drop-down menu for MultiConfiguration Project")
    @Test
    public void testAccessConfigurationPageFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String getHeaderText = new MainPage(getDriver())
                .clickConfigureDropDown(
                        NAME, new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeaderText();

        Assert.assertEquals(getHeaderText, "Configure");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Configuration Page from side menu for MultiConfiguration Project")
    @Test
    public void testAccessConfigurationPageFromSideMenu() {
        final String breadcrumb = "Dashboard > " + NAME + " > Configuration";
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);
        MultiConfigurationProjectConfigPage multiConfigurationProjectConfigPage = new MultiConfigurationProjectPage(getDriver())
                .clickConfigure();

        Assert.assertEquals(multiConfigurationProjectConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(multiConfigurationProjectConfigPage.getHeaderText(), "Configure");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to disable MultiConfiguration Project from Configuration Page")
    @Test
    public void testDisableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MultiConfigurationProjectConfigPage statusSwitchButton = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .clickSwitchEnableOrDisable();

        Boolean availableMode = statusSwitchButton
                .isEnabledDisplayed();

        MainPage mainPage = statusSwitchButton
                .clickSaveButton()
                .getHeader()
                .clickLogo();

        Assert.assertTrue(availableMode, "'Enabled' is not displayed");
        Assert.assertEquals(mainPage.getJobBuildStatusIcon(NAME), "Disabled");
        Assert.assertFalse(mainPage.isScheduleBuildOnDashboardAvailable(NAME), "The 'Build Now' option is available on Dashboard from Home page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to enable disable MultiConfiguration Project from Configuration Page")
    @Test
    public void testEnableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        Boolean enabledButtonText = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .isEnabledDisplayed();

        Assert.assertTrue(enabledButtonText, "'Enabled' is not displayed");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of of presence Preview of description for MultiConfiguration Project can be added from Configuration Page")
    @Test
    public void testPreviewDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String previewDescriptionText = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewDescriptionText, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of possibility 'Description' for MultiConfiguration Project can be added from Configuration Page")
    @Test
    public void testAddDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String descriptionText = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("The 'Display Name' can be added to the MultiConfiguration project from Configuration page")
    @Test
    public void testAddDisplayName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        MultiConfigurationProjectPage multiConfigurationProjectPage = new MultiConfigurationProjectPage(getDriver())
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .enterDisplayName(NEW_NAME)
                .clickSaveButton();

        Assert.assertEquals(multiConfigurationProjectPage.getProjectName(), NEW_NAME);
        Assert.assertEquals(multiConfigurationProjectPage.getProjectNameSubtitleWithDisplayName(), NAME);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("The 'Display name' can be deleted to the MultiConfiguration from Configuration page")
    @Test
    public void testDeleteDisplayName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String actualProjectName = new MultiConfigurationProjectPage(getDriver())
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .enterDisplayName(NEW_NAME)
                .clickSaveButton()
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .enterDisplayName("")
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to configure old build for MultiConfiguration Project")
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
                multiConfigurationProjectConfigPage.getDaysToKeepBuilds()), displayedDaysToKeepBuilds);
        Assert.assertEquals(Integer.parseInt(
                multiConfigurationProjectConfigPage.getMaxNumOfBuildsToKeep()), displayedMaxNumOfBuildsToKeep);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to add MultiConfiguration Project on GitHub")
    @Test
    public void testAddingAProjectOnGithubToTheMultiConfigurationProject() {
        final String gitHubUrl = "https://github.com/ArtyomDulya/TestRepo";
        final String expectedNameRepo = "TestRepo";

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String actualNameRepo = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .clickGitHubProjectCheckbox()
                .inputTextTheInputAreaProjectUrlInGitHubProject(gitHubUrl)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .selectGitHubFromJobDropdownMenu(NAME)
                .getNameRepo();

        Assert.assertEquals(actualNameRepo, expectedNameRepo);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verification of presence parameters for MultiConfiguration Project on GitHub")
    @Test
    public void testThisProjectIsParameterizedOptionsCollectToList() {
        final List<String> expectedOptionsProjectIsParameterizedList = List.of("Boolean Parameter", "Choice Parameter",
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility 'add Repository' from ‘Source Code Management’ ")
    @Test
    public void testAddRepositoryFromSourceCodeManagement() {
        TestUtils.createJob(this, NAME,TestUtils.JobType.MultiConfigurationProject, true);

        GitBuildDataPage gitBuildDataPage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .clickSourceCodeManagementLink()
                .clickRadioButtonGit()
                .inputRepositoryUrl(GITHUB_REPOSITORY_URL)
                .correctMainBranchName()
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .refreshPage()
                .openPermalinksLastBuildsDropDownMenu()
                .clickConsoleOutputType()
                .clickGitBuildDataLink();

        Assert.assertEquals(gitBuildDataPage.getNamesOfBuiltBranches(), "refs/remotes/origin/main");
        Assert.assertEquals(gitBuildDataPage.getRepositoryName(), GITHUB_REPOSITORY_URL);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to add build steps options for MultiConfiguration Project")
    @Test
    public void testAddBuildStepsOptionsCollectToList() {
        final List<String> expectedOptionsInBuildStepsSection = List.of("Execute Windows batch command", "Execute shell",
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to post build actions options for MultiConfiguration Project")
    @Test
    public void testPostBuildActionsOptionsCollectToList() {
        final List<String> expectedOptionsList = List.of("Aggregate downstream test results",
                "Archive the artifacts", "Build other projects", "Publish JUnit test result report",
                "Record fingerprints of files to track usage", "Git Publisher", "E-mail Notification",
                "Editable Email Notification", "Set GitHub commit status (universal)",
                "Set build status on GitHub commit [deprecated]", "Delete workspace when build is done");

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        List<String> actualOptionsList = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickConfigure()
                .clickAddPostBuildActionDropDown()
                .getPostBuildActionsOptionsList();

        Assert.assertEquals(actualOptionsList, expectedOptionsList);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to cancel deleting from drop-down menu for MultiConfiguration Project")
    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean projectIsPresent = new MainPage(getDriver())
                .dropDownMenuClickDelete(NAME)
                .dismissAlert()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(projectIsPresent, "The MultiConfiguration project's name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to cancel deleting from side menu for MultiConfiguration Project")
    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean isProjectPresent = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDeleteAndCancel()
                .getHeader()
                .clickLogo()
                .verifyJobIsPresent(NAME);

        Assert.assertTrue(isProjectPresent, "The MultiConfiguration project's name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to delete MultiConfiguration Project from drop-down menu")
    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean welcomeDisplayed = new MainPage(getDriver())
                .dropDownMenuClickDelete(NAME)
                .acceptAlert()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeDisplayed, "Welcome Jenkins is not displayed!");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to delete MultiConfiguration Project from side menu")
    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        MainPage deletedProjPage = new MainPage(getDriver())
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDeleteAndAccept();

        Assert.assertEquals(deletedProjPage.getTitle(), "Dashboard [Jenkins]");
        Assert.assertEquals(deletedProjPage.getWelcomeText(), "Welcome to Jenkins!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of navigation to options page for MultiConfiguration Project from build drop-down menu")
    @Test(dataProvider = "buildMenu")
    public void testNavigateToOptionsFromDropDown(Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDownMenu, String expectedPage) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String actualPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .getHeader()
                .clickLogoWithLongPause()
                .openBuildDropDownMenu("#1")
                .selectOptionFromDropDownList(pageFromDropDownMenu.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }
}
