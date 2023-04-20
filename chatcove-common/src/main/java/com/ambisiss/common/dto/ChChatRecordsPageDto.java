package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Author: chenxiaoye
 * @Description: ChChatRecords分页查询dto
 * @Data: 2023-4-20 19:36:21
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ChChatRecordsPageDto对象", description = "ChChatRecords分页查询dto")
public class ChChatRecordsPageDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long friendId;

    @Builder.Default
    private Integer pageNum = 1;

    @Builder.Default
    private Integer pageSize = 20;

    private Long messageTypeId;

    private LocalDateTime createTime;

}
