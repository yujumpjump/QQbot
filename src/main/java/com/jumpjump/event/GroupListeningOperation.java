package com.jumpjump.event;


import com.jumpjump.api.GameTool;
import com.jumpjump.base.BfvServers;
import com.jumpjump.util.CreateBfvServersImgUtil;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Image;
import love.forte.simbot.message.ResourceImage;
import love.forte.simbot.resources.PathResource;
import love.forte.simbot.resources.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Component
public class GroupListeningOperation {


    /**
     * bfv查询相关的api对象
     */
    @Autowired
    private GameTool gameTool;

    /**
     * 创建图片工具对象
     */
    @Autowired
    private CreateBfvServersImgUtil createBfvServersImgUtil;

    /**
     * 监听群里面
     */

    @Listener
    @Filter(targets = @Filter.Targets(groups = {"729709028"}))
    public void getBfvServers(GroupMessageEvent event){
        List<BfvServers> bfvServer = gameTool.getBfvServer();
        List<BfvServers> bfvServerS = gameTool.getBfvServers();
        BufferedImage img = createBfvServersImgUtil.createImg(730, 2100, bfvServer,bfvServerS);
        try {
            ImageIO.write(img,"jpg",new BufferedOutputStream(new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\img\\a.jpg"))));
            PathResource resource = Resource.of(Paths.get("C:\\Users\\admin\\Desktop\\img\\a.jpg"));
            ResourceImage resourceImage = Image.of(resource);
            event.getGroup().sendBlocking(resourceImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
