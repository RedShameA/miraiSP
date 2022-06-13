package cn.redsa.miraisp.entity;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddJobDTO {

    @NotEmpty(message = "任务名不能为空")
    private String jobName;

    @NotEmpty(message = "发送人不能为空")
    private String sender;

    @NotEmpty(message = "接收人不能为空")
    private String to;

    @NotEmpty(message = "消息不能为空")
    private String msg;

    @NotEmpty(message = "Cron不能为空")
    private String cron;
}
