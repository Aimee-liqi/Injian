package com.injian.service;

import com.injian.error.BusinessException;
import com.injian.service.model.CommentModel;

import java.util.List;

public interface CommentService {
    //某一商品的所有父评论列表
    List<CommentModel> listItemComments(Integer itemId) throws BusinessException;
    //得到某一评论的所有回复
    List<CommentModel> getReply(Integer itemId,Integer commentId) throws BusinessException;
    void addComment(CommentModel commentModel) throws BusinessException;
}
