package com.huimi.admin.controller.setting;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.constant.Constants;
import com.huimi.core.model.config.AbroadInfoModel;
import com.huimi.core.model.config.BannerInfoModel;
import com.huimi.core.model.config.ConfigInfoModel;
import com.huimi.core.po.system.Conf;
import com.huimi.core.service.system.ConfService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 后台参数配置
 *
 * @author liweidong
 * @date 2017年03月10日 15:28
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class ConfigureController extends BaseController {

    @Resource
    ConfService confService;

    /**
     * 参数配置列表
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/config/list")
    public String list(ModelMap modelMap) {
        return "system/conf/list";
    }

    /**
     * 全局控制列表
     */
    @RequestMapping("/global/list")
    public String globalList(ModelMap modelMap) {
        return ok("setting/global/list");
    }

    /**
     * 添加
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/config/add")
    public String add(ModelMap modelMap) {
        return "system/conf/add";
    }

    /**
     * 更新
     *
     * @param ids
     * @return
     */
    @RequestMapping("/config/edit")
    public ModelAndView update(Integer ids) {
        Conf conf = confService.selectByPrimaryKey(ids);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("conf", conf);
        modelAndView.setViewName("system/conf/update");
        return modelAndView;
    }

    /**
     * 参数配置列表
     *
     * @return
     */
    @RequestMapping("/config/index")
    public ModelAndView info() {
        ModelAndView modelAndView = new ModelAndView();
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
        modelAndView.addObject("model", config);
        modelAndView.setViewName("system/conf/configInfo");
        return modelAndView;
    }


    /**
     * 关于企业
     *
     * @return
     */
    @RequestMapping("/config/abroadInfo")
    public ModelAndView abroadInfo() {
        ModelAndView modelAndView = new ModelAndView();
        AbroadInfoModel config = new AbroadInfoModel();
        config.setCompanyBackdrop(confService.getConfigByKey(Constants.companyBackdrop));
        config.setCompanySource(confService.getConfigByKey(Constants.companySource));
        config.setCompanyVideoUrl(confService.getConfigByKey(Constants.companyVideoUrl));
        config.setCompanyCopyContent(confService.getConfigByKey(Constants.companyCopyContent));
        config.setCompanyPubPicUrl(confService.getConfigByKey(Constants.companyPubPicUrl));
        config.setCompanyHistoryOfDevUrl(confService.getConfigByKey(Constants.companyHistoryOfDevUrl));
        config.setCorporateCulture(confService.getConfigByKey(Constants.corporateCulture));
        config.setCorporateCulturePicUrl(confService.getConfigByKey(Constants.corporateCulturePicUrl));
        config.setEnterpriseHonorCertPicUrl(confService.getConfigByKey(Constants.enterpriseHonorCertPicUrl));
        modelAndView.addObject("model", config);
        modelAndView.setViewName("system/conf/abroadInfo");
        return modelAndView;
    }


    /**
     * 门户资源图片管理
     *
     * @return
     */
    @RequestMapping("/config/banner")
    public ModelAndView banner() {
        ModelAndView modelAndView = new ModelAndView();
        BannerInfoModel config = new BannerInfoModel();
        config.setIndexBannerImgUrl(confService.getConfigByKey(Constants.INDEX_BANNER_IMG_URL));
        config.setAbroadBannerImgUrl(confService.getConfigByKey(Constants.ABROAD_BANNER_IMG_URL));
        config.setProductBannerImgUrl(confService.getConfigByKey(Constants.PRODUCT_BANNER_IMG_URL));
        config.setApplicationBannerImgUrl(confService.getConfigByKey(Constants.APPLICATION_BANNER_IMG_URL));
        config.setMediaBannerImgUrl(confService.getConfigByKey(Constants.MEDIA_BANNER_IMG_URL));
        config.setClientBannerImgUrl(confService.getConfigByKey(Constants.CLIENT_BANNER_IMG_URL));
        config.setBoardBannerImgUrl(confService.getConfigByKey(Constants.BOARD_BANNER_IMG_URL));
        modelAndView.addObject("model", config);
        modelAndView.setViewName("system/conf/banner");
        return modelAndView;
    }
}