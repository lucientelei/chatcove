package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 19:00:03
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChChatMessageDto对象", description = "ChChatMessageDto新增dto")
public class ChChatMessageDto {

    @NotBlank
    @ApiModelProperty("发送者ID")
    private Long senderId;

    @NotBlank
    @ApiModelProperty("接收者ID")
    private Long receiverId;

    @NotBlank
    private String message;

    @NotBlank
    private Long messageTypeId;

}
