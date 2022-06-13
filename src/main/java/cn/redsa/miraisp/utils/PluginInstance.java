package cn.redsa.miraisp.utils;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;

public class PluginInstance {
    private String str = null;

    // 插件id
    public String id(){
        return "cn.plugin.demo4";
    }
    // 插件描述
    public String desc(){
        return "我是一个测试插件";
    }
    // init事件 初始化用
    public void init(){
    }
    // event事件 有event会调用此方法
    public void exec(Event event){
        if (event instanceof FriendMessageEvent){
            ((FriendMessageEvent)event).getSender().sendMessage("reply str");
            MessageChain singleMessages = MessageUtils.newChain(new PlainText("also you send: "), ((FriendMessageEvent)event).getMessage());
            ((FriendMessageEvent)event).getSender().sendMessage(singleMessages);
        }

    }
}
