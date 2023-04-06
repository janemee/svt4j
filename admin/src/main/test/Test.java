import com.huimi.common.utils.JsonUtils;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class Test {

    @org.junit.Test
    public void testMask(){
        List<BizApkHistoryModel> bizApkHistoryModels = new ArrayList<>();
        bizApkHistoryModels.add(BizApkHistoryModel.builder().name("111111").dataUrl("12314@123.com").remake("123123123@123.com").build());
        bizApkHistoryModels.add(BizApkHistoryModel.builder().name("111111").dataUrl("12314@123.com").remake("123123123@123.com").build());
        bizApkHistoryModels.add(BizApkHistoryModel.builder().name("111111").dataUrl("12314@123.com").remake("123123123@123.com").build());
        System.out.println("oldOnject：" + bizApkHistoryModels);
        System.out.println("JsonUtils.toJson(list)：" + JsonUtils.toJson(bizApkHistoryModels));
        System.out.println("JsonUtils.toJson(list) to object：" + JsonUtils.toObject(JsonUtils.toJson(bizApkHistoryModels), BizApkHistoryModel.class));
        bizApkHistoryModels.forEach(doc -> {
            System.out.println("maskName:" + doc.getName() + "-dataurl:" + doc.getDataUrl() + "-remake:" + doc.getRemake());
        });
    }
}
