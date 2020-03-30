package com.gang.wxcommunity.service;

import com.gang.wxcommunity.dto.NotificationDTO;
import com.gang.wxcommunity.enums.NotificationStatusEnum;
import com.gang.wxcommunity.enums.NotificationTypeEnum;
import com.gang.wxcommunity.exception.CustomizeErrorCode;
import com.gang.wxcommunity.exception.CustomizeException;
import com.gang.wxcommunity.mapper.NotificationMapper;
import com.gang.wxcommunity.model.Notification;
import com.gang.wxcommunity.model.NotificationExample;
import com.gang.wxcommunity.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    //得到通知消息
    public List<NotificationDTO> list(Long userId) {

        //得到总记录数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);

        //组装问题和作者，便于显示
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        return notificationDTOS;
}

    //得到用户未读通知数
    public Integer unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(0);
        int unreadCount = (int) notificationMapper.countByExample(notificationExample);
        return unreadCount;
    }

    //读通知
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        notificationDTO.setType(notification.getType());
        return notificationDTO;
    }
}
