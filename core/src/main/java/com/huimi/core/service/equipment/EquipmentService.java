package com.huimi.core.service.equipment;

import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.service.base.GenericService;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lja on 2020/7/28 17:31
 */
public interface EquipmentService extends GenericService<Integer, Equipment> {

    DtGrid findAll(DtGrid dtGrid);

    DtGrid findSysAdminGroup(DtGrid dtGrid);

    List<Equipment> selectByState(Integer state);

    ArrayList<Equipment> selectLiveHotGroup(Long id, ArrayList<Long> groupId);

    Equipment selectByUid(String code, String deviceCode, String invitationCode, String channelId);

    Equipment selectByUid(String uid);

    ArrayList<Equipment> findAllStateByGroup(ArrayList<Long> list, Integer findAllStateByGroup);

    Integer updateByUid(Integer state, String code);

    ArrayList<Equipment> findAllStateByGroupAgent(ArrayList<Long> list, List<String> adminIds);


    /**
     * 根据任务类型获取任务列表
     *
     * @param equipmentId 设备Id
     * @param taskType    任务类型
     * @return
     */
    List<Equipment> findListToTaskType(Long equipmentId, String taskType);

    /**
     * 手机下线
     *
     * @param deviceUid  设备唯一标识
     * @param deviceCode 设备名称
     */
    void offline(String deviceUid, String deviceCode);

    /**
     * @param ids      所有的设备id
     * @param groupIds 同时变更所有设备的id
     */
    Integer updateBatchGrouping(String groupIds, String[] ids);


    /**
     * 获取设备数量
     *
     * @param type 1 空闲设备 2 正在运行设备
     * @return
     */
    Integer findWorkNumber(int type, Integer sysAdminId);

    /**
     * 获取全部的设备信息
     *
     * @return
     */
    List<Equipment> findByAll(String sysAdminCode);

    /**
     * 根据父类id找到2级代理商的分配的设备量总数
     */
    Integer findSubEquipmentNum(Integer parentId);

    /**
     * 根据设备查找正在运行的任务
     */
    DtGrid findEquipmentTask(Integer equipmentId, Integer state);

    /**
     * 查找管理员下面总分配的设备量
     */

    Integer findByAgentEquipment();

    Integer findByAdminId(Integer adminId);

    /**
     * 通过设备对应的通道id更新其状态
     *
     * @param channelId 通道id
     * @return 影响条数
     */
    int updateStateByChannelId(String channelId);

    /**
     * 获取设备数量（根据分组）
     *
     * @param type  0 空闲设备 1 正在运行设备
     * @param groupId
     * @return
     */
    Integer findWorkNumberByGroup(int type, Integer groupId);
}
