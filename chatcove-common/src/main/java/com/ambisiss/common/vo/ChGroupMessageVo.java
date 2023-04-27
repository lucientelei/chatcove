package com.ambisiss.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 18:52:41
 */

@Getter
@Setter
@ApiModel(value = "ChGroupMessageVo对象", description = "群组消息显示对象")
public class ChGroupMessageVo {

    private Long id;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("消息类型")
    private Long messageType;

    @ApiModelProperty("发送时间")
    private LocalDateTime createTime;

}
