package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class CreateJobTest extends BaseTest {

    @DataProvider(name = "job-type")
    public Object[][] jobType() {
        return new Object[][]{
                {TestUtils.JobType.FreestyleProject, "FREESTYLE_NAME"},
                {TestUtils.JobType.Pipeline, "PIPELINE_NAME"},
                {TestUtils.JobType.MultiConfigurationProject, "MULTI_CONFIGURATION_NAME"},
                {TestUtils.JobType.Folder, "FOLDER_NAME"},
                {TestUtils.JobType.MultibranchPipeline, "MULTIBRANCH_PIPELINE_NAME"},
                {TestUtils.JobType.OrganizationFolder, "ORGANIZATION_FOLDER_NAME"}

        };
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating all types of project by clicking 'Create a job' button")
    @Test(dataProvider = "job-type")
    public void testCreateFromCreateAJob(TestUtils.JobType jobType, String jobName) {
        boolean actualJobName = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(jobName)
                .selectJobType(jobType)
                .clickOkButton(jobType.createConfigPage(getDriver()))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(jobName);

        Assert.assertTrue(actualJobName, "The Project's name is not displayed on Dashboard from Main page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify job creation when clicking on 'New Item' button")
    @Test(dataProvider = "job-type")
    public void testCreateFromNewItem(TestUtils.JobType jobType, String jobName) {
        boolean actualProjectName = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(jobName)
                .selectJobType(jobType)
                .clickOkButton(jobType.createConfigPage(getDriver()))
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(jobName);

        Assert.assertTrue(actualProjectName, "The Project's name is not displayed on Dashboard from Main page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating all types of project by clicking 'New Item' button from 'People' Page")
    @Test(dataProvider = "job-type")
    public void testCreateFromPeoplePage(TestUtils.JobType jobType, String jobName) {
        boolean actualProjectName = new MainPage(getDriver())
                .clickPeopleFromSideMenu()
                .clickNewItem()
                .enterItemName(jobName)
                .selectJobType(jobType)
                .clickOkButton(jobType.createConfigPage(getDriver()))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(jobName);

        Assert.assertTrue(actualProjectName, "The Project's name is not displayed on Dashboard from Main page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating all types of project by clicking 'New Item' button from 'Build History' Page")
    @Test(dataProvider = "job-type")
    public void testCreateFromBuildHistoryPage(TestUtils.JobType jobType, String jobName) {
        boolean actualProjectName = new MainPage(getDriver())
                .clickBuildsHistoryFromSideMenu()
                .clickNewItem()
                .enterItemName(jobName)
                .selectJobType(jobType)
                .clickOkButton(jobType.createConfigPage(getDriver()))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(jobName);

        Assert.assertTrue(actualProjectName, "The Project's name is not displayed on Dashboard from Main page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating all types of project by clicking 'New Item' button from 'Manage Jenkins' Page")
    @Test(dataProvider = "job-type")
    public void testCreateFromManageJenkinsPage(TestUtils.JobType jobType, String jobName) {
        boolean actualProjectName = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickNewItem()
                .enterItemName(jobName)
                .selectJobType(jobType)
                .clickOkButton(jobType.createConfigPage(getDriver()))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(jobName);

        Assert.assertTrue(actualProjectName, "The Project's name is not displayed on Dashboard from Main page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating all types of project by clicking 'Create a Job' button from 'My Views' Page")
    @Test(dataProvider = "job-type")
    public void testCreateFromMyViewsCreateAJob(TestUtils.JobType jobType, String jobName) {
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJobAndArrow()
                .enterItemName(jobName)
                .selectJobType(jobType)
                .clickOkButton(jobType.createConfigPage(getDriver()))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(jobName), "The Project's name is not displayed on Dashboard from Home page");
        Assert.assertTrue(projectName.clickMyViewsSideMenuLink()
                .jobIsDisplayed(jobName), "The Project's name is not displayed on Dashboard from MyViews page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of creating all types of project by clicking 'New Item' button from 'My Views' Page")
    @Test(dataProvider = "job-type")
    public void testCreateFromMyViewsNewItem(TestUtils.JobType jobType, String jobName) {
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewItemFromSideMenu()
                .enterItemName(jobName)
                .selectJobType(jobType)
                .clickOkButton(jobType.createConfigPage(getDriver()))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(jobName), "The Project's name is not displayed on Dashboard from Home page");
        Assert.assertTrue(projectName.clickMyViewsSideMenuLink()
                .jobIsDisplayed(jobName), "The Project's name is not displayed on Dashboard from MyViews page");
    }
}
