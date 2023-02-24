package com.jumpjump.event;


import com.jumpjump.base.OutMl;
import com.jumpjump.util.OutMlUtil;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.event.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NiggerColony {

    /**
     * 黑奴群里的快捷指令
     */

    @Listener
    @Filter(targets = @Filter.Targets(groups = {"600723467"}))
    public void onZhiLing(GroupMessageEvent event){
        List<OutMl> noticeInfoList = OutMlUtil.getNoticeInfoList();
        OutMl outMl = noticeInfoList.get(0);
        String seeding = ".seeding ";
        switch (event.getMessageContent().getPlainText()){
            case "N1" -> event.getGroup().sendBlocking(seeding+outMl.getN1());
            case "N2" -> event.getGroup().sendBlocking(seeding+outMl.getN2());
            case "N3" -> event.getGroup().sendBlocking(seeding+outMl.getN3());
            case "N4" -> event.getGroup().sendBlocking(seeding+outMl.getN4());
            case "N5" -> event.getGroup().sendBlocking(seeding+outMl.getN5());
            case "N6" -> event.getGroup().sendBlocking(seeding+outMl.getN6());
            case "N7" -> event.getGroup().sendBlocking(seeding+outMl.getN7());
            case "N8" -> event.getGroup().sendBlocking(seeding+outMl.getN8());
            case "N9" -> event.getGroup().sendBlocking(seeding+outMl.getN9());
            case "N10" -> event.getGroup().sendBlocking(seeding+outMl.getN10());
            case "N11" -> event.getGroup().sendBlocking(seeding+outMl.getN11());
            case "N12" -> event.getGroup().sendBlocking(seeding+outMl.getN12());
            case "N13" -> event.getGroup().sendBlocking(seeding+outMl.getN13());
            case "N14" -> event.getGroup().sendBlocking(seeding+outMl.getN14());
            case "N15" -> event.getGroup().sendBlocking(seeding+outMl.getN15());
            case "N16" -> event.getGroup().sendBlocking(seeding+outMl.getN16());
            case "N17" -> event.getGroup().sendBlocking(seeding+outMl.getN17());
            case "N18" -> event.getGroup().sendBlocking(seeding+outMl.getN18());
            case "lk" -> event.getGroup().sendBlocking(outMl.getLK());
            case "fwq" -> event.getGroup().sendBlocking(outMl.getFWQ());
            case "jc" -> event.getGroup().sendBlocking(outMl.getJC());
            case "gb" -> event.getGroup().sendBlocking(outMl.getGB());
        }
    }




}
