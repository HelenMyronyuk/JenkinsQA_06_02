package school.redrover;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseJobPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.builds.*;
import school.redrover.model.jobs.FolderPage;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.jobs.OrganizationFolderPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.model.jobs.*;
import school.redrover.model.jobsConfig.*;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobsSidemenu.*;
import school.redrover.model.manageJenkins.*;
import school.redrover.model.users.UserConfigPage;
import school.redrover.model.users.UserPage;
import school.redrover.model.views.MyViewsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BreadcrumbTest extends BaseTest {

    private static final String PROJECT_NAME = "JOB";

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user can navigate to 'Manage Jenkins' page from the 'Dashboard' drop-down menu")
    @Test
    public void testNavigateToManageJenkinsFromDropDown() {
        String actualResult = new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu(new ManageJenkinsPage(getDriver()))
                .getActualHeader();

        Assert.assertEquals(actualResult, "Manage Jenkins");
    }

    @DataProvider(name = "subsections")
    public Object[][] provideSubsection() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConfigureSystemPage::new, "Configure System"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) GlobalToolConfigurationPage::new, "Global Tool Configuration"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) PluginsPage::new, "Plugins"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ManageNodesPage::new, "Manage nodes and clouds"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConfigureGlobalSecurityPage::new, "Configure Global Security"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) CredentialsPage::new, "Credentials"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConfigureCredentialProvidersPage::new, "Configure Credential Providers"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) UserPage::new, "Users"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) SystemInformationPage::new, "System Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) LogRecordersPage::new, "Log Recorders"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) LoadStatisticsPage::new, "Load statistics: Jenkins"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) AboutJenkinsPage::new, "Jenkins\n" + "Version\n" + "2.387.2"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ManageOldDataPage::new, "Manage Old Data"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) JenkinsCLIPage::new, "Jenkins CLI"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ScriptConsolePage::new, "Script Console"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) PrepareForShutdownPage::new, "Prepare for Shutdown"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate from 'Dashboard' -> 'Manage Jenkins' to the pages listed in the drop-down")
    @Test(dataProvider = "subsections")
    public void testNavigateToManageJenkinsSubsection(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromSubMenuConstructor, String expectedResult) {

        String actualResult = new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(pageFromSubMenuConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to navigate from 'Dashboard' -> 'Manage Jenkins' to the 'Reload " +
            "Configuration from Disk' page")
    @Test
    public void testReloadConfigurationFromDiskOfManageJenkinsSubmenu() {
        String popUp = new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(new MainPage(getDriver()))
                .getAlertBoxText();

        Assert.assertEquals(popUp, "Reload Configuration from Disk: are you sure?");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that all the 'Dashboard' drop-down menu items are present")
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
        return new Object[][]{{TestUtils.JobType.FreestyleProject}, {TestUtils.JobType.Pipeline},
                {TestUtils.JobType.MultiConfigurationProject}, {TestUtils.JobType.Folder},
                {TestUtils.JobType.MultibranchPipeline}, {TestUtils.JobType.OrganizationFolder}};
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that clicking on 'Dashboard' button returns a user to the 'Dashboard' page")
    @Test(dataProvider = "job-type")
    public void testReturnToDashboardPageFromProjectPage(TestUtils.JobType jobType) {
        TestUtils.createJob(this, PROJECT_NAME, jobType, false);

        String nameProjectOnMainPage = jobType.createConfigPage(getDriver())
                .getBreadcrumb()
                .clickDashboardButton()
                .getJobName(PROJECT_NAME);

        Assert.assertEquals(nameProjectOnMainPage, PROJECT_NAME);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to 'Manage Plugins' page from 'People' page " +
            "by clicking 'Dashboard'->'Manage Jenkins'")
    @Test
    public void testNavigateToPluginsPageFromPeoplePage() {
        String actualTitle = new MainPage(getDriver())
                .clickPeopleFromSideMenu()
                .getBreadcrumb()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(new PluginsPage(getDriver()))
                .getPageTitle();

        Assert.assertEquals(actualTitle, "Plugins");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to 'Manage Plugins' page from the main page " +
            "by clicking 'Dashboard'->'Manage Jenkins'")
    @Test
    public void testNavigateToPluginsPageFromDropDown() {
        String actualResult = new MainPage(getDriver())
                .getBreadcrumb()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(new PluginsPage(getDriver()))
                .getPageTitle();

        Assert.assertEquals(actualResult, "Plugins");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to 'Build History' page from project page " +
            "by clicking 'Dashboard'->'Build History'")
    @Test(dataProvider = "job-type")
    public void testNavigateToBuildHistoryPageFromProjectPage(TestUtils.JobType jobType) {
        TestUtils.createJob(this, PROJECT_NAME, jobType, false);

        String actualHeaderText = jobType.createJobPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu(new BuildHistoryPage(getDriver()))
                .getHeaderText();

        Assert.assertEquals(actualHeaderText, "Build History of Jenkins", "The header is not correct");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to 'Build History' page from project page " +
            "by clicking 'Dashboard'->'Build History'")
    @Test
    public void testNavigateToPeoplePageFromBuildHistoryPage() {
        String actualTitle = new MainPage(getDriver())
                .clickBuildsHistoryFromSideMenu()
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu(new PeoplePage(getDriver()))
                .getPageTitle();

        Assert.assertEquals(actualTitle, "People");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to return to 'Dashboard' page from 'People' page " +
            "by clicking 'Dashboard'")
    @Test
    public void testReturnToDashboardPageFromPeoplePage() {
        boolean welcomeJenkins = new MainPage(getDriver())
                .clickPeopleFromSideMenu()
                .getBreadcrumb()
                .clickDashboardButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeJenkins, "'Welcome Jenkins' text is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to return to 'Dashboard' page from 'Build History' page " +
            "by clicking 'Dashboard'")
    @Test
    public void testReturnToDashboardPageFromBuildHistoryPage() {
        String actualTitle = new MainPage(getDriver())
                .clickBuildsHistoryFromSideMenu()
                .getBreadcrumb()
                .clickDashboardButton()
                .getTitle();

        Assert.assertEquals(actualTitle, "Dashboard [Jenkins]");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to return to 'Dashboard' page from 'New Item' page " +
            "by clicking 'Dashboard'")
    @Test
    public void testReturnToDashboardPageFromNewItemPage() {
        boolean welcomeJenkins = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .getBreadcrumb()
                .clickDashboardButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeJenkins, "'Welcome Jenkins' text is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to return to 'Dashboard' page from 'Configure' page " +
            "by clicking 'Dashboard'")
    @Test(dataProvider = "job-type")
    public void testReturnToDashboardPageFromConfigurationPage(TestUtils.JobType jobType) {
        TestUtils.createJob(this, PROJECT_NAME, jobType, false);

        boolean mainPageOpen = jobType.createConfigPage(getDriver())
                .getBreadcrumb()
                .clickDashboardButton()
                .isMainPageOpen();

        Assert.assertTrue(mainPageOpen, "Main page is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to return to 'Dashboard' page from 'My Views' page " +
            "by clicking 'Dashboard'")
    @Test
    public void testReturnToDashboardPageFromMyViewsPage() {
        boolean welcomeJenkins = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .getBreadcrumb()
                .clickDashboardButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeJenkins, "'Welcome Jenkins' text is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that a user is able to return to 'Dashboard' page from 'Manage Jenkins' page " +
            "by clicking 'Dashboard'")
    @Test
    public void testReturnToDashboardPageFromManageJenkinsPage() {
        boolean welcomeJenkins = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .getBreadcrumb()
                .clickDashboardButton()
                .isWelcomeDisplayed();

        Assert.assertTrue(welcomeJenkins, "'Welcome Jenkins' text is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to 'My Views' page from 'Configure' page " +
            "by selecting 'Dashboard' drop-down menu")
    @Test(dataProvider = "job-type")
    public void testNavigateToMyViewsPageFromConfigurationPage(TestUtils.JobType jobType) {
        TestUtils.createJob(this, PROJECT_NAME, jobType, false);

        String actualTextFromBreadCrumb = jobType.createConfigPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu(new MyViewsPage(getDriver()))
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertEquals(actualTextFromBreadCrumb, "Dashboard > admin > My Views > All");
    }

    @DataProvider(name = "optionsFolder")
    public Object[][] folderDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new FolderConfigPage(new FolderPage(driver)), "Configuration"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new NewJobPage(getDriver()), "Enter an item name"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new FolderPage(getDriver())), "Delete Folder " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PeoplePage(getDriver()), "People"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new BuildHistoryPage(getDriver()), "Build History of Jenkins"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new FolderPage(driver)), "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new FolderPage(driver)), "Rename Folder " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(getDriver()), "Credentials"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to folder pages from the drop-down")
    @Test(dataProvider = "optionsFolder")
    public void testNavigateToFolderPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String textFromPage) {
        TestUtils.checkMoveOptionAndCreateFolder(textFromPage, this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, false);

        String actualTextFromPage = new FolderPage(getDriver())
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromBreadcrumbDropdownMenuWithoutDeleteAlert(pageFromDataConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualTextFromPage, textFromPage);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to a job page from 'Build History' page " +
            "by clicking 'All' drop-down menu")
    @Test
    public void testNavigateToJobFromBuildHistory() {
        Map<String, BaseJobPage<?>> jobMap = TestUtils.getJobMap(this);

        for (Map.Entry<String, BaseJobPage<?>> entry : TestUtils.getJobMap(this).entrySet()) {
            TestUtils.createJob(this, entry.getKey(), TestUtils.JobType.valueOf(entry.getKey()), true);
        }

        List<String> jobNameList = new ArrayList<>(jobMap.keySet());
        List<String> jobNameActualList = new ArrayList<>();

        for (Map.Entry<String, BaseJobPage<?>> jobNameAndJobTypeMap : jobMap.entrySet()) {
            jobNameActualList.add(new MainPage(getDriver())
                    .clickBuildsHistoryFromSideMenu()
                    .getBreadcrumb()
                    .clickProjectNameFromAllButtonDropDownMenu(jobNameAndJobTypeMap.getValue(), jobNameAndJobTypeMap.getKey())
                    .getProjectName());

            jobNameAndJobTypeMap.getValue()
                    .getHeader()
                    .clickLogo();
        }

        Assert.assertEquals(jobNameActualList, jobNameList);
    }

    @Severity(SeverityLevel.NORMAL)
    @DataProvider(name = "job-submenu-option")
    public Object[][] provideJobSubmenuOption() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ChangesPage(driver), "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new WorkspacePage(driver), "Error: no workspace"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(driver)), "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new MultiConfigurationProjectPage(driver)), "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new MultiConfigurationProjectPage(driver)), "Delete Multi-configuration project: are you sure?"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new MultiConfigurationProjectPage(driver)), "Rename Multi-configuration project " + PROJECT_NAME}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to Multiconfiguration Project pages from the project drop-down")
    @Test(dataProvider = "job-submenu-option")
    public void testNavigateToMultiConfigurationPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String pageHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(pageFromDataConstructor.apply(getDriver()).callByMenuItemName(), this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String actualTextFromPage;

        BaseMainHeaderPage<?> baseMainHeaderPage = new MultiConfigurationProjectPage(getDriver())
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(pageFromDataConstructor.apply(getDriver()));
        if (pageFromDataConstructor.apply(getDriver()).callByMenuItemName().contains("Delete")) {
            actualTextFromPage = baseMainHeaderPage.getAlertBoxText();
        } else {
            actualTextFromPage = baseMainHeaderPage.getAssertTextFromPage();
        }

        Assert.assertEquals(actualTextFromPage, pageHeaderText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification that Build from Multiconfiguration Project can be deleted")
    @Test
    public void testSubmenuDeleteMultiConfigBuild() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, true);

        boolean lastBuild = new MainPage(getDriver())
                .clickBuildByGreenArrow(PROJECT_NAME)
                .clickJobName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickLastBuildLink()
                .getBreadcrumb()
                .getLastBuildBreadcrumbDropdownMenu()
                .clickDeleteFromLastBuildDropDownMenu(new DeletePage<>(new MultiConfigurationProjectPage(getDriver())))
                .clickYesButton()
                .isNoBuildsDisplayed();

        Assert.assertTrue(lastBuild, "'No builds' message is not displayed");
    }

    @DataProvider(name = "job-types")
    public Object[][] jobTypes() {
        return new Object[][]{{TestUtils.JobType.FreestyleProject}, {TestUtils.JobType.Pipeline},
                {TestUtils.JobType.MultiConfigurationProject}};
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verification that a user is able to navigate to 'Build' page from the project drop-down")
    @Test(dataProvider = "job-types")
    public void testNavigateToMultiConfigurationPagesFromDropdownOnBreadcrumbBuildNow(TestUtils.JobType jobType) {
        final String optionName = "Build Now";

        TestUtils.createJob(this, PROJECT_NAME, jobType, false);

        boolean isBuildDisplayed = jobType.createJobPage(getDriver())
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .clickBuildNowFromDashboardDropdownMenu(new MultiConfigurationProjectPage(getDriver()))
                .refreshPage()
                .isLastBuildIconDisplayed();

        Assert.assertTrue(isBuildDisplayed, "Last build icon is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to 'New Item' page from 'My Views' page by clicking " +
            "on the 'Dashboard' drop-down")
    @Test
    public void testNavigateToNewItemPageFromMyViewsPage() {
        String actualResult = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu(new NewJobPage(getDriver()))
                .getHeaderText();

        Assert.assertEquals(actualResult, "Enter an item name");
    }

    @Severity(SeverityLevel.NORMAL)
    @DataProvider(name = "optionsOrganizationFolder")
    public Object[][] organizationFolderDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new OrganizationFolderConfigPage(new OrganizationFolderPage(driver)), "Configuration"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ScanOtherFoldersLogPage(driver), "Scan Organization Folder Log"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new OtherFoldersEventsPage(driver), "Organization Folder Events"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new OrganizationFolderPage(driver)), "Delete Organization Folder " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new OrganizationFolderPage(driver)), "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new OrganizationFolderPage(driver)), "Rename Organization Folder " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PipelineSyntaxPage(driver), "Overview"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(driver), "Credentials"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to Organization Folder pages from the project drop-down")
    @Test(dataProvider = "optionsOrganizationFolder")
    public void testNavigateToOrgFolderPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String pageHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(pageFromDataConstructor.apply(getDriver()).callByMenuItemName(), this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.OrganizationFolder, false);

        String actualPageHeaderText = new OrganizationFolderPage(getDriver())
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(pageFromDataConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualPageHeaderText, pageHeaderText);
    }

    @DataProvider(name = "optionsMultibranchPipeline")
    public Object[][] multibranchPipelineDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MultibranchPipelineConfigPage(new MultibranchPipelinePage(driver)), "Configuration"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ScanOtherFoldersLogPage(driver), "Scan Multibranch Pipeline Log"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new OtherFoldersEventsPage(driver), "Multibranch Pipeline Events"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new MultibranchPipelinePage(driver)), "Delete Multibranch Pipeline " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PeoplePage(driver), "People - Welcome"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new BuildHistoryPage(driver), "Build History of Welcome"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new MultibranchPipelinePage(driver)), "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new MultibranchPipelinePage(driver)), "Rename Multibranch Pipeline " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PipelineSyntaxPage(driver), "Overview"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(driver), "Credentials"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to Multibrunch Pipeline pages from the project drop-down")
    @Test(dataProvider = "optionsMultibranchPipeline")
    public void testNavigateToMultibranchPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String expectedHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(pageFromDataConstructor.apply(getDriver()).callByMenuItemName(), this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultibranchPipeline, false);

        String actualPageHeaderText = new MultibranchPipelinePage(getDriver())
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(pageFromDataConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualPageHeaderText, expectedHeaderText);
    }

    @DataProvider(name = "buildSubMenu")
    public Object[][] getBuildSubmenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesBuildPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) DeletePage::new, "Delete build #1"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to the Freestyle Project Build pages from the build drop-down")
    @Test(dataProvider = "buildSubMenu")
    public void testNavigateToFreestyleBuildPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromSubMenuConstructor, String expectedResult) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, false);

        String actualResult = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectOptionFromBuildDropDownList(pageFromSubMenuConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "pipesubmenu")
    public Object[][] pipeDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ChangesPage(driver), "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PipelineConfigPage(new PipelinePage(driver)), "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new PipelinePage(driver)), "Delete Pipeline: are you sure?"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new PipelinePage(driver)), "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new FullStageViewPage(driver), PROJECT_NAME + " - Stage View"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new PipelinePage(driver)), "Rename Pipeline " + PROJECT_NAME},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new PipelineSyntaxPage(driver), "Overview"}};
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to the Pipeline pages from the project drop-down")
    @Test(dataProvider = "pipesubmenu")
    public void testNavigateToPipelinePagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String expectedHeaderText) {
        TestUtils.checkMoveOptionAndCreateFolder(pageFromDataConstructor.apply(getDriver()).callByMenuItemName(), this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Pipeline, false);

        String actualPageText;

        BaseMainHeaderPage<?> baseMainHeaderPage = new PipelinePage(getDriver())
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(pageFromDataConstructor.apply(getDriver()));

        if (pageFromDataConstructor.apply(getDriver()).callByMenuItemName().contains("Delete")) {
            actualPageText = baseMainHeaderPage.getAlertBoxText();
        } else {
            actualPageText = baseMainHeaderPage.getAssertTextFromPage();
        }

        Assert.assertEquals(actualPageText, expectedHeaderText);
    }

    @DataProvider(name = "userDropDownMenu")
    public Object[][] userDropDownBreadcrumbToMyViews2() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new BuildPage(driver), "Builds for admin"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new UserConfigPage(new UserPage(driver)), "Full Name"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MyViewsPage(driver), "This folder is empty"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(driver), "Credentials"},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to 'My Views' page from the 'Admin' drop-down menu items")
    @Test(dataProvider = "userDropDownMenu")
    public void testNavigateToMyViewsPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String expectedPageText) {

        String actualFullBreadcrumbText = new MainPage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickMyViewsFromUserDropdownMenu()
                .getBreadcrumb()
                .getUserBreadcrumbDropdownMenu()
                .clickPageFromUserBreadcrumbDropdownMenu(pageFromDataConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualFullBreadcrumbText, expectedPageText);
    }

    @DataProvider(name = "optionsFreestyleProject")
    public Object[][] FreestyleDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ChangesPage(driver), "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new WorkspacePage(driver), "Error: no workspace"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new FreestyleProjectConfigPage(new FreestyleProjectPage(driver)), "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new FreestyleProjectPage(driver)), "Delete Project: are you sure?"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new FreestyleProjectPage(driver)), "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<FreestyleProjectPage>(new FreestyleProjectPage(driver)), "Rename Project " + PROJECT_NAME}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to the Pipeline pages from the project drop-down")
    @Test(dataProvider = "optionsFreestyleProject")
    public void testNavigateToFreestylePagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String expectedPageText) {
        TestUtils.checkMoveOptionAndCreateFolder(pageFromDataConstructor.apply(getDriver()).callByMenuItemName(), this, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, false);

        String actualPageHeaderText;

        BaseMainHeaderPage<?> baseMainHeaderPage = new FreestyleProjectPage(getDriver())
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(pageFromDataConstructor.apply(getDriver()));

        if (pageFromDataConstructor.apply(getDriver()).callByMenuItemName().contains("Delete")) {
            actualPageHeaderText = baseMainHeaderPage.getAlertBoxText();
        } else {
            actualPageHeaderText = baseMainHeaderPage.getAssertTextFromPage();
        }

        Assert.assertEquals(actualPageHeaderText, expectedPageText);
    }

    @DataProvider(name = "testuserDropDownMenu")
    public Object[][] userDropDownBreadcrumb() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) driver -> new BuildPage(driver), "Builds for tuser"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) driver -> new UserConfigPage(new UserPage(driver)), "Full Name"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) driver -> new MyViewsPage(driver), "This folder is empty"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) driver -> new CredentialsPage(driver), "Credentials"},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to the user pages from the 'User' drop-down")
    @Test(dataProvider = "testuserDropDownMenu")
    public void testNavigateToSubMenuUserFromDropDownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String expectedPageText) {
        final String user = "tuser";
        final String pass = "p@ssword123";
        final String email = "test@test.com";
        final String userFullName = "testuser";

        TestUtils.checkMoveOptionAndCreateFolder(pageFromDataConstructor.apply(getDriver()).callByMenuItemName(), this, true);
        TestUtils.createUserAndReturnToMainPage(this, user, pass, userFullName, email);

        String actualPageHeaderText = new MainPage(getDriver())
                .getHeader()
                .clickLogOutButton()
                .enterUsername(user)
                .enterPassword(pass)
                .enterSignIn(new MainPage(getDriver()))
                .getHeader()
                .clickUserAdminButton()
                .getBreadcrumb()
                .getUserBreadcrumbDropdownMenu()
                .clickPageFromUserBreadcrumbDropdownMenu(pageFromDataConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualPageHeaderText, expectedPageText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to the 'Manaje Jenkins' subsections from the 'Manage Jenkins' drop-down")
    @Test(dataProvider = "subsections")
    public void testNavigateToManageJenkinsSubsectionFromSideMenu(
            Function<WebDriver, BaseSubmenuPage<?>> pageFromSubMenuConstructor, String expectedResult) {

        String actualResult = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .getBreadcrumb()
                .getManageJenkinsDropdownMenu()
                .selectOptionFromManageJenkinsDropDownList(pageFromSubMenuConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "getBuildFromPipelineDropDownSubmenu")
    public Object[][] getBuildFromPipelineDropDownSubmenu() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ChangesPage::new, "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ConsoleOutputPage::new, "Console Output"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) EditBuildInformationPage::new, "Edit Build Information"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new DeletePage<>(new PipelinePage(driver)), "Delete build #1"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new ReplayPage<>(new PipelinePage(driver)), "Replay #1"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) PipelineStepsPage::new, "Pipeline Steps"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) WorkspacePage::new, "Workspaces for " + PROJECT_NAME + " #1"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to the Pipeline Project Build pages from the build drop-down")
    @Test(dataProvider = "getBuildFromPipelineDropDownSubmenu")
    public void testNavigateToPipelineBuildPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromSubMenuConstructor, String expectedResult) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Pipeline, false);

        String actualResult = new PipelinePage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectOptionFromBuildDropDownList(pageFromSubMenuConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification that a user is able to navigate to the MultiConfiguration Project Build pages from the build drop-down")
    @Test(dataProvider = "buildSubMenu")
    public void testNavigateToMultiConfigBuildPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromSubMenuConstructor, String expectedResult) {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject, false);

        String actualResult = new MultiConfigurationProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .getBuildDropdownMenu()
                .selectOptionFromBuildDropDownList(pageFromSubMenuConstructor.apply(getDriver()))
                .getAssertTextFromPage();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
