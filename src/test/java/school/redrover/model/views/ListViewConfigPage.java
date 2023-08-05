package school.redrover.model.views;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.DeletePage;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.runner.TestUtils;
import school.redrover.model.interfaces.IDescription;

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

    public ListViewConfigPage(ViewPage viewPage) {
        super(viewPage);
    }

    public ListViewConfigPage selectJobsInJobFilters(String name) {

        for (WebElement el : viewJobList) {
            if (Objects.equals(el.getText(), name)) {
                el.click();
            }
        }

        return this;
    }

    public ListViewConfigPage selectRecurseCheckbox() {
        getWait2().until(ExpectedConditions.elementToBeClickable(recurseCheckbox)).click();

        return this;
    }

    public ListViewConfigPage scrollToAddJobFilterDropDown() {
        TestUtils.scrollWithPauseByActions(this, addJobFilter, 100);

        return this;
    }

    public ListViewConfigPage clickHelpForFeatureDescription() {
        helpForFeatureDescription.click();

        return this;
    }

    public String getTextHelpFeatureDescription() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(textHelpDescription)).getText();
    }

    public <RedirectPage extends BasePage<?,?>> DeletePage<RedirectPage> clickDeleteView(RedirectPage redirectPage) {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteViewLink)).click();

        return new DeletePage<>(redirectPage);
    }
}