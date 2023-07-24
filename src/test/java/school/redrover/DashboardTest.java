package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardTest extends BaseTest {

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
}
