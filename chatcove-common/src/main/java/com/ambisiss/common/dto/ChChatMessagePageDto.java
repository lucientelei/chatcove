package com.ambisiss.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Author: chenxiaoye
 * @Description: ChChatRecords分页查询dto
 * @Data: 2023-4-20 19:36:21
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChChatMessagePageDto对象", description = "ChChatMessagePageDto查询dto")
public class ChChatMessagePageDto {

    @NotBlank
    @ApiModelProperty("发送者ID")
    private Long senderId;

    @NotBlank
    @ApiModelProperty("接收者ID")
    private Long receiverId;

    private String message;

    private Long messageTypeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
