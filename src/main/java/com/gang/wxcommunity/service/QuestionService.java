package com.gang.wxcommunity.service;

import com.gang.wxcommunity.dto.QuestionDTO;
import com.gang.wxcommunity.mapper.QuestionMapper;
import com.gang.wxcommunity.mapper.UserMapper;
import com.gang.wxcommunity.model.Question;
import com.gang.wxcommunity.model.QuestionExample;
import com.gang.wxcommunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public void addOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insert(question);
        } else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.updateByPrimaryKeySelective(question);
        }
    }

    public List<QuestionDTO> findAll() {
        List<Question> questions = questionMapper.selectByExample(new QuestionExample());

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser( userMapper.selectByPrimaryKey(question.getCreator()));
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    /**
     * 通过问题ID查询一个问题
     * @param id
     * @return QuestionDTO
     */
    public QuestionDTO findQuestionById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
