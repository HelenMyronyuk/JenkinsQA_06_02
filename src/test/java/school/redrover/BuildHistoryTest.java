package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.builds.ConsoleOutputPage;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.model.jobsConfig.PipelineConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class BuildHistoryTest extends BaseTest {

    private static final String NAME_PIPELINE = "Pipeline2023";
    private static final String BUILD_DESCRIPTION = "For QA";
    private final String FREESTYLE_PROJECT_NAME = "FreestyleName"+ RandomStringUtils.randomAlphanumeric(7);;
    private final String MULTI_CONFIGURATION_PROJECT_NAME = "MultiConfiguration001"+ RandomStringUtils.randomAlphanumeric(7);;
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
    @Description("Verification the name of workspace building on the built-in node on Console Output Page")
    @Test
    public void testConsoleFreestyleBuildLocation() {
        String consoleOutputText = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(FREESTYLE_PROJECT_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .clickProjectBuildConsole(FREESTYLE_PROJECT_NAME)
                .getConsoleOutputText();

        String actualLocation = new ConsoleOutputPage(getDriver())
                .getParameterFromConsoleOutput(consoleOutputText, "workspace");

        Assert.assertEquals(actualLocation, "Building in workspace /var/jenkins_home/workspace/" + FREESTYLE_PROJECT_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the user of build on Console Output Page")
    @Test
    public void testConsoleOutputFreestyleBuildStartedByUser() {
        final String currentUser = new MainPage(getDriver()).getHeader().getCurrentUserName();

        final String userConsoleOutput = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(FREESTYLE_PROJECT_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .clickProjectBuildConsole(FREESTYLE_PROJECT_NAME)
                .getStartedByUser();

        Assert.assertEquals(currentUser, userConsoleOutput);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verification the build status on Console Output Page")
    @Test
    public void testConsoleOutputFreestyleBuildStatus() {
        final String consoleOutput = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(FREESTYLE_PROJECT_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSaveButton()
                .clickBuildWithParameters()
                .clickBuild()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .clickProjectBuildConsole(FREESTYLE_PROJECT_NAME)
                .getConsoleOutputText();

        String actualStatus = new ConsoleOutputPage(getDriver())
                .getParameterFromConsoleOutput(consoleOutput, "Finished");

        Assert.assertEquals(actualStatus, "Finished: SUCCESS");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verification of Status Message Text of broken build")
    @Test
    public void testVerifyStatusBroken() {

        final String namePipeline = "NewBuilds";
        final String textToDescriptionField = "What's up";
        final String textToPipelineScript = "Test";
        final String expectedStatusMessageText = "broken since this build";

        String actualStatusMessageText = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(namePipeline)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .addDescription(textToDescriptionField)
                .scrollToBuildTriggers()
                .clickBuildAfterOtherProjectsAreBuiltCheckBox()
                .scrollToPipelineSection()
                .inputInScriptField(textToPipelineScript)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickBuildByGreenArrow("NewBuilds")
                .clickBuildsHistoryFromSideMenu()
                .getStatusMessageText();

        Assert.assertEquals(actualStatusMessageText, expectedStatusMessageText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that name of project is present on Time line on Build History page")
    @Test
    public void testPresenceProjectNameOnBuildHistoryTimeline() {
        final String itemName = "TestProject";

        boolean projectNameOnBuildHistoryTimeline = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(itemName)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickBuildByGreenArrow(itemName)
                .clickBuildsHistoryFromSideMenu()
                .getBubbleTitleOnTimeline();

        Assert.assertTrue(projectNameOnBuildHistoryTimeline, "Project name is not displayed from time line!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to delete a project build from Build page")
    @Test
    public void testDeleteBuild() {
        final int zeroBuild = 0;
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        int countOfBuildsAfterDeleting = new MainPage(getDriver())
                .clickJobName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .clickNameOfBuildLink()
                .clickDeleteBuild(new FreestyleProjectPage(getDriver()))
                .clickYesButton()
                .getHeader()
                .clickLogo()
                .clickBuildsHistoryFromSideMenu()
                .getNumberOfLinesInBuildHistoryTable();

        Assert.assertEquals(countOfBuildsAfterDeleting, zeroBuild);
    }
}
