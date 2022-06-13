package cn.redsa.miraisp.utils;

import cn.redsa.miraisp.entity.LoginObject;
import kotlin.coroutines.Continuation;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.network.LoginFailedException;
import net.mamoe.mirai.network.RetryLaterException;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Deprecated
//@Component
public class MyLoginSolver extends LoginSolver {

    private Map<Long, LoginObject> bots = new HashMap<>();
    private long maxTryTime = 60*1000L; //60s
    private Long slideStartTime;
    private Long safeStartTime;

    @Nullable
    @Override
    public Object onSolvePicCaptcha(@NotNull Bot bot, @NotNull byte[] bytes, @NotNull Continuation<? super String> continuation) {
        log.info("图片验证");
        log.info("Bot=>{}", bot.getId());
        log.info("图片验证码，暂不支持。");
        throw new RuntimeException("图片验证码，暂不支持。");
    }

    @Nullable
    @Override
    public Object onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        long id = bot.getId();
        log.info("滑动验证");
        log.info("Bot=>{}", id);
        log.info("URL=>{}", s);
        this.setSlide(id);
        long maxTryTime = 60*1000L; //60s
        if (slideStartTime == null) {
            slideStartTime = System.currentTimeMillis();
        }
        while (true){
            long nowTime = System.currentTimeMillis();
            if (nowTime-slideStartTime>maxTryTime){
                break; // 超时跳出
            }
            String slideResult = this.getSlideResult(id);
            if (slideResult!=null){
                return slideResult;
            }
        }
        throw new RuntimeException("滑动验证码失败");
    }

    @Nullable
    @Override
    public Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        long id = bot.getId();
        log.info("设备安全验证");
        log.info("Bot=>{}", id);
        log.info("URL=>{}", s);
        this.setSafe(id);
        if (safeStartTime == null) {
            safeStartTime = System.currentTimeMillis();
        }
        while (true){
            long nowTime = System.currentTimeMillis();
            if (nowTime-safeStartTime>maxTryTime){
                break; // 超时跳出
            }
            String safeResult = this.getSafeResult(id);
            if (safeResult!=null){
                return safeResult;
            }
        }
        throw new RuntimeException("设备安全验证失败");
    }


    public void setSlide(Long id){
        LoginObject loginObject = this.bots.getOrDefault(id, new LoginObject());
        loginObject.setStatus(1);
        this.bots.put(id, loginObject);
    }
    public void setSafe(Long id){
        LoginObject loginObject = this.bots.getOrDefault(id, new LoginObject());
        loginObject.setStatus(2);
        this.bots.put(id, loginObject);
    }

    public void setSuccess(Long id){
        LoginObject loginObject = this.bots.getOrDefault(id, new LoginObject());
        loginObject.setStatus(3);
        this.bots.put(id, loginObject);
    }

    public String getSlideResult(Long id){
        LoginObject loginObject = this.bots.getOrDefault(id, new LoginObject());
        return loginObject.getSlideResult();
    }

    public String getSafeResult(Long id){
        LoginObject loginObject = this.bots.getOrDefault(id, new LoginObject());
        return loginObject.getSafeResult();
    }
}
