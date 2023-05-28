package com.ambisiss.mongodb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Author: chenxiaoye
 * @Description: 私聊记录添加
 * @Data: 2023-5-26 10:37:44
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChChatMsgInsertDto {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("发送者ID")
    private Long senderId;

    @ApiModelProperty("接受者ID")
    private Long receiverId;

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("消息类型")
    private Long messageTypeId;

    @ApiModelProperty(value = "消息uuid", hidden = true)
    private String messageUuid;
}
