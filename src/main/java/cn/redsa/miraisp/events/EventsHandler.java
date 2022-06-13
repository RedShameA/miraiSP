package cn.redsa.miraisp.events;

import cn.redsa.miraisp.service.PluginService;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EventsHandler extends SimpleListenerHost {
    @Value("${admin.id}")
    Long adminId;

    @Resource
    PluginService pluginService;
    @EventHandler
    public void onEvent(@NotNull Event event) {
        pluginService.pluginsDo(event);
    }
}
