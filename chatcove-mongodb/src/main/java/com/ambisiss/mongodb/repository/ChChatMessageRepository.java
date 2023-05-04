package com.ambisiss.mongodb.repository;

import com.ambisiss.mongodb.entity.ChChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-4 20:15:44
 */
@Repository
public interface ChChatMessageRepository extends MongoRepository<ChChatMessage, Long> {

}
