package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.jobsconfig.FreestyleProjectConfigPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseConfigProjectsPage<Self extends BaseConfigPage<?, ?>, ProjectPage extends BaseMainHeaderPage<?>> extends BaseConfigPage<Self, ProjectPage> {

    @FindBy(xpath = "//label[normalize-space(text())='Throttle builds']")
    private WebElement throttleBuilds;

    @FindBy(xpath = "//button[contains(text(), 'Add build step')]")
    private WebElement addBuildStepButton;

    @FindBy(xpath = "//a[text()='Invoke top-level Maven targets']")
    private WebElement invokeMavenTargetsButton;

    @FindBy(xpath = "//a[text()='Execute Windows batch command']")
    private WebElement executeWindowsBatchCommandButton;

    @FindBy(xpath = "//textarea[@name='command']")
    private WebElement windowsBatchCommandTextField;

    @FindBy(xpath = "//input[@id='textarea._.targets']")
    private WebElement goalsField;

    @FindBy(xpath = "//button[@data-section-id='post-build-actions']")
    private WebElement postBuildActionsButton;

    @FindBy(xpath = "//button[contains(text(), 'Add post-build action')]")
    private WebElement addPostBuildAction;

    @FindBy(xpath = "//a[text()='Set GitHub commit status (universal)']")
    private WebElement gitHubCommitStatusType;

    @FindBy(xpath = "//div[contains(text(), 'Commit context')]//following-sibling::div/select")
    private WebElement commitContextSelect;

    @FindBy(xpath = "//input[@name='_.context']")
    private WebElement contextNameField;

    @FindBy(xpath = "//a[contains(text(),'Git Publisher')]")
    private WebElement gitPublisher;

    @FindBy(xpath = "//div[text()='Tags']")
    private WebElement textTagsFromPostBuildActions;

    @FindBy(xpath = "//div[contains(text(), 'Git Publisher')]")
    private WebElement gitPublisherHandle;

    @FindBy(xpath = "//a[text()='Delete workspace when build is done']")
    private WebElement deleteWorkspaceType;

    @FindBy(xpath = "//a[text()='Aggregate downstream test results']")
    private WebElement aggregateDownstreamTestResultsType;

    @FindBy(xpath = "//a[text()='Editable Email Notification']")
    private WebElement editableEmailNotificationType;

    @FindBy(xpath = "//textarea[@name='project_recipient_list']")
    private WebElement projectRecipientListInputField;

    @FindBy(xpath = "//a[text()='E-mail Notification']")
    private WebElement emailNotificationType;

    @FindBy(xpath = "//input[@name='_.recipients']")
    private WebElement emailNotificationInputField;

    @FindBy(xpath = "//a[text()= 'Build other projects']")
    private WebElement buildOtherProjectsType;

    @FindBy(xpath = "//input[@name='buildTrigger.childProjects']")
    private WebElement buildOtherProjectsInputField;

    @FindBy(xpath = "//a[text()='Archive the artifacts']")
    private WebElement archiveTheArtifacts;

    @FindBy(xpath = "//div[@descriptorid = 'hudson.tasks.ArtifactArchiver']")
    private WebElement archiveArtifacts;

    @FindBy(xpath = "//a[contains(text(), 'Execute shell')]")
    private WebElement executeShell;

    @FindBy(xpath = "//label[text()='Discard old builds']/../input")
    private WebElement oldBuildCheckBox;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuilds;

    @FindBy(xpath = "//div[text()='Days to keep builds']")
    private WebElement nameFieldDaysToKeepBuilds;

    @FindBy(xpath = "//input[@name='_.numToKeepStr']")
    private WebElement maxNumOfBuildsToKeepNumber;

    @FindBy(xpath = "//label[@for='enable-disable-project']")
    private WebElement enableDisableSwitch;

    @FindBy(xpath = "//span[text() = 'Enabled']")
    private WebElement enabledText;

    @FindBy(xpath = "//label[text()='GitHub project']")
    private WebElement checkBoxGitHubProject;

    @FindBy(css = "[name='_.projectUrlStr']")
    private WebElement inputLineProjectUrl;

    @FindBy(xpath = "//label[text()='This project is parameterized']")
    private WebElement projectIsParametrized;

    @FindBy(xpath = "//button[text()='Add Parameter']")
    private WebElement addParameterDropdown;

    @FindBy(xpath = "//button[text()='Add Parameter']/../../..//a")
    private List<WebElement> optionsOfAddParameterDropdown;

    @FindBy(xpath = "//input[@name='parameter.name']")
    private WebElement inputParameterName;

    @FindBy(xpath = "//textarea[@name='parameter.choices']")
    private WebElement inputParameterChoices;

    @FindBy(xpath = "//textarea[@name='parameter.description']")
    private WebElement inputParameterDescription;

    @FindBy(xpath = "//label[normalize-space(text())='Set by Default']")
    private WebElement checkboxSetByDefault;

    @FindBy(xpath = "//select[@name='_.durationName']")
    private WebElement selectTimePeriod;

    @FindBy(xpath = "//label[text()='Retry Count']")
    private WebElement retryCount;

    @FindBy(xpath = "//input[@name='scmCheckoutRetryCount']")
    private WebElement checkoutRetryCountSCM;

    @FindBy(xpath = "//label[text()='Quiet period']")
    private WebElement quietPeriod;

    @FindBy(xpath = "//input[@name='quiet_period']")
    private WebElement inputQuietPeriod;

    @FindBy(xpath = "//label[text()='Execute concurrent builds if necessary']")
    private WebElement checkBoxExecuteConcurrentBuilds;

    @FindBy(xpath = "//div[@ref='cb8']/following-sibling::div[2]")
    private WebElement trueExecuteConcurrentBuilds;

    @FindBy(xpath = "//label[text()='Block build when upstream project is building']")
    private WebElement blockBuildWhenUpstreamProjectIsBuilding;

    @FindBy(xpath = "//button[@data-section-id='source-code-management']")
    private WebElement sourceCodeManagementLink;

    @FindBy(xpath = "//input[@id='radio-block-1']")
    private WebElement radioButtonGit;

    @FindBy(xpath = "//input[@name='_.url']")
    private WebElement inputRepositoryUrl;

    @FindBy(xpath = "(//input[@default='*/master'])[1]")
    private WebElement mainBranchInput;

    @FindBy(xpath = "//button[text()='Add Branch']")
    private WebElement addBranchButton;

    @FindBy(xpath = "(//input[@default='*/master'])[2]")
    private WebElement additionalBranchInput;

    @FindBy(xpath = "//input[@name='jenkins-triggers-ReverseBuildTrigger']")
    private WebElement buildAfterOtherProjectsAreBuiltCheckBox;

    @FindBy(xpath = "//input[@name='_.upstreamProjects']")
    private WebElement projectsToWatchField;

    public BaseConfigProjectsPage(ProjectPage projectPage) {
        super(projectPage);
    }

    public Self clickPostBuildActionsButton() {
        postBuildActionsButton.click();
        return (Self) this;
    }

    public Self  clickAddPostBuildActionDropDown() {
        getHeader().scrollToFooter();
        getWait2().until(ExpectedConditions.elementToBeClickable(addPostBuildAction)).click();
        return (Self) this;
    }

    public Self clickSetGitHubCommitStatus() {
        getHeader().scrollToFooter();
        gitHubCommitStatusType.click();
        return (Self) this;
    }

    public Self setGitHubCommitStatusContext(String status) {
        getHeader().scrollToFooter();
        new Select(commitContextSelect).selectByVisibleText("Manually entered context name");
        getWait2().until(ExpectedConditions.elementToBeClickable(contextNameField)).sendKeys(status);
        return (Self) this;
    }

    public String getGitHubCommitStatus() {
        getHeader().scrollToFooter();
        return contextNameField.getAttribute("value");
    }

    public Self clickGitPublisher() {
        getHeader().scrollToFooter();
        gitPublisher.click();
        return (Self) this;
    }

    public String getGitPublisherText() {
        new Actions(getDriver())
                .scrollToElement(textTagsFromPostBuildActions)
                .pause(300)
                .perform();

        return gitPublisherHandle.getText();
    }

    public Self clickDeleteWorkspaceWhenBuildDone() {
        getHeader().scrollToFooter();
        deleteWorkspaceType.click();
        return (Self) this;
    }

    public Self clickAggregateDownstreamTestResults() {
        aggregateDownstreamTestResultsType.click();
        return (Self) this;
    }

    public Self selectEditableEmailNotification() {
        getWait2().until(ExpectedConditions.elementToBeClickable(editableEmailNotificationType)).click();
        return (Self) this;
    }

    public Self inputEmailIntoProjectRecipientListInputField(String email) {
        new Actions(getDriver()).moveToElement(projectRecipientListInputField);
        getWait2().until(ExpectedConditions.elementToBeClickable(projectRecipientListInputField)).clear();
        projectRecipientListInputField.sendKeys(email);
        return (Self) this;
    }

    public Self  clickEmailNotification() {
        emailNotificationType.click();
        return (Self) this;
    }

    public Self  setEmailNotification(String email) {
        getHeader().scrollToFooter();
        getWait2().until(ExpectedConditions.elementToBeClickable(emailNotificationInputField)).sendKeys(email);
        return (Self) this;
    }

    public String getEmailNotificationFieldText() {
        return getWait2().until(ExpectedConditions.visibilityOf(emailNotificationInputField)).getAttribute("value");
    }

    public Self clickBuildOtherProjects() {
        buildOtherProjectsType.click();
        return (Self) this;
    }

    public Self setBuildOtherProjects(String projectName) {
        getHeader().scrollToFooter();
        getWait2().until(ExpectedConditions.elementToBeClickable(buildOtherProjectsInputField)).sendKeys(projectName);
        return (Self) this;
    }

    public Self clickArchiveTheArtifacts() {
        archiveTheArtifacts.click();
        return  (Self) this;
    }

    public String getTextArchiveArtifacts() {
        return archiveArtifacts.getText();
    }

    public Self addExecuteShellBuildStep(String command) {
        getWait5().until(ExpectedConditions.elementToBeClickable(addBuildStepButton));
        Actions actions = new Actions(getDriver());
        actions.scrollToElement(addPostBuildAction).click().perform();
        getWait2().until(ExpectedConditions.elementToBeClickable(addBuildStepButton)).click();
        executeShell.click();
        WebElement codeMirror = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("CodeMirror")));
        actions.scrollToElement(addPostBuildAction).click().perform();
        WebElement codeLine = codeMirror.findElements(By.className("CodeMirror-lines")).get(0);
        codeLine.click();
        WebElement commandField = codeMirror.findElement(By.cssSelector("textarea"));
        commandField.sendKeys(command);

        return (Self) this;
    }

    public Self clickOldBuildCheckBox() {
        TestUtils.clickByJavaScript(this, oldBuildCheckBox);
        return (Self) this;
    }

    public Self enterDaysToKeepBuilds(int number) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", nameFieldDaysToKeepBuilds);
        TestUtils.sendTextToInput(this, daysToKeepBuilds, String.valueOf(number));

        return (Self) this;
    }

    public Self enterMaxNumOfBuildsToKeep(int number) {
        TestUtils.sendTextToInput(this, maxNumOfBuildsToKeepNumber, String.valueOf(number));

        return (Self) this;
    }

    public Self clickSwitchEnableOrDisable() {
        getWait2().until(ExpectedConditions.elementToBeClickable(enableDisableSwitch)).click();
        return (Self) this;
    }

    public Boolean isEnabledDisplayed() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(enabledText)).isDisplayed();
    }

    public String getDaysToKeepBuilds(String attribute) {
        return daysToKeepBuilds.getAttribute(attribute);
    }

    public String getMaxNumOfBuildsToKeep(String attribute) {
        return maxNumOfBuildsToKeepNumber.getAttribute(attribute);
    }

    public Self clickGitHubProjectCheckbox() {
        checkBoxGitHubProject.click();
        return (Self) this;
    }

    public Self inputTextTheInputAreaProjectUrlInGitHubProject(String text) {
        inputLineProjectUrl.sendKeys(text);
        return (Self) this;
    }

    public Self checkProjectIsParametrized() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", projectIsParametrized);
        return (Self) this;
    }

    public Self selectParameterInDropDownByType(String type) {
        getDriver().findElement(By.xpath(String.format("//li/a[text()='%s']", type))).click();
        return (Self) this;
    }

    public Self openAddParameterDropDown() {
        getWait5().until(ExpectedConditions.elementToBeClickable(addParameterDropdown));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        getWait5().until(ExpectedConditions.elementToBeClickable(projectIsParametrized));
        js.executeScript("arguments[0].scrollIntoView();", projectIsParametrized);
        addParameterDropdown.click();
        return (Self) this;
    }

    public List<String> getAllOptionsOfAddParameterDropdown() {
        return TestUtils.getTexts(optionsOfAddParameterDropdown);
    }

    public Self inputParameterName(String name) {
        inputParameterName.sendKeys(name);
        return (Self) this;
    }

    public Self inputParameterChoices(List<String> parameterChoices) {
        for (String element : parameterChoices) {
            inputParameterChoices.sendKeys(element + "\n");
        }
        return (Self) this;
    }

    public Self inputParameterDesc(String description) {
        inputParameterDescription.sendKeys(description);
        return (Self) this;
    }

    public Self selectCheckboxSetByDefault() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", checkboxSetByDefault);
        return (Self) this;
    }

    public Self openBuildStepOptionsDropdown() {
        TestUtils.scrollToElementByJavaScript(this, addBuildStepButton);
        getWait5().until(ExpectedConditions.elementToBeClickable(addBuildStepButton)).click();
        return (Self) this;
    }

    public Self addInvokeMavenGoalsTargets(String goals) {
        new Actions(getDriver())
                .moveToElement(invokeMavenTargetsButton)
                .perform();

        getWait5().until(ExpectedConditions.visibilityOf(invokeMavenTargetsButton)).click();
        goalsField.sendKeys(goals);

        return (Self) this;
    }

    public String getMavenGoals() {
        return goalsField.getAttribute("value");
    }

    public Self selectExecuteWindowsBatchCommandBuildStep() {
        new Actions(getDriver())
                .moveToElement(executeWindowsBatchCommandButton)
                .perform();

        getWait5().until(ExpectedConditions.visibilityOf(executeWindowsBatchCommandButton)).click();

        return (Self) this;
    }

    public Self addExecuteWindowsBatchCommand(String command) {
        windowsBatchCommandTextField.sendKeys(command);

        return (Self) this;
    }


    public List<String> getOptionsInBuildStepDropdown() {
        return TestUtils.getTexts(getDriver().findElements(By.xpath("//button[text()='Add build step']/../../..//a")));
    }

    public Self checkThrottleBuilds() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", throttleBuilds);
        js.executeScript("arguments[0].click();", throttleBuilds);
        return (Self) this;
    }

    public Self selectTimePeriod(String timePeriod) {
        new Select(selectTimePeriod).selectByValue(timePeriod.toLowerCase());
        return (Self) this;
    }

    public String getTimePeriodText() {
        return new Select(selectTimePeriod).getFirstSelectedOption().getText();
    }

    public Self clickQuietPeriod() {
        quietPeriod.click();
        return (Self) this;
    }

    public Self inputQuietPeriod(String number) {
        inputQuietPeriod.clear();
        inputQuietPeriod.sendKeys(number);
        return (Self) this;
    }

    public String getQuietPeriod() {
        return inputQuietPeriod.getAttribute("value");
    }

    public Self clickRetryCount() {
        retryCount.click();
        return (Self) this;
    }

    public Self inputSCMCheckoutRetryCount(String count) {
        checkoutRetryCountSCM.clear();
        checkoutRetryCountSCM.sendKeys(count);
        return (Self) this;
    }

    public String getCheckoutRetryCountSCM() {
        return checkoutRetryCountSCM.getAttribute("value");
    }

    public Self clickCheckBoxExecuteConcurrentBuilds() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", throttleBuilds);
        checkBoxExecuteConcurrentBuilds.click();
        return (Self) this;
    }

    public WebElement getTrueExecuteConcurrentBuilds() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", throttleBuilds);
        return trueExecuteConcurrentBuilds;
    }

    public Self clickBlockBuildWhenUpstreamProjectIsBuilding() {
        blockBuildWhenUpstreamProjectIsBuilding.click();
        return (Self) this;
    }

    public Self clickSourceCodeManagementLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(sourceCodeManagementLink)).click();
        return (Self) this;
    }

    public Self clickRadioButtonGit() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", radioButtonGit);
        return (Self) this;
    }

    public Self inputRepositoryUrl(String text) {
        getWait5().until(ExpectedConditions.visibilityOf(inputRepositoryUrl)).sendKeys(text);
        return (Self) this;
    }

    public String getRepositoryUrlText() {
        return getWait5().until(ExpectedConditions.visibilityOf(inputRepositoryUrl)).getAttribute("value");
    }

    public Self correctMainBranchName() {
        getWait15().until(ExpectedConditions.visibilityOf(mainBranchInput)).clear();
        mainBranchInput.sendKeys("*/main");
        return (Self) this;
    }

    public Self clickAddBranchButton() {
        getWait5().until(ExpectedConditions.visibilityOf(addBranchButton)).click();
        return (Self) this;
    }

    public Self inputAddBranchName(String additionalBranchName) {
        getWait5().until(ExpectedConditions.visibilityOf(additionalBranchInput)).clear();
        additionalBranchInput.sendKeys("*/" + additionalBranchName);
        return (Self) this;
    }

    public Self clickBuildAfterOtherProjectsAreBuiltCheckBox() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", buildAfterOtherProjectsAreBuiltCheckBox);
        js.executeScript("arguments[0].click();", buildAfterOtherProjectsAreBuiltCheckBox);
        return (Self) this;
    }

    public Self inputProjectsToWatch(String projectName) {
        getWait5().until(ExpectedConditions.visibilityOf(projectsToWatchField)).sendKeys(projectName);
        return (Self) this;
    }
}
