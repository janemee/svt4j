import cn.hutool.core.collection.CollectionUtil;
import com.jayway.jsonpath.internal.filter.ValueNode;

import java.util.ArrayList;
import java.util.List;

public class TestB {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(4);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
//        System.out.println(getList(list, 1));
//        System.out.println(CollectionUtil.reverse(list));
        reverse(list);


    }

    public static List getList(List<Integer> list, int val) {

        if (CollectionUtil.isEmpty(list)) {
            return list;
        }
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            int value = list.get(i);
            if (value == val) {
                index = i;
                break;
            }
        }

        if (index >= 0 && index < list.size() - 1) {
            System.out.println("移除 " + val + " 成功");
//            list.remove(index);
            list.remove(index);
        } else {
            System.out.println("未找到 " + val);
        }

        return list;
    }

    public static void reverse(List list) {
        if (CollectionUtil.isNotEmpty(list)) {
            int size = list.size();
            for (int i = 0, mid = size >> 1, j = size - 1; i < mid; i++, j--) {
                list.set(i, list.set(j, list.get(i)));
            }
            System.out.println("reverse data：" + list);
            for (int i = 0; i < size >> 1; i++) {
                //从左获取
                Object val1 = list.get(i);
                //从右获取
                Object val2 = list.get((size - 1) - i);
                //进行交换
                list.set((size - 1) - i, val1);
                list.set(i, val2);
            }
        }
        System.out.println("reverse data：" + list);

    }

}
