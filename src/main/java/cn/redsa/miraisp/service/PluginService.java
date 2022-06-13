package cn.redsa.miraisp.service;

import cn.redsa.miraisp.entity.PageBean;
import net.mamoe.mirai.event.Event;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PluginService {

    public void loadPlugins();
    public void pluginsDo(Event event);

    public boolean disablePlugin(String pluginId);

    public boolean enablePlugin(String pluginId);

    public boolean deletePlugin(String pluginId);

    public String uploadPlugin(MultipartFile pluginFile) throws IOException;

    public PageBean listPlugins(Long pageNo, Long pageSize);
}
