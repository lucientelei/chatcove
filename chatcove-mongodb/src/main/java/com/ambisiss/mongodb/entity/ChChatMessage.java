package com.ambisiss.mongodb.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String messageUuid;

    private Long senderId;

    private Long receiverId;

    private String message;

    private Long messageTypeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
