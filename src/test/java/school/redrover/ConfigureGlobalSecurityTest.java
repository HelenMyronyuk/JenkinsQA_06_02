package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class ConfigureGlobalSecurityTest extends BaseTest {

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verification 'Configure Global Security' link from  the 'Manage Jenkins' page")
    @Test
    public void testCheckTitleTexts() {
        final List<String> expectedTitleTexts = List.of(
                "Authentication",
                "Markup Formatter",
                "Agents",
                "CSRF Protection",
                "Git plugin notifyCommit access tokens",
                "Git Hooks",
                "Hidden security warnings",
                "API Token",
                "SSH Server",
                "Git Host Key Verification Configuration");

        List<String> actualTitleTexts = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureGlobalSecurity()
                .getSectionTitleList();

        Assert.assertEquals(actualTitleTexts, expectedTitleTexts);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Checking the number of titles on the 'Configure Global Security' page")
    @Test
    public void testCheckNumberOfTitles() {
        final int expectedNumberOfTitles = 10;

        int actualNumberOfTitles = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureGlobalSecurity()
                .getNumberOfTitles();

        Assert.assertEquals(actualNumberOfTitles, expectedNumberOfTitles);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Checking the number of Help buttons on the 'Configure Global Security' page")
    @Test
    public void testCheckNumberOfHelpButton() {
        final int expectedNumberOfHelpButton = 15;

        int actualNumberOfHelpButton = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureGlobalSecurity()
                .getNumberOfHelpButtons();

        Assert.assertEquals(actualNumberOfHelpButton, expectedNumberOfHelpButton);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Checking Host Key Verification Strategy from dropdown menu")
    @Test
    public void testHostKeyVerificationStrategyDropdownMenuOptions() {
        final List<String> expectedMenuNames = List.of(
                "Accept first connection",
                "Known hosts file",
                "Manually provided keys",
                "No verification");

        List<String> actualMenuNames = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureGlobalSecurity()
                .navigateToHostKeyVerificationStrategyDropdownAndClick()
                .getDropDownMenuTexts();

        Assert.assertEquals(actualMenuNames, expectedMenuNames);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking if 'API' checkboxes are clickable")
    @Test
    public void testAPICheckboxesAreClickable() {
        boolean allChecksAreOk = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureGlobalSecurity()
                .checkAPITokenCheckboxes();

        Assert.assertTrue(allChecksAreOk);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking if radio buttons are clickable")
    @Test
    public void testRadioButtonsAreClickable() {
        boolean allChecksAreOk = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureGlobalSecurity()
                .checkRadioButtons();

        Assert.assertTrue(allChecksAreOk);
    }


    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking that the notification is saved and visible if you click 'Apply' ")
    @Test
    public void testSavedNotificationAppearsWhenClickApply() {

        String savedNotificationText = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickConfigureGlobalSecurity()
                .clickApplyButton()
                .getSavedNotificationText();

        Assert.assertEquals(savedNotificationText, "Saved");
    }
}
