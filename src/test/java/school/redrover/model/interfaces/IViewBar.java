package school.redrover.model.interfaces;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.views.NewViewPage;
import school.redrover.model.base.BaseDashboardPage;
import school.redrover.model.base.IBasePage;

import java.util.ArrayList;
import java.util.List;

public interface IViewBar extends IBasePage {

    @Step("Click to create a new View")
    default NewViewPage createNewView() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'/newView')]"))).click();

        return new NewViewPage(getDriver());
    }

    @Step("Get name of the active View")
    default String getActiveViewName() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'tab active']"))).getText();
    }

    @Step("Click on a View")
    default  <ViewBasePage extends BaseDashboardPage<?>> ViewBasePage clickOnView(String viewName, ViewBasePage viewBasePage) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format("//a[contains(@href,'/view/%s/')]", viewName)))).click();

        return viewBasePage;
    }

    @Step("Check if view is shown in the View Bar")
    default boolean verifyViewIsPresent(String viewName) {
        boolean status = false;

        List<WebElement> views = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tabBar']")))
                .findElements(By.xpath("//div[@class='tabBar']/div"));
        for (WebElement view : views) {
            if (view.getText().equals(viewName)) {
                status = true;
                break;
            }
        }

        return status;
    }

    default List<String> getListOfAllViews() {
        List<String> list = new ArrayList<>();
        List<WebElement> views = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tabBar']")))
                .findElements(By.xpath("//div[@class='tabBar']/div"));
        for (WebElement view : views) {
            list.add(view.getText());
        }

        return list;
    }
}
