package cn.redsa.miraisp.controller;

import cn.hutool.core.util.StrUtil;
import cn.redsa.miraisp.entity.PageBean;
import cn.redsa.miraisp.service.PluginService;
import cn.redsa.miraisp.vo.ResultMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;


@RestController
@RequestMapping("/plugin")
public class PluginController {

    @Resource
    PluginService pluginService;

    @GetMapping("/list")
    public ResultMap list(Long pageNo, Long pageSize){
        PageBean plugins = pluginService.listPlugins(pageNo, pageSize);
        return new ResultMap().success().data(plugins);
    }
    @GetMapping("/enable")
    public ResultMap enable(String pluginId){
        boolean b = pluginService.enablePlugin(pluginId);
        return ResultMap.successOrFail(b);
    }
    @GetMapping("/disable")
    public ResultMap disable(String pluginId){
        boolean b = pluginService.disablePlugin(pluginId);
        return ResultMap.successOrFail(b);
    }

    @GetMapping("/delete")
    public ResultMap delete(String pluginId){
        boolean b = pluginService.deletePlugin(pluginId);
        return ResultMap.successOrFail(b);
    }

    @PostMapping("/upload")
    public ResultMap upload(MultipartFile pluginFile) throws IOException {
        String pluginId = pluginService.uploadPlugin(pluginFile);
        if (StrUtil.isEmpty(pluginId)){
            return new ResultMap().fail().message("上传失败，不是有效插件");
        }else if(pluginId.equals("already_contains")){
            return new ResultMap().fail().message("上传失败，已存在相同ID插件");
        }else{
            System.gc();
            return new ResultMap().success().message("上传成功,插件ID: " + pluginId);
        }
    }

}
