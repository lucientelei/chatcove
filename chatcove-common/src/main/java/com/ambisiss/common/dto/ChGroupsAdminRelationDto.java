package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 13:20:11
 */

@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChGroupsAdminRelationDto对象", description = "ChGroupsAdminRelation传输对象")
public class ChGroupsAdminRelationDto {

    @ApiModelProperty("群组ID")
    private Long groupId;

    @ApiModelProperty("管理员ID")
    private Long userId;
}
