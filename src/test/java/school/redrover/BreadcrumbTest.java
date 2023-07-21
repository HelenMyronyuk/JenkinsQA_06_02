package school.redrover;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseJobPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.jobs.FolderPage;
import school.redrover.model.jobs.OrganizationFolderPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
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
    public Object[][] provideWrongCharacters() {
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
                .clickJobName(PROJECT_NAME, jobType.createConfigPage(getDriver()))
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

    @DataProvider(name = "optionsFolder")
    public Object[][] folderDropDownBreadcrumb (){
       return new Object[][]{
               {"Configure", PROJECT_NAME + " Config [Jenkins]"},
               {"New Item", "New Item [Jenkins]"},
               {"Delete Folder", "Jenkins"},
               {"People", "People - [Jenkins]"},
               {"Build History", "All [Jenkins]"},
               {"Rename", "Rename [Jenkins]"},
               {"Credentials", PROJECT_NAME + " » Credentials [Jenkins]"}};
    }

    @Test(dataProvider = "optionsFolder")
    public void testNavigateToFolderPagesFromDropdownOnBreadcrumb(String option, String expectedTitleText){
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);

        String actualTitle = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FolderPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(option, new FolderPage(getDriver()))
                .getPageTitle();

        Assert.assertEquals(actualTitle, expectedTitleText);
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
                        driver -> new ChangesPage<>(new MultiConfigurationProjectPage(driver)), "Changes"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new WorkspacePage(driver), "Workspace"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MultiConfigurationProjectPage(driver), "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MovePage<>(new MultiConfigurationProjectPage(driver)), "Move"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new RenamePage<>(new MultiConfigurationProjectPage(driver)), "Rename"}
        };
    }

    @Test(dataProvider = "job-submenu-option")
    public void testNavigateToMultiConfigurationPagesFromDropdownOnBreadcrumb(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String optionName) {
        TestUtils.createJob(this, "Folder", TestUtils.JobType.Folder, true);
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.MultiConfigurationProject,true);

        String pageName = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(optionName, pageFromDataConstructor.apply(getDriver()))
                .getOnlyPageNameFromHeader();

        Assert.assertEquals(pageName, optionName);
    }

    @Test
    public void testNavigateToMultiConfigurationPagesFromDropdownOnBreadcrumbDelete() {
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
    public Object[][] organizationFolderDropDownBreadcrumb (){
        return new Object[][]{
                {"Configure", PROJECT_NAME + " Config [Jenkins]"},
                {"Scan Organization Folder Log", PROJECT_NAME + " Scan Organization Folder Log [Jenkins]"},
                {"Organization Folder Events", PROJECT_NAME + " Scan Organization Folder Log [Jenkins]"},
                {"Delete Organization Folder", "Jenkins"},
                {"People", "Error 404 Not Found"},
                {"Build History", "Error 404 Not Found"},
                {"Rename", "Rename [Jenkins]"},
                {"Pipeline Syntax", "Pipeline Syntax: Snippet Generator [Jenkins]"},
                {"Credentials", PROJECT_NAME + " » Credentials [Jenkins]"}
        };
    }

    @Test(dataProvider = "optionsOrganizationFolder")
    public void testNavigateToOrgFolderPagesFromDropdownOnBreadcrumb(String option, String expectedTitleText){
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.OrganizationFolder, true);

        String actualTitle = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new OrganizationFolderPage(getDriver()))
                .getBreadcrumb()
                .getJobBreadcrumbDropdownMenu()
                .getPageFromDashboardDropdownMenu(option, new OrganizationFolderPage(getDriver()))
                .getPageTitle();

        Assert.assertEquals(actualTitle, expectedTitleText);
    }
}
