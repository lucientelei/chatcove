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
 * @Data: 2023-5-4 20:08:15
 */
@Getter
@Setter
@Document(collection = "ch_chat_message")
public class ChChatMessageMongo {

    @Id
    private String id;

    @Field("user_id")
    private Long userId;

    private List<ChatMessage> chatMessageList;

}
