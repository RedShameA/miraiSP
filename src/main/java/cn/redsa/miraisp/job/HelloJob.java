package cn.redsa.miraisp.job;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.NoSuchElementException;

@Slf4j
@DisallowConcurrentExecution
public class HelloJob implements Job {


    @Override
    public void execute(JobExecutionContext context) {
        //QuartzService quartzService = (QuartzService) SpringUtil.getBean("quartzServiceImpl");
        //System.out.println("Hello job");
        long sender = Long.parseLong(context.getJobDetail().getJobDataMap().getString("sender")) ;
        long to = Long.parseLong(context.getJobDetail().getJobDataMap().getString("to"));
        String  msg = context.getJobDetail().getJobDataMap().getString("msg");
        log.info("Hello Job开始执行: {} -> {}", sender, to);
        Bot bot;
        try{
            bot = Bot.getInstance(sender);
        }catch(NoSuchElementException e){
            log.error("没有qq号为{}的Bot", sender);
            return;
        }
        if (!bot.isOnline()) {
            log.error("Bot({})没有在线", sender);
            return;
        }
        Friend friend = bot.getFriend(to);
        if (ObjectUtil.isNull(friend)){
            log.error("没有qq号为{}的好友", to);
            return;
        }
        friend.sendMessage(msg);
        //System.out.println(bot);

    }
}

