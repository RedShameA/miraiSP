package cn.redsa.miraisp.config;

import cn.redsa.miraisp.events.EventsHandler;
import cn.redsa.miraisp.utils.MyLoginSolver;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.LoggerAdapters;
import net.mamoe.mirai.utils.LoginSolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class BotConfig {

    /** mirai logger */
    protected Logger miraiLogger = LoggerFactory.getLogger("mirai.logger");
    protected Logger miraiNetLogger = LoggerFactory.getLogger("mirai.net.logger");


    @Bean
    public BotConfiguration createBotConfiguration(){
        BotConfiguration conf = new BotConfiguration();
        conf.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
        conf.setBotLoggerSupplier(bot-> LoggerAdapters.asMiraiLogger(miraiLogger));
        conf.setNetworkLoggerSupplier(bot-> LoggerAdapters.asMiraiLogger(miraiNetLogger));
        conf.fileBasedDeviceInfo();
        return conf;
    }
}
