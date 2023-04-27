package com.ambisiss.common.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 13:37:15
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChGroupMembersDto对象", description = "群组成员传输对象")
public class ChGroupMembersDto {

    private Long groupId;

    private Long memberId;
}
