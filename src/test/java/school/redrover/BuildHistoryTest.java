package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.builds.ConsoleOutputPage;
import school.redrover.model.builds.EditBuildInformationPage;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.model.jobs.PipelinePage;
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
                {(Function<WebDriver, BaseSubmenuPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) WorkspacePage::new, "Workspace"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) BuildHistoryPage::new, "Build"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConfigureSystemPage::new, "Configure"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) BuildHistoryPage::new, "Delete"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) RenamePage::new, "Rename"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for Multi-configuration project")
    @Test(dataProvider = "projectOptionsFromDropDownMenu")
    public void testNavigateToOptionDropDownMenuForMultiConfigurationProject(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromDropDown, String optionsName) {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);

        new MainPage(getDriver())
                .clickJobName(MULTI_CONFIGURATION_PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectDropDownMenu(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickOptionsFromMenu(pageFromDropDown.apply(getDriver()), optionsName);

        if (optionsName.equals("Delete")) {
            Alert alert = getDriver().switchTo().alert();
            Assert.assertTrue(alert.getText().contains(optionsName), "Navigated to an unexpected page");
        } else {
            String actualPageHeader = pageFromDropDown.apply(getDriver()).getPageHeaderText();
            Assert.assertTrue(actualPageHeader.contains(optionsName), "Navigated to an unexpected page");
        }
    }

    @DataProvider(name = "multiConfigurationBuildDropDownMenu")
    public Object[][] getBuildDropDownMenuFroMultiConfigurationProject() {
        return new Object[][]{
                {(Function<WebDriver, BaseSubmenuPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) DeletePage::new, "Delete build ‘#1’"},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for Multi-configuration project")
    @Test(dataProvider = "multiConfigurationBuildDropDownMenu")
    public void testNavigateToDefaultBuildOptionDropDownMenuForMultiConfigurationProject(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromDropDown, String textFromPage) {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String actualTextFromPage = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openDefaultBuildDropDownMenu(MULTI_CONFIGURATION_PROJECT_NAME)
                .getPageFromDefaultBuildDropdownMenu(pageFromDropDown.apply(getDriver()))
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
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ChangesPage(driver), "Changes", "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new WorkspacePage(driver), "Workspace", "Workspace of default on Built-In Node"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new MultiConfigurationProjectPage(driver)), "Rename", "Rename Configuration default"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to the page from Multiconfiguration default build drop-down")
    @Test(dataProvider = "job-submenu-option")
    public void testNavigateFromMultiConfigurationDefaultDropdownToPage(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String optionName, String pageHeaderText) {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String actualPageHeaderText = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openDefaultProjectDropdown()
                .getPageFromDefaultProjectDropdownMenu(optionName, pageFromDataConstructor.apply(getDriver()))
                .getPageHeaderText();

        Assert.assertEquals(actualPageHeaderText, pageHeaderText);
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
                {(Function<WebDriver, BaseSubmenuPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) BuildHistoryPage::new, "Build"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConfigureSystemPage::new, "Configure"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) BuildHistoryPage::new, "Delete Pipeline"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) FullStageViewPage::new, "Full"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) RenamePage::new, "Rename"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) PipelineSyntaxPage::new, "Syntax"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for Pipeline project")
    @Test(dataProvider = "pipelineProjectOptionsFromDropDownMenu")
    public void testNavigateToPageFromDropDownPipelineProject(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromDropDown, String optionsName) {
        TestUtils.createJob(this, PIPELINE_PROJECT_NAME, TestUtils.JobType.Pipeline, true);

        new MainPage(getDriver())
                .clickBuildByGreenArrow(PIPELINE_PROJECT_NAME)
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectDropDownMenu(PIPELINE_PROJECT_NAME)
                .clickOptionsFromMenu(pageFromDropDown.apply(getDriver()), optionsName);

        if (optionsName.equals("Delete Pipeline")) {
            Alert alert = getDriver().switchTo().alert();
            Assert.assertTrue(alert.getText().contains(optionsName), "Navigated to an unexpected page");
        } else if (optionsName.equals("Full") || optionsName.equals("Syntax")) {
            String actualPageHeader = pageFromDropDown.apply(getDriver()).getTextFromBreadCrumb(optionsName);
            Assert.assertTrue(actualPageHeader.contains(optionsName), "Navigated to an unexpected page");
        } else {
            String actualPageHeader = pageFromDropDown.apply(getDriver()).getPageHeaderText();
            Assert.assertTrue(actualPageHeader.contains(optionsName), "Navigated to an unexpected page");
        }
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for Freestyle project")
    @Test(dataProvider = "projectOptionsFromDropDownMenu")
    public void testNavigateToOptionDropDownMenuForFreestyleProject(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromDropDown, String optionsName) {
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_PROJECT_NAME)
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectDropDownMenu(FREESTYLE_PROJECT_NAME)
                .clickOptionsFromMenu(pageFromDropDown.apply(getDriver()), optionsName);

        if (optionsName.equals("Delete")) {
            Alert alert = getDriver().switchTo().alert();
            Assert.assertTrue(alert.getText().contains(optionsName), "Navigated to an unexpected page");
        } else {
            String actualPageHeader = pageFromDropDown.apply(getDriver()).getPageHeaderText();
            Assert.assertTrue(actualPageHeader.contains(optionsName), "Navigated to an unexpected page");
        }
    }


    @DataProvider(name = "optionsFreestyleProject")
    public Object[][] FreestyleDropDownLink() {
        return new Object[][]{
                {(Function<WebDriver, BaseSubmenuPage<?>>)
                        ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseSubmenuPage<?>>)
                        WorkspacePage::new, "Workspace"},
                {(Function<WebDriver, BaseSubmenuPage<?>>)
                        BuildHistoryPage::new, "Build Now"},
                {(Function<WebDriver, BaseSubmenuPage<?>>)
                        ConfigureSystemPage::new, "Configure"},
                {(Function<WebDriver, BaseSubmenuPage<?>>)
                        BuildHistoryPage::new, "Delete Project"},
                {(Function<WebDriver, BaseSubmenuPage<?>>)
                        RenamePage::new, "Rename"}
        };
    }

    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to the pages from the Freestyle project drop-down")
    @Test(dataProvider = "optionsFreestyleProject")
    public void testNavigateToFreestylePagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromDataConstructor, String submenu) {
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        BaseSubmenuPage<?> optionFromDropdownMenu = new MainPage(getDriver())
                .clickJobName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectDropDownMenu(FREESTYLE_PROJECT_NAME)
                .clickOptionsFromMenu(pageFromDataConstructor.apply(getDriver()), submenu);

        String pageHeader;
        String expectedHeaderHomePage = "Welcome to Jenkins!";
        String expectedHeaderBuildHistory = "Build History of Jenkins";
        if (submenu.equals("Delete Project")) {
            pageHeader = optionFromDropdownMenu.acceptAlert().getPageHeaderText();
            Assert.assertEquals(pageHeader, expectedHeaderHomePage);
        } else if (submenu.equals("Build Now")) {
            pageHeader = optionFromDropdownMenu.getPageHeaderText();
            Assert.assertEquals(pageHeader, expectedHeaderBuildHistory);
        } else {
            pageHeader = optionFromDropdownMenu.getPageHeaderText();
            Assert.assertTrue(pageHeader.contains(submenu), "Wrong page");
        }
    }

    @DataProvider(name = "MultiConfigurationProjectOptionsFromDropDownMenu")
    public Object[][] getMultiConfigurationProjectDropDownMenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseSubmenuPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) BuildHistoryPage::new, "Workspace"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConfigureSystemPage::new, "Build"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) BuildHistoryPage::new, "Configure"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) BuildHistoryPage::new, "Rename"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) FullStageViewPage::new, "Delete Multi-configuration project"},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to options from drop down menu for MultiConfiguration project")
    @Test(dataProvider = "MultiConfigurationProjectOptionsFromDropDownMenu")
    public void testNavigateToPageFromDropDownMultiConfigurationProject(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromDropDown, String optionsName) {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);

        new MainPage(getDriver())
                .clickBuildByGreenArrow(MULTI_CONFIGURATION_PROJECT_NAME)
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .openProjectDropDownMenu(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickOptionsFromMenu(pageFromDropDown.apply(getDriver()), optionsName);

        if (optionsName.equals("Delete Multi-configuration project")) {
            Alert alert = getDriver().switchTo().alert();
            Assert.assertTrue(alert.getText().contains(optionsName), "Navigated to an unexpected page");
        } else {
            String actualPageHeader = pageFromDropDown.apply(getDriver()).getPageHeaderText();
            Assert.assertTrue(actualPageHeader.contains(optionsName), "Navigated to an unexpected page");
        }
    }
}
  

