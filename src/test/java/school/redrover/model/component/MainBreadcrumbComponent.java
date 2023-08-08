package school.redrover.model.component;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.MainPage;
import school.redrover.model.base.BaseComponent;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainBreadcrumbComponent<Page extends BasePage<?, ?>> extends BaseComponent<Page> {

    @FindBy(xpath = "//a[text()='Dashboard']")
    private WebElement dashboard;

    @FindBy(xpath = "//div[@id='breadcrumbBar']")
    private WebElement fullBreadcrumb;

    @FindBy(xpath = "//a[text()='Dashboard']/button")
    private WebElement dashboardButton;

    @FindBy(xpath = "//a[contains(span, 'Manage Jenkins')]")
    private WebElement manageJenkinsSubmenu;

    @FindBy(css = "#breadcrumb-menu>div:first-child>ul>li")
    private List<WebElement> dropDownMenu;

    @FindBy(xpath = "//*[@id='breadcrumbs']/li[3]/a/button")
    private WebElement jobBreadcrumbChevron;

    @FindBy(xpath = "//*[@id='breadcrumbs']/li[3]/a")
    private WebElement jobNameBreadcrumb;

    @FindBy(xpath = "//li[@href='/view/all/']")
    private WebElement allButtonDropDownMenu;

    @FindBy(xpath = "//a[contains(@href, '/user/')]")
    private WebElement userBreadcrumb;

    @FindBy(xpath = "//li[@class='jenkins-breadcrumbs__list-item']//a[contains(@href, 'lastBuild')]")
    private WebElement lastBuildBreadcrumbButton;

    @FindBy(xpath = "//a[contains(@href, 'lastBuild')]//button[@class='jenkins-menu-dropdown-chevron']")
    private WebElement lastBuildChevron;

    @FindBy(xpath = "//div[@class='bd']//span[contains(text(), 'Delete build')]")
    private WebElement deleteBuildLastBuildDropDownButton;

    @FindBy(xpath =  "//li/a/span[contains(text(), 'Build Now')]")
    private WebElement buildNowDropDownButton;

    @FindBy(xpath = "//a[@href='/manage/']")
    private WebElement manageJenkinsBreadcrumb;

    @FindBy(xpath = "//a[@href='/manage/']/button")
    private WebElement manageJenkinsDropDownButton;

    public MainBreadcrumbComponent(Page page) {
        super(page);
    }

    @Step("Click the 'Dashboard' button")
    public MainPage clickDashboardButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(dashboard)).click();

        return new MainPage(getDriver());
    }

    @Step("Get the 'Dashboard' drop-down menu")
    public MainBreadcrumbComponent<Page> getDashboardDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(dashboard)
                .pause(Duration.ofMillis(300))
                .perform();
        getWait2().until(ExpectedConditions.visibilityOf(dashboardButton)).sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Get a page form the 'Dashboard' drop-down menu")
    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage getPageFromDashboardDropdownMenu(String listItemName, ReturnedPage pageToReturn) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//li/a/span[contains(text(), '" + listItemName + "')]"))).click();
            if (listItemName.contains("Delete Pipeline"))
                 {clickOkOnPopUp();}

        return pageToReturn;
    }

    @Step("Select an option from 'Dashboard' -> 'Manage Jenkins' submenu list")
    public <SubmenuPage extends BaseSubmenuPage<?>> SubmenuPage selectAnOptionFromDashboardManageJenkinsSubmenuList(SubmenuPage submenuPage) {
        getDashboardDropdownMenu();
        new Actions(getDriver())
                .moveToElement(manageJenkinsSubmenu)
                .pause(500)
                .moveToElement(getDriver().findElement(By.xpath("//span[contains(text(), '" + submenuPage.callByMenuItemName() + "')]")))
                .click()
                .perform();

        return submenuPage;
    }

    @Step("Get the full breadcrumb text")
    public String getFullBreadcrumbText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOf(fullBreadcrumb))
                .getText()
                .replaceAll("\\n", " > ")
                .trim();
    }

    @Step("Get a page name from the breadcrumb")
    public String getPageNameFromBreadcrumb(){
        String fullBreadcrumbText = getFullBreadcrumbText();
        int index = fullBreadcrumbText.lastIndexOf(">");

        return fullBreadcrumbText.substring(index + 2);
    }

    @Step("Get the drop-down menu list")
    public List<String> getMenuList() {
        List<String> menuList = new ArrayList<>();
        for (WebElement el : dropDownMenu) {
            menuList.add(el.getAttribute("innerText"));
        }

        return menuList;
    }

    @Step("Click the 'OK' button for the pop-up")
    public void clickOkOnPopUp() {
        getDriver()
                .switchTo()
                .alert()
                .accept();
    }

    @Step("Click on a job name from the breadcrumb")
    public <JobPage extends BasePage<?, ?>> JobPage clickJobNameFromBreadcrumb (String jobName, JobPage jobPage) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//a[@href='/job/%s/']", jobName)))).click();

        return jobPage;
    }

    @Step("Get a job drop-down menu from the breadcrumb")
    public MainBreadcrumbComponent<Page> getJobBreadcrumbDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(jobNameBreadcrumb)
                .pause(Duration.ofMillis(300))
                .perform();

        getWait2().until(ExpectedConditions.visibilityOf(jobBreadcrumbChevron)).sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Click on a job name from the 'All' drop-down menu")
    public <JobPage extends BasePage<?, ?>> JobPage clickProjectNameFromAllButtonDropDownMenu(JobPage jobPage, String jobName) {
        getWait2().until(ExpectedConditions.elementToBeClickable(allButtonDropDownMenu)).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + jobName + "']"))).click();

        return jobPage;
    }

    @Step("Get the 'User' drop-down menu")
    public MainBreadcrumbComponent<Page> getUserBreadcrumbDropdownMenu() {
        getWait2().until(ExpectedConditions.visibilityOf(jobBreadcrumbChevron)).sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Click a page from 'User' drop-down")
    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickPageFromUserBreadcrumbDropdownMenu(
            String listItemName, ReturnedPage pageToReturn, String userName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@id='breadcrumb-menu']//a[@href='/user/" + userName + "/" + listItemName + "']")))
                .click();

        return pageToReturn;
    }


    @Step("Get 'Last Build' drop-down menu")
    public MainBreadcrumbComponent<Page> getLastBuildBreadcrumbDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(lastBuildBreadcrumbButton)
                .pause(Duration.ofMillis(500))
                .perform();

        getWait2().until(ExpectedConditions.visibilityOf(lastBuildChevron)).sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Click 'Delete' from 'Last Build' drop-down menu")
    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickDeleteFromLastBuildDropDownMenu(
            ReturnedPage pageToReturn) {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteBuildLastBuildDropDownButton)).click();

        return pageToReturn;
    }

    @Step("Click 'Build Now' from 'Dashboard' drop-down menu'")
    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickBuildNowFromDashboardDropdownMenu(
            ReturnedPage pageToReturn) {
        getWait2().until(ExpectedConditions.elementToBeClickable(buildNowDropDownButton)).click();

        return pageToReturn;
    }

    @Step("Get 'Manage Jenkins' drop-down menu")
    public MainBreadcrumbComponent<Page> getManageJenkinsDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(manageJenkinsBreadcrumb)
                .pause(Duration.ofMillis(300))
                .perform();
        getWait2().until(ExpectedConditions.visibilityOf(manageJenkinsDropDownButton)).sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Select an option from 'Manage Jenkins' drop-down menu")
    public <SubmenuPage extends BaseSubmenuPage<?>> SubmenuPage selectOptionFromManageJenkinsDropDownList(SubmenuPage submenuPage) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//span[contains(text(), '" + submenuPage.callByMenuItemName() + "')]")))
                .click()
                .perform();

        return submenuPage;
    }
}
