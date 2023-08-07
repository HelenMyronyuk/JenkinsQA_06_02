package school.redrover.model.manageJenkins;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.DeletePage;
import school.redrover.model.base.BaseSubmenuPage;

import java.util.ArrayList;
import java.util.List;

public class ManageNodesPage extends BaseSubmenuPage<ManageNodesPage> {

    @FindBy(xpath = "//a[@href='new']")
    private WebElement newNodeButton;

    @FindBy(xpath = "//td/a")
    private List<WebElement> nodesList;

    @FindBy(linkText = "Delete Agent")
    private WebElement deleteAgent;

    public ManageNodesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Manage Nodes and Clouds";
    }

    @Step("Click on 'New Node' button")
    public NewNodePage clickNewNodeButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(newNodeButton)).click();

        return new NewNodePage(getDriver());
    }
    @Step("Get node name")
    public String getNodeName(String nodeName) {
        return getWait2().until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//tr[@id='node_" + nodeName + "']/td/a")))
                .getText();
    }

    @Step("Click on '{nodeName}' node")
    public NodePage clickOnNode(String nodeName) {

        for (WebElement ele : nodesList) {
            if (ele.getText().equals(nodeName)) {
                new Actions(getDriver()).moveToElement(ele).click().perform();
                break;
            }
        }

       return new NodePage(getDriver());
    }

    @Step("Get list of all nodes name")
    public List<String> getNodesList() {
        List<String> nodeNameList = new ArrayList<>();

        for (WebElement element : nodesList) {
          nodeNameList.add(element.getText());
        }

        return nodeNameList;
    }

    @Step("Open '{nodeName}' node in dropdown menu")
    public ManageNodesPage openNodeDropDownMenu(String nodeName) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//td/a[contains(@href,'/%s/')]/button", nodeName.replaceAll(" ", "%20")))))
                .sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Click 'Delete Agent' in drop down menu}")
    public DeletePage<ManageNodesPage> dropDownMenuClickDeleteAgent() {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteAgent)).click();

        return new DeletePage<>(this);
    }
}