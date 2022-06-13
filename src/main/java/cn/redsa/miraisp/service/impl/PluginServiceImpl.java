package cn.redsa.miraisp.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.redsa.miraisp.entity.PageBean;
import cn.redsa.miraisp.entity.PluginBook;
import cn.redsa.miraisp.entity.PluginInfo;
import cn.redsa.miraisp.repository.PluginBookRepository;
import cn.redsa.miraisp.service.PluginService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Service
@Slf4j
public class PluginServiceImpl implements PluginService {

    @Resource
    PluginBookRepository pluginBookRepository;

    @Value("${plugin.path}")
    String pluginPath;
    // 插件储存变量
    private Set<PluginInfo> plugins = new LinkedHashSet<>();

    @Override
    public void loadPlugins() {
        log.info("开始加载插件...");
        List<File> pluginFiles = this.getAllPluginFiles();
        pluginFiles.forEach(file -> {
            if (file.getAbsolutePath().endsWith(".jar")){
                this.parsePlugin(this.plugins, file);
                file = null;
            }
        });
        this.loadPluginStatus();
        System.gc();
        log.info("插件加载完毕，加载成功{}个", this.plugins.size());
    }

    @Override
    public void pluginsDo(Event event) {
        this.plugins.forEach(m->{
            if (m.getEnable()){
                Object instance = m.getInstance();
                Method method = m.getExec();
                ThreadUtil.execute(()->{
                    try {
                        method.invoke(instance, event);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

            }
        });
    }

    @Override
    public boolean disablePlugin(String pluginId) {
        AtomicBoolean ret = new AtomicBoolean(false);
        this.plugins.forEach(e->{
            if (e.getId().equals(pluginId)){
                PluginBook byPluginId = pluginBookRepository.findPluginBookByPluginId(pluginId);
                if (null==byPluginId) {
                    byPluginId = new PluginBook();
                    byPluginId.setPluginId(pluginId);
                    byPluginId.setEnable(false);
                }else{
                    byPluginId.setEnable(false);
                }
                pluginBookRepository.saveAndFlush(byPluginId);
                e.setEnable(false);
                ret.set(true);
            }
        });
        return ret.get();
    }

    @Override
    public boolean enablePlugin(String pluginId) {
        AtomicBoolean ret = new AtomicBoolean(false);
        this.plugins.forEach(e->{
            if (e.getId().equals(pluginId)){
                PluginBook byPluginId = pluginBookRepository.findPluginBookByPluginId(pluginId);
                if (null==byPluginId) {
                    byPluginId = new PluginBook();
                    byPluginId.setPluginId(pluginId);
                    byPluginId.setEnable(true);
                }else{
                    byPluginId.setEnable(true);
                }
                pluginBookRepository.saveAndFlush(byPluginId);
                e.setEnable(true);
                ret.set(true);
            }
        });
        return ret.get();
    }

    @Override
    public boolean deletePlugin(String pluginId) {
        PluginInfo pluginInfo = this.plugins.stream().filter(e -> e.getId().equals(pluginId)).findFirst().get();
        if (null == pluginInfo){
            return false;
        }else{
            File path = pluginInfo.getPath();
            boolean delete = path.delete();
            if (delete){
                this.plugins.remove(pluginInfo);
            }
            return delete;
        }
    }

    @Override
    public String uploadPlugin(MultipartFile pluginFile) {
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        File tempJar = new File(tempDir, System.currentTimeMillis()+".jar");
        try{
            pluginFile.transferTo(tempJar);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        String pluginId = this.handleUploadedPlugin(this.plugins, tempJar);
        if (StrUtil.isNotEmpty(pluginId)){
            File pluginToPath = new File(new File(pluginPath), pluginId + ".jar");
            File copy = FileUtil.copy(tempJar, pluginToPath, true);
            //boolean b = tempJar.renameTo(pluginToPath);
            if (!copy.exists()){
                this.deletePluginIgnoreFile(pluginId);
                pluginId = "";
            }
        }
        return pluginId;
    }

    @Override
    public PageBean listPlugins(Long pageNo, Long pageSize) {
        List<Map<String, Object>> ret = new ArrayList<>();
        Long start = (pageNo-1)*pageSize;
        Long stop = (start+pageSize)>(this.plugins.size())?(this.plugins.size()):(start+pageSize);

        for (Long i = start; i < stop; i++) {

            Object[] objects =  this.plugins.toArray();
            PluginInfo e = (PluginInfo)objects[Math.toIntExact(i)];
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("desc", e.getDesc());
            map.put("enable", e.getEnable());
            ret.add(map);
        }
        return new PageBean<>((long) this.plugins.size(), ret);
    }

    private List<File> getAllPluginFiles() {
        File pluginPathFile = new File(pluginPath);
        File[] files = pluginPathFile.listFiles();
        ArrayList<File> fileList = new ArrayList<>();
        if (files!=null){
            Collections.addAll(fileList, files);
        }
        return fileList;
    }

    private boolean parsePlugin(Set pluginsSet, File file){
        try{
            JarFile jarFile = new JarFile(file.getAbsoluteFile());
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (name.endsWith("PluginInstance.class")){
                    URL url = file.toURI().toURL();
                    URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
                    Class<?> aClass = urlClassLoader.loadClass(name.substring(0,name.length()-6).replace("/", "."));
                    PluginInfo pluginInfo = new PluginInfo();
                    // instance
                    Object instance = aClass.getDeclaredConstructor().newInstance();
                    pluginInfo.setInstance(instance);
                    // id
                    Method getId = aClass.getMethod("id");
                    if (null == getId){
                        return false;
                    }
                    String id = (String) getId.invoke(instance);
                    pluginInfo.setId(id);
                    // Desc
                    Method desc = aClass.getMethod("desc");
                    if (null == desc){
                        return false;
                    }
                    String descContent = (String) desc.invoke(instance);
                    pluginInfo.setDesc(descContent);
                    // init
                    Method init = aClass.getMethod("init");
                    if (null == init){
                        return false;
                    }
                    init.invoke(instance);
                    // method
                    Method exec = aClass.getMethod("exec", Event.class);
                    if (null == exec){
                        return false;
                    }
                    pluginInfo.setExec(exec);
                    pluginInfo.setPath(file);
                    pluginsSet.add(pluginInfo);
                    urlClassLoader.close();
                    return true;
                }
            }
            jarFile.close();
            return false;
        }catch (Exception e){
            return false;
        }
    }

    private String  handleUploadedPlugin(Set pluginsSet, File file){
        try{
            JarFile jarFile = new JarFile(file.getAbsoluteFile());
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (name.endsWith("PluginInstance.class")){
                    URL url = file.toURI().toURL();
                    URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
                    Class<?> aClass = urlClassLoader.loadClass(name.substring(0,name.length()-6).replace("/", "."));
                    PluginInfo pluginInfo = new PluginInfo();
                    // instance
                    Object instance = aClass.getDeclaredConstructor().newInstance();
                    pluginInfo.setInstance(instance);
                    // id
                    Method getId = aClass.getMethod("id");
                    if (null == getId){
                        return null;
                    }
                    String id = (String) getId.invoke(instance);
                    pluginInfo.setId(id);
                    // Desc
                    Method desc = aClass.getMethod("desc");
                    if (null == desc){
                        return null;
                    }
                    String descContent = (String) desc.invoke(instance);
                    pluginInfo.setDesc(descContent);
                    // init
                    Method init = aClass.getMethod("init");
                    if (null == init){
                        return null;
                    }
                    init.invoke(instance);
                    // method
                    Method exec = aClass.getMethod("exec", Event.class);
                    if (null == exec){
                        return null;
                    }
                    pluginInfo.setExec(exec);
                    pluginInfo.setPath(file);
                    if (pluginsSet.contains(pluginInfo)){
                        return "already_contains";
                    }
                    pluginsSet.add(pluginInfo);
                    urlClassLoader.close();
                    return pluginInfo.getId();
                }
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    private void loadPluginStatus(){
        this.plugins.forEach(e->{
            String id = e.getId();
            PluginBook byPluginId = pluginBookRepository.findPluginBookByPluginId(id);
            if (null != byPluginId){
                e.setEnable(byPluginId.getEnable());
            }else{
                PluginBook pluginBook = new PluginBook().setPluginId(id);
                pluginBookRepository.saveAndFlush(pluginBook);
            }
        });
    }

    private boolean deletePluginIgnoreFile(String pluginId) {
        PluginInfo pluginInfo = this.plugins.stream().filter(e -> e.getId().equals(pluginId)).findFirst().get();
        if (null == pluginInfo){
            return false;
        }else{
            boolean remove = this.plugins.remove(pluginInfo);
            return remove;
        }
    }
}
