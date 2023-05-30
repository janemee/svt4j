import com.alibaba.fastjson.JSON;
import com.huimi.apis.config.aop.DataSourceMaskValueAspect;
import com.huimi.common.enums.ExchangeEnum;
import com.huimi.common.enums.LoginEnum;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel2;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel3;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel4;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class TestDome {


    @Test
    public void test() {
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("1", 1);
            map.put("2", 1);
            map.put("3", 1);
            List<BizApkHistoryModel> bizApkHistoryModels = new ArrayList<>();
            BizApkHistoryModel bizApkHistoryModel = new BizApkHistoryModel();
            bizApkHistoryModel.setUserName("123123");
            bizApkHistoryModel.setRemake("1231231");
            bizApkHistoryModel.setDateTime(new Date());
            bizApkHistoryModel.setIntegerList(Arrays.asList(1, 1));
            bizApkHistoryModel.setNumber(1);
            bizApkHistoryModel.setMap(map);
            bizApkHistoryModel.setLoginEnum(LoginEnum.DISABLED);
            bizApkHistoryModel.setExchangeEnum(ExchangeEnum.ALL);

            BizApkHistoryModel2 bizApkHistoryModel2 = new BizApkHistoryModel2();
            bizApkHistoryModel2.setName("123123");
            bizApkHistoryModel2.setEmail("1231231@123.com");

            BizApkHistoryModel3 bizApkHistoryModel3 = new BizApkHistoryModel3();
            bizApkHistoryModel3.setName("123123");
            bizApkHistoryModel3.setEmail("1231231@123.com");

            BizApkHistoryModel4 bizApkHistoryModel4 = new BizApkHistoryModel4();
            bizApkHistoryModel4.setName("123123");
            bizApkHistoryModel4.setEmail("1231231@123.com");

            bizApkHistoryModel2.setList(Arrays.asList(bizApkHistoryModel3));

            bizApkHistoryModel3.setBizApkHistoryModel4(bizApkHistoryModel4);
            bizApkHistoryModel3.setList(Arrays.asList(bizApkHistoryModel4));

            bizApkHistoryModel2.setBizApkHistoryModel3(bizApkHistoryModel3);
            bizApkHistoryModel2.setList(Arrays.asList(bizApkHistoryModel3));

            bizApkHistoryModel.setBizApkHistoryModel2(bizApkHistoryModel2);
            bizApkHistoryModel.setList(Arrays.asList(bizApkHistoryModel2));
            for (int i = 0; i < 2; i++) {
                bizApkHistoryModels.add(bizApkHistoryModel);
            }
            log.info("un mask info log :{} ", (bizApkHistoryModel));
//        return ok(bizApkHistoryModels, "success");
            DataSourceMaskValueAspect dataSourceMaskValueAspect = new DataSourceMaskValueAspect();
            dataSourceMaskValueAspect.handleResultEntity(bizApkHistoryModel);

            log.info("mask info log :{} ", (bizApkHistoryModel));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
//        return fail(e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
