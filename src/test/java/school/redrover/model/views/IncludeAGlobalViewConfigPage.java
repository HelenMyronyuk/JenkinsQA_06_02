package school.redrover.model.views;

import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.model.interfaces.IDescription;

public class IncludeAGlobalViewConfigPage extends BaseConfigPage<IncludeAGlobalViewConfigPage, ViewPage> implements IDescription<IncludeAGlobalViewConfigPage> {
    public IncludeAGlobalViewConfigPage(ViewPage viewPage) {
        super(viewPage);
    }
}
