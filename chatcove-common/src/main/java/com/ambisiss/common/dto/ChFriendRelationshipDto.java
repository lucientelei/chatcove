package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-23 13:49:38
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChFriendRelationshipDto对象", description = "查找新增对象")
public class ChFriendRelationshipDto {

    private Long userId;

    private Long friendId;

    private Long statusId;
    
}
