package school.redrover.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.base.*;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.model.jobs.*;
import school.redrover.model.jobsConfig.*;
import school.redrover.model.views.IncludeAGlobalViewConfigPage;
import school.redrover.model.views.ListViewConfigPage;
import school.redrover.model.views.ViewPage;

import java.util.*;


public class TestUtils {

    public enum JobType {
        FreestyleProject(By.xpath("//span[contains(text(),'Freestyle project')]")) {
            @Override
            public BaseConfigPage<?, ?> createConfigPage(WebDriver driver) {
                return new FreestyleProjectConfigPage(new FreestyleProjectPage(driver));
            }

            @Override
            public BaseJobPage<?> createJobPage(WebDriver driver) {
                return new FreestyleProjectPage(driver);
            }
        },

        Pipeline(By.xpath("//span[contains(text(),'Pipeline')]")) {
            @Override
            public BaseConfigPage<?, ?> createConfigPage(WebDriver driver) {
                return new PipelineConfigPage(new PipelinePage(driver));
            }

            @Override
            public BaseJobPage<?> createJobPage(WebDriver driver) {
                return new PipelinePage(driver);
            }
        },

        MultiConfigurationProject(By.xpath("//span[contains(text(),'Multi-configuration project')]")) {
            @Override
            public BaseConfigPage<?, ?> createConfigPage(WebDriver driver) {
                return new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(driver));
            }

            @Override
            public BaseJobPage<?> createJobPage(WebDriver driver) {
                return new MultiConfigurationProjectPage(driver);
            }
        },

        Folder(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")) {
            @Override
            public BaseConfigPage<?, ?> createConfigPage(WebDriver driver) {
                return new FolderConfigPage(new FolderPage(driver));
            }

            @Override
            public BaseJobPage<?> createJobPage(WebDriver driver) {
                return new FolderPage(driver);
            }
        },

        MultibranchPipeline(By.xpath("//span[contains(text(),'Multibranch Pipeline')]")) {
            @Override
            public BaseConfigPage<?, ?> createConfigPage(WebDriver driver) {
                return new MultibranchPipelineConfigPage(new MultibranchPipelinePage(driver));
            }

            @Override
            public BaseJobPage<?> createJobPage(WebDriver driver) {
                return new MultibranchPipelinePage(driver);
            }
        },

        OrganizationFolder(By.xpath("//span[contains(text(),'Organization Folder')]")) {
            @Override
            public BaseConfigPage<?, ?> createConfigPage(WebDriver driver) {
                return new OrganizationFolderConfigPage(new OrganizationFolderPage(driver));
            }

            @Override
            public BaseJobPage<?> createJobPage(WebDriver driver) {
                return new OrganizationFolderPage(driver);
            }
        };

        private final By locator;

        JobType(By locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return locator;
        }

        public abstract BaseConfigPage<?, ?> createConfigPage(WebDriver driver);

        public abstract BaseJobPage<?> createJobPage(WebDriver driver);
    }

    public enum ViewType {
        IncludeAGlobalView(By.xpath("//label[@for='hudson.model.ProxyView']")) {
            @Override
            public BaseMainHeaderPage<?> createNextPage(WebDriver driver) {
                return new IncludeAGlobalViewConfigPage(new ViewPage(driver));
            }
        },

        ListView(By.xpath("//label[@for='hudson.model.ListView']")) {
            @Override
            public BaseMainHeaderPage<?> createNextPage(WebDriver driver) {
                return new ListViewConfigPage(new ViewPage(driver));
            }
        },

        MyView(By.xpath("//label[@for='hudson.model.MyView']")) {
            @Override
            public BaseMainHeaderPage<?> createNextPage(WebDriver driver) {
                return new ViewPage(driver);
            }
        };

        private final By locator;

        ViewType(By locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return locator;
        }

        public abstract BaseMainHeaderPage<?> createNextPage(WebDriver driver);
    }

    public static void createJob(BaseTest baseTest, String name, JobType jobType, Boolean goToMainPage) {
        final WebDriver driver = baseTest.getDriver();
        BaseConfigPage<?, ?> configPage = jobType.createConfigPage(baseTest.getDriver());

        new MainPage(driver)
                .clickNewItemFromSideMenu()
                .enterItemName(name)
                .selectJobType(jobType)
                .clickOkButton(configPage)
                .clickSaveButton();

        if (goToMainPage) {
            new MainPage(driver)
                    .getHeader()
                    .clickLogo();
        }
    }

    public static List<String> getTexts(List<WebElement> elements) {
        List<String> texts = new ArrayList<>();

        for (WebElement element : elements) {
            texts.add(element.getText());
        }
        return texts;
    }

    public static void sendTextToInput(BaseModel baseModel, WebElement element, String text) {
        baseModel.getWait5().until(ExpectedConditions.elementToBeClickable(element)).click();
        baseModel.getWait5().until(ExpectedConditions.elementToBeClickable(element)).clear();
        baseModel.getWait5().until(ExpectedConditions.elementToBeClickable(element)).click();
        element.sendKeys(text);
    }

    public static void scrollToElementByJavaScript(BaseModel baseModel, WebElement element) {
        JavascriptExecutor jsc = (JavascriptExecutor) baseModel.getDriver();
        jsc.executeScript("arguments[0].scrollIntoView();", baseModel.getWait5().until(ExpectedConditions.elementToBeClickable(element)));
    }

    public static void clickByJavaScript(BaseModel baseModel, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) baseModel.getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public static void createFreestyleProjectInsideFolderAndView(BaseTest baseTest, String jobName, String viewName, String folderName) {
        new ViewPage((baseTest.getDriver()))
                .openJobDropDownMenu(folderName)
                .selectNewItemInJobDropDownMenu(folderName)
                .enterItemName(jobName)
                .selectJobType(JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(baseTest.getDriver())))
                .clickSaveButton();

        List<WebElement> breadcrumbTree = baseTest.getDriver().findElements(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']/a"));
        for (WebElement el : breadcrumbTree) {
            if (el.getText().equals(viewName)) {
                el.click();
                break;
            }
        }
    }

    public static void createUserAndReturnToMainPage(BaseTest baseTest, String username, String password, String fullName, String email) {
        new MainPage(baseTest.getDriver())
                .clickManageJenkinsPage()
                .clickManageUsers()
                .clickCreateUser()
                .enterUsername(username)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .enterFullName(fullName)
                .enterEmail(email)
                .clickCreateUserButton()
                .getHeader()
                .clickLogo();
    }

    public static CreateItemErrorPage createJobWithExistingName(BaseTest baseTest, String jobName, JobType jobType) {
        return new MainPage(baseTest.getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(jobName)
                .selectJobAndOkAndGoError(jobType);
    }

    public static NewJobPage createFolderUsingInvalidData(BaseTest baseTest, String invalidData, JobType jobType) {
        return new MainPage(baseTest.getDriver())
                .clickCreateAJobAndArrow()
                .enterItemName(invalidData)
                .selectJobType(jobType);
    }

    public static CreateItemErrorPage createJobWithSpaceInsteadName(BaseTest baseTest, JobType jobType) {
        return new MainPage(baseTest.getDriver())
                .clickNewItemFromSideMenu()
                .enterItemName(" ")
                .selectJobAndOkAndGoError(jobType);
    }

    public static Map<String, BaseJobPage<?>> getJobMap(BaseTest baseTest) {
        return Map.of(
                "FreestyleProject", new FreestyleProjectPage(baseTest.getDriver()),
                "Pipeline", new PipelinePage(baseTest.getDriver()),
                "MultiConfigurationProject", new MultiConfigurationProjectPage(baseTest.getDriver()),
                "Folder", new FolderPage(baseTest.getDriver()),
                "MultibranchPipeline", new MultibranchPipelinePage(baseTest.getDriver()),
                "OrganizationFolder", new OrganizationFolderPage(baseTest.getDriver())
        );
    }

    public static void checkMoveOptionAndCreateFolder(
            String optionName, BaseTest baseTest, Boolean goToMainPage) {
        if (optionName.equals("Move")) {
            createJob(baseTest, "Folder", JobType.Folder, goToMainPage);
        }
    }

    public static void scrollWithPauseByActions(BaseModel baseModel, WebElement element, int mls) {
        new Actions(baseModel.getDriver())
                .scrollToElement(element)
                .pause(mls)
                .perform();
    }

    public static void createNode(WebDriver driver, String nodeName, Boolean goToMainPage) {
        new MainPage(driver)
                .clickManageJenkinsPage()
                .clickManageNodes()
                .clickNewNodeButton()
                .inputNodeNameField(nodeName)
                .clickPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton();

        if (goToMainPage) {
            new MainPage(driver)
                    .getHeader()
                    .clickLogo();
        }
    }
}
