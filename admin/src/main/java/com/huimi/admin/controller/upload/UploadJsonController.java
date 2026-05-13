package com.huimi.admin.controller.upload;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.huimi.admin.controller.BaseController;
import com.huimi.common.entity.Result;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.*;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.service.cache.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 管理后台文件上传控制器
 * 提供多种文件上传功能，包括单文件上传、多文件上传、带二维码生成的上传等
 * 所有文件均上传到阿里云OSS存储
 *
 * @author Vector
 * @create 2017-06-16 14:54
 */
@Controller
@RequestMapping(value = BaseController.BASE_URI)
public class UploadJsonController extends BaseController {


    @Autowired
    private RedisService redisService;


    /**
     * 上传图片并生成二维码
     * 上传图片到OSS后，会根据图片URL生成一个二维码并一同上传
     *
     * @param request HTTP请求对象
     * @return 包含原图URL和二维码URL的结果
     */
    @RequestMapping(value = "/file/doUploadPic", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity doUploadPic(HttpServletRequest request) {
        MultipartFile file = null;
        System.out.println("文件上传");
        ResultEntity resultEntity = new ResultEntity();
        try {
            // 从请求中获取上传的文件
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                multipartHttpServletRequest.getFileNames();
                file = multipartHttpServletRequest.getFile("file");
            }
            if (file == null) {
                return ResultEntity.fail("请选择图片");
            }
            // 获取图片扩展名
            String originalFilename = file.getOriginalFilename();
            String extName = file.getOriginalFilename().substring(originalFilename.lastIndexOf(".") + 1);
            // 使用时间戳作为文件名，避免文件名重复
            String fileName = DateUtils.getTimeStr(new Date()) + "." + extName;
            // 上传原图到OSS
            String url = OSSClientUtils.uploadFile(file.getBytes(), fileName);
            if (StringUtil.isNotBlank(url)) {
                // 拼接访问前缀
                url = OSSClientUtils.ACCESS_BASE_PATH + "/" + url;

                // 生成二维码相关配置
                String qrCodeUrl = "webapp/template/ui/img/qrode/"; // 本地临时保存路径
                Integer size = 500; // 二维码大小（像素）
                String imgUrl = "webapp/template/ui/img/qrode/dgj_log.png"; // 二维码中心图标

                // 生成二维码文件
                File qrCodeFile = QRCodeUtil.zxingCodeCreate(url, qrCodeUrl, size, imgUrl);
                String qrCodeFileName = qrCodeFile.getName();
                // 上传二维码到OSS
                String qrCodeImgUrl = OSSClientUtils.uploadFile(FileUtil.getFileByteArray(qrCodeFile), "qrCode/" + qrCodeFileName);
                // 删除本地临时二维码文件
                qrCodeFile.delete();
                // 拼接二维码访问地址
                qrCodeImgUrl = OSSClientUtils.ACCESS_BASE_PATH + "/" + qrCodeImgUrl;

                resultEntity.setCode(ResultEntity.SUCCESS);
                resultEntity.setMsg("上传成功");
                Map<String, Object> data = new HashMap<>();
                data.put("url", url);
                data.put("imgUrl", qrCodeImgUrl);
                System.out.println("上传成功，文件地址 ：" + url);
                resultEntity.setData(data);
            } else {
                return ResultEntity.fail("上传失败！");
            }
        } catch (Exception e) {
            return ResultEntity.fail("上传失败！");
        }
        return resultEntity;
    }

    /**
     * 普通文件上传接口
     * 上传单个文件到OSS，文件存储在apk/目录下
     *
     * @param file 上传的文件
     * @return 包含文件访问URL的结果
     */
    @ResponseBody
    @RequestMapping(value = "file/upload", method = RequestMethod.POST)
    public ResultEntity fileUpload(MultipartFile file) throws Exception {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(ResultEntity.SUCCESS);

        // 判断图片是否为空
        if (file.isEmpty()) {
            resultEntity.setCode(ResultEntity.FAIL);
            resultEntity.setMsg("图片为空");
            return resultEntity;
        }
        // 上传到图片服务器
        try {
            // 获取图片扩展名
            String originalFilename = file.getOriginalFilename();
            String extName = file.getOriginalFilename().substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = RandomUtil.randomUUID() + "." + extName;
            // 上传到OSS的apk/目录下
            String url = OSSClientUtils.uploadFile(file.getBytes(), OSSClientUtils.BASE_PATH + fileName);
            url = OSSClientUtils.ACCESS_BASE_PATH + "/" + url;
            resultEntity.setData(url);
            System.out.println("图片地址:" + url);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(ResultEntity.FAIL);
            resultEntity.setMsg("图片上传失败");
        }

        return resultEntity;
    }

    /**
     * 图片文件上传接口
     * 上传单个图片文件到OSS的upload/目录下
     *
     * @param imgFile 上传的图片文件
     * @return 包含图片访问URL的Result对象
     */
    @ResponseBody
    @RequestMapping(value = "file/imgFile", method = RequestMethod.POST)
    public Result imgFile(MultipartFile imgFile) {
        Result result = new Result();
        // 判断图片是否为空
        if (imgFile.isEmpty()) {
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }
        // 上传到图片服务器
        try {
            // 获取图片扩展名
            String originalFilename = imgFile.getOriginalFilename();
            String extName = imgFile.getOriginalFilename().substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = RandomUtil.randomUUID() + "." + extName;
            // 上传到OSS的upload/目录下
            String url = OSSClientUtils.uploadFile(imgFile.getBytes(), "/upload/" + fileName);
            url = OSSClientUtils.ACCESS_BASE_PATH + "/" + url;
            result.setError(1);
            result.setUrl(url);
            result.setMessage("上传成功");
            System.out.println("图片地址:" + url);
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            result.setMessage("图片上传失败");
        }
        return result;
    }

    /**
     * KindEditor文件管理器接口
     * 用于KindEditor富文本编辑器的文件浏览功能
     * 注意：此接口访问的是本地文件系统，而非OSS
     *
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     */
    @RequestMapping("/file/fileManagerJson")
    public void fileManagerJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        // 根目录路径（本地文件系统）
        String rootPath = "/attached/";
        // 根目录URL
        String rootUrl = request.getContextPath() + "/attached/";
        // 允许的图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        String dirName = request.getParameter("dir");
        if (dirName != null) {
            if (!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)) {
                out.println("Invalid Directory name.");
                return;
            }
            rootPath += dirName + "/";
            rootUrl += dirName + "/";
            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }
        // 根据path参数设置各路径和URL
        String path = request.getParameter("path") != null ? request.getParameter("path") : "";
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0, currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }

        // 排序形式：name（按名称）、size（按大小）、type（按类型）
        String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

        // 安全检查：不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            out.println("Access is not allowed.");
            return;
        }
        // 最后一个字符必须是/
        if (!"".equals(path) && !path.endsWith("/")) {
            out.println("Parameter is not valid.");
            return;
        }
        // 检查目录是否存在且是目录
        File currentPathFile = new File(currentPath);
        if (!currentPathFile.isDirectory()) {
            out.println("Directory does not exist.");
            return;
        }

        // 遍历目录获取文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }

        // 按指定方式排序
        if ("size".equals(order)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        JSONObject result = new JSONObject();
        result.put("moveup_dir_path", moveupDirPath);
        result.put("current_dir_path", currentDirPath);
        result.put("current_url", currentUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);

        out.println(result.toJSONString());
    }


    /**
     * 多文件上传接口（商品图片）
     * 一次上传多个文件，文件存储在public/goods/目录下
     * 需要管理员登录权限
     *
     * @param request HTTP请求对象
     * @return 包含所有文件访问URL数组的结果
     */
    @ResponseBody
    @RequestMapping(value = "file/multi_upload", method = RequestMethod.POST)
    public ResultEntity multiFileUpload(HttpServletRequest request) throws Exception {
        Integer adminId = getAdminId();
        if (null == adminId) {
            return ResultEntity.fail("请先登录");
        }
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> files = Murequest.getFileMap();

        // 商品图片存储目录
        String goodsDirPath = "public/goods";
        // 使用UUID的MD5值作为基础文件名
        String fileName = DigestUtil.md5Hex(RandomUtils.randomCustomUUID());

        int counter = 0;
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files.values()) {
            counter++;
            String orginalFileName = file.getOriginalFilename();
            // 获取文件扩展名
            String suffix = orginalFileName.substring(orginalFileName.lastIndexOf(".") + 1);
            // 生成新文件名：基础名_序号.扩展名
            String newFileName = fileName + "_" + counter + "." + suffix;
            String filePath = "/" + goodsDirPath + "/" + newFileName;
            // 上传到OSS
            String url = OSSClientUtils.uploadFile(file.getBytes(), filePath);
            // 从Redis获取图片服务器地址并拼接
            urls.add(redisService.get(ConfigNID.IMAGE_SEVER_URL) + "/" + url);
        }
        System.out.println("接收完毕");
        ResultEntity resultEntity = ResultEntity.success("上传成功");
        Map<String, Object> data = new HashMap<>();
        System.out.println(urls);
        data.put("urlArray", urls);
        resultEntity.setData(data);

        return resultEntity;
    }

    /**
     * 多文件上传接口（树形商品图片）
     * 一次上传多个文件，文件存储在public/tree/目录下
     * 需要管理员登录权限
     *
     * @param request HTTP请求对象
     * @return 包含所有文件访问URL数组的结果
     */
    @ResponseBody
    @RequestMapping(value = "file/tree_multi_upload", method = RequestMethod.POST)
    public ResultEntity treeMultiFileUpload(HttpServletRequest request) throws Exception {
        Integer adminId = getAdminId();
        if (null == adminId) {
            return ResultEntity.fail("请先登录");
        }
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> files = Murequest.getFileMap();

        // 树形商品图片存储目录
        String goodsDirPath = "public/tree";
        // 使用UUID的MD5值作为基础文件名
        String fileName = DigestUtil.md5Hex(RandomUtils.randomCustomUUID());

        int counter = 0;
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files.values()) {
            counter++;
            String orginalFileName = file.getOriginalFilename();
            // 获取文件扩展名
            String suffix = orginalFileName.substring(orginalFileName.lastIndexOf(".") + 1);
            // 生成新文件名：基础名_序号.扩展名
            String newFileName = fileName + "_" + counter + "." + suffix;
            String filePath = "/" + goodsDirPath + "/" + newFileName;
            // 上传到OSS
            String url = OSSClientUtils.uploadFile(file.getBytes(), filePath);
            // 从Redis获取图片服务器地址并拼接
            urls.add(redisService.get(ConfigNID.IMAGE_SEVER_URL) + "/" + url);
        }
        System.out.println("接收完毕");
        ResultEntity resultEntity = ResultEntity.success("上传成功");
        Map<String, Object> data = new HashMap<>();
        data.put("urlArray", urls);
        resultEntity.setData(data);

        return resultEntity;
    }
}
