package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-23 17:55:38
 */

@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChFriendshipApplyDto对象", description = "")
public class ChFriendshipApplyDto {

    @ApiModelProperty("发起好友申请的用户ID")
    private Long userId;

    @ApiModelProperty("接收好友申请的用户ID")
    private Long friendId;

    @ApiModelProperty("申请留言")
    private String message;
}
