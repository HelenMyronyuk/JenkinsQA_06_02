package school.redrover;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.builds.UserBuildsPage;
import school.redrover.model.users.ManageUsersPage;
import school.redrover.model.users.UserConfigPage;
import school.redrover.model.users.UserPage;
import school.redrover.model.views.MyViewsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.function.Function;

public class UsersTest extends BaseTest {

    protected static final String USER_NAME = "testuser";
    protected static final String PASSWORD = "p@ssword123";
    protected static final String EMAIL = "test@test.com";
    protected static final String USER_FULL_NAME = "Test User";
    private static final String EXPECTED_TEXT_ALERT_INCORRECT_LOGIN_AND_PASSWORD = "Invalid username or password";
    protected static final String PROJECT_NAME = "PROJECT_NAME";

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testCreateNewUser() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean newUser = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .isUserExist(USER_NAME);

        Assert.assertTrue(newUser);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testCreateUsingInvalidEmail() {
        String errorEmail = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickCreateUser()
                .fillUserDetails(USER_NAME, PASSWORD, USER_FULL_NAME, "test.mail.com")
                .getInvalidEmailError();

        Assert.assertEquals(errorEmail, "Invalid e-mail address",
                "The error message is incorrect or missing");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Test
    public void testAddDescriptionToUserOnUserStatusPage() {
        final String displayedDescriptionText = "Test User Description";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String actualDisplayedDescriptionText = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickUserIDName(USER_NAME)
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(displayedDescriptionText)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(actualDisplayedDescriptionText, displayedDescriptionText);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Test
    public void testEditDescriptionToUserOnUserStatusPage() {
        final String displayedDescriptionText = "User Description Updated";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickUserIDName(USER_NAME);

        UserPage statusUserPage = new UserPage(getDriver());
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

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Test
    public void testAddDescriptionFromConfigure() {
        String descriptionText = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickFirstUserEditButton()
                .clearDescriptionArea()
                .addDescription("Description text")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals("Description text", descriptionText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testPreviewDescriptionFromConfigurePage() {
        String descriptionText = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickFirstUserEditButton()
                .clearDescriptionArea()
                .addDescription("Description text")
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals("Description text", descriptionText);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testEditEmailOnTheUserProfilePageByDropDown() {
        final String displayedEmail = "testedited@test.com";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        UserConfigPage configureUserPage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .openUserIDDropDownMenu(USER_NAME)
                .selectItemInUserIDDropDownMenu("Configure", new UserConfigPage(new UserPage(getDriver())));

        String oldEmail = configureUserPage.getEmailValue("value");

        String actualEmail = configureUserPage
                .enterEmail(displayedEmail)
                .clickSaveButton()
                .clickConfigureSideMenu()
                .getEmailValue("value");

        Assert.assertNotEquals(actualEmail, oldEmail);
        Assert.assertEquals(actualEmail, displayedEmail);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Navigation")
    @Test(dataProvider = "sideMenuItem")
    public void testNavigateToSideMenuUserFromUsersPage(
            Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenuConstructor, String optionName, String expectedFullBreadcrumbText) {

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

    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Test
    public void testViewIconButtonsPeoplePage() {
        List<String> expectedIconButtonsNames = List.of("S" + "\n" + "mall", "M" + "\n" + "edium", "L" + "\n" + "arge");

        List<String> actualIconButtonsNames = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .getIconButtonsList();

        Assert.assertEquals(actualIconButtonsNames, expectedIconButtonsNames);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testDeleteFromSideMenu() {
        String newUserName = "testuser";

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean isUserDeleted = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .clickUserName(newUserName)
                .clickDeleteUserBtnFromUserPage(newUserName)
                .clickYesButton()
                .clickPeopleOnLeftSideMenu()
                .checkIfUserWasDeleted(newUserName);

        Assert.assertTrue(isUserDeleted);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testDeleteFromBin() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean userNotFound = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickDeleteUser()
                .clickYesButton()
                .getUserDeleted(USER_NAME);

        Assert.assertFalse(userNotFound);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testDeleteFromDropDown() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean isUserDeleted = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .openUserIDDropDownMenu(USER_NAME)
                .selectItemInUserIDDropDownMenu("Delete", new DeletePage<>(new MainPage(getDriver())))
                .clickYesButton()
                .clickPeopleOnLeftSideMenu()
                .checkIfUserWasDeleted(USER_NAME);

        Assert.assertTrue(isUserDeleted, "The user was not deleted");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testDeleteFromConfigure() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean isUserDeleted = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickConfigureButton(USER_NAME)
                .clickDeleteUser()
                .clickYesButton()
                .clickPeopleOnLeftSideMenu()
                .checkIfUserWasDeleted(USER_NAME);

        Assert.assertTrue(isUserDeleted, "The user was not deleted");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Test
    public void testLogInWithDeletedUserCredentials() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String invalidMessage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickDeleteUser()
                .clickYesButton()
                .getHeader()
                .clickLogOutButton()
                .enterUsername(USER_NAME)
                .enterPassword(PASSWORD)
                .enterSignIn(new LoginPage(getDriver()))
                .getTextAlertIncorrectUsernameOrPassword();

        Assert.assertEquals(invalidMessage, "Invalid username or password");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Test
    public void testUserCanLoginToJenkinsWithCreatedAccount() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        MainPage actualResult = new MainPage(getDriver())
                .getHeader()
                .clickLogOutButton()
                .enterUsername(USER_NAME)
                .enterPassword(PASSWORD)
                .enterSignIn(new MainPage(getDriver()));
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        Assert.assertTrue(actualResult.jobIsDisplayed(PROJECT_NAME), "true");
    }

    @DataProvider(name = "invalid data for user login")
    public Object[][] loginNameAndPassword() {
        return new Object[][]{
                {"incorrect user name", PASSWORD},
                {USER_NAME, "12345hi"},
                {"incorrect user name", "12345hi"}
        };
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Test(dataProvider = "invalid data for user login")
    public void testLogInWithInvalidData(String userName, String userPassword) {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String actualTextAlertLogInWithInvalidData = new MainPage(getDriver())
                .getHeader()
                .clickLogOutButton()
                .enterUsername(userName)
                .enterPassword(userPassword)
                .enterSignIn(new LoginPage(getDriver()))
                .getTextAlertIncorrectUsernameOrPassword();

        Assert.assertEquals(actualTextAlertLogInWithInvalidData, EXPECTED_TEXT_ALERT_INCORRECT_LOGIN_AND_PASSWORD);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testCreateUserCheckInPeople() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean actualResultFindUserName = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .checkIfUserWasAdded(USER_NAME, USER_FULL_NAME);

        Assert.assertTrue(actualResultFindUserName, "The user not found");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Test
    public void testCreateUserCheckInManageUsers() {
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean actualResultFindUserID = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .isUserExist(USER_NAME);

        Assert.assertTrue(actualResultFindUserID, "true");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
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
    public void testCreateBuildForUser() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean isBuildPresent = new MainPage(getDriver())
                .clickBuildByGreenArrow(PROJECT_NAME)
                .getHeader()
                .clickUserDropdownMenu()
                .getPageFromUserDropdownMenu("Builds", new UserBuildsPage(getDriver()))
                .isBuildFroUserPresent(PROJECT_NAME);

        Assert.assertTrue(isBuildPresent, "Build for user is not displayed!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testPreviewDescriptionFromUserPage() {
        final String expectedPreviewDescriptionText = "User Description";

        String previewDescriptionText = new MainPage(getDriver())
                .getHeader()
                .clickUserAdminButton()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription("User Description")
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewDescriptionText, expectedPreviewDescriptionText);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Test
    public void testSearchBoxInsensitive() {
        boolean isSearchResultContainsText = new MainPage(getDriver()).getHeader()
                .clickUserDropdownMenu()
                .openConfigureTabFromUserDropdownMenu()
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Test(dataProvider = "sideMenuItem")
    public void testNavigateToPageFromSideMenuOnConfigure(Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenuConstructor, String optionName, String expectedFullBreadcrumbText) {

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String actualFullBreadcrumbText = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .selectConfigureButton(USER_NAME)
                .selectItemFromTheSideMenu(optionName, pageFromSideMenuConstructor.apply(getDriver()))
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertEquals(actualFullBreadcrumbText, expectedFullBreadcrumbText);
    }

    @DataProvider(name = "dropDownOnUsersPageMenu")
    public Object[][] provideDropDownMenuItem() {
        return new Object[][]{
                {(Function<WebDriver, BaseMainHeaderPage<?>>) driver -> new UserBuildsPage(driver), "Builds", "Dashboard > Test User > Builds"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) driver -> new UserConfigPage(new UserPage(driver)), "Configure", "Dashboard > Test User > Configure"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) driver -> new MyViewsPage(driver), "My Views", "Dashboard > Test User > My Views > All"},
                {(Function<WebDriver, BaseMainHeaderPage<?>>) driver -> new DeletePage<>(new ManageUsersPage(driver)), "Delete", "Dashboard > Test User > Delete"},
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Test(dataProvider = "dropDownOnUsersPageMenu")
    public void testNavigateToPageFromDropDownOnUsersPage(Function<WebDriver, BaseMainHeaderPage<?>> pageFromSideMenuConstructor, String optionName, String expectedBreadcrumbText) throws InterruptedException {

        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String breadcrumbText = new MainPage(getDriver())
                .getBreadcrumb()
                .selectAnOptionFromDashboardManageJenkinsSubmenuList(new ManageUsersPage(getDriver()))
                .openUserIDDropDownMenu(USER_NAME)
                .selectItemInUserIDDropDownMenu(optionName, pageFromSideMenuConstructor.apply(getDriver()))
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertEquals(breadcrumbText, expectedBreadcrumbText);

    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testCancelDeletingFromBin(){
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean userIsExist = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickDeleteUser()
                .getHeader()
                .clickLogo()
                .clickManageJenkinsPage()
                .clickManageUsers()
                .isUserExist(USER_NAME);

        Assert.assertTrue(userIsExist, "User is not exist");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Test
    public void testCancelDeletingFromConfigurePage(){
        TestUtils.createUserAndReturnToMainPage(this, USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        boolean userIsExist = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickConfigureButton(USER_NAME)
                .clickDeleteUser()
                .getHeader()
                .clickLogo()
                .clickManageJenkinsPage()
                .clickManageUsers()
                .isUserExist(USER_NAME);

        Assert.assertTrue(userIsExist, "User is not exist");
    }
}
