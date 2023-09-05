package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.builds.ConsoleOutputPage;
import school.redrover.model.builds.EditBuildInformationPage;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.builds.PipelineStepsPage;
import school.redrover.model.builds.ReplayPage;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobsConfig.MultiConfigurationProjectConfigPage;
import school.redrover.model.jobsConfig.PipelineConfigPage;
import school.redrover.model.jobsSidemenu.ChangesPage;
import school.redrover.model.jobsSidemenu.FullStageViewPage;
import school.redrover.model.jobsSidemenu.PipelineSyntaxPage;
import school.redrover.model.jobsSidemenu.WorkspacePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.function.Function;

public class BuildHistoryTest extends BaseTest {

    private final String FREESTYLE_PROJECT_NAME = "Freestyle" + RandomStringUtils.randomAlphanumeric(7);
    private final String MULTI_CONFIGURATION_PROJECT_NAME = "MultiConfiguration" + RandomStringUtils.randomAlphanumeric(7);
    private final String PIPELINE_PROJECT_NAME = "Pipeline" + RandomStringUtils.randomAlphanumeric(7);

    @DataProvider(name = "project-type")
    public Object[][] projectType() {
        return new Object[][]{
                {TestUtils.JobType.FreestyleProject},
                {TestUtils.JobType.Pipeline},
                {TestUtils.JobType.MultiConfigurationProject},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that build history table contains information about all types of built projects")
    @Test
    public void testAllTypesOfProjectsIsDisplayedInTable() {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        TestUtils.createJob(this, PIPELINE_PROJECT_NAME, TestUtils.JobType.Pipeline, true);

        int numberOfLinesInBuildHistoryTable = new MainPage(getDriver())
                .getHeader()
                .clickLogo()
                .clickJobDropdownMenuBuildNow(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickJobDropdownMenuBuildNow(FREESTYLE_PROJECT_NAME)
                .clickJobDropdownMenuBuildNow(PIPELINE_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .getNumberOfLinesInBuildHistoryTable();

        Assert.assertEquals(numberOfLinesInBuildHistoryTable, 4);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of navigation to Console Output Page")
    @Test
    public void testConsoleOutputFreestyleBuild() {
        final String expectedConsoleOutputText = "Started by user \nadmin\nRunning as SYSTEM\n"
                + "Building in workspace /var/jenkins_home/workspace/"
                + FREESTYLE_PROJECT_NAME
                + "\nFinished: SUCCESS";
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        ConsoleOutputPage consoleOutputPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .clickProjectBuildConsole(FREESTYLE_PROJECT_NAME);

        String actualConsoleOutputText = consoleOutputPage.getConsoleOutputText();
        String pageHeader = consoleOutputPage.getPageHeaderText();

        Assert.assertEquals(pageHeader, "Console Output");
        Assert.assertEquals(actualConsoleOutputText, expectedConsoleOutputText);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verification of Status Message Text of broken build")
    @Test
    public void testVerifyStatusBroken() {
        final String textToPipelineScript = "Test";
        final String expectedStatusMessageText = "broken since this build";
        TestUtils.createJob(this, PIPELINE_PROJECT_NAME, TestUtils.JobType.Pipeline, true);

        String actualStatusMessageText = new MainPage(getDriver())
                .clickJobName(PIPELINE_PROJECT_NAME, new PipelinePage(getDriver()))
                .clickConfigure()
                .scrollToPipelineSection()
                .inputInScriptField(textToPipelineScript)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickBuildByGreenArrow(PIPELINE_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .getStatusMessageText();

        Assert.assertEquals(actualStatusMessageText, expectedStatusMessageText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that name of project is present on Time line on Build History page")
    @Test
    public void testPresenceProjectNameOnBuildHistoryTimeline() {
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean projectNameOnBuildHistoryTimeline = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .getBubbleTitleOnTimeline();

        Assert.assertTrue(projectNameOnBuildHistoryTimeline, "Project name is not displayed from time line!");
    }

    @DataProvider(name = "projectOptionsFromDropDownMenu")
    public Object[][] getProjectDropDownMenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) WorkspacePage::new,
                        "Workspace of " + MULTI_CONFIGURATION_PROJECT_NAME + " on Built-In Node"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(driver)),
                        "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new MultiConfigurationProjectPage(driver)),
                        "Delete Multi-configuration project: are you sure?"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new MultiConfigurationProjectPage(driver)),
                        "Rename Multi-configuration project " + MULTI_CONFIGURATION_PROJECT_NAME}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for Multi-configuration project")
    @Test(dataProvider = "projectOptionsFromDropDownMenu")
    public void testNavigateToOptionDropDownMenuForMultiConfigurationProject(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDown, String pageText) {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String actualPageText;

        BaseMainHeaderPage<?> baseMainHeaderPage = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectDropDownMenu(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickOptionsFromMenu(pageFromDropDown.apply(getDriver()));

        if (pageFromDropDown.apply(getDriver()).callByMenuItemName().contains("Delete")) {
            actualPageText = baseMainHeaderPage.getAlertBoxText();
        } else {
            actualPageText = baseMainHeaderPage.getAssertTextFromPage();
        }

        Assert.assertEquals(actualPageText, pageText);
    }

    @DataProvider(name = "multiConfigurationBuildDropDownMenu")
    public Object[][] getBuildDropDownMenuFroMultiConfigurationProject() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new MultiConfigurationProjectPage(driver)), "Delete build #1"},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for Multi-configuration project")
    @Test(dataProvider = "multiConfigurationBuildDropDownMenu")
    public void testNavigateToDefaultBuildOptionDropDownMenuForMultiConfigurationProject(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDown, String textFromPage) {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String actualTextFromPage = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openDefaultBuildDropDownMenu(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickOptionsFromMenu(pageFromDropDown.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualTextFromPage, textFromPage);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to build page of Freestyle, Pipeline and Multiconfiguration (not default) from timeline")
    @Ignore
    @Test(dataProvider = "project-type")
    public void testNavigateToBuildPageFromTimeline(TestUtils.JobType jobType) {
        final String jobName = "BUILD_PROJECT";
        TestUtils.createJob(this, jobName, jobType, true);

        boolean buildPageHeader = new MainPage(getDriver())
                .clickBuildByGreenArrow(jobName)
                .clickBuildsHistoryFromSideMenu()
                .clickLastNotDefaultBuildFromTimeline()
                .clickLastNotDefaultBuildLinkFromBubblePopUp()
                .isDisplayedBuildPageHeaderText();

        Assert.assertTrue(buildPageHeader, "Wrong page! The build page header text is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verify that the Freestyle project's bubble pop up can be opened from the Timeline on the Build History page")
    @Test
    public void testOpenBuildPopUpOfFreestyle() {
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean isHeaderTextOfBuildPopUpDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .clickBuildNameOnTimeline(FREESTYLE_PROJECT_NAME)
                .isBuildPopUpHeaderTextDisplayed(FREESTYLE_PROJECT_NAME);

        Assert.assertTrue(isHeaderTextOfBuildPopUpDisplayed, "The build pop up cannot be opened from timeline!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verify that default build bubble to MultiConfiguration project is present on Time line on Build History page")
    @Test
    public void testOpenDefaultBuildPopUpOfMultiConfiguration() {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean isDefaultBuildPopUpDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .clickDefaultBuildBubbleFromTimeline()
                .isDefaultBuildPopUpHeaderTextDisplayed();

        Assert.assertTrue(isDefaultBuildPopUpDisplayed, "Default build pop up is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verify the ability to close the bubble pop up of Default MultiConfiguration from Timeline")
    @Ignore
    @Test
    public void testCloseDefaultMultiConfigurationPopOpFromTimeline() {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject,
                true);

        boolean isBubblePopUpClosed = new MainPage(getDriver())
                .clickBuildByGreenArrow(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .clickBuildNameOnTimeline(MULTI_CONFIGURATION_PROJECT_NAME)
                .closeProjectWindowButtonInTimeline()
                .isBuildPopUpInvisible();

        Assert.assertTrue(isBubblePopUpClosed, "Bubble pop up window is not closed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to close the bubble pop up of Freestyle, Pipeline, MultiConfiguration(not default) project build from timeline")
    @Ignore
    @Test(dataProvider = "project-type")
    public void testCloseBuildPopUp(TestUtils.JobType jobType) {
        final String jobName = "BUILD_PROJECT";
        TestUtils.createJob(this, jobName, jobType, true);

        boolean isBubblePopUpClosed = new MainPage(getDriver())
                .clickBuildByGreenArrow(jobName)
                .clickBuildsHistoryFromSideMenu()
                .clickLastNotDefaultBuildFromTimeline()
                .closeProjectWindowButtonInTimeline()
                .isBuildPopUpInvisible();

        Assert.assertTrue(isBubblePopUpClosed, "Bubble pop up window not closed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to delete the all types of project build from Build page")
    @Test(dataProvider = "project-type")
    public void testDeleteBuild(TestUtils.JobType jobType) {
        final int size = 0;
        final String jobName = "BUILD_PROJECT";
        TestUtils.createJob(this, jobName, jobType, true);

        int numberOfLinesInBuildHistoryTable = new MainPage(getDriver())
                .clickBuildByGreenArrow(jobName)
                .clickBuildsHistoryFromSideMenu()
                .clickLastNotDefaultBuild()
                .clickDeleteBuild(jobType.createJobPage(getDriver()))
                .clickYesButton()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .getNumberOfLinesInBuildHistoryTable();

        Assert.assertEquals(numberOfLinesInBuildHistoryTable, size);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to build page of Multiconfiguration default from timeline")
    @Test
    public void testNavigateToMultiConfigurationDefaultBuildPageFromTimeline() {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean buildPageHeader = new MainPage(getDriver())
                .clickBuildByGreenArrow(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .clickDefaultBuildBubbleFromTimeline()
                .clickDefaultBuildLinkFromTimeline()
                .isDisplayedBuildPageHeaderText();

        Assert.assertTrue(buildPageHeader, "Wrong page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to close the bubble pop up of MultiConfiguration project build from timeline")
    @Ignore
    @Test
    public void testCloseBuildPopUpOfMultiConfiguration() {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean isBubblePopUpClosed = new MainPage(getDriver())
                .clickBuildByGreenArrow(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .clickBuildNameOnTimeline(MULTI_CONFIGURATION_PROJECT_NAME)
                .closeProjectWindowButtonInTimeline()
                .isBuildPopUpInvisible();

        Assert.assertTrue(isBubblePopUpClosed, "Bubble pop up window not closed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verify that build bubble to Pipeline project is present on Time line on Build History page")
    @Test
    public void testOpenDefaultBuildPopUpOfPipeline() {
        TestUtils.createJob(this, PIPELINE_PROJECT_NAME, TestUtils.JobType.Pipeline, false);

        boolean isBuildPopUpDisplayed = new PipelinePage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .clickBuildNameOnTimeline(PIPELINE_PROJECT_NAME)
                .isBuildPopUpHeaderTextDisplayed(PIPELINE_PROJECT_NAME);

        Assert.assertTrue(isBuildPopUpDisplayed, "Default build pop up is not displayed!");
    }

    @DataProvider(name = "job-submenu-option")
    public Object[][] provideJobSubmenuOption() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) WorkspacePage::new, "Workspace of default on Built-In Node"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new MultiConfigurationProjectPage(driver)), "Rename Configuration default"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to the page from Multiconfiguration default build drop-down")
    @Test(dataProvider = "job-submenu-option")
    public void testNavigateFromMultiConfigurationDefaultDropdownToPage(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String pageText) {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String actualPageHeaderText = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openDefaultProjectDropdown()
                .getPageFromDefaultProjectDropdownMenu(pageFromDataConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualPageHeaderText, pageText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verify that build bubble to Multiconfiguration project is present on Time line on Build History page")
    @Test
    public void testOpenBuildTableOfMultiConfigurationFromTimeline() {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        boolean isBuildPopUpDisplayed = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .clickBuildNameOnTimeline(MULTI_CONFIGURATION_PROJECT_NAME)
                .isBuildPopUpHeaderTextDisplayed(MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertTrue(isBuildPopUpDisplayed, "Default build pop up is not displayed!");
    }

    @DataProvider(name = "pipelineProjectOptionsFromDropDownMenu")
    public Object[][] getPipelineProjectDropDownMenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PipelineConfigPage(new PipelinePage(driver)), "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new PipelinePage(driver)), "Delete Pipeline: are you sure?"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) FullStageViewPage::new, PIPELINE_PROJECT_NAME + " - Stage View"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new PipelinePage(driver)), "Rename Pipeline " + PIPELINE_PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) PipelineSyntaxPage::new, "Overview"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for Pipeline project")
    @Test(dataProvider = "pipelineProjectOptionsFromDropDownMenu")
    public void testNavigateToPageFromDropDownPipelineProject(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDown, String pageText) {
        TestUtils.createJob(this, PIPELINE_PROJECT_NAME, TestUtils.JobType.Pipeline, false);

        String actualPageText;

        BaseMainHeaderPage<?> baseMainHeaderPage = new PipelinePage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectDropDownMenu(PIPELINE_PROJECT_NAME)
                .clickOptionsFromMenu(pageFromDropDown.apply(getDriver()));

        if (pageFromDropDown.apply(getDriver()).callByMenuItemName().contains("Delete")) {
            actualPageText = baseMainHeaderPage.getAlertBoxText();
        } else {
            actualPageText = baseMainHeaderPage.getAssertTextFromPage();
        }

        Assert.assertEquals(actualPageText, pageText);
    }

    @DataProvider(name = "freestyleProjectOptionsFromBuildDropDownMenu")
    public Object[][] getFreestyleProjectBuildDropDownMenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new PipelinePage(driver)), "Delete build #1"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for Freestyle project")
    @Test(dataProvider = "freestyleProjectOptionsFromBuildDropDownMenu")
    public void testNavigateToOptionBuildDropDownMenuForFreestyleProject(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDown, String pageText) {
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, false);

        String actualPageText = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectBuildDropDownMenu()
                .clickOptionsFromBuildMenu(pageFromDropDown.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualPageText, pageText);
    }

    @DataProvider(name = "pipelineProjectBuildOptionsFromDropDownMenu")
    public Object[][] getPipelineProjectBuildDropDownMenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new PipelinePage(driver)), "Delete build #1"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ReplayPage<>(new PipelinePage(driver)), "Replay #1"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) PipelineStepsPage::new, "Pipeline Steps"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) WorkspacePage::new, "Workspaces for " + PIPELINE_PROJECT_NAME + " #1"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from Build drop down menu for Pipeline project")
    @Test(dataProvider = "pipelineProjectBuildOptionsFromDropDownMenu")
    public void testNavigateToPageFromBuildDropDownPipelineProject(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDropDown, String pageText) {
        TestUtils.createJob(this, PIPELINE_PROJECT_NAME, TestUtils.JobType.Pipeline, false);

        String actualPageText = new PipelinePage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectBuildDropDownMenu()
                .clickOptionsFromBuildMenu(pageFromDropDown.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualPageText, pageText);
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to click to Build Now option from Project drop down menu")
    @Test(dataProvider = "project-type")
    public void testClickBuildNowFromDropDown(TestUtils.JobType jobType) {
        final String jobName = "JOB_NAME";
        TestUtils.createJob(this, jobName, jobType, true);

        boolean actualPageText = new MainPage(getDriver())
                .clickBuildByGreenArrow(jobName)
                .clickBuildsHistoryFromSideMenu()
                .openProjectDropDownMenu(jobName)
                .clickBuildNowFromMenu()
                .isNewBuildDisplayed(jobType);

        Assert.assertTrue(actualPageText, "The new build is not displayed!");
    }
}