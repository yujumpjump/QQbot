package com.jumpjump.event;


import com.jumpjump.api.GameTool;
import com.jumpjump.base.BfvServers;
import com.jumpjump.util.CreateBfvServersImgUtil;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Filters;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.ID;
import love.forte.simbot.LongID;
import love.forte.simbot.component.mirai.MiraiGroup;
import love.forte.simbot.component.mirai.MiraiMember;
import love.forte.simbot.component.mirai.announcement.MiraiAnnouncements;
import love.forte.simbot.component.mirai.event.MiraiGroupMessageEvent;
import love.forte.simbot.component.mirai.event.MiraiMemberJoinEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.*;
import love.forte.simbot.resources.PathResource;
import love.forte.simbot.resources.Resource;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.announcement.Announcements;
import net.mamoe.mirai.contact.announcement.OnlineAnnouncement;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class GroupListeningOperation {


    /**
     * 保存没有看群告的人
     */

    private static Map<String,LongID> LOOKMAP = new HashMap<>();


    /**
     * bfv查询相关的api对象
     */
    private final GameTool gameTool;

    /**
     * 创建图片工具对象
     */
    private final CreateBfvServersImgUtil createBfvServersImgUtil;

    public GroupListeningOperation(GameTool gameTool, CreateBfvServersImgUtil createBfvServersImgUtil) {
        this.gameTool = gameTool;
        this.createBfvServersImgUtil = createBfvServersImgUtil;
    }

    /**
     * 获取 加群的人 并看群告
     * MiraiMemberJoinEvent
     */

    @Listener
    @Filters({
            @Filter(targets = @Filter.Targets(groups = {"865370542"})),
            @Filter(targets = @Filter.Targets(groups = {"865370554"}))
    })
    public void kickHuoYue(MiraiMemberJoinEvent event) throws InterruptedException {
        /*-----------------------禁言部分-------------------------*/
        // 先把加入群里面的人 的id获取下来并且保存到 map里面
        MiraiMember author = event.getAfter();
        String keyUser = author.getId().toString();
        LongID id = author.getId();
        LOOKMAP.put(keyUser, id);

        // 开始禁言
        author.muteBlocking(30, TimeUnit.MINUTES);
        At at = new At(id);
        Messages elements = Messages.toMessages(at, Text.of("\n看群公告解除禁言"));
        event.getGroup().sendBlocking(elements);
        System.out.println("长度"+LOOKMAP.size());
        /*---------------------检查看过公告成员----------------------*/
        while (LOOKMAP.size() >0) {
            Thread.sleep(5000);
            MiraiGroup group = event.getGroup();
            MiraiAnnouncements announcements = group.getAnnouncements();
            Announcements originalAnnouncements = announcements.getOriginalAnnouncements();
            List<OnlineAnnouncement> onlineAnnouncements = originalAnnouncements.toList();
            // 获取群里面的第一个公告
            OnlineAnnouncement onlineAnnouncement = onlineAnnouncements.get(0);
            // 获取群里面都有谁看了公告
            List<NormalMember> members = onlineAnnouncement.members(false);
            for (int i = 0; i < members.size(); i++) {

                // 获取到刚才进群的成员
                String key = members.get(i).toString().substring(13, members.get(i).toString().length() - 1);
                if (LOOKMAP.get(key)==null) {
                    continue;
                }
                // 获取这个新成员进行解除禁言
                MiraiMember member1 = group.getMember(LOOKMAP.get(key));
                member1.unmuteBlocking();
                At at1 = new At(LOOKMAP.get(key));
                Messages elements1 = Messages.toMessages(at1, Text.of("\n检测到你已经阅读新人群公告自动解除禁言"));
                event.getGroup().sendBlocking(elements1);
                LOOKMAP.remove(key);
                }
            }

    }

    /**
     * 监听群内查询服务器的功能 并且生成图片进行发送
     */
    @Listener
    @Filter(targets = @Filter.Targets(groups = {"600723467","865370542"}),value = "!8653",matchType = MatchType.TEXT_EQUALS)
    public void getBfvServers(GroupMessageEvent event){
        List<BfvServers> bfvServer = gameTool.getBfvServer();
        List<BfvServers> bfvServerS = gameTool.getBfvServers();
        BufferedImage img = createBfvServersImgUtil.createImg(730, 2100, bfvServer,bfvServerS);
        try {
            ImageIO.write(img,"jpg",new BufferedOutputStream(new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\img\\8653serverImg.jpg"))));
            PathResource resource = Resource.of(Paths.get("C:\\Users\\admin\\Desktop\\img\\8653serverImg.jpg"));
            At at = new At(event.getAuthor().getId());
            ResourceImage resourceImage = Image.of(resource);
            Messages elements = Messages.toMessages(at, Text.of(" 8653服务器如下\n"));
            event.getGroup().sendBlocking(elements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 监听群里面的关键字
     * 例:被踢了 注册
     */

    @Listener
    @Filters(value = {
            @Filter(value = "被踢",matchType = MatchType.TEXT_CONTAINS),
            @Filter(value = "注册",matchType = MatchType.TEXT_CONTAINS)
    })
    public void  onKeyword(MiraiGroupMessageEvent event){
        LongID id = event.getAuthor().getId();
        // 获取 关键字
        String keyWord = event.getMessageContent().getPlainText();
        if(keyWord.contains("被踢")){
            event.getGroup().sendBlocking("pb="+event.getAuthor().getNickname());
        }else {
            try {
                PathResource resource = Resource.of(Paths.get("C:\\Users\\admin\\Desktop\\img\\reg.jpg"));
                ResourceImage resourceImage = Image.of(resource);
                Messages elements = Messages.toMessages(new At(id), Text.of("\n注册如下图\n")).plus(Messages.toMessages(resourceImage));
                event.getGroup().sendBlocking(elements);
            } catch (Exception e) {
                event.getGroup().sendBlocking("注册图片消失了...@管理员检查！");
            }
        }
    }

    /**
     * 监听管理员来进行操作
     * 可以进行的操作：修改群成员名片，
     */
    @Listener
    @Filter(value = "修改昵称",matchType = MatchType.TEXT_CONTAINS,targets = @Filter.Targets(groups = {"865370542"}))
    public void onEventAdmin(MiraiGroupMessageEvent miraiGroupMessageEvent) {
        //判断是否为管理员说话
        if(miraiGroupMessageEvent.getAuthor().isAdmin()){
            // 获取命令语句
            String plainText = miraiGroupMessageEvent.getMessageContent().getPlainText();
            // 分割命令语句 得到 qq号，和新的昵称
            String[] arr = plainText.split(" ");
            // 把字符串类型为ID类型
            ID user = ID.$(arr[1]);
            MiraiMember member = null;
            try {
                member = miraiGroupMessageEvent.getGroup().getMember(user);
                member.setNickname(arr[2]);
                miraiGroupMessageEvent.getGroup().sendBlocking("昵称修改成功");
            }catch (Exception e){
                miraiGroupMessageEvent.getGroup().sendBlocking("请输入正确的修改命令！");
            }
        }
    }

}
