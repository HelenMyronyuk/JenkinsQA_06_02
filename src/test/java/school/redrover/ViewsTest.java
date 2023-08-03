package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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
    private static final String NEW_VIEW_DESCRIPTION = RandomStringUtils.randomAlphanumeric(7);

    @DataProvider(name = "myView types")
    public Object[][] myViewType() {
        return new Object[][]{
                {TestUtils.ViewType.IncludeAGlobalView},
                {TestUtils.ViewType.ListView},
                {TestUtils.ViewType.MyView}
        };
    }

    @DataProvider(name = "view types with config")
    public Object[][] viewTypeWithConfig() {
        return new Object[][]{
                {TestUtils.ViewType.IncludeAGlobalView, IncludeAGlobalViewConfigPage.class},
                {TestUtils.ViewType.ListView, ListViewConfigPage.class}
        };
    }

    @DataProvider(name = "mainView types")
    public Object[][] viewType() {
        return new Object[][]{
                {TestUtils.ViewType.ListView},
                {TestUtils.ViewType.MyView}
        };
    }

    private void createNewView(boolean goToMyViewsPage, String viewName, TestUtils.ViewType viewType, boolean goToMainPage) {
        MainPage mainPage = new MainPage(getDriver());
        NewViewPage newViewPage;

        if (goToMyViewsPage) {
            newViewPage = mainPage
                    .clickMyViewsSideMenuLink()
                    .createNewView();
        } else {
            newViewPage = mainPage
                    .createNewView();
        }
        newViewPage
                .setNewViewName(viewName)
                .selectTypeViewClickCreate(viewType, viewType.createNextPage(getDriver()).getClass());

        if (goToMainPage) {
            new MainPage(getDriver())
                    .getHeader()
                    .clickLogo();
        }
    }

    @Test
    public void testCreateAJobFromMyViewsPage() {
        FreestyleProjectPage project = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJobAndArrow()
                .enterItemName(PROJECT_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSaveButton();

        Assert.assertEquals(project.getJobName(), "Project " + PROJECT_NAME);
    }

    @Test(dataProvider = "mainView types")
    public void testCreateViewTypes(TestUtils.ViewType viewType) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(false, VIEW_NAME, viewType, true);

        boolean isCreatedViewPresent = new MainPage(getDriver())
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertTrue(isCreatedViewPresent, "The view is not created");
    }

    @Test(dataProvider = "myView types")
    public void testCreateMyViewTypes(TestUtils.ViewType viewType) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, viewType, true);

        boolean isCreatedMyViewPresent = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertTrue(isCreatedMyViewPresent, "The myView type is not created");
    }

    @Test(dataProvider = "myView types")
    public void testRenameViewTypes(TestUtils.ViewType viewType) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, viewType, true);

        boolean actualViewName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickInactiveLastCreatedMyView(VIEW_NAME)
                .clickEditView()
                .editMyViewNameAndClickSubmitButton(NEW_VIEW_NAME)
                .getHeader()
                .clickLogo()
                .clickMyViewsSideMenuLink()
                .getListOfAllViews()
                .contains(NEW_VIEW_NAME);

        Assert.assertTrue(actualViewName, "The new name is not displayed");
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
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(false, VIEW_NAME, TestUtils.ViewType.ListView, false);

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
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(false, VIEW_NAME, TestUtils.ViewType.ListView, false);

        ListViewConfigPage listViewConfigPage = new ListViewConfigPage(new ViewPage(getDriver()));

        String previewText = listViewConfigPage
                .addDescription(VIEW_DESCRIPTION)
                .clickPreview()
                .getPreviewText();

        String textDescription = listViewConfigPage
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(previewText, VIEW_DESCRIPTION);
        Assert.assertEquals(textDescription, VIEW_DESCRIPTION);
    }

    @Test
    public void testAddDescriptionForGlobalViewTypeFromConfigure() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.IncludeAGlobalView, false);

        String descriptionText = new IncludeAGlobalViewConfigPage(new ViewPage(getDriver()))
                .addDescription(VIEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, VIEW_DESCRIPTION);
    }

    @Test
    public void testAddDescriptionForListViewTypeFromConfigure() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.ListView, false);

        String descriptionText = new ListViewConfigPage(new ViewPage(getDriver()))
                .addDescription(VIEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, VIEW_DESCRIPTION);
    }

    @Test
    public void testAddDescriptionForMyViewOnMyView() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.MyView, false);

        String descriptionText = new ViewPage(getDriver())
                .clickAddOrEditDescription()
                .enterDescription(VIEW_DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, VIEW_DESCRIPTION);
    }

    @Test(dataProvider = "myView types")
    public void testEditDescription(TestUtils.ViewType viewType){
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, viewType, true);

        String descriptionText = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickInactiveLastCreatedMyView(VIEW_NAME)
                .clickEditView()
                .enterDescription(VIEW_DESCRIPTION)
                .clickSaveButton()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(NEW_VIEW_DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, NEW_VIEW_DESCRIPTION);
    }

    @Test(dataProvider = "myView types")
    public void testCancelDeletingFromViewPage(TestUtils.ViewType viewType){
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, viewType, true);

        boolean viewIsPresent = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickOnView(VIEW_NAME, new ViewPage(getDriver()))
                .clickDeleteView(new MyViewsPage(getDriver()))
                .getHeader()
                .clickLogo()
                .clickMyViewsSideMenuLink()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertTrue(viewIsPresent,"View is not present on My Views page");
    }

    @Test
    public void testCancelDeletingFromConfigurationPage(){
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.ListView, false);

        Boolean isViewPresent = new ListViewConfigPage(new ViewPage(getDriver()))
                .clickDeleteView(new MyViewsPage(getDriver()))
                .getHeader()
                .clickLogo()
                .clickMyViewsSideMenuLink()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertTrue(isViewPresent,"View is not displayed");
    }

    @Test(dataProvider = "myView types")
    public void testDeleteMyViewTypesFromViewPage(TestUtils.ViewType viewType) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, viewType, true);

        boolean isDeletedViewPresent = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickInactiveLastCreatedMyView(VIEW_NAME)
                .clickDeleteViewButton()
                .clickYesButton()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertFalse(isDeletedViewPresent, "The view is not deleted from view page");
    }

    @Test(dataProvider = "mainView types")
    public void testDeleteViewTypesFromViewPage(TestUtils.ViewType viewType) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(false, VIEW_NAME, viewType, true);

        boolean isDeletedViewPresent = new MainPage(getDriver())
                .clickOnView(VIEW_NAME, new ViewPage(getDriver()))
                .clickDeleteView(new MainPage(getDriver()))
                .clickYesButton()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertFalse(isDeletedViewPresent, "The view is not deleted from view page");
    }

    @Test
    public void testDeleteViewFromConfigureOfMyViewsPage() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.MyView, false);

        Boolean isViewPresent = new ViewPage(getDriver())
                .selectView(VIEW_NAME)
                .clickEditView()
                .clickDeleteViewSideMenu()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertFalse(isViewPresent, "Error");
    }

    @Test
    public void testDeleteViewFromConfigureOfNewViewPage() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(false, VIEW_NAME, TestUtils.ViewType.ListView, false);

        boolean viewIsPresent = new ListViewConfigPage(new ViewPage(getDriver()))
                .clickDeleteView(new MainPage(getDriver()))
                .clickYesButton()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertFalse(viewIsPresent, "View is present on Main page");
    }
}
