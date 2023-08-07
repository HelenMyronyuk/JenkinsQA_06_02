package school.redrover.model.views;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.DeletePage;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.runner.TestUtils;
import school.redrover.model.interfaces.IDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListViewConfigPage extends BaseConfigPage<ListViewConfigPage, ViewPage> implements IDescription<ListViewConfigPage> {

    @FindBy(xpath = "//div[@class = 'listview-jobs']/span")
    private List<WebElement> viewJobList;

    @FindBy(xpath = "//label[contains(text(), 'Recurse in subfolders')]")
    private WebElement recurseCheckbox;

    @FindBy(id = "yui-gen1-button")
    private WebElement addJobFilter;

    @FindBy(xpath = "//a[@tooltip='Help for feature: Description']")
    private WebElement helpForFeatureDescription;

    @FindBy(xpath = "//div[@class='help-area tr']/div/div")
    private WebElement textHelpDescription;

    @FindBy(xpath = "//span[text()='Delete View']/..")
    private WebElement deleteViewLink;

    @FindBy(xpath = "//div[contains(text(), 'Columns')]")
    private WebElement columnsText;

    @FindBy(xpath = "//button[text()='Add column']")
    private WebElement addColumnButton;

    @FindBy(xpath = "//footer")
    private WebElement footer;

    @FindBy(xpath = "//div[@class='bd']//li")
    private List<WebElement> addColumnOptions;

    @FindBy(xpath = "//div[@class='help-sibling']")
    private List<WebElement> activeColumnList;

    public ListViewConfigPage(ViewPage viewPage) {
        super(viewPage);
    }

    @Step("Select Jobs name '{name}' in 'Job Filters' ")
    public ListViewConfigPage selectJobsInJobFilters(String name) {
        TestUtils.scrollWithPauseByActions(this, columnsText, 500);

        for (WebElement el : viewJobList) {
            if (Objects.equals(el.getText(), name)) {
                el.click();
            }
        }

        return this;
    }

    @Step("Select and click checkBox 'Recurse in subfolders'")
    public ListViewConfigPage selectRecurseCheckbox() {
        getWait2().until(ExpectedConditions.elementToBeClickable(recurseCheckbox)).click();

        return this;
    }

    @Step("Scroll option 'Add Job Filter'")
    public ListViewConfigPage scrollToAddJobFilterDropDown() {
        TestUtils.scrollWithPauseByActions(this, addJobFilter, 100);

        return this;
    }

    @Step("Click on the '?' icon of field 'Description'")
    public ListViewConfigPage clickHelpForFeatureDescription() {
        helpForFeatureDescription.click();

        return this;
    }

    @Step("Get text from '?' icon of field 'Description'")
    public String getTextHelpFeatureDescription() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(textHelpDescription)).getText();
    }

    @Step("Click on the 'Delete View' button on side menu")
    public <RedirectPage extends BasePage<?,?>> DeletePage<RedirectPage> clickDeleteView(RedirectPage redirectPage) {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteViewLink)).click();

        return new DeletePage<>(redirectPage);
    }

    public ListViewConfigPage scrollAndClickAddColumnButton() {
        TestUtils.scrollWithPauseByActions(this, footer, 300);

        getWait5().until(ExpectedConditions.elementToBeClickable(addColumnButton)).click();
        return this;
    }

    public List<String> getAddColumnOptionList() {
        List<String> addColumnOptionList = new ArrayList<>();
        for (WebElement element : addColumnOptions) {
            addColumnOptionList.add(element.getText());
        }
        return addColumnOptionList;
    }

    private List<String> getActiveColumnOptionList() {
        List<String> activeColumnOptionList = new ArrayList<>();

        for (WebElement element : activeColumnList) {
            activeColumnOptionList.add(element.getText());
        }

        return activeColumnOptionList;
    }

    public ListViewConfigPage deleteColumn(String columnName) {
        TestUtils.scrollWithPauseByActions(this, addColumnButton, 1000);

        new Actions(getDriver())
                .click(getDriver().findElement(By.xpath("//div[contains(text(), '" + columnName + "')]//button")))
                .pause(600)
                .perform();

        return this;
    }

    public boolean isColumnDeleted(String columnName) {
        TestUtils.scrollWithPauseByActions(this, addColumnButton, 1000);

        return getActiveColumnOptionList().contains(columnName);
    }
}
