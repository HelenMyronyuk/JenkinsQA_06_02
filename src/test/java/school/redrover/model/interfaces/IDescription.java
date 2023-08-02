package school.redrover.model.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.IBasePage;

public interface IDescription <Self extends BaseMainHeaderPage<?>> extends IBasePage {

    default Self clickAddOrEditDescription() {
        getWait5().until(ExpectedConditions.elementToBeClickable((By.id("description-link")))).click();

        return (Self) this;
    }

    default String getDescriptionButton() {
        return getDriver().findElement(By.id("description-link")).getText();
    }

    default Self enterDescription(String name) {
        getWait5().until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.xpath("//textarea[@name='description']")))).sendKeys(name);

        return (Self) this;
    }

    default Self clearDescriptionField() {
        getWait10().until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.xpath("//textarea[@name='description']")))).clear();

        return (Self) this;
    }

    default String getDescriptionField() {
        return  getDriver().findElement(By.xpath("//textarea[@name='description']")).getText();
    }

    default String getDescriptionText() {
        return  getDriver().findElement(By.xpath("//div[@id='description']//div[not(@class)]")).getText();
    }

    default Self clickPreviewDescription() {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@class='textarea-show-preview']"))).click();

        return (Self) this;
    }

    default String getPreviewDescriptionText() {
        return getWait5().until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.xpath("//div[@class='textarea-preview']")))).getText();
    }

    default Self clickSaveButtonDescription() {
        getWait5().until(ExpectedConditions.visibilityOf(
                        getDriver().findElement(By.xpath("//button[text()='Save']"))))
                .click();

        return (Self) this;
    }
}
