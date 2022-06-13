package cn.redsa.miraisp.events;

import cn.redsa.miraisp.service.PluginService;
import lombok.Setter;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Setter
@Component
@ConfigurationProperties(prefix = "bots")
public class IOCEvents {
    @Resource
    PluginService pluginService;
    @Resource
    BotConfiguration conf;
    @Resource
    EventsHandler eventsHandler;
    Map<Long, String> bots;

    @PostConstruct
    public void postDo(){
        if (bots!=null && bots.size()>0){
            bots.forEach((id, password)->{
                Bot bot = BotFactory.INSTANCE.newBot(id , password, conf);
                bot.login();
                bot.getEventChannel().registerListenerHost(eventsHandler);
            });
        }
        pluginService.loadPlugins();
    }
}
