package school.redrover.model.views;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.model.interfaces.IDescription;
import school.redrover.runner.TestUtils;

public class MyViewConfigPage extends BaseConfigPage<MyViewConfigPage, ViewPage> implements IDescription<MyViewConfigPage> {

    @FindBy(xpath = "//input[@name = 'name']")
    private WebElement nameView;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement submitView;

    @FindBy(xpath = "//label[text()='Filter build queue']")
    private WebElement filterQueueLabel;

    @FindBy(xpath = "//label[text()='Filter build executors']")
    private WebElement filterExecutorsLabel;

    @FindBy(xpath = "//input[@name='filterQueue']")
    private WebElement filterQueueCheckBox;

    @FindBy(xpath = "//input[@name='filterExecutors']")
    private WebElement filterExecutorsCheckBox;

    public MyViewConfigPage(ViewPage viewPage) {
        super(viewPage);
    }

    @Step("Edit MyView's name and click 'Submit' ")
    public MyViewsPage editMyViewNameAndClickSubmitButton(String editedMyViewName) {
        TestUtils.sendTextToInput(this, nameView, editedMyViewName);
        getWait5().until(ExpectedConditions.elementToBeClickable(submitView)).click();

        return new MyViewsPage(getDriver());
    }

    @Step("Click to Filter Queue check box")
    public MyViewConfigPage clickFilterQueueCheckBox() {
        getWait5().until(ExpectedConditions.elementToBeClickable(filterQueueLabel)).click();

        return this;
    }

    @Step("Click to Filter Executors check box")
    public MyViewConfigPage clickFilterExecutorsCheckBox() {
        getWait5().until(ExpectedConditions.elementToBeClickable(filterExecutorsLabel)).click();

        return this;
    }

    @Step("Verification the filters check boxes are checked")
    public boolean getFiltersCheckBoxAttribute() {
        String filterQueueCheckBoxAttribute = getWait5().until(ExpectedConditions.visibilityOf(filterQueueCheckBox)).getAttribute("checked");
        String filterExecutorsCheckBoxAttribute = getWait5().until(ExpectedConditions.visibilityOf(filterExecutorsCheckBox)).getAttribute("checked");

        return filterQueueCheckBoxAttribute.equals("true") && filterExecutorsCheckBoxAttribute.equals("true");
    }
}