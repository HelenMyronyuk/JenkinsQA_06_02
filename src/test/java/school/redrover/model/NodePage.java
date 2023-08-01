package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.interfaces.IDescription;

public class NodePage extends BaseMainHeaderPage<NodePage> implements IDescription<NodePage> {

    @FindBy(xpath = "//a[contains(@href, '/delete')]")
    private WebElement deleteAgent;

    public NodePage(WebDriver driver) {
        super(driver);
    }

    public DeletePage<ManageNodesPage> clickOnDeleteAgent() {
        deleteAgent.click();

        return new DeletePage<>(new ManageNodesPage(getDriver()));
    }
}
