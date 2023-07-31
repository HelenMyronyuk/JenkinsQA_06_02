package school.redrover;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class UsersTest extends BaseTest {

    protected static final String USER_NAME = "testuser";
    protected static final String PASSWORD = "p@ssword123";
    protected static final String EMAIL = "test@test.com";
    protected static final String USER_FULL_NAME = "Test User";
    private static final String EXPECTED_TEXT_ALERT_INCORRECT_LOGIN_AND_PASSWORD = "Invalid username or password";

    @Test
    public void testCreateNewUser() {
        boolean newUser = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickCreateUser()
                .enterUsername(USER_NAME)
                .enterPassword(PASSWORD)
                .enterConfirmPassword(PASSWORD)
                .enterFullName(USER_FULL_NAME)
                .enterEmail(EMAIL)
                .clickCreateUserButton()
                .isUserExist(USER_NAME);

        Assert.assertTrue(newUser);
    }

    @Test
    public void testErrorIfCreateNewUserWithInvalidEmail() {
        String errorEmail = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickCreateUser()
                .fillUserDetails(USER_NAME, PASSWORD, USER_FULL_NAME, "test.mail.com")
                .getInvalidEmailError();

        Assert.assertEquals(errorEmail, "Invalid e-mail address",
                "The error message is incorrect or missing");
    }


    @Test
    public void testCreateWithExistingName() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String errorDuplicatedUser = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickCreateUser()
                .fillUserDetails(USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL)
                .getUserNameExistsError();

        Assert.assertEquals(errorDuplicatedUser, "User name is already taken",
                "The error message is incorrect or missing");
    }

    @Test
    public void testAddDescriptionToUserOnUserStatusPage() {
        final String displayedDescriptionText = "Test User Description";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickUserIDName(USER_NAME);

        String actualDisplayedDescriptionText = new StatusUserPage(getDriver())
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(displayedDescriptionText)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(actualDisplayedDescriptionText, displayedDescriptionText);
    }

    @Test
    public void testEditDescriptionToUserOnUserStatusPage() {
        final String displayedDescriptionText = "User Description Updated";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickUserIDName(USER_NAME);

        StatusUserPage statusUserPage = new StatusUserPage(getDriver());
        String existingDescriptionText = statusUserPage
                .clickAddOrEditDescription()
                .getDescriptionField();

        String actualDisplayedDescriptionText = statusUserPage
                .clearDescriptionField()
                .enterDescription(displayedDescriptionText)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(actualDisplayedDescriptionText, displayedDescriptionText);
        Assert.assertNotEquals(actualDisplayedDescriptionText, existingDescriptionText);
    }

    @Test
    public void testAddDescriptionToUserOnTheUserProfilePage() {
        String descriptionText = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickUserEditButton()
                .clearDescriptionArea()
                .addDescription("Description text")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals("Description text", descriptionText);
    }

    @Test
    public void testEditEmailOnTheUserProfilePageByDropDown() {
        final String displayedEmail = "testedited@test.com";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .openUserIDDropDownMenu(USER_NAME)
                .selectConfigureUserIDDropDownMenu();

        UserConfigPage configureUserPage = new UserConfigPage(new StatusUserPage(getDriver()));

        String oldEmail = configureUserPage.getEmailValue("value");

        String actualEmail = configureUserPage
                .enterEmail(displayedEmail)
                .clickSaveButton()
                .clickConfigureSideMenu()
                .getEmailValue("value");

        Assert.assertNotEquals(actualEmail, oldEmail);
        Assert.assertEquals(actualEmail, displayedEmail);
    }

    @Test
    public void testVerifyUserPageMenu() {
        final List<String> listMenuExpected = Arrays.asList("People", "Status", "Builds", "Configure", "My Views", "Delete");

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        List<WebElement> listMenu = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickUserIDName(USER_NAME)
                .getListMenu();

        for (int i = 0; i < listMenu.size(); i++) {
            Assert.assertEquals(listMenu.get(i).getText(), listMenuExpected.get(i));
        }
    }

    @Test
    public void testViewIconButtonsPeoplePage() {
        List<String> expectedIconButtonsNames = List.of("S" + "\n" + "mall", "M" + "\n" + "edium", "L" + "\n" + "arge");

        List<String> actualIconButtonsNames = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .getIconButtonsList();

        Assert.assertEquals(actualIconButtonsNames, expectedIconButtonsNames);
    }

    @Test
    public void testSortArrowModeChangesAfterClickingSortHeaderButton() {

        boolean userIDButtonWithoutArrow = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .isUserIDButtonWithoutArrow();

        Assert.assertTrue(userIDButtonWithoutArrow, "UserID button has sort arrow");

        boolean userIDButtonWithUpArrow = new PeoplePage(getDriver())
                .clickUserIDButton()
                .isUserIDButtonWithUpArrow();

        Assert.assertTrue(userIDButtonWithUpArrow, "UserID button has not up arrow");

        boolean userIDButtonWithDownArrow = new PeoplePage(getDriver())
                .clickUserIDButton()
                .isUserIDButtonWithDownArrow();

        Assert.assertTrue(userIDButtonWithDownArrow, "UserID button has not down arrow");

        boolean userIDButtonNotContainsArrow = new PeoplePage(getDriver())
                .clickNameButton()
                .isUserIDButtonWithoutArrow();

        Assert.assertTrue(userIDButtonNotContainsArrow, "UserID button has sort arrow");
    }

    @Test
    public void testSearchPeople() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String actualUserName = new MainPage(getDriver())
                .getHeader()
                .sendSearchBoxUser(USER_NAME)
                .getActualNameUser();

        Assert.assertEquals(actualUserName, "Jenkins User ID: " + USER_NAME);
    }

    @Test
    public void testDeleteUserViaPeopleMenu() {
        String newUserName = "testuser";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean isUserDeleted = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .clickUserName(newUserName)
                .clickDeleteUserBtnFromUserPage(newUserName)
                .clickOnYesButton()
                .clickPeopleOnLeftSideMenu()
                .checkIfUserWasDeleted(newUserName);

        Assert.assertTrue(isUserDeleted);
    }

    @Test
    public void testDeleteUserViaManageUsersByDeleteButton() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean userNotFound = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickDeleteUser()
                .clickYesButton()
                .getUserDeleted(USER_NAME);

        Assert.assertFalse(userNotFound);
    }

    @Test
    public void testLogInWithDeletedUserCredentials() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String invalidMessage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickDeleteUser()
                .clickYesButton()
                .getHeader()
                .clickLogoutButton()
                .enterUsername(USER_NAME)
                .enterPassword(PASSWORD)
                .enterSignIn(new LoginPage(getDriver()))
                .getTextAlertIncorrectUsernameOrPassword();

        Assert.assertEquals(invalidMessage, "Invalid username or password");
    }

    @Test(dependsOnMethods = "testSearchPeople")
    public void testUserCanLoginToJenkinsWithCreatedAccount() {
        String nameProject = "Engineer";

        new MainPage(getDriver())
                .getHeader()
                .clickLogoutButton()
                .enterUsername(USER_NAME)
                .enterPassword(PASSWORD)
                .enterSignIn(new MainPage(getDriver()));
        TestUtils.createJob(this, nameProject, TestUtils.JobType.FreestyleProject, true);
        String actualResult = new MainPage(getDriver()).getJobName(nameProject);

        Assert.assertEquals(actualResult, nameProject);
    }

    @Test
    public void testInputtingAnIncorrectUsername() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String actualTextAlertIncorrectUsername = new MainPage(getDriver())
                .getHeader()
                .clickLogoutButton()
                .enterUsername("incorrect user name")
                .enterPassword(PASSWORD)
                .enterSignIn(new LoginPage(getDriver()))
                .getTextAlertIncorrectUsernameOrPassword();

        Assert.assertEquals(actualTextAlertIncorrectUsername, EXPECTED_TEXT_ALERT_INCORRECT_LOGIN_AND_PASSWORD);
    }

    @Test
    public void testInputtingAnIncorrectPassword() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String actualTextAlertIncorrectPassword = new MainPage(getDriver())
                .getHeader()
                .clickLogoutButton()
                .enterUsername(USER_NAME)
                .enterPassword("12345hi")
                .enterSignIn(new LoginPage(getDriver()))
                .getTextAlertIncorrectUsernameOrPassword();

        Assert.assertEquals(actualTextAlertIncorrectPassword, EXPECTED_TEXT_ALERT_INCORRECT_LOGIN_AND_PASSWORD);
    }

    @Test
    public void testInputtingAnIncorrectUsernameAndPassword() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String actualTextAlertIncorrectUsernameAndPassword = new MainPage(getDriver())
                .getHeader()
                .clickLogoutButton()
                .enterUsername("incorrect user name")
                .enterPassword("12345hi")
                .enterSignIn(new LoginPage(getDriver()))
                .getTextAlertIncorrectUsernameOrPassword();

        Assert.assertEquals(actualTextAlertIncorrectUsernameAndPassword, EXPECTED_TEXT_ALERT_INCORRECT_LOGIN_AND_PASSWORD);
    }

    @Test
    public void testCreateUserFromManageUser() {
        final String expectedResultTitle = "Dashboard [Jenkins]";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        new MainPage(getDriver())
                .getHeader()
                .clickLogoutButton();

        new LoginPage(getDriver())
                .enterUsername(USER_NAME)
                .enterPassword(PASSWORD)
                .enterSignIn(new LoginPage(getDriver()));

        String actualResultTitle = getDriver().getTitle();
        String actualResultNameButton = new MainPage(getDriver())
                .getHeader()
                .getCurrentUserName();

        Assert.assertEquals(actualResultTitle, expectedResultTitle);
        Assert.assertEquals(actualResultNameButton, USER_FULL_NAME);
    }

    @Test
    public void testCreateUserCheckInPeople() {
        final String expectedResultTitle = "People - [Jenkins]";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu();

        String actualResultTitle = getDriver().getTitle();
        boolean actualResultFindUserID = new PeoplePage(getDriver())
                .checkIfUserWasAdded(USER_NAME);
        boolean actualResultFindUSerName = new PeoplePage(getDriver())
                .checkIfUserWasAdded(USER_FULL_NAME);

        Assert.assertEquals(actualResultTitle, expectedResultTitle);
        Assert.assertTrue(actualResultFindUserID, "true");
        Assert.assertTrue(actualResultFindUSerName, "true");
    }

    @Test
    public void testCreateUserCheckInManageUsers() {
        final String expectedResultTitle = "Users [Jenkins]";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers();

        String actualResultTitle = getDriver().getTitle();
        boolean actualResultFindUserID = new ManageUsersPage(getDriver())
                .isUserExist(USER_NAME);

        Assert.assertEquals(actualResultTitle, expectedResultTitle);
        Assert.assertTrue(actualResultFindUserID, "true");
    }

    @Test
    public void testVerifyCreateUserButton() {
        String buttonName = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .getButtonText();

        Assert.assertEquals(buttonName, "Create User");
    }

    @Test
    public void testCreateUserButtonClickable() {
        String iconName = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickCreateUser()
                .getActualIconName();

        Assert.assertEquals(iconName, "Create User");
    }

    @Test
    public void testSearchBoxInsensitive() {
        boolean isSearchResultContainsText = new MainPage(getDriver()).getHeader()
                .clickAdminDropdownMenu()
                .openConfigureTabFromAdminDropdownMenu()
                .selectInsensitiveSearch()
                .clickSaveButton()
                .getHeader()
                .typeToSearch("built")
                .isSearchResultContainsText("built");

        Assert.assertTrue(isSearchResultContainsText, "Wrong search result");
    }


    @DataProvider(name = "sideMenuItem")
    public Object[][] provideSideMenuItem() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) UserPage::new, "asynchPeople", "Dashboard > People"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) UserPage::new, "testuser", "Dashboard > Manage Jenkins > Jenkins’ own user database > Test User"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) UserPage::new, "builds", "Dashboard > Test User > Builds"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) UserPage::new, "configure", "Dashboard > Test User > Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) UserPage::new, "my-views", "Dashboard > Test User > My Views > All"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) UserPage::new, "delete", "Dashboard > Test User > Delete"},
        };
    }

    @Test(dataProvider = "sideMenuItem")
    public void testNavigateToPageFromSideMenuOnConfigure(Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenuConstructor, String optionName, String expectedFullBreadcrumbText) {

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String actualFullBreadcrumbText = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickUserIDName(USER_NAME)
                .selectItemFromTheSideMenu(optionName, pageFromSideMenuConstructor.apply(getDriver()))
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertEquals(actualFullBreadcrumbText, expectedFullBreadcrumbText);
    }
}
