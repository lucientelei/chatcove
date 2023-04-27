package com.ambisiss.common.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 14:14:04
 */
@Getter
@Setter
@ApiModel(value = "ChGroupMessageDto对象", description = "群组消息传输对象")
public class ChGroupMessageDto {

    private Long groupId;

    private Long senderId;

    private String message;

    private Long messageType;
}
