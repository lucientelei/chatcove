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
 * @Data: 2023-5-24 09:54:31
 */
@Getter
@Setter
@Document(collection = "group_message")
public class GroupMessage {

//    @Field("message_uuid")
    private String messageUuid;

    @Field("group_id")
    private Long groupId;

    @Field("sender_id")
    private Long senderId;

    @Field("message_uuid")
    private String message;

    @Field("message_type_id")
    private Long messageTypeId;

    @Field("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 0: -> 未读
     * 1: -> 已读
     */
    @Field("read")
    private Integer read;

    @Override
    public String toString() {
        return "GroupMessage{" +
                "messageUuid='" + messageUuid + '\'' +
                ", groupId=" + groupId +
                ", senderId=" + senderId +
                ", message='" + message + '\'' +
                ", messageTypeId=" + messageTypeId +
                ", createTime=" + createTime +
                ", read=" + read +
                '}';
    }
}
