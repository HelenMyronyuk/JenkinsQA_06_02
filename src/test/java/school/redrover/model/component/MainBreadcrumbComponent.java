package school.redrover.model.component;

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

    public MainBreadcrumbComponent(Page page) {
        super(page);
    }

    public MainPage clickDashboardButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(dashboard)).click();

        return new MainPage(getDriver());
    }

    public MainBreadcrumbComponent<Page> getDashboardDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(dashboard)
                .pause(Duration.ofMillis(300))
                .perform();
        getWait2().until(ExpectedConditions.visibilityOf(dashboardButton)).sendKeys(Keys.RETURN);

        return this;
    }

    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage getPageFromDashboardDropdownMenu(String listItemName, ReturnedPage pageToReturn) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//li/a/span[contains(text(), '" + listItemName + "')]"))).click();
            if (listItemName.contains("Delete Pipeline"))
                 {clickOkOnPopUp();}

        return pageToReturn;
    }

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

    public String getFullBreadcrumbText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOf(fullBreadcrumb))
                .getText()
                .replaceAll("\\n", " > ")
                .trim();
    }

    public List<String> getMenuList() {
        List<String> menuList = new ArrayList<>();
        for (WebElement el : dropDownMenu) {
            menuList.add(el.getAttribute("innerText"));
        }

        return menuList;
    }

    public void clickOkOnPopUp() {
        getDriver()
                .switchTo()
                .alert()
                .accept();
    }

    public <JobPage extends BasePage<?, ?>> JobPage clickJobNameFromBreadcrumb (String jobName, JobPage jobPage) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//a[@href='/job/%s/']", jobName)))).click();

        return jobPage;
    }

    public MainBreadcrumbComponent<Page> getJobBreadcrumbDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(jobNameBreadcrumb)
                .pause(Duration.ofMillis(300))
                .perform();

        getWait2().until(ExpectedConditions.visibilityOf(jobBreadcrumbChevron)).sendKeys(Keys.RETURN);

        return this;
    }

    public <JobPage extends BasePage<?, ?>> JobPage clickProjectNameFromAllButtonDropDownMenu(JobPage jobPage, String jobName) {
        getWait2().until(ExpectedConditions.elementToBeClickable(allButtonDropDownMenu)).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + jobName + "']"))).click();

        return jobPage;
    }

    public MainBreadcrumbComponent<Page> getUserBreadcrumbDropdownMenu() {
        getWait2().until(ExpectedConditions.visibilityOf(jobBreadcrumbChevron)).sendKeys(Keys.RETURN);

        return this;
    }

    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickPageFromUserBreadcrumbDropdownMenu(
            String listItemName, ReturnedPage pageToReturn, String userName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@id='breadcrumb-menu']//a[@href='/user/" + userName + "/" + listItemName + "']")))
                .click();

        return pageToReturn;
    }

    public MainBreadcrumbComponent<Page> getLastBuildBreadcrumbDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(lastBuildBreadcrumbButton)
                .pause(Duration.ofMillis(500))
                .perform();

        getWait2().until(ExpectedConditions.visibilityOf(lastBuildChevron)).sendKeys(Keys.RETURN);

        return this;
    }

    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickDeleteFromLastBuildDropDownMenu(
            ReturnedPage pageToReturn) {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteBuildLastBuildDropDownButton)).click();

        return pageToReturn;
    }

    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickBuildNowFromDashboardDropdownMenu(
            ReturnedPage pageToReturn) {
        getWait2().until(ExpectedConditions.elementToBeClickable(buildNowDropDownButton)).click();

        return pageToReturn;
    }
}
