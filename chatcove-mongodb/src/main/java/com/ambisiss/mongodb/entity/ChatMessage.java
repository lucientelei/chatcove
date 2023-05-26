package com.ambisiss.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-26 10:31:03
 */
@Getter
@Setter
@Document(collection = "chat_message")
public class ChatMessage {

    @Field("message_uuid")
    private String messageUuid;

    @Field("sender_id")
    private Long senderId;

    @Field("receiver_id")
    private Long receiverId;

    @Field("message")
    private String message;

    @Field("message_type_id")
    private Long messageTypeId;

    /**
     * 0: -> 未读
     * 1: -> 已读
     */
    @Field("read")
    private Integer read;

    @Field("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
