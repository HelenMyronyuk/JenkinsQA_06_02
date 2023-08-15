package school.redrover.model.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;
import school.redrover.model.base.IBasePage;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.model.builds.*;
import school.redrover.model.externalPages.GitHubTestRepo;
import school.redrover.runner.TestUtils;

import java.util.List;

public interface IDashboardTable <Self extends BaseMainHeaderPage<?>> extends IBasePage {

    default NewJobPage clickCreateAJobAndArrow() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='newJob']"))).click();

        return new NewJobPage(getDriver());
    }

    default Integer getJenkinsTableHeight() {
        return getWait5().until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.xpath("//table[@id='projectstatus']")))).getSize().height;
    }

    default String getJobBuildStatusIcon(String jobName) {
        return getDriver().findElement(By.id(String.format("job_%s", jobName))).findElement(
                        By.xpath("//span/span/*[name()='svg' and @class= 'svg-icon ']"))
                .getAttribute("tooltip");
    }

    default String getJobBuildStatusByWeatherIcon(String jobName) {
        WebElement buildStatus = getDriver().findElement(By.id(String.format("job_%s", jobName)))
                .findElement(By.xpath(".//*[contains(@href, 'build-status-static')]/.."));

        return buildStatus.getAttribute("tooltip");
    }

    default List<String> getJobList() {
        List<WebElement> jobList = getDriver().findElements(By.cssSelector(".jenkins-table__link"));

        return jobList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private WebElement getProjectStatusTable() {
        return getWait2().until(ExpectedConditions.presenceOfElementLocated(By.id("main-panel")))
                .findElement(By.id("projectstatus"));
    }

    default boolean projectStatusTableIsDisplayed() {
        return getProjectStatusTable().isDisplayed();
    }

    default Self clickSortByName() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Name')]"))).click();

        return (Self) this;
    }

    default boolean verifyJobIsPresent(String jobName) {
        List<WebElement> jobList = getDriver().findElements(By.cssSelector(".jenkins-table__link"));
        boolean status = false;
        for (WebElement job : jobList) {
            if (!job.getText().equals(jobName)) {
                status = false;
            } else {
                new Actions(getDriver()).moveToElement(job).build().perform();
                status = true;
                break;
            }
        }

        return status;
    }

    default <JobPage extends BasePage<?, ?>> JobPage clickJobName(String jobName, JobPage jobPage) {
        WebElement job = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//a[@href='job/%s/']", jobName.replaceAll(" ", "%20")))));
        new Actions(getDriver()).moveToElement(job).click(job).perform();

        return jobPage;
    }

    default boolean jobIsDisplayed(String jobName) {
        return getWait5().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath(String.format("//a[@href='job/%s/']", jobName.replaceAll(" ", "%20")))))).isDisplayed();
    }

    default boolean jobIsDisplayedF(String viewName) {
        try {

            return getDriver().findElement(By.linkText(viewName)).isDisplayed();
        } catch (Exception e) {

            return false;
        }
    }

    default String getJobName(String projectName) {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//tr[@id='job_" + projectName + "']//a//span['" + projectName + "']")))
                .getText();
    }

    default String getLastBuildIconStatus() {
        return getWait5().until(ExpectedConditions.visibilityOf(
                        getDriver().findElement(By.xpath("//span[@class='build-status-icon__outer']//*[name()='svg']"))))
                .getAttribute("title");
    }

    default Self hoverOverWeather(String jobName){
        WebElement weather = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//tr[@id = 'job_%s']/td[contains(@class,'healthReport')]", jobName))));
        new Actions(getDriver())
                .moveToElement(weather)
                .perform();

        return (Self)this;
    }

    default String getTooltipDescription(){
        return getWait10().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//div[@class='tippy-box']//td[@align='left' and not(contains(@class, 'jenkins-table__icon'))]")))).getText();
    }

    default boolean isIconFolderDisplayed() {
        return getDriver().findElement(By.cssSelector("svg[title='Folder']")).isDisplayed();
    }

    default Self openJobDropDownMenu(String jobName) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//a[contains(@href,'job/%s/')]/button", jobName
                                .replaceAll(" ", "%20")))))
                .sendKeys(Keys.RETURN);

        return (Self) this;
    }

    default List<String> getListOfProjectMenuItems(String jobName) {
        openJobDropDownMenu(jobName);

        return TestUtils.getTexts(getDriver().findElements(
                By.xpath("//div[@id = 'breadcrumb-menu' and @class = 'yui-module yui-overlay yuimenu visible']//li/a/span")));
    }

    default Self clickJobDropdownMenuBuildNow(String jobName) {
        openJobDropDownMenu(jobName);
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Build Now']"))).click();

        return (Self) this;
    }

    default <JobConfigPage extends BaseConfigPage<?, ?>> JobConfigPage clickConfigureDropDown(String jobName, JobConfigPage jobConfigPage) {
        openJobDropDownMenu(jobName);
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div//li//span[contains(text(),'Configure')]"))).click();

        return jobConfigPage;
    }

    default Self dropDownMenuClickDelete(String jobName) {
        openJobDropDownMenu(jobName);
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(), 'Delete') and not(contains(text(), 'View'))]"))).click();

        return (Self) this;
    }

    default DeletePage<Self> dropDownMenuClickDeleteFolders(String jobName) {
        dropDownMenuClickDelete(jobName);

        return new DeletePage<>((Self) this);
    }

    default <JobTypePage extends BasePage<?, ?>> MovePage<JobTypePage> dropDownMenuClickMove(String jobName, JobTypePage jobTypePage) {
        openJobDropDownMenu(jobName);
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(), 'Move')]"))).click();

        return new MovePage<>(jobTypePage);
    }

    default <JobTypePage extends BasePage<?, ?>> RenamePage<JobTypePage> dropDownMenuClickRename(String jobName, JobTypePage jobTypePage) {
        openJobDropDownMenu(jobName);
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li//span[contains(text(), 'Rename')]"))).click();

        return new RenamePage<>(jobTypePage);
    }

    default NewJobPage selectNewItemInJobDropDownMenu(String folderName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath(String.format("//a[contains(@href,'/job/%s/newJob')]", folderName)))).click();

        return new NewJobPage(getDriver());
    }

    default GitHubTestRepo selectGitHubFromJobDropdownMenu(String jobName) {
        openJobDropDownMenu(jobName);
        getDriver().findElement(By.xpath("//a[contains(@href, 'github.com')]")).click();

        return new GitHubTestRepo(getDriver());
    }

    default Self clickBuildByGreenArrow(String projectName) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='job/" + projectName + "/build?delay=0sec']"))).click();

        return (Self) this;
    }

    default <JobTypePage extends BaseProjectPage<?>> BuildWithParametersPage<JobTypePage> clickBuildButton(JobTypePage jobTypePage) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class='jenkins-table__cell--tight']"))).click();
        return new BuildWithParametersPage<>(jobTypePage);
    }

    default boolean isScheduleBuildOnDashboardAvailable(String jobName) {
        boolean status = false;

        List<WebElement> scheduleBuildLinks = getDriver().findElements(By.xpath("//a[contains(@tooltip,'Schedule a Build for ')]"));
        for (WebElement link : scheduleBuildLinks) {
            String tooltip = link.getAttribute("tooltip");
            if (jobName.equals(tooltip.substring(tooltip.length() - jobName.length()))) {
                status = true;
                break;
            }
        }

        return status;
    }

    default Self openLastBuildDropDownMenu() {
        getWait10().until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.xpath("//a[@class='jenkins-table__link jenkins-table__badge model-link inside']//button[@class='jenkins-menu-dropdown-chevron']"))))
                .sendKeys(Keys.RETURN);

        return (Self)this;
    }

    default Self openBuildDropDownMenu(String buildNumber) {
        WebElement dropDownBuildButton = getDriver().findElement(By.xpath("//td/a[text()='#1']/button"));
        Actions act = new Actions(getDriver());
        act.moveToElement(dropDownBuildButton).perform();
        dropDownBuildButton.sendKeys(Keys.RETURN);

        return (Self)this;
    }

    default ChangesBuildPage clickChangesBuildFromDropDown() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Changes')]"))).click();

        return new ChangesBuildPage(getDriver());
    }

    default ConsoleOutputPage clickConsoleOutputLastBuildDropDown() {
        getDriver().findElement(By.xpath("//span[text()= 'Console Output']")).click();

        return new ConsoleOutputPage(getDriver());
    }

    default EditBuildInformationPage clickEditBuildInformFromDropDown(){
        getDriver().findElement(By.xpath("//span[contains(text(),'Edit Build Information')]")).click();

        return new EditBuildInformationPage(getDriver());
    }

    default DeletePage<Self> clickBuildDropdownMenuDeleteBuild(String buildNumber) {
        openBuildDropDownMenu(buildNumber);
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'confirmDelete')]"))).click();

        return new DeletePage<>((Self)this);
    }

    default WorkspacesBuildPage clickWorkspacesLastBuildDropDown() {
        getDriver().findElement(By.xpath("//span[text()= 'Workspaces']")).click();

        return new WorkspacesBuildPage(getDriver());
    }

    default PipelineStepsPage clickBuildDropdownMenuPipelineSteps(String buildNumber) {
        openBuildDropDownMenu(buildNumber);
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//a[contains(@href, 'lastSuccessfulBuild')]/span[text()='Pipeline Steps']"))).click();

        return new PipelineStepsPage(getDriver());
    }
}
