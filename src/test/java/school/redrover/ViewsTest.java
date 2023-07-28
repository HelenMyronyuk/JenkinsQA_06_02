package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.Arrays;
import java.util.List;

public class ViewsTest extends BaseTest {

    private static final String PROJECT_NAME = "Project1";

    private static final String NEW_PROJECT_NAME = "Project2";

    private static final String VIEW_NAME = "View1";

    private static final String NEW_VIEW_NAME = "View2";

    private static final String VIEW_DESCRIPTION = RandomStringUtils.randomAlphanumeric(7);


    private void createNewFreestyleProjectAndNewView(String name) {
        TestUtils.createJob(this, name, TestUtils.JobType.FreestyleProject, true);

        new MainPage(getDriver())
                .createNewView()
                .setNewViewName(name)
                .selectTypeViewClickCreate(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .getHeader()
                .clickLogo()
                .clickOnView(name, new ViewPage(getDriver()))
                .clickEditListView(name);
    }

    @Test
    public void testCreateAJobFromMyViewsPage() {
        FreestyleProjectPage project = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJob()
                .enterItemName(PROJECT_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSaveButton();

        Assert.assertEquals(project.getJobName(), "Project " + PROJECT_NAME);
    }

    @Test
    public void testCreateListViewType() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualName = new MainPage(getDriver())
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .clickSaveButton()
                .getActiveViewName();

        Assert.assertEquals(actualName, VIEW_NAME);
    }

    @Test
    public void testCreateMyViewType() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualViewName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.MyView, ViewPage.class)
                .getActiveViewName();

        Assert.assertEquals(actualViewName, VIEW_NAME);
    }

    @Test
    public void testRenameGlobalViewType() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean actualViewName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.IncludeAGlobalView, IncludeAGlobalViewConfigPage.class)
                .getHeader()
                .clickLogo()
                .clickMyViewsSideMenuLink()
                .clickInactiveLastCreatedMyView(VIEW_NAME)
                .clickEditView()
                .editMyViewNameAndClickSubmitButton(NEW_VIEW_NAME)
                .getHeader()
                .clickLogo()
                .clickMyViewsSideMenuLink()
                .getListOfAllViews()
                .contains(NEW_VIEW_NAME);

        Assert.assertTrue(actualViewName, NEW_VIEW_NAME);
    }

    @Test
    public void testRenameMyViewType() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualViewName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.MyView, ViewPage.class)
                .getHeader()
                .clickLogo()
                .clickMyViewsSideMenuLink()
                .clickInactiveLastCreatedMyView(VIEW_NAME)
                .clickEditView()
                .editMyViewNameAndClickSubmitButton(NEW_VIEW_NAME)
                .getActiveViewName();

        Assert.assertEquals(actualViewName, NEW_VIEW_NAME);
    }

    @Test
    public void testDeleteMyViewFromViewPage() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.MyView, ViewPage.class)
                .getHeader()
                .clickLogo();

        int numberOfAllViews = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .getListOfAllViews().size();

        int numberOfAllViewsAfterDeletion = new MyViewsPage(getDriver())
                .clickInactiveLastCreatedMyView(VIEW_NAME)
                .clickDeleteViewButton()
                .clickYesButton()
                .getListOfAllViews().size();

        Assert.assertEquals(numberOfAllViews - numberOfAllViewsAfterDeletion, 1);
    }

    @Test
    public void testDeleteListViewFromViewPage() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean isDeletedViewPresent = new MainPage(getDriver())
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .clickSaveButton()
                .clickDeleteView()
                .clickYesButton()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertFalse(isDeletedViewPresent);
    }

    @Ignore
    @Test
    public void testMoveFolderToNewViewList() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);
        TestUtils.createJob(this, NEW_PROJECT_NAME, TestUtils.JobType.Folder, true);

        new MainPage(getDriver())
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .selectJobsInJobFilters(PROJECT_NAME)
                .clickSaveButton();

        ViewPage viewPage = new MainPage(getDriver()).clickOnView(VIEW_NAME, new ViewPage(getDriver()));

        Assert.assertEquals(viewPage.getActiveViewName(), VIEW_NAME);
        Assert.assertEquals(viewPage.getJobName(PROJECT_NAME), PROJECT_NAME);

    }

    @Ignore
    @Test
    public void testCreateNewViewWithJobFilters() {
        final String jobName1 = "job1";
        final String jobName2 = "job2";
        final String jobName3 = "job3";
        final List<String> expectedViewJobs = Arrays.asList(PROJECT_NAME + " » " + jobName1, PROJECT_NAME + " » " + jobName3, NEW_PROJECT_NAME);

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);
        TestUtils.createJob(this, NEW_PROJECT_NAME, TestUtils.JobType.Folder, true);

        new MainPage(getDriver())
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .selectJobsInJobFilters(PROJECT_NAME)
                .clickSaveButton()
                .clickOnView(VIEW_NAME, new ViewPage(getDriver()));

        TestUtils.createFreestyleProjectInsideFolderAndView(this, jobName1, VIEW_NAME, PROJECT_NAME);
        TestUtils.createFreestyleProjectInsideFolderAndView(this, jobName2, VIEW_NAME, PROJECT_NAME);
        TestUtils.createFreestyleProjectInsideFolderAndView(this, jobName3, VIEW_NAME, PROJECT_NAME);

        ViewPage viewPage = new ViewPage(getDriver())
                .createNewView()
                .setNewViewName(NEW_VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .selectRecurseCheckbox()
                .scrollToAddJobFilterDropDown()
                .selectJobsInJobFilters(PROJECT_NAME + " » " + jobName1)
                .selectJobsInJobFilters(PROJECT_NAME + " » " + jobName3)
                .selectJobsInJobFilters(NEW_PROJECT_NAME)
                .clickSaveButton();

        List<String> actualViewJobsTexts = viewPage.getJobList();

        Assert.assertEquals(viewPage.getActiveViewName(), NEW_VIEW_NAME);
        Assert.assertEquals(actualViewJobsTexts.size(), 3);
        Assert.assertEquals(actualViewJobsTexts, expectedViewJobs);
    }

    @Test
    public void testHelpForFeatureDescription() {
        createNewFreestyleProjectAndNewView(PROJECT_NAME);

        String helpFeature = new ListViewConfigPage(new ViewPage(getDriver()))
                .clickHelpForFeatureDescription()
                .getTextHelpFeatureDescription();

        Assert.assertEquals(
                helpFeature,
                "This message will be displayed on the view page . Useful " +
                        "for describing what this view does or linking to " +
                        "relevant resources. Can contain HTML tags or whatever" +
                        " markup language is defined for the system."
        );
    }

    @Test
    public void testAddViewDescriptionPreview() {
        createNewFreestyleProjectAndNewView(PROJECT_NAME);

        String previewText =
                new ListViewConfigPage(new ViewPage(getDriver()))
                        .addDescription(VIEW_DESCRIPTION)
                        .clickPreview()
                        .getPreviewText();

        String textDescription =
                new ListViewConfigPage(new ViewPage(getDriver()))
                        .clickSaveButton()
                        .getDescriptionText();

        Assert.assertEquals(previewText, VIEW_DESCRIPTION);
        Assert.assertEquals(textDescription, VIEW_DESCRIPTION);
    }

    @Test
    public void testAddDescriptionForMyViewFromConfigPage() {
        createNewFreestyleProjectAndNewView(PROJECT_NAME);

        String descriptionText = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickConfigureDropDown(PROJECT_NAME, new ListViewConfigPage(new ViewPage(getDriver())))
                .addDescription(VIEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, VIEW_DESCRIPTION);
    }
}
