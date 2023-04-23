package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-23 21:02:56
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChRelationStatusDto对象", description = "")
public class ChRelationStatusDto {

    private String name;

    private String description;

}
