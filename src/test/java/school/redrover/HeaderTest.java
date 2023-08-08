package school.redrover;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.builds.BuildPage;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.model.jobsSidemenu.CredentialsPage;
import school.redrover.model.manageJenkins.ManageJenkinsPage;
import school.redrover.model.users.UserConfigPage;
import school.redrover.model.users.UserPage;
import school.redrover.model.views.MyViewsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.testng.Assert.*;

public class HeaderTest extends BaseTest {

    @Severity(SeverityLevel.CRITICAL)
    @Feature("UI")
    @Test
    public void testHeaderLogoIconPresent() {
        boolean logoIcon = new MainPage(getDriver())
                .getHeader()
                .isDisplayedLogoIcon();

        boolean logoText = new MainPage(getDriver())
                .getHeader()
                .isDisplayedLogoText();

        Assert.assertTrue(logoIcon);
        Assert.assertTrue(logoText);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Test
    public void testSearchPresent() {
        String placeholder = new MainPage(getDriver())
                .getHeader()
                .getAttributeFromSearchBox();

        boolean helpIcon = new MainPage(getDriver())
                .getHeader()
                .isDisplayedHelpIcon();

        boolean searchIcon = new MainPage(getDriver())
                .getHeader()
                .isDisplayedSearchBoxIcon();

        Assert.assertEquals(placeholder, "Search (CTRL+K)");
        Assert.assertTrue(helpIcon);
        Assert.assertTrue(searchIcon);
    }

    @DataProvider(name = "sideMenuOptions")
    public Object[][] provideSubsection() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) NewJobPage::new, "/view/all/newJob"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) PeoplePage::new, "/asynchPeople/"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) BuildHistoryPage::new, "/view/all/builds"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) ManageJenkinsPage::new, "/manage"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) MyViewsPage::new, "/me/my-views"},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test(dataProvider = "sideMenuOptions")
    public void testReturnToDashboardFromSideMenuPages(Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenu, String sideMenuLink) {
        String textTitle = new MainPage(getDriver())
                .clickOptionOnLeftSideMenu(pageFromSideMenu.apply(getDriver()), sideMenuLink)
                .getHeader()
                .clickLogo()
                .getTitle();

        String textFromMainPage = new MainPage(getDriver())
                .getWelcomeText();

        Assert.assertEquals(textTitle, "Dashboard [Jenkins]");
        Assert.assertEquals(textFromMainPage, "Welcome to Jenkins!");
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Test
    public void testPressEnterButtonSearchField() {
        String textPageFromSearchBox = new MainPage(getDriver())
                .getHeader()
                .sendSearchBox()
                .getHeaderText();

        Assert.assertEquals(textPageFromSearchBox, "Built-In Node");
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Test
    public void testSearchError() {
        String errorText = new MainPage(getDriver())
                .getHeader()
                .sendKeysSearchBox("p", new SearchPage(getDriver()))
                .getErrorText();

        Assert.assertEquals(errorText, "Nothing seems to match.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Test
    public void testLogOutButton() {
        boolean signInButtonPresence = new MainPage(getDriver())
                .getHeader()
                .clickLogoutButton()
                .isSignInButtonPresent();

        Assert.assertTrue(signInButtonPresence, "Sign In button is not displayed after logout");
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Test
    public void testNotificationAndSecurityIcon() {
        String expectedManageJenkinsPageHeader = "Manage Jenkins";

        String backgroundColorBefore = new MainPage(getDriver())
                .getHeader()
                .getBackgroundColorNotificationIcon();

        String backgroundColorAfter = new MainPage(getDriver())
                .getHeader()
                .clickNotificationIcon()
                .getBackgroundColorNotificationIcon();

        String actualManageJenkinsPageHeader = new ManageJenkinsPage(getDriver())
                .clickManageJenkinsLink()
                .getActualHeader();

        Assert.assertNotEquals(backgroundColorBefore, backgroundColorAfter, " The color of icon is not changed");
        Assert.assertEquals(actualManageJenkinsPageHeader, expectedManageJenkinsPageHeader, " The page is not correct");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testReturnToDashboardFromProjectAndConfigPage() {
        final List<String> listItemName = new ArrayList<>(List.of("Test Item", "Second"));

        TestUtils.createJob(this, listItemName.get(0), TestUtils.JobType.FreestyleProject, true);
        TestUtils.createJob(this, listItemName.get(1), TestUtils.JobType.FreestyleProject, false);

        boolean isPageOpen = new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver()))
                .getHeader()
                .clickLogo()
                .isMainPageOpen();

        Assert.assertTrue(isPageOpen, "Wrong title or wrong page");

        List<String> listJob = new MainPage(getDriver())
                .getJobList();

        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < listJob.size(); i++) {
            softAssert.assertTrue(listJob.contains(listItemName.get(i)),
                    "The result list doesn't contain the item " + listItemName.get(i));
        }
        softAssert.assertAll();
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Test
    public void testUserPageFromUserButton() {
        boolean adminOrUserPage = new MainPage(getDriver())
                .getHeader()
                .clickOnAdminButton()
                .isUserPageAvailable();

        assertTrue(adminOrUserPage, "'Jenkins User ID:' text is not displayed!");
    }

    @DataProvider(name = "userDropDown")
    public Object[][] userDropDown() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new BuildPage(getDriver()), "Builds", "Builds"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new UserConfigPage(new UserPage(getDriver())), "Configure", "Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new MyViewsPage(getDriver()), "My Views", "All"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>)
                        driver -> new CredentialsPage(getDriver()), "Credentials", "Credentials"}};
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test(dataProvider = "userDropDown")
    public void testSubMenuUserFromHeader(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromDataConstructor, String menu, String expectedPageName) {

        String pageNameFromBreadcrumb = new MainPage(getDriver())
                .getHeader()
                .clickOnUserDropDownMenu()
                .getPageFromUserDropdownMenu(menu, pageFromDataConstructor.apply(getDriver()))
                .getBreadcrumb()
                .getPageNameFromBreadcrumb();

        Assert.assertEquals(pageNameFromBreadcrumb, expectedPageName);

    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Test
    public void testNotificationPopUpClickManageJenkinsLink() {
        String screenManageFromPopUp = new MainPage(getDriver())
                .getHeader()
                .clickNotificationIcon()
                .clickManageLinkFromNotificationPopUp()
                .getActualHeader();

        Assert.assertEquals(screenManageFromPopUp, "Manage Jenkins");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Test
    public void testSearchHelpButton() {
        final String expectedResult = "Search Box";

        String actualResult = new MainPage(getDriver())
                .getHeader()
                .clickHelpIcon()
                .getTitleText();

        assertEquals(actualResult, expectedResult);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Ignore
    @Test
    public void testSecurityPopUpClickManageJenkinsLink() {
        String actualHeaderPage = new MainPage(getDriver())
                .getHeader()
                .clickSecurityIcon()
                .clickManageLinkFromSecurityPopUp()
                .getActualHeader();

        Assert.assertEquals(actualHeaderPage, "Manage Jenkins");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testSearchJobWithFullJobName() {
        final String projectName = "SearchProject";

        TestUtils.createJob(this, projectName, TestUtils.JobType.MultiConfigurationProject, true);

        String actualProjectName = new MainPage(getDriver())
                .getHeader()
                .sendKeysSearchBox(projectName, new MultiConfigurationProjectPage(getDriver()))
                .getJobName();

        assertEquals("Project " + projectName, actualProjectName);

    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testSearchJobWithOneLetter() {
        final String projectName = "Project";

        TestUtils.createJob(this, projectName, TestUtils.JobType.FreestyleProject, false);

        String actualResult = new MainPage(getDriver())
                .getHeader()
                .sendKeysSearchBox("p", new SearchPage(getDriver()))
                .clickSearchResult(projectName)
                .getJobName();

        assertEquals("Project " + projectName, actualResult);

    }

    @Feature("UI")
    @Test
    public void testSearchPeople() {
        final String USER_NAME = "testuser";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, "p@ssword123", "Test User", "test@test.com");

        String actualUserName = new MainPage(getDriver())
                .getHeader()
                .sendKeysSearchBox(USER_NAME, new UserPage(getDriver()))
                .getActualNameUser();

        Assert.assertEquals(actualUserName, "Jenkins User ID: " + USER_NAME);
    }
}
