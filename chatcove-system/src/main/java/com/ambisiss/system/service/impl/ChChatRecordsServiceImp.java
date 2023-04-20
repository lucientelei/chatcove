package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChChatRecordsDto;
import com.ambisiss.common.dto.ChChatRecordsPageDto;
import com.ambisiss.system.entity.ChChatRecords;
import com.ambisiss.system.mapper.ChChatRecordsDao;
import com.ambisiss.system.service.ChChatRecordsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@Service
public class ChChatRecordsServiceImp extends ServiceImpl<ChChatRecordsDao, ChChatRecords> implements ChChatRecordsService {

    @Autowired
    private ChChatRecordsDao chatRecordsDao;

    @Override
    public int insertRecords(ChChatRecordsDto dto) {
        ChChatRecords records = new ChChatRecords();
        BeanUtils.copyProperties(dto, records);
        return chatRecordsDao.insert(records);
    }

    @Override
    public int delRecords(List<Long> ids) {
        return chatRecordsDao.deleteBatchIds(ids);
    }

    @Override
    public List<ChChatRecords> listRecords(ChChatRecordsPageDto dto) {
        QueryWrapper<ChChatRecords> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", dto.getUserId()).eq("friend_id", dto.getFriendId())
                .or()
                .eq("user_id", dto.getFriendId()).eq("friend_id", dto.getUserId());
        //TODO 判断是否查询指定类型聊天记录
        if (!StringUtils.isEmpty(dto.getMessageTypeId())) {
            wrapper.eq("message_type_id", dto.getMessageTypeId());
        }
//        if (!StringUtils.isEmpty(dto.getCreateTime())) {
//            wrapper.
//        }
        wrapper.orderByDesc("create_time");
        List<ChChatRecords> records = chatRecordsDao.selectList(wrapper);
        return records;
    }
}
