package school.redrover.model.interfaces;

import io.qameta.allure.Step;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.IBasePage;

public interface IAlert  <Self extends BaseMainHeaderPage<?>> extends IBasePage {

    @Step("Get the text of the alert window")
    default String getAlertBoxText(){
        return getDriver().switchTo().alert().getText();
    }

    @Step("Click the 'OK' button in the alert window")
    default Self acceptAlert() {
        getDriver().switchTo().alert().accept();

        return (Self) this;
    }

    @Step("Click the 'Cancel' button in the alert window")
    default Self dismissAlert() {
        getDriver().switchTo().alert().dismiss();

        return (Self) this;
    }
}
