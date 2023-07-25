package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseJobPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.jobs.FolderPage;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobs.OrganizationFolderPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.model.jobs.*;
import school.redrover.model.jobsconfig.*;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BreadcrumbTest extends BaseTest {

    private static final String PROJECT_NAME = "JOB";

    @Test
    public void testNavigateToManageJenkinsFromDropDown() {
        String actualResult = new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu("Manage Jenkins", new ManageJenkinsPage(getDriver()))
                .getActualHeader();

        Assert.assertEquals(actualResult, "Manage Jenkins");
    }

    @DataProvider(name = "subsections")
    public Object[][] provideSubsection() {
        return new Object[][]{
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConfigureSystemPage::new, "Configure System"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) GlobalToolConfigurationPage::new, "Global Tool Configuration"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) PluginsPage::new, "Plugins"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ManageNodesPage::new, "Manage nodes and clouds"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConfigureGlobalSecurityPage::new, "Configure Global Security"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) CredentialsPage::new, "Credentials"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConfigureCredentialProvidersPage::new, "Configure Credential Providers"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) UserPage::new, "Users"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) SystemInformationPage::new, "System Information"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) LogRecordersPage::new, "Log Recorders"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) LoadStatisticsPage::new, "Load statistics: Jenkins"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) AboutJenkinsPage::new, "Jenkins\n" + "Version\n" + "2.387.2"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ManageOldDataPage::new, "Manage Old Data"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) JenkinsCLIPage::new, "Jenkins CLI"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ScriptConsolePage::new, "Script Console"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) PrepareForShutdownPage::new, "Prepare for Shutdown"}
        };
    }

    @Test(dataProvider = "subsections")
    public  void testNavigateToManageJenkinsSubsection(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromSubMenuConstructor, String expectedResult) {

        String actualResult = new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(pageFromSubMenuConstructor.apply(getDriver()))
                .getHeading();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testReloadConfigurationFromDiskOfManageJenkinsSubmenu() {
        String popUp = new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(new MainPage(getDriver()))
                .getPopUp();

        Assert.assertEquals(popUp, "Reload Configuration from Disk: are you sure?");
    }

    @Test
    public void testDashboardDropdownMenu() {
        final List<String> expectedMenuList = Arrays.asList("New Item", "People", "Build History", "Manage Jenkins", "My Views");

        List<String> actualMenuList = new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getMenuList();

        Assert.assertEquals(actualMenuList, expectedMenuList);
    }

    @DataProvider(name = "job-type")
    public Object[][] provideJobTypes() {
        return new Object[][]{{TestUtils.JobType.FreestyleProject},{TestUtils.JobType.Pipeline},
                {TestUtils.JobType.MultiConfigurationProject}, {TestUtils.JobType.Folder},
                {TestUtils.JobType.MultibranchPipeline}, {TestUtils.JobType.OrganizationFolder}};
    }

    @Test(dataProvider = "job-type")
    public void testReturnToDashboardPageFromProjectPage(TestUtils.JobType jobType) {
        TestUtils.createJob(this, PROJECT_NAME, jobType, false);

        String nameProjectOnMainPage = jobType.createConfigPage(getDriver())
                .getBreadcrumb()
                .clickDashboardButton()
                .getJobName(PROJECT_NAME);

        Assert.assertEquals(nameProjectOnMainPage, PROJECT_NAME);
    }

    @Test
    public void testNavigateToPluginsPageFromPeoplePage() {
        String actualTitle = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .getBreadcrumb()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(new PluginsPage(getDriver()))
                .getPageTitle();

        Assert.assertEquals(actualTitle, "Plugins");
    }

    @Test
    public void testNavigateToPluginsPageFromDropDown() {
        String actualResult = new MainPage(getDriver())
                .getBreadcrumb()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(new PluginsPage(getDriver()))
                .getPageTitle();

        Assert.assertEquals(actualResult, "Plugins");
    }

    @Test(dataProvider = "job-type")
    public void testNavigateToBuildHistoryPageFromProjectPage(TestUtils.JobType jobType) {
        TestUtils.createJob(this, PROJECT_NAME, jobType, true);

        String actualHeaderText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, jobType.createJobPage(getDriver()) )
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu("Build History", new BuildHistoryPage(getDriver()))
                .getHeaderText();

        Assert.assertEquals(actualHeaderText, "Build History of Jenkins", "The header is not correct");
    }

    @Test
    public void testNavigateToPeoplePageFromBuildHistoryPage() {
        String actualTitle = new MainPage(getDriver())
                .clickBuildsHistoryButton()
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu("People", new PeoplePage(getDriver()))
                .getPageTitle();

        Assert.assertEquals(actualTitle, "People");
    }

    @Test
    public void testReturnToDashboardPageFromPeoplePage() {
       boolean welcomeJenkins = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .getBreadcrumb()
                .clickDashboardButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeJenkins, "Welcome Jenkins text is not displayed!");
    }

    @Test
    public void testReturnToDashboardPageFromBuildHistoryPage() {
        String actualTitle = new MainPage(getDriver())
                .clickBuildsHistoryButton()
                .getBreadcrumb()
                .clickDashboardButton()
                .getTitle();

        Assert.assertEquals(actualTitle, "Dashboard [Jenkins]");
    }

    @Test
    public void testReturnToDashboardPageFromNewItemPage() {
        boolean welcomeJenkins = new MainPage(getDriver())
                .clickNewItem()
                .getBreadcrumb()
                .clickDashboardButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeJenkins, "Welcome Jenkins text is not displayed!");
    }

    @Test(dataProvider = "job-type")
    public void testReturnToDashboardPageFromConfigurationPage(TestUtils.JobType jobType) {
        TestUtils.createJob(this, PROJECT_NAME, jobType, true);

        boolean mainPageOpen = new MainPage(getDriver())
                .clickConfigureDropDown(PROJECT_NAME, jobType.createConfigPage(getDriver()))
                .getBreadcrumb()
                .clickDashboardButton()
                .isMainPageOpen();

        Assert.assertTrue(mainPageOpen, "Main page is not displayed!");
    }

    @Test
    public void testReturnToDashboardPageFromMyViewsPage() {
        boolean welcomeJenkins = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .getBreadcrumb()
                .clickDashboardButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeJenkins, "Welcome Jenkins text is not displayed!");
    }

    @Test
    public void testReturnToDashboardPageFromManageJenkinsPage() {
        boolean welcomeJenkins = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .getBreadcrumb()
                .clickDashboardButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeJenkins, "Welcome Jenkins text is not displayed!");
    }

    @Test(dataProvider = "job-type")
    public void testNavigateToMyViewsPageFromConfigurationPage(TestUtils.JobType jobType) {
        TestUtils.createJob(this, PROJECT_NAME, jobType, true);

        String actualTextFromBreadCrumb = new MainPage(getDriver())
                .clickConfigureDropDown(PROJECT_NAME, jobType.createConfigPage(getDriver()))
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu("My Views", new MyViewsPage(getDriver()))
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertEquals(actualTextFromBreadCrumb, "Dashboard > admin > My Views > All");
    }

    @DataProvider(name = "optionsFolder")
    public Object[][] folderDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new FolderConfigPage(new FolderPage(driver)), "Configure", "Configuration"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PeoplePage(getDriver()), "People", "People"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new BuildHistoryPage(getDriver()), "Build History", "Build History of Jenkins"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new FolderPage(driver)), "Move", "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new FolderPage(driver)), "Rename", "Rename Folder " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(getDriver()), "Credentials", "Credentials"}
        };
    }

    @Test(dataProvider = "optionsFolder")
    public void testNavigateToFolderPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String optionName, String pageHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(optionName, this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);

        String pageName = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FolderPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, pageFromDataConstructor.apply(getDriver()))
                .getPageHeaderText();

        Assert.assertEquals(pageName, pageHeaderText);
    }

    @Test
    public void testNavigateToJobFromBuildHistory() {
        Map<String, BaseJobPage<?>> jobMap = TestUtils.getJobMap(this);

        for (Map.Entry<String, BaseJobPage<?>> entry : TestUtils.getJobMap(this).entrySet()) {
            TestUtils.createJob(this, entry.getKey(), TestUtils.JobType.valueOf(entry.getKey()), true);
        }

        List<String> jobNameList = new ArrayList<>(jobMap.keySet());
        List<String> jobNameActualList = new ArrayList<>();

        for (Map.Entry<String, BaseJobPage<?>> jobNameAndJobTypeMap: jobMap.entrySet()) {
            jobNameActualList.add(new MainPage(getDriver())
                    .clickBuildsHistoryButton()
                    .getBreadcrumb()
                    .clickProjectNameFromAllButtonDropDownMenu(jobNameAndJobTypeMap.getValue(), jobNameAndJobTypeMap.getKey())
                    .getProjectName());

            jobNameAndJobTypeMap.getValue()
                    .getHeader()
                    .clickLogo();
        }

        Assert.assertEquals(jobNameActualList, jobNameList);
    }

    @DataProvider(name = "job-submenu-option")
    public Object[][] provideJobSubmenuOption() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ChangesPage<>(new MultiConfigurationProjectPage(driver)), "Changes", "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new WorkspacePage(driver), "Workspace", "Error: no workspace"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MultiConfigurationProjectPage(driver), "Configure", "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new MultiConfigurationProjectPage(driver)), "Move", "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new MultiConfigurationProjectPage(driver)), "Rename", "Rename Multi-configuration project " + PROJECT_NAME}
        };
    }

    @Test(dataProvider = "job-submenu-option")
    public void testNavigateToMultiConfigurationPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String optionName, String pageHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(optionName, this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject,true);

        String pageText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, pageFromDataConstructor.apply(getDriver()))
                .getPageHeaderText();

        Assert.assertEquals(pageText, pageHeaderText);
    }

    @Test
    public void testDeleteNavigateToMultiConfigurationPagesFromDropdownOnBreadcrumb() {
        final String optionName = "Delete Multi-configuration project";
        final String alertText = "Delete Multi-configuration project: are you sure?";

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject,true);

        String actualAlertText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, new MultiConfigurationProjectPage(getDriver()))
                .getDeleteAlertText();

        Assert.assertEquals(actualAlertText, alertText);
    }

    @Test
    public void testNavigateToMultiConfigurationPagesFromDropdownOnBreadcrumbBuildNow() {
        final String optionName = "Build Now";

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject,true);

        boolean isBuildDisplayed = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, new MultiConfigurationProjectPage(getDriver()))
                .refreshPage()
                .isLastBuildIconDisplayed();

        Assert.assertTrue(isBuildDisplayed, "Last build icon is not displayed!");
    }

    @Test
    public void testNavigateToNewItemPageFromMyViewsPage() {
        String actualResult = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu("New Item", new NewJobPage(getDriver()))
                .getHeaderText();

        Assert.assertEquals(actualResult, "Enter an item name");
    }

    @DataProvider(name = "optionsOrganizationFolder")
    public Object[][] organizationFolderDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new OrganizationFolderConfigPage(new OrganizationFolderPage(driver)), "Configure", "Configuration"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ScanOtherFoldersLogPage(driver), "Scan Organization Folder Log", "Scan Organization Folder Log"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new OtherFoldersEventsPage(driver), "Organization Folder Events", "Organization Folder Events"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new OrganizationFolderPage(driver)), "Move", "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new OrganizationFolderPage(driver)), "Rename", "Rename Organization Folder " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(driver), "Credentials", "Credentials"}
        };
    }

    @Test(dataProvider = "optionsOrganizationFolder")
    public void testNavigateToOrgFolderPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String optionName, String pageHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(optionName, this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.OrganizationFolder, true);

        String actualPageHeaderText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new OrganizationFolderPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, pageFromDataConstructor.apply(getDriver()))
                .getPageHeaderText();

        Assert.assertEquals(actualPageHeaderText, pageHeaderText);
    }

    @DataProvider(name = "optionsMultibranchPipeline")
    public Object[][] multibranchPipelineDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MultibranchPipelineConfigPage(new MultibranchPipelinePage(driver)), "Configure", "Configuration"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ScanOtherFoldersLogPage(driver), "Scan Multibranch Pipeline Log", "Scan Multibranch Pipeline Log"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new OtherFoldersEventsPage(driver), "Multibranch Pipeline Events", "Multibranch Pipeline Events"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PeoplePage(getDriver()), "People", "People - Welcome"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new BuildHistoryPage(getDriver()), "Build History", "Build History of Welcome"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new MultibranchPipelinePage(driver)), "Move", "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new MultibranchPipelinePage(driver)), "Rename", "Rename Multibranch Pipeline " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(driver), "Credentials", "Credentials"}
        };
    }

    @Test(dataProvider = "optionsMultibranchPipeline")
    public void testNavigateToMultibranchPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String submenu, String expectedHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(submenu, this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultibranchPipeline, true);

        String actualPageHeaderText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new MultibranchPipelinePage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(submenu, pageFromDataConstructor.apply(getDriver()))
                .getPageHeaderText();

        Assert.assertEquals(actualPageHeaderText, expectedHeaderText);
    }

    @Test
    public void testNewItemNavigateToFolderPagesFromDropdownOnBreadcrumb() {
        final String optionName = "New Item";
        final String pageHeaderText = "Enter an item name";
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);

        String actualPageHeaderText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FolderPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, new NewJobPage(getDriver()))
                .getHeaderText();

        Assert.assertEquals(actualPageHeaderText, pageHeaderText);
    }

    @DataProvider(name = "buildSubMenu")
    public Object[][] getBuildSubmenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseSubmenuPage<?>>) ChangesBuildPage::new, "Changes"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseSubmenuPage<?>>) EditBuildInformationPage::new, "Edit Build Information"}
        };
    }

    @Test(dataProvider = "buildSubMenu")
    public void testNavigateToFreestyleBuildPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromSubMenuConstructor, String expectedResult) {
        String projectName = "FreestyleProject";
        TestUtils.createJob(this, projectName, TestUtils.JobType.FreestyleProject, true);
        String actualResult = "";

        BaseSubmenuPage submenuPage = new MainPage(getDriver())
                .clickJobName(projectName, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectOptionFromBuildDropDownList(pageFromSubMenuConstructor.apply(getDriver()));

        if ("configure".equals(pageFromSubMenuConstructor.apply(getDriver()).callByMenuItemName())) {
            actualResult = submenuPage.getTextFromBreadCrumb();
        } else {
            actualResult = submenuPage.getHeading();
        }
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testNavigateToFreestyleDeletePageFromDropdownOnBreadcrumb() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        Boolean deleteSubmenuPage = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectDeleteOptionFromBuildDropDownList(new FreestyleProjectPage(getDriver()))
                .clickYesButton()
                .getBreadcrumb()
                .clickJobNameFromBreadcrumb(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .isNoBuildsDisplayed();

        Assert.assertTrue(deleteSubmenuPage, "Error");
    }

    @DataProvider(name = "pipesubmenu")
    public Object[][] pipeDropDownBreadcrumb (){
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ChangesPage<>(new PipelinePage(driver)), "Changes", "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PipelineConfigPage(new PipelinePage(driver)), "Configure", "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PipelinePage(driver), "Delete Pipeline", "Welcome to Jenkins!"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new PipelinePage(driver)), "Move", "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new FullStageViewPage(driver), "Full Stage View", PROJECT_NAME + " - Stage View"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new PipelinePage(driver)), "Rename", "Rename Pipeline " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PipelineSyntaxPage(driver), "Pipeline Syntax","Overview"}};
    }

    @Test(dataProvider = "pipesubmenu")
    public void testNavigateToPipelinePagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String submenu, String expectedHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(submenu, this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Pipeline, true);

        String actualPageHeaderText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new PipelinePage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(submenu, pageFromDataConstructor.apply(getDriver()))
                .getPageHeaderText();

        Assert.assertEquals(actualPageHeaderText, expectedHeaderText);

    }

    @Test
    public void testPipelineSyntaxNavigateToOrgFolderPagesFromDropdownOnBreadcrumb() {
        final String optionName = "Pipeline Syntax";
        final String text = "Overview";

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.OrganizationFolder, true);

        String actualText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new OrganizationFolderPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, new PipelineSyntaxPage(getDriver()))
                .getOverviewText();

        Assert.assertEquals(actualText, text);
    }

    @Test
    public void testNavigateToPipelineSyntaxFromMultibranchPagesFromDropdownOnBreadcrumb() {
        final String optionName = "Pipeline Syntax";
        final String text = "Overview";

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultibranchPipeline, true);

        String actualText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new MultibranchPipelinePage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, new PipelineSyntaxPage(getDriver()))
                .getOverviewText();

        Assert.assertEquals(actualText, text);
    }

    @Test
    public void testDeleteNavigateToFolderPagesFromDropdownOnBreadcrumb() {
        final String optionName = "Delete Folder";

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);

        boolean deleteButton = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FolderPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, new DeletePage<>(new FolderPage(getDriver())))
                .isDeleteButtonDisplayed();

        Assert.assertTrue(deleteButton, "Delete button is not displayed!");
    }

    @Test
    public void testDeleteNavigateToOrgFolderPagesFromDropdownOnBreadcrumb() {
        final String optionName = "Delete Organization Folder";

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.OrganizationFolder, true);

        boolean deleteButton = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new OrganizationFolderPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, new DeletePage<>(new OrganizationFolderPage(getDriver())))
                .isDeleteButtonDisplayed();

        Assert.assertTrue(deleteButton, "Delete button is not displayed!");
    }

    @Test
    public void testNavigateToDeleteFromMultibranchPagesFromDropdownOnBreadcrumb() {
        final String optionName = "Delete Multibranch Pipeline";

        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultibranchPipeline, true);

        DeletePage deletePage = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new MultibranchPipelinePage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, new DeletePage<>(new MultibranchPipelinePage(getDriver())));

        Assert.assertEquals(deletePage.getTextFromConfirmDeletionForm(), "Delete the Multibranch Pipeline ‘" + PROJECT_NAME + "’?\nYes");
        Assert.assertTrue(deletePage.isDeleteButtonDisplayed(), "Error: Delete button is not displayed!");
    }

    @DataProvider(name = "userDropDownMenu")
    public Object[][] userDropDownBreadcrumbToMyViews2 (){
        return new Object[][]{
                {"builds", (Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new BuildPage(driver), "Dashboard > admin > Builds"},
                {"configure", (Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ConfigureSystemPage(driver), "Dashboard > admin > Configure"},
                {"my-views", (Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MyViewsPage(driver), "Dashboard > admin > My Views > All"},
                {"credentials", (Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(driver), "Dashboard > admin > Credentials"},
        };
    }

    @Test(dataProvider = "userDropDownMenu")
    public void testNavigateToMyViewsPagesFromDropdownOnBreadcrumb(
            String submenu, Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String expectedFullBreadcrumbText){

        String actualFullBreadcrumbText =
                new MainPage(getDriver())
                        .getHeader()
                        .clickAdminDropdownMenu()
                        .clickMyViewsTabFromAdminDropdownMenu()
                        .getBreadcrumb()
                        .getUserBreadcrumbDropdownMenu()
                        .clickPageFromUserBreadcrumbDropdownMenu(submenu, pageFromDataConstructor.apply(getDriver()))
                        .getBreadcrumb()
                        .getFullBreadcrumbText();

        Assert.assertEquals(actualFullBreadcrumbText, expectedFullBreadcrumbText);
    }

    @DataProvider(name = "optionsFreestyleProject")
    public Object[][] FreestyleDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ChangesPage<FreestyleProjectPage>(new FreestyleProjectPage(driver)), "Changes", "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new WorkspacePage(driver), "Workspace", "Error: no workspace"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new FreestyleProjectPage(driver), "Build Now", "Project " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new FreestyleProjectConfigPage(new FreestyleProjectPage(driver)), "Configure", "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new FreestyleProjectPage(driver)), "Move", "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<FreestyleProjectPage>(new FreestyleProjectPage(driver)), "Rename", "Rename Project " + PROJECT_NAME}
        };
    }

    @Test(dataProvider = "optionsFreestyleProject")
    public void testNavigateToFreestylePagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String submenu, String expectedHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(submenu, this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualPageHeaderText = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(submenu, pageFromDataConstructor.apply(getDriver()))
                .getPageHeaderText();

        Assert.assertEquals(actualPageHeaderText, expectedHeaderText);
    }
}
