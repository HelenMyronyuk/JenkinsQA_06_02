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

    public MyViewConfigPage(ViewPage viewPage) {
        super(viewPage);
    }

    @Step("Edit MyView's name and click 'Submit' ")
    public MyViewsPage editMyViewNameAndClickSubmitButton(String editedMyViewName) {
        TestUtils.sendTextToInput(this, nameView, editedMyViewName);
        getWait5().until(ExpectedConditions.elementToBeClickable(submitView)).click();

        return new MyViewsPage(getDriver());
    }
}