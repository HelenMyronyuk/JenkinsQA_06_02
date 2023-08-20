package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;
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

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating MultiConfiguration project by clicking Create a job button")
    @Test
    public void testCreateFromCreateAJob() {
        boolean jobIsDisplayed = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(jobIsDisplayed, "error was not show name project");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating MultiConfiguration project by clicking +New Item button")
    @Test
    public void testCreateFromNewItem() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean jobNameDisplayed = new MainPage(getDriver())
                .jobIsDisplayed(NAME);

        Assert.assertTrue(jobNameDisplayed, "Error: was not show name MultiConfiguration Project");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating MultiConfiguration project by clicking +New Item button from People Page")
    @Test
    public void testCreateFromPeoplePage() {
        boolean jobIsDisplayed = new MainPage(getDriver())
                .clickPeopleFromSideMenu()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(jobIsDisplayed, "Job name is not displayed!");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating MultiConfiguration project by clicking +New Item button from Build History Page")
    @Test
    public void testCreateFromBuildHistoryPage() {
        boolean jobIsDisplayed = new MainPage(getDriver())
                .clickBuildsHistoryFromSideMenu()
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(jobIsDisplayed, "Job name is not displayed!");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating MultiConfiguration project by clicking +New Item button from Manage Jenkins Page")
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

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating MultiConfiguration project by clicking Create a job button from My Views Page")
    @Test
    public void testCreateFromMyViewsCreateAJob() {
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJobAndArrow()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(NAME), "Error: the MultiConfiguration Project's name is not displayed on Dashboard from Home page");
        Assert.assertTrue(projectName.clickMyViewsSideMenuLink()
                .jobIsDisplayed(NAME), "Error: the MultiConfiguration Project's name is not displayed on Dashboard from MyViews page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating MultiConfiguration project by clicking +New Item button from My Views Page")
    @Test
    public void testCreateFromMyViewsNewItem() {
        boolean jobIsDisplayed = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewItemFromSideMenu()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .clickOkButton(new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(jobIsDisplayed, "Error: the project name is not displayed");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating MultiConfiguration project with existing name")
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

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating MultiConfiguration project with name using unsafe characters")
    @Test(dataProvider = "invalid-characters")
    public void testCreateUsingInvalidData(char invalidCharacters) {
        NewJobPage newJobPage = TestUtils.createFolderUsingInvalidData
                (this, invalidCharacters + "MyProject", TestUtils.JobType.MultiConfigurationProject);

        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "error OK button is enabled");
        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» ‘" + invalidCharacters + "’" + " is an unsafe character");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating MultiConfiguration project with empty name")
    @Test
    public void testCreateWithEmptyName() {
        final String expectedError = "» This field cannot be empty, please enter a valid name";

        String actualError = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .selectJobType(TestUtils.JobType.MultiConfigurationProject)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(actualError, expectedError);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message on Error Page while creating MultiConfiguration project with space instead name")
    @Test
    public void testCreateWithSpaceInsteadOfName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        CreateItemErrorPage errorPage =
                TestUtils.createJobWithSpaceInsteadName(this, TestUtils.JobType.MultiConfigurationProject);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "No name is specified");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating MultiConfiguration project with dot instead name")
    @Test
    public void testCreateWithDotInsteadOfName() {
        NewJobPage newJobPage = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(".")
                .selectJobType(TestUtils.JobType.MultiConfigurationProject);

        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» “.” is not an allowed name");
        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "error OK button is enabled");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating MultiConfiguration project with long name")
    @Test
    public void testCreateWithLongName() {
        String longName = RandomStringUtils.randomAlphanumeric(256);

        String errorMessage = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(longName)
                .selectJobAndOkAndGoToBugPage(TestUtils.JobType.MultiConfigurationProject)
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
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

        Assert.assertTrue(buildHeaderIsDisplayed, "build not created");
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

        Assert.assertTrue(buildHeaderIsDisplayed, "Build is not created");
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

        Assert.assertTrue(buildHeaderText, "Error: The Display Name for the Build has not been changed.");
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
    public void testAddDescriptionFromBuildPage(){
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
    @Description("Verification of possibility to build changes from last build of MultiConfiguration project")
    @Test
    public void testBuildChangesFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, false);

        boolean isTextContains = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickChangesViaLastBuildDropDownMenu()
                .getTextOfPage()
                .contains("No changes.");

        Assert.assertTrue(isTextContains, "The text is not contains No changes.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to make console output from MultiConfiguration Project Page")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to make console output from last build of MultiConfiguration project")
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

    @DataProvider(name = "buildSubMenu")
    public Object[][] getBuildSubmenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseSubmenuPage<?>>) ChangesBuildPage::new, "Changes"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) EditBuildInformationPage::new, "Edit Build Information"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to navigate to the MultiConfiguration Project Build pages from the build drop-down")
    @Test(dataProvider = "buildSubMenu")
    public void testNavigateToOptionsFromBuildPage(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromSubMenuConstructor, String expectedResult) {

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);
        String actualResult = "";

        BaseSubmenuPage submenuPage  = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectOptionFromBuildDropDownList(pageFromSubMenuConstructor.apply(getDriver()));

        if ("configure".equals(pageFromSubMenuConstructor.apply(getDriver()).callByMenuItemName())) {
            actualResult = submenuPage.getTextEditBuildInformFromBreadCrumb();
        } else {
            actualResult = submenuPage.getHeading();
        }
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to navigate to 'Delete' Build page from the build drop-down")
    @Test
    public void testNavigateToDeleteBuildFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean deleteBuildPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectDeleteOptionFromBuildDropDownList(new MultiConfigurationProjectPage(getDriver()))
                .isDeleteButtonDisplayed();

        Assert.assertTrue(deleteBuildPage, "Test is not navigate to delete build from MultiConfigurationProject build page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to edit build information from MultiConfiguration Project Page")
    @Test
    public void testEditBuildInformationFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String titleEditBuildPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickEditBuildInformFromProjectPage()
                .getHeaderText();

        Assert.assertEquals(titleEditBuildPage, "Edit Build Information");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to edit build information from last build of MultiConfiguration Project")
    @Test
    public void testEditBuildInformationFromLastBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        String editBuildInformPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new MultiConfigurationProjectPage(getDriver()))
                .editBuildInfoPermalinksLastBuildDropDown()
                .getTextEditBuildInformFromBreadCrumb();

        Assert.assertEquals(editBuildInformPage, "Edit Build Information");
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
        Assert.assertTrue(checkboxesVisibleClickable);
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
                "In theMultiConfiguration project Changes chapter, not displayed status of the latest build.");
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

        Assert.assertEquals(workspacePage, "Workspace of MULTI_CONFIGURATION_NAME on Built-In Node");
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
        Assert.assertFalse(mainPage.isScheduleBuildOnDashboardAvailable(NAME), "Error: disabled project cannot be built");
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
    public void testPreviewDescriptionFromConfigurationPage(){
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
    public void testAddDescriptionFromConfigurationPage(){
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

        Assert.assertTrue(projectIsPresent, "Error: the name of the MultiConfiguration project is not shown");
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

        Assert.assertTrue(isProjectPresent, "error! project is not displayed!");
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

    @DataProvider(name = "buildDropDownMenuOptions")
    public Object[][] optionsFromDropDown() {
        return new Object[][] {
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesBuildPage::new, "Changes", "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output", "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information", "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) DeletePage::new, "Delete build", "Confirm deletion"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of navigation to options page for MultiConfiguration Project from build drop-down menu")
    @Test(dataProvider = "buildDropDownMenuOptions")
    public void testNavigateToOptionsFromDropDown(Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDownMenu, String dropDownMenuLink, String expectedPageHeader) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultiConfigurationProject, true);

        new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .getHeader()
                .clickLogo()
                .openBuildDropDownMenu("#1")
                .clickBuildOptionFromDropDownMenu(pageFromDropDownMenu.apply(getDriver()), dropDownMenuLink);

        if (dropDownMenuLink.equals("Changes") || dropDownMenuLink.equals("Console Output")) {
            String pageHeader = pageFromDropDownMenu.apply(getDriver()).getPageHeaderText();
            Assert.assertTrue(pageHeader.contains(expectedPageHeader), "Navigated to an unexpected page");
        } else {
            String breadcrumbHeader = pageFromDropDownMenu.apply(getDriver()).getBreadcrumb().getPageNameFromBreadcrumb();
            Assert.assertTrue(breadcrumbHeader.contains(expectedPageHeader), "Navigated to an unexpected page");
        }
    }
}
