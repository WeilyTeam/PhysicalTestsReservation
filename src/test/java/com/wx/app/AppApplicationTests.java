package com.wx.app;

import com.wx.app.entity.LoginUser;
import com.wx.app.mapper.MenuMapper;
import com.wx.app.mapper.UserMapper;
import com.wx.app.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.List;

@SpringBootTest
class AppApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisCache redisCache;

    @Test
    void contextLoads() {
        //List<User> users = userMapper.selectList(null);
        //System.out.println(users);

        String redisKey = "login:" + 2;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
    }

    @Test
    public void testPasswordEncoder(){
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String encode = ps.encode("3456456");
        //String encode2 = ps.encode("1234");
        System.out.println(encode);
        //System.out.println(encode2);
        //$2a$10$UViL.jTzZHy/m7K29SuwPenDT5s5XcfIoSHoEJImRBjbsnok3Y7Nu

        System.out.println(ps.matches("3456456",
                "$2a$10$F.dBOqHdwJ8KtH.oMmsL6u4kF/gXTn179LsQOsl0HodFJhQn7/NKu"));
    }

    @Autowired
    private MenuMapper menuMapper;
    @Test
    public void testSelectPermsByUserId(){
        List<String> list = menuMapper.selectPermsByUserId(2L);
        for (String userId : list){
            System.out.println(userId);
        }
    }

    @Test
    public void deleteImg(){
        //定义文件路径
        String filePath = "/images/8e63ea2a-3b0d-42be-be7c-f9e6e5d81e301597585966281.jpg";
        //这里因为我文件是相对路径 所以需要在路径前面加一个点
        File file = new File("."+filePath);
        if (file.exists()){//文件是否存在
            file.delete();//删除文件
        }
        //从数据库删除文件
        //userFileService.deleteById(file_id);
        //剩下的 从硬盘删除 后面再写
        //result.setMsg("删除成功");
        //result.setCode(1);
    }

    @Test
    public void redisTest(){
        String redisKey = "login:" + 55;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (loginUser == null) {
            System.out.println("null");
        }
    }

}
