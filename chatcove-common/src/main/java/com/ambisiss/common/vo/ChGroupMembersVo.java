package com.ambisiss.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 13:40:21
 */
@Getter
@Setter
@ApiModel(value = "ChGroupMembersVo对象", description = "群组成员显示对象")
public class ChGroupMembersVo {

    @ApiModelProperty("群成员用户ID")
    private Long memberId;

    @ApiModelProperty("群组ID")
    private Long groupId;

    @ApiModelProperty("群成员用户名")
    private String username;

    @ApiModelProperty("群组名称")
    private String groupNickname;

    @ApiModelProperty("群成员性别")
    private String gender;

    @ApiModelProperty("群成员头像")
    private String avatar;



}
