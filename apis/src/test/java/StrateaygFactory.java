import cn.hutool.core.collection.CollectionUtil;

import java.util.Map;

public class StrateaygFactory {

    private static Map<TestEnum, testStrategy> strategyMap;

    //初始化策略映射
    private static Map<TestEnum, testStrategy> getCache() {
        if (CollectionUtil.isEmpty(strategyMap)) {

        }
        return strategyMap;
    }


    public static testStrategy getTestStrategy(String url) {
        return null;
    }


}
