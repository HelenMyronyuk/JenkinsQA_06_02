package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.builds.ConsoleOutputPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class BuildHistoryTest extends BaseTest {

    private final String FREESTYLE_PROJECT_NAME = "Freestyle"+ RandomStringUtils.randomAlphanumeric(7);
    private final String MULTI_CONFIGURATION_PROJECT_NAME = "MultiConfiguration"+ RandomStringUtils.randomAlphanumeric(7);
    private final String PIPELINE_PROJECT_NAME = "Pipeline"+ RandomStringUtils.randomAlphanumeric(7);


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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to navigate to build page of Freestyle, Pipeline and Multiconfiguration(not default) from timeline")
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
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        boolean isDefaultBuildPopUpDisplayed = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .clickDefaultBuildBubbleFromTimeline()
                .isDefaultBuildPopUpHeaderTextDisplayed();

        Assert.assertTrue(isDefaultBuildPopUpDisplayed, "Default build pop up is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to close the bubble pop up of Freestyle project build from timeline")
    @Ignore
    @Test
    public void testCloseBuildPopUpOfFreestyle() {
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean isBubblePopUpClosed =  new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .clickBuildNameOnTimeline(FREESTYLE_PROJECT_NAME)
                .closeProjectWindowButtonInTimeline()
                .isBuildPopUpInvisible();

        Assert.assertTrue(isBubblePopUpClosed, "Bubble pop up window not closed!");
    }

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
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        boolean buildPageHeader = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .clickDefaultBuildBubbleFromTimeline()
                .clickDefaultBuildLinkFromTimeline()
                .isDisplayedBuildPageHeaderText();

        Assert.assertTrue(buildPageHeader, "Wrong page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to close the bubble pop up of MultiConfiguration project build from timeline")
    @Test
    public void testCloseBuildPopUpOfMultiConfiguration() {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean isBubblePopUpClosed =  new MainPage(getDriver())
                .clickBuildByGreenArrow(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .clickBuildNameOnTimeline(MULTI_CONFIGURATION_PROJECT_NAME)
                .closeProjectWindowButtonInTimeline()
                .isBuildPopUpInvisible();

        Assert.assertTrue(isBubblePopUpClosed, "Bubble pop up window not closed!");
    }
}
