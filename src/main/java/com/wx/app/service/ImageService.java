package com.wx.app.service;

import com.wx.app.utils.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lingqu
 * @date 2022/3/26
 * @apiNote
 */
public interface ImageService {
    Result uploadImage(MultipartFile file);
}
