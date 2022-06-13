package cn.redsa.miraisp.utils;

import cn.redsa.miraisp.entity.LoginObject;
import cn.redsa.miraisp.events.EventsHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class BotManager {

    @Resource
    private BotConfiguration conf;
    @Resource
    EventsHandler eventsHandler;

    public void login(Long id, String password){
        Bot bot = BotFactory.INSTANCE.newBot(id , password, conf);
        bot.getEventChannel().registerListenerHost(eventsHandler);
        bot.login();
    }

}
