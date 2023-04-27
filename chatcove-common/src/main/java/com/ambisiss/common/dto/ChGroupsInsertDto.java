package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 11:37:19
 */

@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChGroupsInsertDto对象", description = "")
public class ChGroupsInsertDto {

    @ApiModelProperty("群组名称")
    private String name;

    @ApiModelProperty("群组管理员名单")
    private List<Long> adminList;

    @ApiModelProperty("群组成员名单")
    private List<Long> memberList;

}
