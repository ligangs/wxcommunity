package com.gang.wxcommunity.service;

import com.gang.wxcommunity.dto.QuestionDTO;
import com.gang.wxcommunity.mapper.QuestionExtMapper;
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
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 添加提问或更新提问
     * @param question
     */
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

    /**
     * 得到所有提问
     * @return 返回问题集合
     */
    public List<QuestionDTO> findAll() {
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExample(example);
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
     * 根据UserId得到所有提问
     * @return 返回问题集合
     */
    public List<QuestionDTO> getQuestionByUserId(Long id) {
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        example.createCriteria().andCreatorEqualTo(id);
        List<Question> questions = questionMapper.selectByExample(example);
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

    /**
     * 增加问题阅读数
     * @param id
     */
    public void incView(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public Integer getMyQuestionCount(Long id) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(id);
        return (int)questionMapper.countByExample(example);
    }
}
