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
    private Long friendId;

    @ApiModelProperty("添加状态")
    private Long statusId;

    @ApiModelProperty("是否置顶联系人 1：是 0：否")
    private String isTop;
}
