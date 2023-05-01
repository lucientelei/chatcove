package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChChatMessageDto;
import com.ambisiss.common.dto.ChChatMessagePageDto;
import com.ambisiss.common.utils.MessageUUIDGenerator;
import com.ambisiss.system.entity.ChChatMessage;
import com.ambisiss.system.mapper.ChChatMessageDao;
import com.ambisiss.system.service.ChChatMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-05-01
 */
@Service
public class ChChatMessageServiceImpl extends ServiceImpl<ChChatMessageDao, ChChatMessage> implements ChChatMessageService {

    @Autowired
    private ChChatMessageDao messageDao;

    @Override
    public int insertMessage(ChChatMessageDto dto) {
        ChChatMessage records = new ChChatMessage();
        BeanUtils.copyProperties(dto, records);
        records.setMessageUuid(MessageUUIDGenerator.generateUUID());
        return messageDao.insert(records);
    }

    @Override
    public int delMessage(List<Long> ids) {
        return messageDao.deleteBatchIds(ids);
    }

    @Override
    public List<ChChatMessage> listMessage(ChChatMessagePageDto dto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<ChChatMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("sender_id", dto.getSenderId()).eq("receiver_id", dto.getReceiverId())
                .or()
                .eq("sender_id", dto.getReceiverId()).eq("receiver_id", dto.getSenderId());
        //TODO 判断是否查询指定类型聊天记录
        if (!StringUtils.isEmpty(dto.getMessageTypeId())) {
            wrapper.eq("message_type_id", dto.getMessageTypeId());
        }
        wrapper.orderByDesc("create_time");
        List<ChChatMessage> messages = messageDao.selectList(wrapper);
        return messages;
    }
}
