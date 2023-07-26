package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.model.ViewPage;
import school.redrover.model.jobs.FolderPage;
import school.redrover.model.jobsconfig.FolderConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class DashboardTest extends BaseTest {

    private static final String PROJECT_NAME = RandomStringUtils.randomAlphanumeric(7);
    private static final String VIEW_NAME = RandomStringUtils.randomAlphanumeric(5);
    final String VIEW_DESCRIPTION = RandomStringUtils.randomAlphanumeric(7);
    final String NEW_VIEW_DESCRIPTION = RandomStringUtils.randomAlphanumeric(7);

    @Test
    public void testDashboardTableSize() {
        Map<String, Integer> tableSizeMap = TestUtils.getJenkinsTableSizeMap();

        List<Integer> sizeList = new ArrayList<>(tableSizeMap.values());
        List<Integer> tableSizeActualList = new ArrayList<>();

        TestUtils.createJob(this, "JOB", TestUtils.JobType.Pipeline, true);

        for (Map.Entry<String, Integer> tableSizeNameAndTableSizeMap: tableSizeMap.entrySet()) {
            tableSizeActualList.add(new MainPage(getDriver())
                    .clickChangeJenkinsTableSize(tableSizeNameAndTableSizeMap.getKey())
                    .getJenkinsTableHeight());
        }

        Assert.assertEquals(tableSizeActualList, sizeList);
    }

    @Test
    public void testAddDescriptionFromMyViewsPage() {
        String description = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickOnDescription()
                .clearTextFromDescription()
                .enterDescription(VIEW_DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(description, VIEW_DESCRIPTION);
    }

    @Test
    public void testEditDescription() {
        String description = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickOnDescription()
                .enterDescription(VIEW_DESCRIPTION)
                .clickSaveButtonDescription()
                .clickOnDescription()
                .clearTextFromDescription()
                .enterDescription(NEW_VIEW_DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(description, NEW_VIEW_DESCRIPTION);
    }

    @Test
    public void testCreateMyViewInFolder() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);

        String newView = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FolderPage(getDriver()))
                .clickNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.MyView, ViewPage.class)
                .getActiveViewName();

        assertEquals(newView, VIEW_NAME);
    }
}
