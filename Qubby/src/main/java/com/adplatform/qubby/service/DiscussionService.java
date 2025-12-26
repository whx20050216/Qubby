package com.adplatform.qubby.service;

import com.adplatform.qubby.model.Discussion;
import com.adplatform.qubby.model.Reply;
import com.adplatform.qubby.repository.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    // 获取所有讨论（按时间倒序）
    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAllByOrderByCreateTimeDesc();
    }

    // 根据ID查找讨论
    public Discussion findById(Long id) {
        return discussionRepository.findById(id).orElse(null);
    }

    // 发布新讨论
    public Discussion create(Discussion discussion) {
        discussion.setCreateTime(new Date());
        return discussionRepository.save(discussion);
    }

    // 添加回复
    public void addReply(Long discussionId, Reply reply) {
        Discussion discussion = findById(discussionId);
        if (discussion != null) {
            reply.setCreateTime(new Date());
            reply.setDiscussion(discussion);
            discussion.getReplies().add(reply);
            discussionRepository.save(discussion);  // 级联保存
        }
    }
}