package school.redrover.model;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.runner.TestUtils;

public class MyViewConfigPage extends BaseConfigPage<MyViewConfigPage, ViewPage> implements IDescription<MyViewConfigPage> {

    @FindBy(xpath = "//input[@name = 'name']")
    private WebElement nameView;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement submitView;

    public MyViewConfigPage(ViewPage viewPage) {
        super(viewPage);
    }

    public MyViewsPage editMyViewNameAndClickSubmitButton(String editedMyViewName) {
        TestUtils.sendTextToInput(this, nameView, editedMyViewName);
        TestUtils.click(this, submitView);

        return new MyViewsPage(getDriver());
    }
}