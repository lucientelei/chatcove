package com.ambisiss.common.enums;

/**
 * @Author: chenxiaoye
 * @Description: 好友状态常量
 * @Data: 2023-4-23 17:18:09
 */
public enum RelationEnum {

    /**
     * 未确认
     */
    UNCONFIRMED(1L, "未确认", "表示一个用户向另一个用户发出了好友请求，但该请求还未被接受"),
    /**
     * 已确认
     */
    CONFIRMED(2L, "已确认", "表示双方用户已经互相确认了好友关系"),
    /**
     * 已拒绝
     */
    REJECTED(3L, "已拒绝", "表示一个用户拒绝了另一个用户的好友请求或者解除好友关系"),
    /**
     * 已屏蔽
     */
    MASKED(4L, "已屏蔽", "表示一个用户屏蔽了另一个用户，不再接收其消息或其他活动"),
    /**
     * 黑名单
     */
    BLACKLIST(5L, "黑名单", "表示一个用户将另一个用户添加到了黑名单中，不能再进行任何交互"),
    /**
     * 已删除
     */
    DELETED(6L, "已删除", "表示一个用户将另一个用户从好友列表中删除");

    private final Long id;

    private final String name;

    private final String description;

    RelationEnum(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
