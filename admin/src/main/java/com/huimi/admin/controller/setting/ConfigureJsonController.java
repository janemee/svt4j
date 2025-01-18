package com.huimi.admin.controller.setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.controller.GenericController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.CacheID;
import com.huimi.core.constant.Constants;
import com.huimi.core.model.config.AbroadInfoModel;
import com.huimi.core.model.config.BannerInfoModel;
import com.huimi.core.model.config.ConfigInfoModel;
import com.huimi.core.po.system.Conf;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.ConfService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台参数配置
 *
 * @author liweidong
 * @date 2017年03月10日 15:28
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class ConfigureJsonController extends GenericController<Integer, Conf> {

    @Resource
    ConfService confService;
    @Resource
    RedisService redisService;

    /**
     * 参数配置列表json数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/config/json/list")
//    @RequiresPermissions("sys:config:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.valueOf(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.valueOf(request.getParameter("page"));
        String search_val = request.getParameter("search_val");
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        if (!StringUtils.isBlank(search_val)) {
            whereSql.append(" and (name like '%").append(search_val).append("%' or ").append("nid like '%").append(search_val).append("%')");
        } else {
            String nid = request.getParameter("nid");
            if (StringUtils.isNotBlank(nid)) {
                whereSql.append(" and nid like '%").append(nid).append("%'");
            }
            String name = request.getParameter("name");
            if (StringUtils.isNotBlank(name)) {
                whereSql.append(" and name like '%").append(name).append("%'");
            }
            String type = request.getParameter("type");
            if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
                whereSql.append(" and type like '%").append(type).append("%'");
            }
            String state = request.getParameter("state");
            if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
                whereSql.append(" and state like '%").append(state).append("%'");
            }
        }
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by type asc,nid asc");
        return confService.getDtGridList(dtGrid);
    }

    /**
     * 添加参数配置
     *
     * @param conf
     * @return
     */
    @ResponseBody
    @RequestMapping("/config/json/saveOrUpdata")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(Conf conf) throws Exception {
//        conf.setUuid(RandomUtils.randomCustomUUID());
        int insert = 0;
        if (conf.getId() != null) {
            insert = confService.updateByPrimaryKeySelective(conf);
            if (insert > 0) {
                if (conf.getState() == Conf.STATE.ENABLED.code) {
                    redisService.put(CacheID.CONFIG_PREFIX + conf.getNid(), conf.getValue());
                } else {
                    redisService.del(CacheID.CONFIG_PREFIX + conf.getNid());
                }
            }
        } else {
            insert = confService.insert(conf);
            if (insert > 0) {
                if (conf.getState() == Conf.STATE.ENABLED.code) {
                    redisService.put(CacheID.CONFIG_PREFIX + conf.getNid(), conf.getValue());
                }
            }
        }

        return insert > 0 ? ok() : fail();
    }

    /**
     * 编辑参数配置
     *
     * @param conf
     * @return
     */
    @ResponseBody
    @RequestMapping("/config/json/edit")
//    @RequiresPermissions("sys:config:edit")
    public ResultEntity editJson(Conf conf) throws Exception {
        int insert = confService.updateByPrimaryKeySelective(conf);
        return insert > 0 ? ok() : fail();
    }

    /**
     * 删除参数配置
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/config/json/del")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity delJson(String ids) throws Exception {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] idArr = ids.split(",");
        int insert = 0;
        for (String id : idArr) {
            insert += confService.deleteByPrimaryKey(Integer.valueOf(id));
        }
//        int insert = confService.removeById(new Conf(c -> c.setId(id)));
        return insert == idArr.length ? ok() : fail();
    }


    /**
     * 全局控制列表json数据
     *
     * @param dtGridPager
     * @return
     */
    @ResponseBody
    @RequestMapping("/global/json/list")
    @RequiresPermissions("sys:global:list")
    public DtGrid globalListJson(String dtGridPager) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DtGrid dtGrid = mapper.readValue(dtGridPager, DtGrid.class);
        Map<String, Object> map = new HashMap<>();
        map = dtGrid.getFastQueryParameters();
        map.put("lk_nid", "global_ban");
        dtGrid.setFastQueryParameters(map);
        return confService.getDtGridList(dtGrid);
    }



    /**
     * 全局控制列表json数据
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/config/json/updateInfo")
    public ResultEntity updateInfoJson(ConfigInfoModel params) throws Exception {
        confService.updateConfByNid(Constants.COMPANY_NAME, params.getCompanyName());
        confService.updateConfByNid(Constants.COMPANY_ADDRESS, params.getAddress());
        confService.updateConfByNid(Constants.CN_MOBILE, params.getCnMobile());
        confService.updateConfByNid(Constants.ABROAD_MOBILE, params.getAbroadMobile());
        confService.updateConfByNid(Constants.FAX, params.getFax());
        confService.updateConfByNid(Constants.WEB_ICP, params.getWebIcp());
        confService.updateConfByNid(Constants.COPY_RIGHT, params.getCopyRight());
        return ResultEntity.success();
    }


    /**
     * 关于企业
     *
     * @return
     */
    @RequestMapping("/config/json/updateAbroadInfo")
    public ResultEntity updateAbroadInfoJson(AbroadInfoModel params) {
        confService.updateConfByNid(Constants.companyBackdrop, params.getCompanyBackdrop());
        confService.updateConfByNid(Constants.companySource, params.getCompanySource());
        confService.updateConfByNid(Constants.companyVideoUrl, params.getCompanyVideoUrl());
        confService.updateConfByNid(Constants.companyCopyContent, params.getCompanyCopyContent());
        confService.updateConfByNid(Constants.companyPubPicUrl, params.getCompanyPubPicUrl());
        confService.updateConfByNid(Constants.companyHistoryOfDevUrl, params.getCompanyHistoryOfDevUrl());
        confService.updateConfByNid(Constants.corporateCulture, params.getCorporateCulture());
        confService.updateConfByNid(Constants.corporateCulturePicUrl, params.getCompanyPubPicUrl());
        confService.updateConfByNid(Constants.enterpriseHonorCertPicUrl, params.getEnterpriseHonorCertPicUrl());
        return ResultEntity.success();
    }


    /**
     * 门户资源图片管理
     *
     * @return
     */
    @RequestMapping("/config/json/banner")
    public ResultEntity banner(BannerInfoModel params) {
        confService.updateConfByNid(Constants.INDEX_BANNER_IMG_URL, params.getIndexBannerImgUrl());
        confService.updateConfByNid(Constants.ABROAD_BANNER_IMG_URL, params.getAbroadBannerImgUrl());
        confService.updateConfByNid(Constants.PRODUCT_BANNER_IMG_URL, params.getProductBannerImgUrl());
        confService.updateConfByNid(Constants.APPLICATION_BANNER_IMG_URL, params.getApplicationBannerImgUrl());
        confService.updateConfByNid(Constants.MEDIA_BANNER_IMG_URL, params.getMediaBannerImgUrl());
        confService.updateConfByNid(Constants.CLIENT_BANNER_IMG_URL, params.getClientBannerImgUrl());
        confService.updateConfByNid(Constants.BOARD_BANNER_IMG_URL, params.getBoardBannerImgUrl());
        return ResultEntity.success();
    }

}