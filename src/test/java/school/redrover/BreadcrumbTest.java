package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.jobs.FolderPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Arrays;
import java.util.List;
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

    @Ignore
    @Test
    public void testReloadConfigurationFromDiskOfManageJenkinsSubmenu() {
        String expectedLoadingText = "Please wait while Jenkins is getting ready to work ...";

        new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(new ConfigureSystemPage(getDriver()))
                .getBreadcrumb()
                .clickOkOnPopUp();

        String loadingText = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(loadingText, expectedLoadingText);
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

    @DataProvider(name = "options")
    public Object[][] jobDropDownBreadcrumb (){
       return new Object[][]{
               {"Configure", PROJECT_NAME + " Config [Jenkins]"},
               {"New Item", "New Item [Jenkins]"},
               {"Delete Folder", "Jenkins"},
               {"People", "People - [Jenkins]"},
               {"Build History", "All [Jenkins]"},
               {"Rename", "Rename [Jenkins]"},
               {"Credentials", PROJECT_NAME + " » Credentials [Jenkins]"}};
    }

    @Test(dataProvider = "options")
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
}
