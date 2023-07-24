package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class NodePage extends BaseMainHeaderPage<NodePage> {

    @FindBy(xpath = "//div[@id='description']/div[not(@class)]")
    private WebElement nodeDescription;

    @FindBy(xpath = "//a[contains(@href, '/delete')]")
    private WebElement deleteAgent;

    public NodePage(WebDriver driver) {
        super(driver);
    }

    public String getNodeDescription() {
        return getWait5().until(ExpectedConditions.visibilityOf(nodeDescription)).getText();
    }

    public DeletePage<ManageNodesPage> clickOnDeleteAgent() {
        deleteAgent.click();
        return new DeletePage<>(new ManageNodesPage(getDriver()));
    }
}
