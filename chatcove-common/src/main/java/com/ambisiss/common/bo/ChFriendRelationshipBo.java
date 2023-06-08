package com.ambisiss.common.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-23 17:12:22
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChFriendRelationshipBo对象", description = "")
public class ChFriendRelationshipBo {

    @ApiModelProperty("好友ID")
    private Long userId;

    @ApiModelProperty("备注昵称")
    private String nickName;

}
