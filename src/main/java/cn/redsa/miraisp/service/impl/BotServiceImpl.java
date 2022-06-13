package cn.redsa.miraisp.service.impl;

import cn.redsa.miraisp.entity.BotDTO;
import cn.redsa.miraisp.service.BotService;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BotServiceImpl implements BotService {



    @Override
    public List listBots() {
        List<Bot> instances = Bot.getInstances();
        List<BotDTO> bots = new ArrayList<>();
        instances.forEach(e->{
            BotDTO botDTO = new BotDTO().setId(e.getId()).setNickname(e.getNick());
            bots.add(botDTO);
        });
        return bots;
    }

    @Override
    public List listFriends(Long botId) {
        Bot instance = Bot.getInstance(botId);
        ContactList<Friend> friends = instance.getFriends();
        List<BotDTO> bots = new ArrayList<>();
        friends.forEach(e->{
            BotDTO botDTO = new BotDTO().setId(e.getId()).setNickname(e.getNick()).setRemark(e.getRemark());
            bots.add(botDTO);
        });
        return bots;
    }
}
