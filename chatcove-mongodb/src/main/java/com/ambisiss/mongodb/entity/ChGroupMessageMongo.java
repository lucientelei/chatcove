package com.ambisiss.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    List<GroupMessage> groupMessages;

    @Getter
    @Setter
    @Document
    public static class GroupMessage {
        @Field("message_uuid")
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
    }
}
