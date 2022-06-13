package cn.redsa.miraisp;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageSyncEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.DeviceInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiraiSpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiraiSpApplication.class, args);
	}

}
