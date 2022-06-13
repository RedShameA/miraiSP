package cn.redsa.miraisp.service;

import java.util.List;

public interface BotService {
    List listBots();
    List listFriends(Long botId);
}
