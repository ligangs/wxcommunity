package com.gang.wxcommunity.controller;

import com.gang.wxcommunity.dto.CommentCreateDTO;
import com.gang.wxcommunity.dto.CommentDTO;
import com.gang.wxcommunity.dto.CommentReq;
import com.gang.wxcommunity.enums.CommentTypeEnum;
import com.gang.wxcommunity.enums.ResponseCodeEnum;
import com.gang.wxcommunity.model.Comment;
import com.gang.wxcommunity.model.User;
import com.gang.wxcommunity.service.CommentService;
import com.gang.wxcommunity.service.UserService;
import com.gang.wxcommunity.vo.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Object comment(@RequestBody CommentCreateDTO commentCreateDTO) {

        //根据token判断是否登录
        User user = userService.findUserByToken(commentCreateDTO.getToken());
        if(user==null){
            return ResponseVo.getErrResponse(ResponseCodeEnum.NOT_LOGIN.getCode(), ResponseCodeEnum.NOT_LOGIN.getDesc());
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getComment())) {
            return ResponseVo.getErrResponse(ResponseCodeEnum.NO_COMMENT.getCode(), ResponseCodeEnum.NO_COMMENT.getDesc());
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setComment(commentCreateDTO.getComment());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setCommentCount(0);
        comment.setLikeCount(0);
        commentService.insert(comment,user);
        return ResponseVo.getSuccResponse("回答成功！");
    }

    @ResponseBody
        @RequestMapping(value = "/comment/{id}",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Object comments(@PathVariable(name="id")Long id, @RequestBody CommentReq commentReq) {
        List<CommentDTO> commentDTOS=null;
        if (commentReq.getType()==CommentTypeEnum.QUESTION.getType()) {
            commentDTOS = commentService.getCommentListByType(id, CommentTypeEnum.QUESTION);
        }else{
            commentDTOS = commentService.getCommentListByType(id, CommentTypeEnum.COMMENT);
        }
        return ResponseVo.getSuccResponse(commentDTOS);
    }
}
