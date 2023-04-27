package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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


    private Long adminId;

}
