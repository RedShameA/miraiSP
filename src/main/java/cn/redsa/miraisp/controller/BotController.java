package cn.redsa.miraisp.controller;

import cn.redsa.miraisp.service.BotService;
import cn.redsa.miraisp.vo.ResultMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/bot")
public class BotController {

    @Resource
    BotService botService;

    @GetMapping("/list")
    public ResultMap list(){
        List list = botService.listBots();
        return new ResultMap().success().data(list);
    }

    @GetMapping("/listFriends")
    public ResultMap list(Long botId){
        List list = botService.listFriends(botId);
        return new ResultMap().success().data(list);
    }
}
