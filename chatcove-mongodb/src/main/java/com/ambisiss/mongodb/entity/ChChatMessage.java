package com.ambisiss.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-4 20:08:15
 */
@Getter
@Setter
@Document("ch_chat_message")
public class ChChatMessage {

    @Id
    private String id;

    @Field("message_uuid")
    private String messageUuid;

    @Field("sender_id")
    private Long senderId;

    @Field("receiver_id")
    private Long receiverId;

    @Field("message")
    private String message;

    @Field("messageType_id")
    private Long messageTypeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Override
    public String toString() {
        return "ChChatMessage{" +
                "messageUuid='" + messageUuid + '\'' +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", message='" + message + '\'' +
                ", messageTypeId=" + messageTypeId +
                ", createTime=" + createTime +
                '}';
    }
}
