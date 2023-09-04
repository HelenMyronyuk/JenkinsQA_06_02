package school.redrover;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.builds.*;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobsConfig.PipelineConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class PipelineTest extends BaseTest {

    private static final String NAME = "PIPELINE_NAME";
    private static final String NEW_NAME = "Pipeline Project";
    private static final String DESCRIPTION = "This is a test description";
    private static final String DISPLAYED_BUILD_NAME = "New Build Name";

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to create Pipeline project with allowed characters")
    @Test
    public void testCreateWithAllowedCharacters() {
        final String allowedChar = "_-+=”{},";

        String projectNameDashboard = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(allowedChar)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .getJobName(allowedChar);

        Assert.assertEquals(projectNameDashboard, allowedChar);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to sort Pipeline projects alphabetically")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to create Pipeline project through Jenkins UI")
    @Test
    public void testCreatingBasicPipelineProjectThroughJenkinsUI() {
        String resultOptionDefinitionFieldText = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .scrollToPipelineSection()
                .getOptionTextInDefinitionField();

        Assert.assertEquals(resultOptionDefinitionFieldText, "Pipeline script");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to rename Pipeline project from drop-down menu")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to rename Pipeline project from side menu")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Pipeline project with current name")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Pipeline project with unsafe character")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to create build with parameters for Pipeline project")
    @Test
    public void testCreateBuildWithParameters() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        ConsoleOutputPage consoleOutputPage = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickTrend()
                .clickBuildIcon();

        Assert.assertTrue(consoleOutputPage.isDisplayedGreenIconV(), "The build of the Pipeline Project is not finished successfully");
        Assert.assertTrue(consoleOutputPage.isDisplayedBuildTitle(), "The build of the Pipeline Project is not found");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to create build for Pipeline project from drop-down menu")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to create build for Pipeline project from side menu")
    @Test
    public void testCreateBuildNowFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "The build of the Pipeline Project is not created");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to create build for Pipeline project by clicking green arrow")
    @Test
    public void testCreateBuildNowFromArrow() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "The build of the Pipeline Project is not created");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of possibility to add display name for build of Pipeline project")
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

        Assert.assertTrue(newDisplayedBuildName, "Added name for the build is not displayed");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of possibility to preview description from BuildPage for Pipeline project")
    @Test
    public void testPreviewDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String previewText = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
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
    @Description("Verification of possibility to add description for Pipeline project from BuildPage")
    @Test
    public void testAddDescriptionFromBuildPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String newBuildDescription = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(newBuildDescription, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of possibility to edit description for Pipeline project from BuildPage")
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
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(NEW_NAME)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(newBuildDescription, NEW_NAME);
    }

    @DataProvider(name = "buildPipelineMenuOptions")
    public Object[][] buildPipelineMenuOptions() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesBuildPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) DeletePage::new, "Delete"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ReplayPage::new, "Replay"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) PipelineStepsPage::new, "Pipeline Steps"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) WorkspacesBuildPage::new, "Workspaces"},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of navigation to options page from build drop-down menu from dashboard")
    @Test(dataProvider = "buildPipelineMenuOptions")
    public void testNavigateToOptionsFromBuildDropDownFromDashboard(Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDownMenu, String expectedPage) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);
        String actualPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .getHeader()
                .clickLogo()
                .openBuildDropDownMenu("#1")
                .selectOptionFromDropDownList(pageFromDropDownMenu.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to build changes for Pipeline project from drop-down menu")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to the option from the last build drop-down menu of Pipeline project")
    @Test(dataProvider = "buildPipelineMenuOptions")
    public void testNavigateToOptionsFromLastBuild(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDownMenu, String expectedPage) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String lastBuildNumber = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .refreshPage()
                .getLastBuildNumber();

        String actualPage = new PipelinePage(getDriver())
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .clickBuildOptionFromSideMenu(pageFromDropDownMenu.apply(getDriver()))
                .getAssertTextFromPage();

        String breadcrumb = pageFromDropDownMenu.apply(getDriver())
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
        Assert.assertTrue(breadcrumb.contains(lastBuildNumber), "The full text of the breadcrumb does not contain the last build number");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of navigation to options page from build drop-down menu from ProjectPage")
    @Test(dataProvider = "buildPipelineMenuOptions")
    public void testNavigateToOptionsFromBuildDropDownFromProjectPage(Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDownMenu, String expectedPage) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String actualPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .refreshPage()
                .openBuildsDropDownMenu()
                .selectOptionFromDropDownList(pageFromDropDownMenu.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }


    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to edit build information for Pipeline project from ProjectPage")
    @Test
    public void testEditBuildInformationFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String titleEditBuildPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickEditBuildInformFromProjectPage()
                .getHeaderText();

        Assert.assertEquals(titleEditBuildPage, "Edit Build Information");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of presence preview description of build information for Pipeline project")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence description of build information for Pipeline project")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to delete build  for Pipeline project from drop-down menu")
    @Test
    public void testDeleteBuildNow() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean noBuildsMessage = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildDropdownMenuDeleteBuild("#1")
                .clickDelete(new PipelinePage(getDriver()))
                .isNoBuildsDisplayed();

        Assert.assertTrue(noBuildsMessage, "'No builds' message is not displayed on the Pipeline project's page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to delete build  for Pipeline project from side menu")
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

        Assert.assertTrue(noBuildsMessage, "'No builds' message is not displayed on the Pipeline Project's page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to replay build  for Pipeline project from drop-down menu")
    @Test
    public void testReplayBuild() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String lastBuildNumber = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .openPermalinksLastBuildsDropDownMenu()
                .clickReplayFromDropDownMenu()
                .clickRunButton()
                .refreshPage()
                .getLastBuildNumber();

        Assert.assertEquals(lastBuildNumber, "#2");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to step build for Pipeline project from ProjectPage")
    @Test
    public void testPipelineStepsBuildFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String pipelineSteps = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickPipelineStepsFromBuildDropDownFromSideMenu()
                .getAssertTextFromPage();

        Assert.assertEquals(pipelineSteps, "Pipeline Steps");
    }


    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of presence workspaces for Pipeline project from ProjectPage")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to make several builds of Pipeline project")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to make script sample 'Hello' for Pipeline project")
    @Test
    public void testSelectHelloAndConsoleOutputSuccess() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String textSuccessBuild = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .selectScriptSample("hello")
                .clickSaveButtonDescription()
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .getConsoleOutputText();

        Assert.assertTrue(textSuccessBuild.contains("Finished: SUCCESS"), "The build of the Pipeline Project is not finished successfully");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of presence description of Pipeline project from ProjectPage")
    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String previewDescription = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewDescription, DESCRIPTION);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to make script sample 'Hello' for Pipeline project")
    @Test
    public void testPipelineBuildNow() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String stageName = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .selectScriptSample("hello")
                .clickSaveButtonDescription()
                .clickBuildNowFromSideMenu()
                .getStage();

        Assert.assertEquals(stageName, "Hello");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of possibility to add description for Pipeline project from ProjectPage")
    @Test
    public void testAddDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String resultDescriptionText = new PipelinePage(getDriver())
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(resultDescriptionText, DESCRIPTION);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to disable Pipeline project from ProjectPage")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to add enable Pipeline project from ProjectPage")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to get changes information for builds of Pipeline project")
    @Test
    public void testProjectChangesFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String text = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickChangeOnLeftSideMenu()
                .getTextOfPage();

        Assert.assertTrue(text.contains("No changes in any of the builds"),
                "In the Pipeline Changes chapter is not displayed status of the latest build.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to build Pipeline project after making changes in code")
    @Test
    public void testPipelineBuildingAfterChangesInCode() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        BuildPage buildPage = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .clickPipelineLeftMenu()
                .selectScriptSample("hello")
                .clickSaveButtonDescription()
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink();

        Assert.assertTrue(buildPage.isDisplayedBuildPageHeaderText(), "The build's header text 'Build #1' is not displayed");
        Assert.assertTrue(buildPage.isDisplayedGreenIconV(), "The build #1 is not finished successfully");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to get ConfigurationPage for  Pipeline project from drop-down menu")
    @Test
    public void testAccessConfigurationPageFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String configPageHeaderText = new MainPage(getDriver())
                .clickConfigureDropDown(NAME, new PipelineConfigPage(new PipelinePage(getDriver())))
                .getPageHeaderText();

        Assert.assertEquals(configPageHeaderText, "Configure");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to get ConfigurationPage for  Pipeline project from side menu")
    @Test
    public void testAccessConfigurationPageFromSideMenu() {
        final String breadcrumb = "Dashboard > " + NAME + " > Configuration";
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);
        PipelineConfigPage pipelineConfigPage = new PipelinePage(getDriver())
                .clickConfigure();

        Assert.assertEquals(pipelineConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(pipelineConfigPage.getHeaderText(), "Configure");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to disable Pipeline project from ConfigurationPage")
    @Test
    public void testDisableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean projectDisable = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .clickSwitchEnableOrDisable()
                .clickSaveButton()
                .clickConfigure()
                .isEnabledDisplayed();

        Assert.assertFalse(projectDisable, "The Pipeline Project is enabled");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to enable Pipeline project from ConfigurationPage")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to edit description for Pipeline project from ConfigurationPage")
    @Test
    public void testEditDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);
        final String newDescription = "Edited description text";

        String jobDescription = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickSaveButton()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(newDescription)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(jobDescription, newDescription);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence preview description for Pipeline project from ConfigurationPage")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of possibility to add description for Pipeline project from ConfigurationPage")
    @Test
    public void testAddDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String jobDescription = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(jobDescription, DESCRIPTION);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of presence checkbox for discard old builds for Pipeline project")
    @Test
    public void testDiscardOldBuildsIsChecked() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean discardOldBuildsCheckbox = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .clickOldBuildCheckBox()
                .clickSaveButton()
                .clickConfigure()
                .checkboxDiscardOldBuildsIsSelected();

        Assert.assertTrue(discardOldBuildsCheckbox, "The checkbox 'Discard old builds' is not checked");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to discard old builds Pipeline project")
    @Test
    public void testDiscardOldBuildsPipeline() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        String jobName = new PipelinePage(getDriver())
                .clickConfigure()
                .clickOldBuildCheckBox()
                .enterDaysToKeepBuilds(2)
                .enterMaxNumOfBuildsToKeep(30)
                .clickSaveButton()
                .getJobName();

        Assert.assertEquals(jobName, "Pipeline " + NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to discard old builds parameters for Pipeline project")
    @Test
    public void testDiscardOldBuildsParams() {
        final int days = 7;
        final int builds = 5;

        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        PipelineConfigPage pipelineConfigPage = new PipelinePage(getDriver())
                .clickConfigure()
                .clickOldBuildCheckBox()
                .enterDaysToKeepBuilds(days)
                .enterMaxNumOfBuildsToKeep(builds)
                .clickSaveButton()
                .clickConfigure();

        Assert.assertEquals(pipelineConfigPage.getDaysToKeepBuilds(), String.valueOf(days));
        Assert.assertEquals(pipelineConfigPage.getMaxNumOfBuildsToKeep(), String.valueOf(builds));
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to discard old builds days =0 for Pipeline project")
    @Test
    public void testDiscardOldBuilds0Days() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        String actualErrorMessage = new PipelinePage(getDriver())
                .clickConfigure()
                .clickOldBuildCheckBox()
                .enterDaysToKeepBuilds(0)
                .enterMaxNumOfBuildsToKeep(0)
                .getErrorMessageStrategyDays();

        Assert.assertEquals(actualErrorMessage, "Not a positive integer");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to add project on GitHub to Pipeline project")
    @Test
    public void testAddingAProjectOnGithubToThePipelineProject() {
        final String gitHubUrl = "https://github.com/ArtyomDulya/TestRepo";
        final String expectedNameRepo = "TestRepo";

        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String actualNameRepo = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
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
    @Feature("Function")
    @Description("Verification of possibility to add boolean parameter with description for Pipeline project")
    @Test
    public void testAddBooleanParameterWithDescription() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        final String name = "Pipeline Boolean Parameter";
        final String description = "Some boolean parameters here";
        final String parameterName = "Boolean Parameter";

        BuildWithParametersPage<PipelinePage> buildParametersPagePage = new PipelinePage(getDriver())
                .clickConfigure()
                .checkProjectIsParametrized()
                .openAddParameterDropDown()
                .selectParameterInDropDownByType(parameterName)
                .inputBooleanParameterName(name)
                .selectCheckboxSetByDefault()
                .setBooleanParameterDescription(description)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickBuildButton(new PipelinePage(getDriver()));

        Assert.assertEquals(buildParametersPagePage.getBooleanParameterName(), name);
        Assert.assertEquals(buildParametersPagePage.getBooleanParameterCheckbox(), "true");
        Assert.assertEquals(buildParametersPagePage.getParameterDescription(), description);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to add boolean parameter for Pipeline project")
    @Test
    public void testAddBooleanParameter() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        final String name = "Pipeline Boolean Parameter";
        final String parameterName = "Boolean Parameter";

        BuildWithParametersPage<PipelinePage> buildParametersPage = new PipelinePage(getDriver())
                .clickConfigure()
                .checkProjectIsParametrized()
                .openAddParameterDropDown()
                .selectParameterInDropDownByType(parameterName)
                .inputBooleanParameterName(name)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickBuildButton(new PipelinePage(getDriver()));

        Assert.assertEquals(buildParametersPage.getBooleanParameterName(), name);
        Assert.assertNull(buildParametersPage.getBooleanParameterCheckbox());
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to add all parameters for Pipeline project")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of possibility to display name for Pipeline project")
    @Test
    public void testAddDisplayName() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, false);

        String pipelineName = new PipelinePage(getDriver())
                .clickConfigure()
                .scrollAndClickAdvancedButton()
                .setDisplayName(NEW_NAME)
                .clickSaveButton()
                .getJobName();

        String projectName = new PipelinePage(getDriver())
                .getProjectNameSubtitleWithDisplayName();

        String jobName = new MainPage(getDriver())
                .getHeader()
                .clickLogo()
                .getJobName(NAME);

        Assert.assertEquals(pipelineName, "Pipeline " + NEW_NAME);
        Assert.assertEquals(projectName, NAME);
        Assert.assertEquals(jobName, NEW_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to create Pipeline project with script")
    @Test
    public void testCreateNewPipelineWithScript() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean projectIsPresent = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .selectScriptSample("scripted")
                .clickSaveButtonDescription()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(projectIsPresent, "The Pipeline Project's name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to cancel deleting Pipeline project from drop-down menu")
    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean projectIsPresent = new MainPage(getDriver())
                .dropDownMenuClickDelete(NAME)
                .dismissAlert()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(projectIsPresent, "The Pipeline Project's name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to cancel deleting Pipeline project from side menu")
    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        boolean isProjectPresent = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickDeleteAndCancel()
                .getHeader()
                .clickLogo()
                .verifyJobIsPresent(NAME);

        Assert.assertTrue(isProjectPresent, "The Pipeline Project's name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to delete Pipeline project from drop-down menu")
    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String welcomeText = new MainPage(getDriver())
                .dropDownMenuClickDelete(NAME)
                .acceptAlert()
                .getWelcomeText();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of possibility to delete Pipeline project from side menu")
    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String welcomeText = new MainPage(getDriver())
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickDeleteAndAccept()
                .getWelcomeText();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of navigation to options page for Pipeline project from build side menu")
    @Test(dataProvider = "buildPipelineMenuOptions")
    public void testNavigateToOptionsFromBuildSide(Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenu, String expectedPage) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.Pipeline, true);

        String actualPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(NAME)
                .clickJobName(NAME, new PipelinePage(getDriver()))
                .clickLastBuildLink()
                .clickBuildOptionFromSideMenu(pageFromSideMenu.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertTrue(actualPage.contains(expectedPage), "Navigated to an unexpected page");
    }
}
