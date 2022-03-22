package com.wx.app.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.PageDTO;
import com.wx.app.enums.CommonCode;
import com.wx.app.excel.DemoDAO;
import com.wx.app.excel.DemoData;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.StudentFreeTestService;
import com.wx.app.utils.Result;
import com.wx.app.vo.StudentFreeTestVo;
import com.wx.app.vo.StudentInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lingqu
 * @date 2022/3/12
 * @apiNote
 */

@RestController
@Slf4j
@Api(value = "Excel导入导出",tags = "Excel导入导出")
@RequestMapping("/excel")
public class ExcelController {


    @Autowired
    private DemoDAO demoDAO;

    @Autowired
    private StudentFreeTestService studentFreeTestService;

    @Autowired
    private UserMapper userMapper;


    /**
     * 文件上传
     * 1. 创建excel对应的实体对象
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器
     * 3. 直接读即可
     */
    @PostMapping("uploadFreeTestStudent")
    @ApiOperation(value = "Excel上传")
    public Result upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), StudentFreeTestVo.class, new ReadListener<StudentFreeTestVo>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<StudentFreeTestVo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(StudentFreeTestVo data, AnalysisContext context) {

                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                for(StudentFreeTestVo s:cachedDataList){
                    log.info("数据：{}", s.toString());
                }
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();
        //EasyExcel.read(file.getInputStream(), StudentInfoVo.class, new DemoDataListener(demoDAO)).sheet().doRead();
        return new Result(CommonCode.SUCCESS);
    }

    /**
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     */
    @GetMapping("download")
    @ApiOperation(value = "Excel下载")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), DemoData.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(data());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    @GetMapping("downloadFreeTestStudent")
    @ApiOperation(value = "免测学生Excel下载")
    public void downloadFreeTestStudent(PageDTO pageDTO, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("免测学生", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            Result result = studentFreeTestService.freeTestList(pageDTO);
            Page<StudentFreeTestVo> data = (Page<StudentFreeTestVo>)result.getData();
            List<StudentFreeTestVo> records = data.getRecords();
            EasyExcel.write(response.getOutputStream(), StudentFreeTestVo.class).autoCloseStream(Boolean.FALSE).sheet("免测学生")
                    .doWrite(records);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    @GetMapping("downloadStudentList")
    @ApiOperation(value = "学生信息Excel下载")
    public void downloadStudentList(PageDTO pageDTO, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("学生信息", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            Page<StudentInfoVo> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
            Page<StudentInfoVo> data = userMapper.getStudentList(page);
            List<StudentInfoVo> records = data.getRecords();
            EasyExcel.write(response.getOutputStream(), StudentInfoVo.class).autoCloseStream(Boolean.FALSE).sheet("学生信息")
                    .doWrite(records);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    @PostMapping("uploadStudentList")
    @ApiOperation(value = "学生信息Excel上传")
    public Result uploadStudentList(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), StudentInfoVo.class, new ReadListener<StudentInfoVo>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<StudentInfoVo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(StudentInfoVo data, AnalysisContext context) {

                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                for(StudentInfoVo s:cachedDataList){
                    log.info("数据：{}", s.toString());
                }
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();
        //EasyExcel.read(file.getInputStream(), StudentInfoVo.class, new DemoDataListener(demoDAO)).sheet().doRead();
        return new Result(CommonCode.SUCCESS);
    }


    private String PATH = "E:\\学习\\web前后端\\预学生约系统\\wxApp\\target\\" ;
    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
