package com.jumpjump.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jumpjump.base.BfvServers;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * 战地5的API
 */
@Component
public class GameTool {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 查询所有的服务器
     *
     * @return
     */
    public List<BfvServers> getBfvServer() {
        String url = "https://api.gametools.network/bfv/servers/?name=8653&platform=pc&limit=100&lang=zh-CN";
        String forObject1 = restTemplate.getForObject(url, String.class);
        // 先获取key值
        JSONObject jsonObject = JSON.parseObject(forObject1);
        String servers = jsonObject.getString("servers");
        // 在转换为java对象
       return JSONArray.parseArray(servers, BfvServers.class);
    }


    /**
     * 获取图片资源
     */

    public String getImgPath(String url){
        ResponseEntity<byte[]> exchange = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
        byte[] body = exchange.getBody();
        FileOutputStream fileOutputStream = null;
        try {

            //创建输出流  输出到本地
          fileOutputStream = new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\img\\bfvServiceImg.jpg"));
          fileOutputStream.write(body);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "C:\\Users\\admin\\Desktop\\img\\bfvServiceImg.jpg";
    }

    /**
     * 获取所以的服务器
     * @return
     */
    public List<BfvServers> getBfvServers() {
        String url = "https://api.gametools.network/bfv/servers/?name=BFV&region=all&platform=pc&limit=100&lang=zh-CN";
        String forObject = restTemplate.getForObject(url, String.class);
        // 先获取key值
        JSONObject jsonObject = JSON.parseObject(forObject);
        String servers = jsonObject.getString("servers");
        // 在转换为java对象
        return JSONArray.parseArray(servers, BfvServers.class);
    }








}
