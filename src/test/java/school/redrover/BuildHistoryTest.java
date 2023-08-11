package school.redrover;

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

public class BuildHistoryTest extends BaseTest {

    private static final String NAME_PIPELINE = "Pipeline2023";
    private static final String BUILD_DESCRIPTION = "For QA";
    private final String FREESTYLE_PROJECT_NAME = "FreestyleName";
    private final String MULTI_CONFIGURATION_PROJECT_NAME = "MultiConfiguration001";


    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testBuildHistoryOfTwoDifferentTypesProjectsIsShown() {
        TestUtils.createJob(this, MULTI_CONFIGURATION_PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);
        TestUtils.createJob(this, FREESTYLE_PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        int numberOfLinesInBuildHistoryTable = new MainPage(getDriver())
                .getHeader()
                .clickLogo()
                .clickJobDropdownMenuBuildNow(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickJobDropdownMenuBuildNow(FREESTYLE_PROJECT_NAME)
                .clickBuildsHistoryFromSideMenu()
                .getNumberOfLinesInBuildHistoryTable();

        Assert.assertTrue(numberOfLinesInBuildHistoryTable >= 2);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testAddDescriptionToBuild() {
        String buildDescription = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(NAME_PIPELINE)
                .selectJobType(TestUtils.JobType.Pipeline)
                .clickOkButton(new PipelineConfigPage(new PipelinePage(getDriver())))
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickJobName(NAME_PIPELINE, new PipelinePage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(BUILD_DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(buildDescription, BUILD_DESCRIPTION);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
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
