package com.wx.app.service.impl;

import com.wx.app.entity.ImgAll;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.ImgAllMapper;
import com.wx.app.service.ImageService;
import com.wx.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author lingqu
 * @date 2022/3/26
 * @apiNote
 */
@Service
@Slf4j
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImgAllMapper imgAllMapper;

    @Override
    public Result uploadImage(MultipartFile file) {
        File absolutePath = new File("images");
        log.info("绝对地址：{}",absolutePath.getAbsolutePath());
        String PATH =  absolutePath.getAbsolutePath();
        //String PATH = "/opt/reserveApp/images";
        //获取文件名
        String fileName = file.getOriginalFilename();
        String[] split = fileName.split("\\.");
        // 图片存储目录及图片名称
        String urlPath = "/" + UUID.randomUUID() + "." +split[split.length - 1];
        //图片保存路径
        String savePath = PATH + urlPath;
        log.info("图片保存地址：{}",savePath);
        // 访问路径=静态资源路径+文件目录路径
        log.info("图片访问保存到数据库去：{}", urlPath);

        File saveFile = new File(savePath);
        if (!absolutePath.exists()){
            absolutePath.mkdirs();
        }
        try {
            //将临时存储的文件移动到真实存储路径下
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(CommonCode.FAILURE_UPLOAD_IMG);
        }
        //将文件路径添加到数据库
        ImgAll images = new ImgAll(urlPath);
        imgAllMapper.insert(images);
        return new Result(CommonCode.SUCCESS, urlPath);
    }
}
