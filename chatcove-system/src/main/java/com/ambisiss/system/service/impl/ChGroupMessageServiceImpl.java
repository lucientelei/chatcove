package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChGroupMessageDto;
import com.ambisiss.common.utils.JwtUtils;
import com.ambisiss.common.utils.MessageUUIDGenerator;
import com.ambisiss.common.vo.ChGroupMessageVo;
import com.ambisiss.system.entity.ChFriendRelationship;
import com.ambisiss.system.entity.ChGroupMessage;
import com.ambisiss.system.entity.ChUser;
import com.ambisiss.system.mapper.ChFriendRelationshipDao;
import com.ambisiss.system.mapper.ChGroupMembersDao;
import com.ambisiss.system.mapper.ChGroupMessageDao;
import com.ambisiss.system.mapper.ChUserDao;
import com.ambisiss.system.service.ChGroupMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
public class ChGroupMessageServiceImpl extends ServiceImpl<ChGroupMessageDao, ChGroupMessage> implements ChGroupMessageService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ChGroupMessageDao groupMessageDao;

    @Autowired
    private ChGroupMembersDao groupMembersDao;

    @Autowired
    private ChFriendRelationshipDao friendRelationshipDao;

    @Autowired
    private ChUserDao userDao;

    @Override
    public int insertMessage(ChGroupMessageDto dto) {
        ChGroupMessage groupMessage = new ChGroupMessage();
        BeanUtils.copyProperties(dto, groupMessage);
        groupMessage.setMessageUuid(MessageUUIDGenerator.generateUUID());
        return groupMessageDao.insert(groupMessage);
    }

    @Override
    public int delMessage(Long id) {
        return groupMessageDao.deleteById(id);
    }

    @Override
    public List<ChGroupMessageVo> listMessage(Long groupId) {
        QueryWrapper<ChGroupMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        wrapper.orderByDesc("create_time");
        List<ChGroupMessage> messageList = groupMessageDao.selectList(wrapper);
        return generatorVo(messageList);
    }

    /**
     * 返回群组聊天记录
     *
     * @param messageList
     * @return
     */
    private List<ChGroupMessageVo> generatorVo(List<ChGroupMessage> messageList) {
        List<ChGroupMessageVo> messageVos = new ArrayList<>();
        for (ChGroupMessage item : messageList) {
            ChGroupMessageVo messageVo = new ChGroupMessageVo();
            messageVo.setId(item.getId());
            messageVo.setMemberId(item.getSenderId());
            //TODO 判断与登录用户是否为好友 获取用户群聊中的群昵称
            //获取登录用户token
            String token = request.getHeader(JwtUtils.header);
            String username = JwtUtils.getClaimFiled(token, "username");
            messageVo.setUsername(getUsername(item.getGroupId(), userDao.getIdByUsername(username), item.getSenderId()));
            ChUser chUser = userDao.selectById(item.getSenderId());
            messageVo.setAvatar(chUser.getAvatar());
            messageVo.setMessage(item.getMessage());
            messageVo.setMessageType(item.getMessageType());
            messageVo.setCreateTime(item.getCreateTime());
        }
        return messageVos;
    }

    /**
     * 获取用户群组群昵称
     *
     * @param userId
     * @param friendId
     * @return
     */
    private String getUsername(Long groupId, Long userId, Long friendId) {
        String nickname = null;
        //自己发送的消息显示群昵称
        if (userId.equals(friendId)) {
            nickname = groupMembersDao.selectGroupNickname(groupId, userId);
        }
        QueryWrapper<ChFriendRelationship> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("friend_id", friendId);
        ChFriendRelationship relationship = friendRelationshipDao.selectOne(wrapper);
        //用户与群成员为好友关系 返回备注名 --备注名默认用户名
        if (relationship != null) {
            nickname = relationship.getFriendNickname();
        } else {
            //用户与群成员不是好友关系 返回群成员的群昵称
            nickname = groupMembersDao.selectGroupNickname(groupId, friendId);
        }

        return nickname;
    }
}
