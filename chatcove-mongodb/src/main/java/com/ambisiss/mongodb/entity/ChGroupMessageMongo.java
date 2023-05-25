package com.ambisiss.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 16:04:09
 */
@Getter
@Setter
@Document("ch_group_message")
public class ChGroupMessageMongo {

    @Id
    private String id;

    @Field("user_id")
    private Long userId;

    private List<GroupMessage> groupMessageList;

    @ApiModelProperty(hidden = true)
    private GroupMessage groupMessage;
}
