package school.redrover.model.base.baseConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseProjectPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseConfigFreestyleAndMulticonfigProjectsPage <Self extends BaseConfigProjectsPage<?, ?>, ProjectPage extends BaseProjectPage<?>> extends BaseConfigProjectsPage<Self, ProjectPage> {

    @FindBy(xpath = "(//button[contains(text(),'Advanced')])[3]")
    private WebElement advancedDropdownMenu;

    @FindBy(xpath = "//label[text()='Quiet period']")
    private WebElement quietPeriod;

    @FindBy(xpath = "//input[@name='quiet_period']")
    private WebElement inputQuietPeriod;

    @FindBy(xpath = "//label[text()='Retry Count']")
    private WebElement retryCount;

    @FindBy(xpath = "//input[@name='scmCheckoutRetryCount']")
    private WebElement checkoutRetryCountSCM;

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

    @FindBy(xpath = "//button[contains(text(), 'Add build step')]")
    private WebElement addBuildStepButton;

    @FindBy(xpath = "//a[text()='Execute Windows batch command']")
    private WebElement executeWindowsBatchCommandButton;

    @FindBy(xpath = "//textarea[@name='command']")
    private WebElement windowsBatchCommandTextField;

    @FindBy(xpath = "//a[contains(text(), 'Execute shell')]")
    private WebElement executeShell;

    @FindBy(xpath = "//input[@name='_.concurrentBuild']")
    private WebElement checkBoxExecuteConcurrentBuilds;

    @FindBy(xpath = "//label[text()='Execute concurrent builds if necessary']")
    private WebElement checkBoxExecuteConcurrentBuildsWithText;

    @FindBy(xpath = "//a[text()='Invoke top-level Maven targets']")
    private WebElement invokeMavenTargetsButton;

    @FindBy(xpath = "//input[@id='textarea._.targets']")
    private WebElement goalsField;

    @FindBy(xpath = "//button[text()='Add build step']/../../..//a")
    private List<WebElement> optionsInBuildStepDropdown;

    @FindBy(xpath = "//button[@data-section-id='post-build-actions']")
    private WebElement postBuildActionsButton;

    @FindBy(xpath = "//button[contains(text(), 'Add post-build action')]")
    private WebElement addPostBuildActionButton;

    @FindBy(xpath = "//a[text()='Aggregate downstream test results']")
    private WebElement aggregateDownstreamTestResultsType;

    @FindBy(xpath = "//a[text()='Editable Email Notification']")
    private WebElement editableEmailNotificationType;

    @FindBy(xpath = "//textarea[@name='project_recipient_list']")
    private WebElement projectRecipientListInputField;

    @FindBy(xpath = "//a[text()='Archive the artifacts']")
    private WebElement archiveTheArtifacts;

    @FindBy(xpath = "//div[@descriptorid = 'hudson.tasks.ArtifactArchiver']")
    private WebElement archiveArtifacts;

    @FindBy(xpath = "//a[text()= 'Build other projects']")
    private WebElement buildOtherProjectsType;

    @FindBy(xpath = "//input[@name='buildTrigger.childProjects']")
    private WebElement buildOtherProjectsInputField;

    @FindBy(xpath = "//a[contains(text(),'Git Publisher')]")
    private WebElement gitPublisher;

    @FindBy(xpath = "//div[text()='Tags']")
    private WebElement textTagsFromPostBuildActions;

    @FindBy(xpath = "//div[contains(text(), 'Git Publisher')]")
    private WebElement gitPublisherHandle;

    @FindBy(xpath = "//a[text()='E-mail Notification']")
    private WebElement emailNotificationType;

    @FindBy(xpath = "//input[@name='_.recipients']")
    private WebElement emailNotificationInputField;

    @FindBy(xpath = "//a[text()='Set GitHub commit status (universal)']")
    private WebElement gitHubCommitStatusType;

    @FindBy(xpath = "//div[contains(text(), 'Commit context')]//following-sibling::div/select")
    private WebElement commitContextSelect;

    @FindBy(xpath = "//input[@name='_.context']")
    private WebElement contextNameField;

    @FindBy(xpath = "//a[text()='Delete workspace when build is done']")
    private WebElement deleteWorkspaceType;

    @FindBy(xpath = "//*[text()='Use custom workspace']")
    private WebElement useCustomWorkspace;

    @FindBy(xpath = "//input[@name='_.customWorkspace']")
    private WebElement useDirectoryName;

    @FindBy(xpath = "//*[@id='source-code-management']")
    private WebElement soursCodeManagement;

    @FindBy(xpath = "//div[text()='Additional Behaviours']")
    private WebElement additionalBehavioursText;

    public BaseConfigFreestyleAndMulticonfigProjectsPage(ProjectPage projectPage) {
        super(projectPage);
    }

    public Self clickAdvancedDropdownMenu() {
        getWait10().until(ExpectedConditions.elementToBeClickable(advancedDropdownMenu)).click();
        TestUtils.scrollToElementByJavaScript(this, advancedDropdownMenu);

        return (Self) this;
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

    public Self clickBlockBuildWhenUpstreamProjectIsBuilding() {
        blockBuildWhenUpstreamProjectIsBuilding.click();

        return (Self) this;
    }

    public Self clickSourceCodeManagementLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(sourceCodeManagementLink)).click();

        return (Self) this;
    }

    public Self clickRadioButtonGit() {
        TestUtils.clickByJavaScript(this, radioButtonGit);

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
        TestUtils.scrollWithPauseByActions(this, additionalBehavioursText, 300);
        getWait15().until(ExpectedConditions.visibilityOf(mainBranchInput)).clear();
        mainBranchInput.sendKeys("*/main");

        return (Self) this;
    }

    public Self clickAddBranchButton() {
        TestUtils.scrollWithPauseByActions(this, additionalBehavioursText, 300);
        getWait5().until(ExpectedConditions.visibilityOf(addBranchButton)).click();

        return (Self) this;
    }

    public Self inputAddBranchName(String additionalBranchName) {
        getWait5().until(ExpectedConditions.visibilityOf(additionalBranchInput)).clear();
        additionalBranchInput.sendKeys("*/" + additionalBranchName);

        return (Self) this;
    }

    public Self openBuildStepOptionsDropdown() {
        TestUtils.scrollToElementByJavaScript(this, addBuildStepButton);
        getWait5().until(ExpectedConditions.elementToBeClickable(addBuildStepButton)).click();

        return (Self) this;
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

    public Self addExecuteShellBuildStep(String command) {
        getWait5().until(ExpectedConditions.elementToBeClickable(addBuildStepButton));
        Actions actions = new Actions(getDriver());
        actions.scrollToElement(addPostBuildActionButton).click().perform();
        getWait2().until(ExpectedConditions.elementToBeClickable(addBuildStepButton)).click();
        executeShell.click();
        WebElement codeMirror = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("CodeMirror")));
        actions.scrollToElement(addPostBuildActionButton).click().perform();
        WebElement codeLine = codeMirror.findElements(By.className("CodeMirror-lines")).get(0);
        codeLine.click();
        WebElement commandField = codeMirror.findElement(By.cssSelector("textarea"));
        commandField.sendKeys(command);

        return (Self) this;
    }

    public Self  clickAddPostBuildActionDropDown() {
        getHeader().scrollToFooter();
        getWait2().until(ExpectedConditions.elementToBeClickable(addPostBuildActionButton)).click();
        return (Self) this;
    }

    public Self clickCheckBoxExecuteConcurrentBuilds() {
        checkBoxExecuteConcurrentBuildsWithText.click();

        return (Self) this;
    }

    public boolean isExecuteConcurrentBuildsSelected(){
        return checkBoxExecuteConcurrentBuilds.isSelected();
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

    public List<String> getOptionsInBuildStepDropdown() {
        return TestUtils.getTexts(optionsInBuildStepDropdown);
    }

    public Self clickPostBuildActionsButton() {
        postBuildActionsButton.click();

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

    public Self clickArchiveTheArtifacts() {
        archiveTheArtifacts.click();

        return  (Self) this;
    }

    public String getTextArchiveArtifacts() {
        return archiveArtifacts.getText();
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

    public Self clickGitPublisher() {
        getHeader().scrollToFooter();
        gitPublisher.click();

        return (Self) this;
    }

    public String getGitPublisherText() {
        TestUtils.scrollWithPauseByActions(this, textTagsFromPostBuildActions, 300);

        return gitPublisherHandle.getText();
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

    public Self clickDeleteWorkspaceWhenBuildDone() {
        getHeader().scrollToFooter();
        deleteWorkspaceType.click();

        return (Self) this;
    }
    public Self clickAdvancedGeneral() {
        TestUtils.scrollWithPauseByActions(this, soursCodeManagement, 100);
        advancedDropdownMenu.click();

        return (Self) this;
    }
    public Self clickUseCustomWorkspace(String directoryName) {
        TestUtils.scrollWithPauseByActions(this, soursCodeManagement, 100);
        useCustomWorkspace.click();
        useDirectoryName.sendKeys(directoryName);

        return (Self) this;
    }
}