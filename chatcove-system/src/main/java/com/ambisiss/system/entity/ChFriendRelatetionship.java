package com.ambisiss.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ch_friend_relatetionship")
@ApiModel(value = "ChFriendRelatetionship对象", description = "")
public class ChFriendRelatetionship implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long friendId;

    private Long statusId;


}
