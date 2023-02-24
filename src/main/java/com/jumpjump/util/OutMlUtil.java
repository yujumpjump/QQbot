package com.jumpjump.util;

import com.alibaba.fastjson2.JSON;
import com.jumpjump.base.OutMl;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;



public class OutMlUtil {

    /**
     * 读取外部配置命令文件
     * @return
     */
    public static  List<OutMl> getNoticeInfoList(){
        File file = null;
        try {
            file = ResourceUtils.getFile("C:\\Users\\admin\\Desktop\\img\\OutMl.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String json = null;
        try {
            json = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<OutMl> noticeInfoList = JSON.parseArray(json, OutMl.class);
        return noticeInfoList;
    }

}
