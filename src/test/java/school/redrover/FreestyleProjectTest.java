package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.builds.*;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = "FREESTYLE_NAME";
    private static final String NEW_FREESTYLE_NAME = "NEW_FREESTYLE_NAME";
    private static final String DESCRIPTION_TEXT = "DESCRIPTION_TEXT";
    private static final String NEW_DESCRIPTION_TEXT = "NEW_DESCRIPTION_TEXT";
    private static final String GITHUB_URL = "https://github.com/ArtyomDulya/TestRepo";
    private static final String NEW_GITHUB_URL = "https://github.com/nikabenz/repoForJenkinsBuild";
    private static final String DISPLAY_NAME = "FreestyleDisplayName";
    private static final String NEW_DISPLAY_NAME = "NewFreestyleDisplayName";

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Freestyle' can be renamed from drop down menu on the Main page")
    @Test
    public void testRenameFromDropDownMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualFreestyleProjectName = new MainPage(getDriver())
                .dropDownMenuClickRename(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .enterNewName(NEW_FREESTYLE_NAME)
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(actualFreestyleProjectName, "Project " + NEW_FREESTYLE_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Freestyle' can be renamed from side menu on the Project page")
    @Test
    public void testRenameFromSideMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String projectName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickRename()
                .enterNewName(FREESTYLE_NAME + " New")
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(projectName, "Project " + FREESTYLE_NAME + " New");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Freestyle project from drop-down menu with existing name")
    @Test
    public void testRenameToTheCurrentNameAndGetError() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String errorMessage = new MainPage(getDriver())
                .dropDownMenuClickRename(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .enterNewName(FREESTYLE_NAME)
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
    @Description("Verification of impossibility to rename Freestyle project with invalid data")
    @Test(dataProvider = "wrong-character")
    public void testRenameWithInvalidData(String invalidData, String expectedResult) {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickRename()
                .enterNewName(invalidData)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "‘" + expectedResult + "’ is an unsafe character");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Freestyle project with '.' name'")
    @Test
    public void testRenameWithDotInsteadName() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickRename()
                .enterNewName(".")
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "“.” is not an allowed name");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to build Freestyle project from drop-down menu")
    @Test
    public void testCreateBuildNowFromDropDown() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String createBuildNow = new MainPage(getDriver())
                .clickJobDropdownMenuBuildNow(FREESTYLE_NAME)
                .getHeader()
                .clickLogoWithPause()
                .getLastBuildIconStatus();

        Assert.assertEquals(createBuildNow, "Success");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to build Freestyle project from side menu")
    @Test
    public void testCreateBuildNowFromSideMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "The build of the Freestyle Project is not created");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to build Freestyle project by clicking green arrow")
    @Test
    public void testCreateBuildNowFromArrow() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "The build of the Freestyle Project is not created");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Checking presence links to the build after the build is created")
    @Test
    public void testPresenceOfBuildLinksAfterBuild() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String statusIcon = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getBreadcrumb()
                .clickDashboardButton()
                .getJobBuildStatusIcon(NEW_FREESTYLE_NAME);

        int sizeOfPermalinksList = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .getSizeOfPermalinksList();

        Assert.assertEquals(statusIcon, "Success");
        Assert.assertEquals(sizeOfPermalinksList, 4);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of presence display name for build of Freestyle project")
    @Test
    public void testAddDisplayNameForBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String buildHeaderText = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDisplayName("DisplayName")
                .clickSaveButton()
                .getBuildHeaderText();

        Assert.assertTrue(buildHeaderText.contains("DisplayName"), "The Display Name for the Build has not been changed.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of presence of preview description for build of Freestyle project")
    @Test
    public void testPreviewDescriptionFromBuildPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String previewText = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION_TEXT)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewText, DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to rename description for build of Freestyle project")
    @Test
    public void testEditDescriptionFromBuildPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String newBuildDescription = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildFromSideMenu(FREESTYLE_NAME, 1)
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(NEW_DESCRIPTION_TEXT)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(newBuildDescription, NEW_DESCRIPTION_TEXT);
    }

    @DataProvider(name = "buildMenu")
    public Object[][] getBuildMenu() {
        return new Object[][] {
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesBuildPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) DeletePage::new, "Delete"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to the build options from from ProjectPage for Freestyle project")
    @Test(dataProvider = "buildMenu")
    public void testNavigateToOptionsFromProjectPage(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDown, String expectedPage) {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .openBuildsDropDownMenu()
                .selectOptionFromDropDownList(pageFromDropDown.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of navigation to options page for Freestyle Project from build drop-down menu on Dashboard from Home page")
    @Test(dataProvider = "buildMenu")
    public void testNavigateToOptionsFromDropDown(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDownMenu, String expectedPage) {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .getHeader()
                .clickLogoWithPause()
                .openBuildDropDownMenu("#1")
                .selectOptionFromDropDownList(pageFromDropDownMenu.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to build changes for Freestyle project from last build")
    @Test
    public void testBuildChangesFromLastBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String text = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickChangesViaLastBuildDropDownMenu()
                .getTextOfPage();

        Assert.assertTrue(text.contains("No changes."), "The text from Changes page does not contain 'No changes.'");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to the build options from the Build Page for Freestyle project")
    @Test(dataProvider = "buildMenu")
    public void testNavigateToOptionsFromBuildPage(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenu, String expectedPage) {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .refreshPage()
                .clickLastBuildLink()
                .clickBuildOptionFromSideMenu(pageFromSideMenu.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to make console output from last build of Freestyle project")
    @Test
    public void testConsoleOutputFromLastBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage freestyleJob = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()));

        String lastBuildNumber = freestyleJob
                .getLastBuildNumber();

        ConsoleOutputPage consoleOutput = freestyleJob
                .clickLastBuildLink()
                .clickConsoleOutput();

        String breadcrumb = consoleOutput
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertTrue(consoleOutput.isDisplayedBuildTitle(), "Console output page is not displayed");
        Assert.assertTrue(breadcrumb.contains(lastBuildNumber), "The full breadcrumb text does not contain the last build number");
    }


    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to edit build information from last build of Freestyle Project")
    @Test
    public void testEditBuildInformationFromLastBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String buildName = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .editBuildInfoPermalinksLastBuildDropDown()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .editBuildInfoPermalinksLastBuildDropDown()
                .editDisplayName(NEW_DISPLAY_NAME)
                .enterDescription(NEW_DESCRIPTION_TEXT)
                .clickSaveButton()
                .getBuildNameFromTitle();

        String description = new BuildPage(getDriver())
                .getDescriptionText();

        Assert.assertEquals(buildName, NEW_DISPLAY_NAME);
        Assert.assertEquals(description, NEW_DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of presence preview description of build from Edit Information Page for Freestyle Project")
    @Test
    public void testPreviewDescriptionFromEditInformationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String previewDescriptionText = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION_TEXT)
                .clickPreviewButton()
                .getPreviewText();

        Assert.assertEquals(previewDescriptionText, DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of description of build can be added from Edit Information Page for Freestyle Project")
    @Test
    public void testAddDescriptionFromEditInformationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String descriptionText = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildFromSideMenu(FREESTYLE_NAME, 1)
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to delete build  for Freestyle project from LastBuild")
    @Test
    public void testDeleteBuildNowFromLastBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean buildMessage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .deleteBuildPermalinksLastBuildDropDown()
                .clickYesButton()
                .isNoBuildsDisplayed();

        Assert.assertTrue(buildMessage, "'No builds' message is not displayed on the Freestyle Project's page");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verify that icons is not displayed")
    @Test
    public void testKeepThisBuildForever() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);
        final List<String> buildKeepForeverMenuOptions = new ArrayList<>(List.of(
                "Changes", "Console Output", "Edit Build Information"));

        FreestyleProjectPage freestyleProjectPage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildDateFromBuildRow()
                .clickKeepBuildForever()
                .getBreadcrumb()
                .clickJobNameFromBreadcrumb(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .openBuildsDropDownMenu();

        Assert.assertEquals(freestyleProjectPage.getTextBuildDropDownMenuOptions(), buildKeepForeverMenuOptions);
        Assert.assertTrue(freestyleProjectPage.isIconLockIsDispalyed(), "The lock icon is not displayed");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verify that Project Name is Visible On the Project Page")
    @Test
    public void testVisibleProjectNameOnProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String projectNameOnProjectPage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .getJobName();

        Assert.assertEquals(projectNameOnProjectPage, "Project " + FREESTYLE_NAME);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to disable Freestyle Project from Project Page")
    @Test
    public void testDisableFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage projectPage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickDisable();

        String disabledMessageText = projectPage
                .getDisabledMessageText();

        String enableButtonText = projectPage
                .getEnableButtonText();

        List<String> dropDownMenu = projectPage
                .getHeader()
                .clickLogo()
                .getListOfProjectMenuItems(FREESTYLE_NAME);

        SoftAssert soft = new SoftAssert();
        soft.assertFalse(dropDownMenu.contains("Build Now"), "'Build Now' option is present in drop-down menu");
        soft.assertEquals(disabledMessageText, "This project is currently disabled");
        soft.assertEquals(enableButtonText, "Enable");
        soft.assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to enable disabled Freestyle Project from Project Page")
    @Test
    public void testEnableFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage projectName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickDisable()
                .clickEnable();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(projectName.getDisableButtonText(), "Disable Project");
        soft.assertTrue(projectName.clickConfigure().isEnabledDisplayed(), "'Enabled' is not displayed");
        soft.assertEquals(projectName.getHeader().clickLogo().getJobBuildStatusIcon(FREESTYLE_NAME), "Not built");
        soft.assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of presence description for Freestyle Project")
    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String previewDescription = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION_TEXT)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewDescription, "DESCRIPTION_TEXT");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of presence description added from Freestyle Project Page")
    @Test
    public void testAddDescriptionFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualDescription = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .addDescription("Freestyle project")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, "Freestyle project");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to rename description for build of Freestyle project")
    @Test
    public void testEditDescription() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String editDescription = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(NEW_DESCRIPTION_TEXT)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(editDescription, NEW_DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Changes Page from side menu for Freestyle Project")
    @Test
    public void testNavigateToChangePage() {
        TestUtils.createJob(this, "Engineer", TestUtils.JobType.FreestyleProject, true);

        String text = new MainPage(getDriver())
                .clickJobName("Engineer", new FreestyleProjectPage(getDriver()))
                .clickChangeOnLeftSideMenu()
                .getTextOfPage();

        Assert.assertTrue(text.contains("No builds."),
                "In the Freestyle project Changes chapter, not displayed status of the latest build.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Workspaces from Project Page for Freestyle Project")
    @Test
    public void testNavigateToWorkspaceFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String workspacePage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickWorkspaceFromSideMenu()
                .getTextFromWorkspacePage();

        Assert.assertEquals(workspacePage, "Workspace of FREESTYLE_NAME on Built-In Node");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of of presence Preview of description for Freestyle Project can be added from Configuration Page")
    @Test
    public void testPreviewDescriptionFromConfigurationPage() {
        final String descriptionText = "In publishing and graphic design, Lorem ipsum is a placeholder " +
                "text commonly used to demonstrate the visual form of a document or a typeface without relying .";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String previewText = new MainPage(getDriver())
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .addDescription(descriptionText)
                .clickPreview()
                .getPreviewText();

        String actualDescriptionText = new FreestyleProjectPage(getDriver())
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(previewText, descriptionText);
        Assert.assertEquals(actualDescriptionText, descriptionText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility 'Description' for Freestyle Project can be added from Configuration Page")
    @Test
    public void testAddDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String description = new FreestyleProjectPage(getDriver())
                .clickConfigure()
                .addDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION_TEXT);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify navigation to Configuration Page from drop-down menu on Dashboard for Freestyle Project")
    @Test
    public void testAccessConfigurationPageFromDashboard() {
        final String breadcrumb = "Dashboard > " + FREESTYLE_NAME + " > Configuration";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectConfigPage freestyleConfigPage = new MainPage(getDriver())
                .clickConfigureDropDown(
                        FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver()))
                );

        Assert.assertEquals(freestyleConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(freestyleConfigPage.getHeaderText(), "Configure");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify navigation to Configuration Page from drop-down menu on Project Page for Freestyle Project")
    @Test
    public void testAccessConfigurationPageFromProjectPage() {
        final String breadcrumbRoute = "Dashboard > " + FREESTYLE_NAME + " > Configuration";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectConfigPage freestyleConfigPage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure();

        Assert.assertEquals(freestyleConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumbRoute);
        Assert.assertEquals(freestyleConfigPage.getHeaderText(), "Configure");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to disable Freestyle Project from Configuration Page")
    @Test
    public void testDisableFromConfigurationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage freestyleProjectPage = new MainPage(getDriver())
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSwitchEnableOrDisable()
                .clickSaveButton();

        String availableMode = freestyleProjectPage
                .getEnableButtonText();

        MainPage mainPage = freestyleProjectPage
                .getHeader()
                .clickLogo();

        Assert.assertEquals(availableMode, "Enable");
        Assert.assertEquals(mainPage.getJobBuildStatusIcon(FREESTYLE_NAME), "Disabled");
        Assert.assertFalse(mainPage.isScheduleBuildOnDashboardAvailable(FREESTYLE_NAME), "The 'Build Now' option is available on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to enable disable Freestyle Project from Configuration Page")
    @Test
    public void testEnableFromConfigurationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage freestyleProjectPageDisabled = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickSwitchEnableOrDisable()
                .clickSaveButton()
                .clickConfigure()
                .clickSwitchEnableOrDisable()
                .clickSaveButton();

        Assert.assertEquals(freestyleProjectPageDisabled.getDisableButtonText(), "Disable Project");
        Assert.assertTrue(freestyleProjectPageDisabled.isDisabledMessageNotDisplayed(),
                "The info message 'This project is currently disabled' is displayed");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility of Setting Parameters to delete Builds ")
    @Test
    public void testSetParametersToDiscardOldBuilds() {
        final int daysToKeepBuilds = 3;
        final int maxOfBuildsToKeep = 5;
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectConfigPage freestyleProjectConfigPage = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickOldBuildCheckBox()
                .enterDaysToKeepBuilds(daysToKeepBuilds)
                .enterMaxNumOfBuildsToKeep(maxOfBuildsToKeep)
                .clickSaveButton()
                .clickConfigure();

        Assert.assertEquals(Integer
                .parseInt(freestyleProjectConfigPage.getDaysToKeepBuilds()), daysToKeepBuilds);
        Assert.assertEquals(Integer
                .parseInt(freestyleProjectConfigPage.getMaxNumOfBuildsToKeep()), maxOfBuildsToKeep);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility of adding a Project on GitHub to a Freestyle Project")
    @Test
    public void testAddingAProjectOnGitHubToTheFreestyleProject() {
        final String expectedNameRepo = "TestRepo";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final String actualNameRepo = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickGitHubProjectCheckbox()
                .inputTextTheInputAreaProjectUrlInGitHubProject(GITHUB_URL)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .selectGitHubFromJobDropdownMenu(NEW_FREESTYLE_NAME)
                .getNameRepo();

        Assert.assertEquals(actualNameRepo, expectedNameRepo);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to add boolean parameter for Freestyle project")
    @Test
    public void testAddBooleanParameterTheFreestyleProject() {
        final String booleanParameter = "Boolean Parameter";
        final String booleanParameterName = "Boolean";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final boolean checkedSetByDefault = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .checkProjectIsParametrized()
                .openAddParameterDropDown()
                .selectParameterInDropDownByType(booleanParameter)
                .inputBooleanParameterName(booleanParameterName)
                .selectCheckboxSetByDefault()
                .clickSaveButton()
                .clickBuildWithParameters()
                .checkedTrue();

        Assert.assertTrue(checkedSetByDefault, "The 'This build requires parameters:' checkbox is unchecked");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the ability to Add a choice parameter")
    @Test
    public void testAddChoiceParameter() {
        final String parameterType = "Choice Parameter";
        final String parameterName = "Choice parameter name test";
        final String parameterDesc = "Choice parameter desc test";
        final List<String> parameterChoicesList = new ArrayList<>() {{
            add("choice one");
            add("choice two");
            add("choice three");
        }};
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        BuildWithParametersPage buildPage = new FreestyleProjectPage(getDriver())
                .clickConfigure()
                .checkProjectIsParametrized()
                .openAddParameterDropDown()
                .selectParameterInDropDownByType(parameterType)
                .inputBooleanParameterName(parameterName)
                .inputParameterChoices(parameterChoicesList)
                .inputParameterDesc(parameterDesc)
                .clickSaveButton()
                .clickBuildWithParameters();

        Assert.assertTrue(buildPage.isParameterNameDisplayed(parameterName), "The Parameter Name is not displayed in the 'This build requires parameters:' section");
        Assert.assertEquals(buildPage.getParameterDescription(), parameterDesc);
        Assert.assertEquals(buildPage.getChoiceParametersValuesList(), parameterChoicesList);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the ability to limit the speed for Builds")
    @Test
    public void testSetRateLimitForBuilds() {
        final String timePeriod = "Week";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final String actualTimePeriod = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .checkThrottleBuilds()
                .selectTimePeriod(timePeriod)
                .clickSaveButton()
                .clickConfigure()
                .getTimePeriodText();

        Assert.assertEquals(actualTimePeriod, timePeriod);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility click 'ExecuteConcurrentBuilds' checkBox from Configuration page")
    @Test
    public void testAllowParallelBuilds() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final boolean statusExecuteConcurrentBuilds = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickCheckBoxExecuteConcurrentBuilds()
                .clickSaveButton()
                .clickConfigure()
                .isExecuteConcurrentBuildsSelected();

        Assert.assertTrue(statusExecuteConcurrentBuilds, "The 'Execute concurrent builds if necessary' checkbox is unchecked");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Check period for Jenkins to wait before Actually Starting Triggered Build")
    @Test
    public void testSetPeriodForJenkinsToWaitBeforeActuallyStartingTriggeredBuild() {
        final String expectedQuietPeriod = "10";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final String actualQuietPeriod = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .clickQuietPeriod()
                .inputQuietPeriod(expectedQuietPeriod)
                .clickSaveButton()
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .getQuietPeriod();

        Assert.assertEquals(actualQuietPeriod, expectedQuietPeriod);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Check number of count for Jenkins if input SCM Checkout Retry Count from Configuration page")
    @Test
    public void testSetNumberOfCountForJenkinsToCheckOutFromTheSCMUntilItSucceeds() {
        final String retryCount = "5";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final String actualRetryCount = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .clickRetryCount()
                .inputSCMCheckoutRetryCount(retryCount)
                .clickSaveButton()
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .getCheckoutRetryCountSCM();

        Assert.assertEquals(actualRetryCount, retryCount);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility enable 'Block Build' from ‘Advanced’in the Configuration page")
    @Test
    public void testEnableJenkinsToBlockBuildsWhenUpstreamProjectIsBuilding() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final boolean statusBlockBuildWhenUpstreamProjectIsBuilding = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .clickBlockBuildWhenUpstreamProjectIsBuilding()
                .clickSaveButton()
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .getTrueBlockBuildWhenUpstreamProjectIsBuilding();

        Assert.assertTrue(statusBlockBuildWhenUpstreamProjectIsBuilding, "The 'Block build when upstream project is building' checkbox is unchecked");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility  'Use Custom Workspace' from ‘General Advanced’in the Configuration page ")
    @Test
    public void testUseCustomWorkspaceFromConfigureGeneralAdvanced() {
        String directoryName = "My directory";

        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualConsoleOutputText = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedGeneral()
                .clickUseCustomWorkspace(directoryName)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .openLastBuildDropDownMenu()
                .clickConsoleOutputLastBuildDropDown()
                .getConsoleOutputText();

        Assert.assertTrue(actualConsoleOutputText.contains(directoryName), "The directory is not used!");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that 'Display name' can be added from Configuration page")
    @Test
    public void testAddDisplayName() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String displayName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .setDisplayName(NEW_FREESTYLE_NAME)
                .clickSaveButton()
                .getJobName();

        Assert.assertEquals(displayName, "Project " + NEW_FREESTYLE_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility 'add Repository' from ‘Source Code Management’ ")
    @Test
    public void testAddRepositoryFromSourceCodeManagement() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String repositoryUrl = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickSourceCodeManagementLink()
                .clickRadioButtonGit()
                .inputRepositoryUrl(GITHUB_URL)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSourceCodeManagementLink()
                .getRepositoryUrlText();

        Assert.assertEquals(repositoryUrl, GITHUB_URL);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility  'add branch' from ‘Source Code Management’ ")
    @Test
    public void testAddBranchFromSourceCodeManagement() {
        final String branchName = "for_jenkins_build";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        GitBuildDataPage gitBuildDataPage = new MainPage(getDriver())
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSourceCodeManagementLink()
                .clickRadioButtonGit()
                .inputRepositoryUrl(NEW_GITHUB_URL)
                .correctMainBranchName()
                .clickAddBranchButton()
                .inputAddBranchName(branchName)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickIconAdditionalBranchBuild()
                .clickGitBuildDataLink();

        Assert.assertEquals(gitBuildDataPage.getNamesOfBuiltBranches(), "origin/main, origin/for_jenkins_build");
        Assert.assertEquals(gitBuildDataPage.getRepositoryName(), NEW_GITHUB_URL);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility  in the Configure click checkBox ‘Build After OtherProjects Are Built’ ")
    @Test
    public void testConfigureBuildTriggersBuildAfterOtherProjectsAreBuilt() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String lastBuildInfo = new MainPage(getDriver())
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickBuildAfterOtherProjectsAreBuiltCheckBox()
                .inputProjectsToWatch(NEW_FREESTYLE_NAME)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickJobDropdownMenuBuildNow(NEW_FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .getBuildInfo();

        Assert.assertEquals(lastBuildInfo, "Started by upstream project " + NEW_FREESTYLE_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to add build steps options for Freestyle Project")
    @Test
    public void testBuildStepsOptions() {
        List<String> expectedOptionsInBuildStepsSection = List.of("Execute Windows batch command", "Execute shell",
                "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets", "Run with timeout",
                "Set build status to \"pending\" on GitHub commit");

        List<String> actualOptionsInBuildStepsSection = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .openBuildStepOptionsDropdown()
                .getOptionsInBuildStepDropdown();

        Assert.assertEquals(actualOptionsInBuildStepsSection, expectedOptionsInBuildStepsSection);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility  in the steps of Build add ‘Execute Windows Batch Command’ ")
    @Test
    public void testBuildStepsExecuteWindowsBatchCommand() {
        final String commandFieldText = "echo Hello";
        final String cmdCommand = "$ cmd /c call";

        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String consoleOutput = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .openBuildStepOptionsDropdown()
                .selectExecuteWindowsBatchCommandBuildStep()
                .addExecuteWindowsBatchCommand(commandFieldText)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .getConsoleOutputText();

        Assert.assertTrue(consoleOutput.contains(cmdCommand), "Command wasn't run");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility  in the steps of Build add ‘Execute Shell’ ")
    @Test
    public void testBuildStepsExecuteShell() {
        final String commandFieldText = "echo Hello";

        String consoleOutput = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .addExecuteShellBuildStep(commandFieldText)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .getConsoleOutputText();

        Assert.assertTrue(consoleOutput.contains(commandFieldText), "Command wasn't run OR test was run on the Windows");
        Assert.assertTrue(consoleOutput.contains("Finished: SUCCESS"), "Build wasn't finished successfully");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility  in the steps of Build add ‘Invoke Maven Targets’")
    @Test
    public void testBuildStepsInvokeMavenGoalsTargets() {
        String goals = "clean";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String mavenGoals = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .openBuildStepOptionsDropdown()
                .addInvokeMavenGoalsTargets(goals)
                .clickSaveButton()
                .clickConfigure()
                .getMavenGoals();

        Assert.assertEquals(mavenGoals, goals);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility of configuring actions after the build, aggregating test results in a downstream flow")
    @Test
    public void testConfigurePostBuildActionsAggregateDownStreamTestResults() {
        BuildPage buildPage = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickAggregateDownstreamTestResults()
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink();

        Assert.assertTrue(buildPage.isDisplayedAggregatedTestResultLink(), "The 'Aggregated Test Result' link is not displayed on Build page");
        Assert.assertEquals(buildPage.getTestResultsNodeText(), "Aggregated Test Result (no tests)");
        Assert.assertTrue(buildPage.getAggregateTestResultSideMenuLinkText().contains("/job/" + FREESTYLE_NAME + "/lastBuild/aggregatedTestReport"),
                "The Attribute 'href' of the 'Aggregated Test Result' side menu link does not contain '/job/" + FREESTYLE_NAME + "/lastBuild/aggregatedTestReport'");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the possibility of configuring the artifacts of the archive of actions after the build")
    @Test
    public void testConfigurePostBuildActionArchiveArtifacts() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String archiveTheArtifacts = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickArchiveTheArtifacts()
                .clickSaveButton()
                .clickConfigure()
                .clickPostBuildActionsButton()
                .getTextArchiveArtifacts();

        Assert.assertEquals(archiveTheArtifacts, "Archive the artifacts\n" +
                "?\n" +
                "Files to archive\n" +
                "?\n" +
                "Advanced");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking whether the post-build action can be configured to create other projects")
    @Test
    public void testConfigurePostBuildActionBuildOtherProjects() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String lastBuildInfo = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickBuildOtherProjects()
                .setBuildOtherProjects(NEW_FREESTYLE_NAME)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .getBuildInfo();

        Assert.assertEquals(lastBuildInfo, "Started by upstream project " + FREESTYLE_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the ability to add Git Publisher after the build in the configuration")
    @Test
    public void testAddGitPublisherInPostBuildActions() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String gitPublisherText = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickGitPublisher()
                .clickSaveButton()
                .clickConfigure()
                .getGitPublisherText();

        Assert.assertEquals(gitPublisherText, "Git Publisher\n?");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the ability to add email notifications after the build in the configuration")
    @Test
    public void testAddEmailNotificationToPostBuildActions() {
        final String email = "email@email.com";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String currentEmail = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickEmailNotification()
                .setEmailNotification(email)
                .clickSaveButton()
                .clickConfigure()
                .clickPostBuildActionsButton()
                .getEmailNotificationFieldText();

        Assert.assertEquals(currentEmail, email);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the ability to edit email notifications after the build in the configuration")
    @Test
    public void testConfigurePostBuildActionEditableEmailNotification() {
        final String userEmail = "jenkins06test@gmail.com";
        final String userPass = "bfdzlscazepasstj";
        final String userPort = "465";
        final String userStmp = "smtp.gmail.com";

        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String emailSentLog = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureSystemLink()
                .inputSmtpServerFieldExtendedEmailNotifications(userStmp)
                .inputSmtpPortFieldExtendedEmailNotifications(userPort)
                .clickAdvancedButtonExtendedEmailNotification()
                .clickAddCredentialButton()
                .inputUsernameIntoAddCredentialPopUpWindow(userEmail)
                .inputPasswordIntoAddCredentialPopUpWindow(userPass)
                .clickAddButtonAddCredentialPopUp()
                .selectCreatedCredentials(userEmail)
                .checkUseSSLCheckbox()
                .clickDefaultTriggersButton()
                .checkAlwaysDefaultTriggers()
                .checkSuccessDefaultTriggers()
                .inputSmtpServerFieldEmailNotifications(userStmp)
                .clickAdvancedButtonEmailNotification()
                .clickUseSMTPAuthenticationCheckbox()
                .inputUserNameAndPasswordSMTPAuthentication(userEmail, userPass)
                .checkUseSSLCheckboxEmailNotifications()
                .inputSmtpPortEmailNotificationsField(userPort)
                .clickSaveButton()
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .selectEditableEmailNotification()
                .inputEmailIntoProjectRecipientListInputField(userEmail)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickBuildIconStatus()
                .getConsoleOutputText();

        Assert.assertTrue(emailSentLog.contains("Sending email to: " + userEmail), "The Email report wasn't sent");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking the commit status on GitHub for post-build actions")
    @Test
    public void testSetGitHubCommitStatusToPostBuildActions() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String commitContextName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickSetGitHubCommitStatus()
                .setGitHubCommitStatusContext(FREESTYLE_NAME)
                .clickSaveButton()
                .clickConfigure()
                .clickPostBuildActionsButton()
                .getGitHubCommitStatus();

        Assert.assertEquals(commitContextName, FREESTYLE_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking that the workspace is deleted when the build is completed After performing the build actions")
    @Test
    public void testDeleteWorkspaceWhenBuildDonePostBuildActions() {
        String expectedWorkspaceStatus = "Error: no workspace";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualWorkspaceStatus = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickDeleteWorkspaceWhenBuildDone()
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickWorkspaceFromSideMenu()
                .getTextFromWorkspacePage();

        Assert.assertEquals(actualWorkspaceStatus, expectedWorkspaceStatus);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that deleting Folder can be canceled from drop-down menu on the Main page")
    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean projectIsPresent = new MainPage(getDriver())
                .dropDownMenuClickDelete(FREESTYLE_NAME)
                .dismissAlert()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(FREESTYLE_NAME);

        Assert.assertTrue(projectIsPresent, "The name of the Freestyle project is not shown");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that deleting Folder can be canceled from side menu on the Main page")
    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean isProjectPresent = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickDeleteAndCancel()
                .getHeader()
                .clickLogo()
                .verifyJobIsPresent(NEW_FREESTYLE_NAME);

        Assert.assertTrue(isProjectPresent, "The Freestyle project's name is not displayed on Dashboard from Home page!");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to delete Freestyle Project from drop-down menu")
    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);
        MainPage welcomeIsDisplayed = new MainPage(getDriver())
                .dropDownMenuClickDelete(FREESTYLE_NAME)
                .acceptAlert();

        Assert.assertTrue(welcomeIsDisplayed.isWelcomeDisplayed(), "'Welcome to Jenkins!' text is not displayed");
        Assert.assertEquals(welcomeIsDisplayed.clickMyViewsSideMenuLink().getStatusMessageText(), "This folder is empty");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to delete Freestyle Project from side menu")
    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean isProjectPresent = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickDeleteAndAccept()
                .isWelcomeDisplayed();

        Assert.assertTrue(isProjectPresent, "'Welcome to Jenkins!' text is not displayed");
    }
}
