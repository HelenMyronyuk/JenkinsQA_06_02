package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.model.jobsconfig.MultibranchPipelineConfigPage;
import school.redrover.model.jobs.MultibranchPipelinePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class MultibranchPipelineTest extends BaseTest {

    private static final String NAME = "MultibranchPipeline";
    private static final String RENAMED = "MultibranchPipelineRenamed";

    @Test
    public void testCreateMultibranchPipelineWithDisplayName() {
        final String multibranchPipelineDisplayName = "MultibranchDisplayName";

        MultibranchPipelinePage multibranchPipelinePage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultibranchPipeline)
                .clickOkButton(new MultibranchPipelineConfigPage(new MultibranchPipelinePage(getDriver())))
                .enterDisplayName(multibranchPipelineDisplayName)
                .clickSaveButton();

        Assert.assertEquals(multibranchPipelinePage.getJobName(), multibranchPipelineDisplayName);
        Assert.assertTrue(multibranchPipelinePage.isMetadataFolderIconDisplayed(), "error was not shown Metadata Folder icon");
    }

    @Test
    public void testCreateMultibranchPipelineWithDescription() {
        String MultibranchPipeline = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultibranchPipeline)
                .clickOkButton(new MultibranchPipelineConfigPage(new MultibranchPipelinePage(getDriver())))
                .addDescription("DESCRIPTION")
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .getAddedDescriptionFromConfig();

        Assert.assertEquals(MultibranchPipeline, "DESCRIPTION");
    }

    @Test
    public void testCreateMultibranchPipelineWithoutDescription() {
        boolean isDescriptionEmpty = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(NAME)
                .selectJobType(TestUtils.JobType.MultibranchPipeline)
                .clickOkButton(new MultibranchPipelineConfigPage(new MultibranchPipelinePage(getDriver())))
                .clickSaveButton()
                .isDescriptionEmpty();

        Assert.assertTrue(isDescriptionEmpty);
    }

    @Test
    public void testRenameMultibranchPipeline() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String actualDisplayedName = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickRename()
                .enterNewName(RENAMED)
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(actualDisplayedName, RENAMED);
    }

    @Test
    public void testDisableMultibranchPipeline() {
        TestUtils.createJob(this, RENAMED, TestUtils.JobType.MultibranchPipeline, true);

        String actualDisableMessage = new MainPage(getDriver())
                .clickJobName(RENAMED, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickDisable()
                .clickSaveButton()
                .getTextFromDisableMessage();
        Assert.assertTrue(actualDisableMessage.contains("This Multibranch Pipeline is currently disabled"));
    }

    @Test
    public void testDeleteMultibranchPipeline() {
        TestUtils.createJob(this, RENAMED, TestUtils.JobType.MultibranchPipeline, true);

        String WelcomeJenkinsPage = new MainPage(getDriver())
                .dropDownMenuClickDeleteFolders(RENAMED)
                .clickYesButton()
                .getWelcomeText();

        Assert.assertEquals(WelcomeJenkinsPage, "Welcome to Jenkins!");
    }

    @Test
    public void testChooseDefaultIcon() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean defaultIconDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickAppearance()
                .selectDefaultIcon()
                .clickSaveButton()
                .isDefaultIconDisplayed();

        Assert.assertTrue(defaultIconDisplayed, "error was not shown default icon");
    }

    @Test
    public void testAddHealthMetrics() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean healthMetricIsVisible = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .addHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .healthMetricIsVisible();

        Assert.assertTrue(healthMetricIsVisible, "error was not shown Health Metrics");
    }

    @Test
    public void testFindCreatedMultibranchPipelineOnMainPage(){
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean jobIsPresent = new MainPage(getDriver())
                .jobIsDisplayed(NAME);

        Assert.assertTrue(jobIsPresent);
    }
}
