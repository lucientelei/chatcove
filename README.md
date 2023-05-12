# chatcove

#### 介绍
{**以下是 Gitee 平台说明，您可以替换此简介**
Gitee 是 OSCHINA 推出的基于 Git 的代码托管平台（同时支持 SVN）。专为开发者提供稳定、高效、安全的云端软件开发协作平台
无论是个人、团队、或是企业，都能够用 Gitee 实现代码托管、项目管理、协作开发。企业项目请看 [https://gitee.com/enterprises](https://gitee.com/enterprises)}

#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)

# 用户不在线消息如何接收

> 使用 MongoDB 存储离线消息，为每个用户分别创建一个文档，将该用户的所有离线消息存储到该文档中，使用一个标记字段（比如已读或未读）标识哪些消息已经被成功发送到客户端。当用户上线后，从该文档中查询出所有标记为未读的离线消息，并将这些消息发送给用户。在发送完毕后，将这些消息的标记字段更新为已读，以避免重复发送，后通过定时任务存储到mysql

- offline_messages

~~~json
{
  _id: ObjectId("60a7d4c67ca4eb6f4b06a5f1"),   // 文档ID
  user_id: 123,                               // 用户ID
  messages: [                                 // 离线消息列表
    {                                        
      from: "user1",
      to: "user2",
      content: "Hello, user2!",
      timestamp: ISODate("2023-05-12T10:00:00Z"),
      read: false                              // 消息是否已读
    },
    {
      from: "user3",
      to: "user2",
      content: "How are you, user2?",
      timestamp: ISODate("2023-05-12T11:00:00Z"),
      read: true
    }
  ]
}

~~~

- 在用户上线后，可以使用 MongoDB 的聚合管道查询该用户的离线消息列表，并将其中所有 `read` 字段值为 `false` 的消息发送给该用户
- 使用 MongoDB 存储离线消息时，你需要定期清理过期的离线消息，避免占用过多的存储空间。可以考虑使用 TTL 索引来自动删除过期的离线消息文档

~~~java
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "offline_messages")  // 指定对应的集合名称
public class OfflineMessage {
    @Id
    private ObjectId id;                      // 文档 ID

    @Field("user_id")
    private long userId;                      // 用户 ID

    private List<Message> messages;           // 离线消息列表

    // 省略 getter 和 setter 方法

    @Document
    public static class Message {
        @Field("from")
        private String from;                   // 发送者用户名

        @Field("to")
        private String to;                     // 接收者用户名

        private String content;                // 消息内容

        @Field("timestamp")
        private Date timestamp;                // 发送时间

        private boolean read;                  // 是否已读

        // 省略 getter 和 setter 方法
    }
}

~~~











