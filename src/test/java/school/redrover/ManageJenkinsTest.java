package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.ConfigureSystemPage;
import school.redrover.model.GlobalCredentialsPage;
import school.redrover.model.MainPage;
import school.redrover.model.manageJenkins.ManageJenkinsPage;
import school.redrover.model.manageJenkins.ManageNodesPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    private static String getRandomStr(int length) {
        return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Navigation")
    @Description("Navigate to the configure system page by search menu using one letter")
    @Test
    public void testSearchWithLetterConfigureSystem() {
        String textConfigureSystem = "Configure System";
        String configurePage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .inputToSearchField("m")
                .selectOnTheFirstLineInDropdown(textConfigureSystem)
                .getHeading();

        Assert.assertEquals(configurePage, textConfigureSystem);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Navigation")
    @Description("Navigate to 'Manage Jenkins' menu from main page using dashboard")
    @Test
    public void testNavigateToManageJenkinsFromMainPageUsingDashboard() {

        String actualResult = new MainPage(getDriver())
                .getBreadcrumb()
                .getDashboardDropdownMenu()
                .getPageFromDashboardDropdownMenu("Manage Jenkins", new ManageJenkinsPage(getDriver()))
                .getActualHeader();

        Assert.assertEquals(actualResult, "Manage Jenkins");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verify the name of new node is displayed")
    @Test
    public void testNameNewNodeOnCreatePage() {
        final String nodeName = "NodeTest";

        String actualNodeName = new MainPage(getDriver())
                .clickBuildExecutorStatus()
                .clickNewNodeButton()
                .inputNodeNameField(nodeName)
                .clickPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton()
                .getNodeName(nodeName);

        Assert.assertEquals(actualNodeName, nodeName);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Check error message creating new node with empty name")
    @Test
    public void testTextErrorWhenCreateNewNodeWithEmptyName() {

        String textError = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageNodes()
                .clickNewNodeButton()
                .inputNodeNameField("testNameNewNode")
                .clickPermanentAgentRadioButton()
                .clickCreateButton()
                .clearNameField()
                .clickSaveButtonWhenNameFieldEmpty()
                .getErrorMessage();

        Assert.assertEquals(textError, "Query parameter 'name' is required");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Check search field using numeric symbol")
    @Test
    public void testSearchNumericSymbol() {

        String searchText = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .inputToSearchField("1")
                .getNoResultTextInSearchField();

        Assert.assertEquals(searchText, "No results");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Navigation")
    @Description("Navigate to the configure system page by search field")
    @Test
    public void testNavigateToConfigureSystemPageBySearchField() {

        String configureSystemPageTitle = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .inputToSearchField("c")
                .clickConfigureSystemFromSearchDropdown()
                .getHeading();

        Assert.assertEquals(getDriver().getTitle(), "Configure System [Jenkins]");
        Assert.assertEquals(configureSystemPageTitle, "Configure System");
    }

    @DataProvider(name = "keywords")
    public Object[][] searchSettingsItem() {
        return new Object[][]{{"manage"}, {"tool"}, {"sys"}, {"sec"}, {"cred"}, {"dow"}, {"script"}, {"jenkins"}, {"stat"}};
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verify access search field using keywords")
    @Test(dataProvider = "keywords")
    public void testSearchSettingsItemsByKeyword(String keyword) {

        boolean manageJenkinsPage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .inputToSearchField(keyword)
                .selectAllDropdownResultsFromSearchField()
                .isDropdownResultsFromSearchFieldContainsTextToSearch(keyword);

        Assert.assertTrue(manageJenkinsPage);
    }

    @DataProvider(name = "ToolsAndActions")
    public Object[][] searchToolsAndActions() {
        return new Object[][]{{"Script Console"}, {"Jenkins CLI"}, {"Prepare for Shutdown"}};
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verify access search field")
    @Test(dataProvider = "ToolsAndActions")
    public void testSearchToolsAndActions(String inputText) {
        String searchResult = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .inputToSearchField(inputText)
                .getDropdownResultsInSearchField();
        Assert.assertEquals(searchResult, inputText);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verify access search field using short cut key")
    @Test
    public void testAccessSearchSettingsFieldUsingShortcutKey() {
        final String partOfSettingsName = "manage";

        ManageJenkinsPage manageJenkinsPage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .inputToSearchFieldUsingKeyboardShortcut(partOfSettingsName)
                .selectAllDropdownResultsFromSearchField();

        Assert.assertTrue(manageJenkinsPage.isDropdownResultsFromSearchFieldContainsTextToSearch(partOfSettingsName));
        Assert.assertTrue(manageJenkinsPage.isDropdownResultsFromSearchFieldLinks());
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Create a new agent node")
    @Test
    public void testCreateNewAgentNode() {
        final String nodeName = "NewAgentNode";

        String manageNodesPage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageNodes()
                .clickNewNodeButton()
                .inputNodeNameField(nodeName)
                .clickPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton()
                .getNodeName(nodeName);

        Assert.assertEquals(manageNodesPage, nodeName);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Create a new agent node with description")
    @Test
    public void testCreateNewAgentNodeWithDescription() {
        final String description = getRandomStr(50);
        final String nodeName = "NameWithDescription";

        String nodeDescription = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageNodes()
                .clickNewNodeButton()
                .inputNodeNameField(nodeName)
                .clickPermanentAgentRadioButton()
                .clickCreateButton()
                .addDescription(description)
                .clickSaveButton()
                .clickOnNode(nodeName)
                .getDescriptionText();

        Assert.assertEquals(nodeDescription, description);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Create a new agent node by copying existing nod")
    @Test
    public void testCreateNewAgentNodeByCopyingExistingNode() {
        final String nodeName = getRandomStr(10);
        final String newNodeName = getRandomStr(10);
        final String description = getRandomStr(50);

        String newNodeDescription = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageNodes()
                .clickNewNodeButton()
                .inputNodeNameField(nodeName)
                .clickPermanentAgentRadioButton()
                .clickCreateButton()
                .addDescription(description)
                .clickSaveButton()
                .clickNewNodeButton()
                .inputNodeNameField(newNodeName)
                .clickCopyExistingNode()
                .inputExistingNode(nodeName)
                .clickCreateButton()
                .clickSaveButton()
                .clickOnNode(newNodeName)
                .getDescriptionText();

        Assert.assertEquals(newNodeDescription, description);
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Create a new agent node by copying non existing nod")
    @Test
    public void testCreateNewAgentNodeByCopyingNonExistingNode() {
        final String nonExistingNodeName = ".0";

        String errorMessage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManageNodes()
                .clickNewNodeButton()
                .inputNodeNameField("NewNode")
                .clickCopyExistingNode()
                .inputExistingNode(nonExistingNodeName)
                .clickCreateButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "No such agent: " + nonExistingNodeName);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify is displayed tasks on the side panel of the Manage plugins menu")
    @Test
    public void testFourTasksOnLeftSidePanel() {
        final List<String> expectedListOfTasks = List.of(new String[]{"Updates", "Available plugins", "Installed plugins", "Advanced settings"});
        List<String> actualListOfTasks = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManagePlugins()
                .checkTasksOnTheLeftSidePanel();

        Assert.assertEquals(actualListOfTasks, expectedListOfTasks);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify help info is displayed")
    @Test
    public void testServerHelpInfo() {
        final String expectedServerHelpInfo = """
                If your Jenkins server sits behind a firewall and does not have the direct access to the internet, and if your server JVM is not configured appropriately ( See JDK networking properties for more details ) to enable internet connection, you can specify the HTTP proxy server name in this field to allow Jenkins to install plugins on behalf of you. Note that Jenkins uses HTTPS to communicate with the update center to download plugins.
                Leaving this field empty means Jenkins will try to connect to the internet directly.
                If you are unsure about the value, check the browser proxy configuration.""";
        String ServerHelpInfo = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickManagePlugins()
                .clickAdvancedSettings()
                .clickExtraInfoServerIcon()
                .getExtraInfoServerTextBox();

        Assert.assertEquals(ServerHelpInfo, expectedServerHelpInfo);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Delete node by side menu on the nodes page")
    @Test
    public void testDeleteNodeBySideMenuOnNodePage() {
        final String nodeName = "NameDeleteSideMenu";
        TestUtils.createNode(getDriver(), nodeName, false);

        List<String> nodeNameList = new ManageNodesPage(getDriver())
                .clickOnNode(nodeName)
                .clickOnDeleteAgent()
                .clickYesButton()
                .getNodesList();

        Assert.assertFalse(nodeNameList.contains(nodeName));
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Delete node by drop down menu on the nodes page")
    @Test
    public void testDeleteNodeByDropDownOnManageNodesPage() {
        final String nodeName = "NameFor Delete";
        TestUtils.createNode(getDriver(), nodeName, false);

        List<String> nodeNameList = new ManageNodesPage(getDriver())
                .openNodeDropDownMenu(nodeName)
                .dropDownMenuClickDeleteAgent()
                .clickYesButton()
                .getNodesList();

        Assert.assertFalse(nodeNameList.contains(nodeName));
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Ignore
    @Description("Set up email notification in the 'Manage Jenkins' menu")
    @Test
    public void testManageJenkinsEmailNotificationSetUp() {
        String smtpServer = "smtp.gmail.com";
        String smtpPort = "465";
        String username = "jenkins05test@gmail.com";
        String password = "bfdzlscazepasstj";
        String expectedTestConfigurationMessage = "Email was successfully sent";

        String actualTestConfigurationMessage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureSystemLink()
                .inputSmtpServerFieldExtendedEmailNotifications(smtpServer)
                .inputSmtpPortFieldExtendedEmailNotifications(smtpPort)
                .clickAdvancedButtonExtendedEmailNotification()
                .clickAddCredentialButton()
                .inputUsernameIntoAddCredentialPopUpWindow(username)
                .inputPasswordIntoAddCredentialPopUpWindow(password)
                .clickAddButtonAddCredentialPopUp()
                .selectCreatedCredentials(username)
                .checkUseSSLCheckbox()
                .clickDefaultTriggersButton()
                .checkAlwaysDefaultTriggers()
                .checkSuccessDefaultTriggers()
                .inputSmtpServerFieldEmailNotifications(smtpServer)
                .clickAdvancedButtonEmailNotification()
                .clickUseSMTPAuthenticationCheckbox()
                .inputUserNameAndPasswordSMTPAuthentication(username, password)
                .checkUseSSLCheckboxEmailNotifications()
                .inputSmtpPortEmailNotificationsField(smtpPort)
                .checkTestConfigurationBySendingTestEmailCheckbox()
                .inputEmailIntoTestEmailRecipientInputField(username)
                .clickTestConfigurationButton()
                .getConfigurationMessageText();

        Assert.assertEquals(actualTestConfigurationMessage, expectedTestConfigurationMessage);
        new ConfigureSystemPage(getDriver()).clickSaveButton();
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Ignore
    @Description("Return to the 'Manage Jenkins' menu after setting up email notifications")
    @Test(dependsOnMethods = {"testManageJenkinsEmailNotificationSetUp"})
    public void testManageJenkinsEmailNotificationGoingBackToOriginalSettings() {

        ConfigureSystemPage configureSystemPage = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureSystemLink()
                .inputSmtpServerFieldExtendedEmailNotifications("")
                .inputSmtpPortFieldExtendedEmailNotifications("25")
                .clickAdvancedButtonExtendedEmailNotification()
                .unCheckUseSSLCheckboxExtendedEmailNotifications()
                .clickDefaultTriggersButton()
                .unCheckDefaultTriggerAlwaysCheckbox()
                .unCheckDefaultTriggerSuccessCheckbox()
                .inputSmtpServerFieldEmailNotifications("")
                .clickAdvancedButtonEmailNotification()
                .unCheckSMTPAuthenticationCheckbox()
                .unCheckUseSSLCheckboxEmailNotifications()
                .inputSmtpPortEmailNotificationsField("25")
                .clickSaveButton()
                .clickManageJenkinsPage()
                .clickConfigureSystemLink();

        Assert.assertTrue(configureSystemPage.isSmtpServerFieldExtendedEmailNotificationsEmpty());
        Assert.assertTrue(configureSystemPage.isSmtpPortFieldExtendedEmailNotificationsBackToOriginal());
        Assert.assertFalse(configureSystemPage.isUseSSLCheckboxChecked());
        Assert.assertFalse(configureSystemPage.isTriggersAlwaysChecked());
        Assert.assertFalse(configureSystemPage.isTriggersSuccessChecked());
        Assert.assertTrue(configureSystemPage.isSmtpServerFieldEmailNotificationsEmpty());
        Assert.assertFalse(configureSystemPage.isUseSMTPAuthenticationCheckboxChecked());
        Assert.assertFalse(configureSystemPage.isUseSSLCheckboxEmailNotificationsChecked());
        Assert.assertTrue(configureSystemPage.isSmtpPortFieldEmailNotificationsBackToOriginal());
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Delete node from the Manage Jenkins page")
    @Test
    public void testDeleteNodeFromManageJenkinsPage() {
        final String nodeName = "NodeName";
        TestUtils.createNode(getDriver(), nodeName, true);

        List<String> nodeNameList = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickNodeDropdownMenu(nodeName)
                .selectDeleteAgentInDropdown()
                .clickYesButton()
                .getNodesList();

        Assert.assertFalse(nodeNameList.contains(nodeName));
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Delete node from the main page")
    @Test
    public void testDeleteNodeFromMainPage() {
        final String nodeName = "NameDeleteSideMenu";
        TestUtils.createNode(getDriver(), nodeName, true);

        List<String> nodeNameList = new MainPage(getDriver())
                .clickNodeDropdownMenu(nodeName)
                .selectDeleteAgentInDropdown()
                .clickYesButton()
                .getNodesList();

        Assert.assertFalse(nodeNameList.contains(nodeName));
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Create new credentials from Manage Jenkins page")
    @Test
    public void testCreateNewCredentials() {
        final String credentialUsername = getRandomStr(5);
        final String credentialPassword = getRandomStr(5);

        int amountOfGlobalCredentialsBefore = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickCredentialsLink()
                .clickGlobalStoresScopedToJenkinsLink()
                .getSizeOfGlobalCredentialsNamesList();

        int amountOfGlobalCredentialsAfter = new GlobalCredentialsPage(getDriver())
                .clickAddCredentialsButton()
                .inputUsernameIntoUsernameInputField(credentialUsername)
                .inputPasswordIntoPasswordInputField(credentialPassword)
                .clickCreateButton()
                .getSizeOfGlobalCredentialsNamesList();

        Assert.assertNotEquals(amountOfGlobalCredentialsBefore, amountOfGlobalCredentialsAfter);
        Assert.assertTrue(new GlobalCredentialsPage(getDriver()).isCredentialWithSpecificNameDisplayed(credentialUsername), "Credentials with specific name weren't created");
    }
}