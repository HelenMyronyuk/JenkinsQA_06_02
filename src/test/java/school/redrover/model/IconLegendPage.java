package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;

import java.util.ArrayList;
import java.util.List;

public class IconLegendPage extends BaseMainHeaderPage<IconLegendPage> {

    @FindBy(xpath = "//dl[@class='app-icon-legend']/dt")
    private List<WebElement> iconList;

    @FindBy(xpath = "//dl[@class='app-icon-legend']/dd")
    private List<WebElement> textForIconList;

    public IconLegendPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get list of description to displaying icons on the Icon Legend page")
    public List<String> getIconDescriptionTextList() {
        List<String> iconDescriptionList = new ArrayList<>();
        for(int i = 0; i <= iconList.size() - 1; i++) {
            if (iconList.get(i).isDisplayed()) {
                iconDescriptionList.add(textForIconList.get(i).getText());
            }
        }

        return iconDescriptionList;
    }
}
