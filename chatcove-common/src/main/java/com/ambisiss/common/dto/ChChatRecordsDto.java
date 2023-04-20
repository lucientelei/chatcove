package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 19:00:03
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChChatRecordsVo对象", description = "ChChatRecords新增vo")
public class ChChatRecordsDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long friendId;

    @NotBlank
    private String message;

    @NotBlank
    private Long messageTypeId;

}
