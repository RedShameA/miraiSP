package cn.redsa.miraisp.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BotDTO {
    private Long id;
    private String nickname;
    private String remark;
}
