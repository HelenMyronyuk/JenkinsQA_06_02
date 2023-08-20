package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.jobs.FolderPage;
import school.redrover.model.views.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of creating 'My View' Type View in Folder")
    @Test
    public void testCreateMyViewInFolder() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);

        String newView = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FolderPage(getDriver()))
                .createNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.MyView, ViewPage.class)
                .getActiveViewName();

        assertEquals(newView, VIEW_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of creating all type of 'View'")
    @Test(dataProvider = "mainView types")
    public void testCreateViewTypes(TestUtils.ViewType viewType) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(false, VIEW_NAME, viewType, true);

        boolean isCreatedViewPresent = new MainPage(getDriver())
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertTrue(isCreatedViewPresent, "The view is not created");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of creating all type of 'My View'")
    @Test(dataProvider = "myView types")
    public void testCreateMyViewTypes(TestUtils.ViewType viewType) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, viewType, true);

        boolean isCreatedMyViewPresent = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertTrue(isCreatedMyViewPresent, "The myView type is not created");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of renaming all type of 'My View'")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of moving folder to new View list")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of creating New View with job filters")
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

    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verification of showing help message after click on '?'")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of adding column list on ListViewConfigPage")
    @Test
    public void testAddColumnList() {
        final List<String> expectedOptionsList = List.of(
                "Status", "Weather", "Name", "Last Success", "Last Failure", "Last Stable",
                "Last Duration", "Build Button", "Git Branches", "Name", "Project description");

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(false, VIEW_NAME, TestUtils.ViewType.ListView, true);

        List<String> actualOptionList =  new MainPage(getDriver())
                .clickOnView(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .scrollAndClickAddColumnButton()
                .getAddColumnOptionList();

        Assert.assertEquals(actualOptionList, expectedOptionsList);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of deleting column list on ListViewConfigPage")
    @Test
    public void testDeleteColumn() {
        final List<String> optionsList = List.of(
                "Status", "Weather", "Name", "Last Success",
                "Last Failure", "Last Duration", "Build Button");

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(false, VIEW_NAME, TestUtils.ViewType.ListView, true);

        String column = optionsList.get((int)(Math.random() * (optionsList.size() - 1)) + 1);

        boolean isColumnDelete = new MainPage(getDriver())
                .clickOnView(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .deleteColumn(column)
                .clickSaveButton()
                .clickEditView(TestUtils.ViewType.ListView, ListViewConfigPage.class)
                .isColumnDeleted(column);

        Assert.assertFalse(isColumnDelete, "Column " + column + " is not delete!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verification the possibility of preview View Description")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification the possibility of adding Description For GlobalView Type From Configuration page")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification the possibility of adding Description For ListView Type From Configuration page")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification the possibility of adding Description For MyView Type on 'MyView'")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of adding Description For all Type on 'MyView'")
    @Test(dataProvider = "myView types")
    public void testAddDescription(TestUtils.ViewType viewType) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, viewType, true);

        String descriptionTest = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickInactiveLastCreatedMyView(VIEW_NAME)
                .clickEditView()
                .enterDescription(VIEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();
        Assert.assertEquals(descriptionTest, VIEW_DESCRIPTION);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of editing Description of all Type on 'MyView'")
    @Test(dataProvider = "myView types")
    public void testEditDescription(TestUtils.ViewType viewType) {
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of Cancel Deleting of all Type of 'MyView' From ViewPage")
    @Test(dataProvider = "myView types")
    public void testCancelDeletingFromViewPage(TestUtils.ViewType viewType) {
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

        Assert.assertTrue(viewIsPresent, "View is not present on My Views page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of Cancel Deleting of ListView Type From ConfigurationPage")
    @Test
    public void testCancelDeletingFromConfigurationPage() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.ListView, false);

        Boolean isViewPresent = new ListViewConfigPage(new ViewPage(getDriver()))
                .clickDeleteView(new MyViewsPage(getDriver()))
                .getHeader()
                .clickLogo()
                .clickMyViewsSideMenuLink()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertTrue(isViewPresent, "View is not displayed");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of Deleting of all Type of 'MyView' From ViewPage")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of Deleting of all Type of 'View' From ViewPage")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of Deleting View From Configure Of MyViewsPage")
    @Test
    public void testDeleteViewFromConfigureOfMyViewsPage() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.MyView, false);

        Boolean isViewPresent = new ViewPage(getDriver())
                .clickOnView(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView()
                .clickDeleteView(new MainPage(getDriver()))
                .clickYesButton()
                .verifyViewIsPresent(VIEW_NAME);

        Assert.assertFalse(isViewPresent, "Error");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of Deleting View From Configure Of NewViewPage")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of adding filters to MyView")
    @Test
    public void testMyViewAddFilters() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.MyView, false);

        boolean isFiltersChecked = new MyViewsPage(getDriver())
                .clickEditView()
                .clickFilterQueueCheckBox()
                .clickFilterExecutorsCheckBox()
                .pushApply()
                .getFiltersCheckBoxAttribute();

        Assert.assertTrue(isFiltersChecked, "Check box is not checked!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification the possibility of Deleting View From Default View To User")
    @Test
    public void testNotAvailableDeleteForDefaultViewForUser() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);
        createNewView(true, VIEW_NAME, TestUtils.ViewType.ListView, false);

        boolean deleteButtonInvisibility = new ViewPage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .openConfigureFromUserDropdownMenu()
                .enterDefaultView(VIEW_NAME)
                .clickSaveButton()
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu("My Views", new MyViewsPage(getDriver()))
                .clickOnView(VIEW_NAME, new ViewPage(getDriver()))
                .isDeleteViewButtonDisplayed();

        new ViewPage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .openConfigureFromUserDropdownMenu()
                .enterDefaultView("")
                .clickSaveButton();

        Assert.assertTrue(deleteButtonInvisibility, "Delete View button is displayed!");
    }
}
