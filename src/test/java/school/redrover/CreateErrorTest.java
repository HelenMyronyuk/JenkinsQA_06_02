package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.CreateItemErrorPage;
import school.redrover.model.MainPage;
import school.redrover.model.NewJobPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class CreateErrorTest extends BaseTest {

    @DataProvider(name = "job-type")
    public Object[][] jobType() {
        return new Object[][]{
                {TestUtils.JobType.FreestyleProject},
                {TestUtils.JobType.Pipeline},
                {TestUtils.JobType.MultiConfigurationProject},
                {TestUtils.JobType.Folder},
                {TestUtils.JobType.MultibranchPipeline},
                {TestUtils.JobType.OrganizationFolder}
        };
    }

    @DataProvider(name = "invalid-characters")
    public Object[][] getInvalidCharacters() {
        return new Object[][]{{"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {">"}, {"["}, {"]"}};
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating all types of project with existing name")
    @Test(dataProvider = "job-type")
    public void testCreateWithExistingName(TestUtils.JobType jobType) {
        final String jobName = "PROJECT_NAME";

        TestUtils.createJob(this, jobName, jobType, true);

        CreateItemErrorPage errorPage =
                TestUtils.createJobWithExistingName(this, jobName, jobType);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(),
                String.format("A job already exists with the name ‘%s’", jobName));
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating all types of project with name using unsafe characters")
    @Test(dataProvider = "invalid-characters")
    public void testCreateUsingInvalidData(String character) {
        String invalidMessage = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(character)
                .getItemInvalidMessage();

        Assert.assertEquals(invalidMessage, "» ‘" + character + "’ is an unsafe character");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verification of showing error message while creating all types of project with empty name")
    @Test(dataProvider = "job-type")
    public void testCreateWithEmptyName(TestUtils.JobType jobType) {
        final String expectedError = "» This field cannot be empty, please enter a valid name";

        String actualError = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .selectJobType(jobType)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(actualError, expectedError);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verification of showing error message after creating all types of project with space instead of name")
    @Test(dataProvider = "job-type")
    public void testCreateWithSpaceInsteadOfName(TestUtils.JobType jobType) {
        CreateItemErrorPage errorPage = TestUtils.createJobWithSpaceInsteadName(this, jobType);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "No name is specified");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verification of showing error message after creating all types of project with dot instead of name")
    @Test(dataProvider = "job-type")
    public void testCreateWithDotInsteadOfName(TestUtils.JobType jobType) {
        NewJobPage newJobPage = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(".")
                .selectJobType(jobType);

        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» “.” is not an allowed name");
        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "The OK button is enabled");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verification of showing error message after creating all types of project with long name")
    @Test(dataProvider = "job-type")
    public void testCreateWithLongName(TestUtils.JobType jobType) {
        String longName = RandomStringUtils.randomAlphanumeric(256);

        String errorMessage = new MainPage(getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(longName)
                .selectJobAndOkAndGoToBugPage(jobType)
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Checking that the OK button is disabled if the all types of project name has not been entered")
    @Test(dataProvider = "job-type")
    public void testOKButtonIsDisabledWhenEmptyName(TestUtils.JobType jobType) {
        boolean okButton = new MainPage(getDriver())
                .clickCreateAJobAndArrow()
                .selectJobType(jobType)
                .isOkButtonEnabled();

        Assert.assertFalse(okButton, "The OK button is enabled");
    }
}
