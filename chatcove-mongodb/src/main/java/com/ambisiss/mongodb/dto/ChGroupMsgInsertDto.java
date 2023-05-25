package com.ambisiss.mongodb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Author: chenxiaoye
 * @Description: 添加群聊信息dto
 * @Data: 2023-5-25 11:09:32
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChGroupMsgInsertDto {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("群聊ID")
    private Long groupId;

    @ApiModelProperty("发送者ID")
    private Long senderId;

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("消息类型")
    private Long messageTypeId;

}
