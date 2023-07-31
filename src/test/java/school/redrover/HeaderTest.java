package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.model.*;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class HeaderTest extends BaseTest {

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

    @Test
    public void testReturnToDashboardFromPeoplePage() {
        String textTitle = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .getHeader()
                .clickLogo()
                .getTitle();

        String textFromMainPage = new MainPage(getDriver())
                .getWelcomeText();

        Assert.assertEquals(textTitle, "Dashboard [Jenkins]");
        Assert.assertEquals(textFromMainPage, "Welcome to Jenkins!");
    }

    @Test
    public void testPressEnterButtonSearchField() {
        String textPageFromSearchBox = new MainPage(getDriver())
                .getHeader()
                .sendSearchBox()
                .getHeaderText();

        Assert.assertEquals(textPageFromSearchBox, "Built-In Node");
    }

    @Test
    public void testLogOutButton() {
        boolean signInButtonPresence = new MainPage(getDriver())
                .getHeader()
                .clickLogOUTButton()
                .isSignInButtonPresent();

        Assert.assertTrue(signInButtonPresence, "Sign In button is not displayed after logout");
    }

    @Test
    public void testNotificationAndSecurityIcon() {
        String expectedManageJenkinsPageHeader = "Manage Jenkins";

        String backgroundColorBefore = new MainPage(getDriver())
                .getHeader()
                .getBackgroundColorNotificationIcon();

        String backgroundColorAfter = new MainPage(getDriver())
                .getHeader()
                .clickNotificationIcon()
                .getNotificationIconBackgroundColor();

        String actualManageJenkinsPageHeader = new ManageJenkinsPage(getDriver())
                .clickManageJenkinsLink()
                .getActualHeader();

        Assert.assertNotEquals(backgroundColorBefore, backgroundColorAfter, " The color of icon is not changed");
        Assert.assertEquals(actualManageJenkinsPageHeader, expectedManageJenkinsPageHeader, " The page is not correct");
    }

    @Test
    public void testReturnToTheDashboardPageAfterCreatingTheItem() {
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

    @Test
    public void testUserPageFromUserButton() {
        boolean adminOrUserPage = new MainPage(getDriver())
                .getHeader()
                .clickOnAdminButton()
                .isUserPageAvailable();

        assertTrue(adminOrUserPage, "'Jenkins User ID:' text is not displayed!");
    }

    @Test
    public void testOfNotificationIconColorChange() {
        String notificationIconColorBefore = new MainPage(getDriver())
                .getHeader()
                .getNotificationIconBackgroundColor();

        String notificationIconColorAfter = new MainPage(getDriver())
                .getHeader()
                .hoverOverNotificationIcon()
                .getNotificationIconBackgroundColor();

        Assert.assertNotEquals(notificationIconColorAfter, notificationIconColorBefore,
                "The Notification icon background has not changed");
    }

    @Test
    public void testAppearanceOfPopUpMenuWhenClickingOnNotificationIcon() {
        boolean isPopUpScreenDisplayed = new MainPage(getDriver())
                .getHeader()
                .clickNotificationIcon()
                .isPopUpNotificationScreenDisplayed();

        Assert.assertTrue(isPopUpScreenDisplayed, "The pop-up Notification icon screen is not displayed");
    }

    @Ignore
    @Test
    public void testOpenTheLinkOfManageJenkinsLinkFromThePopUpScreen() {
        String screenManageFromPopUp = new MainPage(getDriver())
                .getHeader()
                .clickNotificationIcon()
                .clickManageLinkFromPopUp()
                .getActualHeader();

        Assert.assertEquals(screenManageFromPopUp, "Manage Jenkins");
    }

    @Test
    public void testSearchHelpButton() {
        final String expectedResult = "Search Box";

        String actualResult = new MainPage(getDriver())
                .getHeader()
                .clickHelpIcon()
                .getTitleText();

        assertEquals(actualResult, expectedResult);
    }

    @Ignore
    @Test
    public void testSecurityPopUpClickManageJenkinsLink() {
        final String pageHeaderText = "Manage Jenkins";

        String actualHeaderPage = new MainPage(getDriver())
                .getHeader()
                .clickSecurityIcon()
                .clickManageLinkFromPopUp()
                .getActualHeader();

        Assert.assertEquals(actualHeaderPage, pageHeaderText);
    }

    @Test
    public void testSearchJobWithFullJobName() {
        final String projectName = "SearchProject";

        TestUtils.createJob(this, projectName, TestUtils.JobType.MultiConfigurationProject, true);

        String actualProjectName = new MainPage(getDriver())
                .getHeader()
                .sendSearchBoxProjectName(projectName)
                .getJobName();

        assertEquals("Project " + projectName, actualProjectName);

    }
}
