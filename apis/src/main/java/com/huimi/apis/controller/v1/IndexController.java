package com.huimi.apis.controller.v1;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.PageResult;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.constant.Constants;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.model.config.AbroadInfoModel;
import com.huimi.core.model.config.ConfigInfoModel;
import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.bs.BsNoticeService;
import com.huimi.core.service.system.ConfService;
import com.huimi.core.task.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController

@RequestMapping("/v1/index")
@Api(tags = "首页模块")
public class IndexController extends WebGenericController<Integer, Task> {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ConfService confService;

    @Autowired
    private BsNoticeService bsNoticeService;

    /**
     * 基础配置参数接口
     *
     * @return
     */
    @ApiOperation(value = "基础配置信息")
    @RequestMapping("/config")
    public ResultEntity<Object> configInfo() {
        try {
            //公司名称
            String companyName = confService.getConfigByKey(Constants.COMPANY_NAME);
            //销售电话(国内)
            String cnMobile = confService.getConfigByKey(Constants.CN_MOBILE);
            //销售电话(国内)
            String abroadMobile = confService.getConfigByKey(Constants.ABROAD_MOBILE);
            //传真
            String fax = confService.getConfigByKey(Constants.FAX);
            //地址
            String address = confService.getConfigByKey(Constants.COMPANY_ADDRESS);
            //备案号
            String webIcp = confService.getConfigByKey(Constants.WEB_ICP);
            //copy_right
            String copyRight = confService.getConfigByKey(Constants.COPY_RIGHT);
            ConfigInfoModel config = new ConfigInfoModel();
            config.setAbroadMobile(abroadMobile);
            config.setCnMobile(cnMobile);
            config.setCopyRight(copyRight);
            config.setFax(fax);
            config.setCompanyName(companyName);
            config.setAddress(address);
            config.setWebIcp(webIcp);
            return ok(config, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }


    @ApiOperation(value = "关于")
    @RequestMapping("/about")
    public ResultEntity<Object> aboutInfo() {
        try {
            AbroadInfoModel config = new AbroadInfoModel();
            config.setCompanyBackdrop(confService.getConfigByKey(Constants.companyBackdrop));
            config.setCompanySource(confService.getConfigByKey(Constants.companySource));
            config.setCompanyVideoUrl(confService.getConfigByKey(Constants.companyVideoUrl));
            config.setCompanyCopyContent(confService.getConfigByKey(Constants.companyCopyContent));
            config.setCompanyPubPicUrlList(Arrays.asList(confService.getConfigByKey(Constants.companyPubPicUrl).split(",")));
            config.setCompanyHistoryOfDevUrlList(Arrays.asList(confService.getConfigByKey(Constants.companyHistoryOfDevUrl).split(",")));
            config.setCorporateCulture(confService.getConfigByKey(Constants.corporateCulture));
            config.setCorporateCulturePicUrlList(Arrays.asList(confService.getConfigByKey(Constants.corporateCulturePicUrl).split(",")));
            config.setEnterpriseHonorCertPicUrlList(Arrays.asList(confService.getConfigByKey(Constants.enterpriseHonorCertPicUrl).split(",")));
            return ok(config, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }


    @ApiOperation(value = "公告列表 (分页列表)")
    @RequestMapping("/noticeList")
    public ResultEntity<Object> noticeList(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "20") Integer pageSize,
                                           @RequestParam(defaultValue = "") String title) {
        try {
            DtGrid dtGrid = new DtGrid();
            dtGrid.setNowPage(page);
            dtGrid.setPageSize(pageSize);
            StringBuilder whereSql = new StringBuilder();
            if (StringUtils.isNotBlank(title)) {
                whereSql.append(" and title like '%").append(title).append("%'");
            }
            whereSql.append(" and del_flag = ").append(Admin.DELFLAG.NO.code).append(" ");
            whereSql.append(" and status = ").append(EnumConstants.ApplicationStatusEunm.YES.value).append(" ");
            dtGrid.setWhereSql(whereSql.toString());
            dtGrid.setSortSql("order by update_time DESC");
            dtGrid = bsNoticeService.getDtGridList(dtGrid);
            List<Object> poList = dtGrid.getExhibitDatas();
            PageResult result = new PageResult();
            result.setList(poList);
            result.setTotalCount(dtGrid.getPageCount());
            return ok(result, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }


    @ApiOperation(value = "公告详情")
    @RequestMapping("/notice")
    public ResultEntity<Object> notice(@RequestParam Integer id) {
        try {
            if (Objects.isNull(id)) {
                return fail("参数不能为空");
            }
            BsNoticePo bsNoticePo = bsNoticeService.selectByPrimaryKey(id);
            if (Objects.isNull(bsNoticePo) || bsNoticePo.getStatus() == EnumConstants.ApplicationStatusEunm.NO.value) {
                return fail("信息不存在，请检查后重试");
            }
            return ok(bsNoticePo, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }
}
