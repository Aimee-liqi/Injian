package com.injian.service.impl;

import com.injian.dao.CommentDOMapper;
import com.injian.dataobject.CommentDO;
import com.injian.error.BusinessException;
import com.injian.error.EmBusinessError;
import com.injian.service.CommentService;
import com.injian.service.UserService;
import com.injian.service.ValidateService;
import com.injian.service.model.CommentModel;
import com.injian.service.model.UserModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDOMapper commentDOMapper;
    @Autowired
    private ValidateService validateService;
    @Autowired
    private UserService userService;

    @Override
    public List<CommentModel> listItemComments(Integer itemId) throws BusinessException {
        validateService.itemValidate(itemId);
        List<CommentDO> commentDOList = commentDOMapper.selectItemComment(itemId);
        List<CommentModel> commentModelList = commentDOList.stream().map(commentDO -> {
            UserModel userModel = userService.getUserById(commentDO.getFromUserId());
            CommentModel commentModel = this.convertFromDO(commentDO,userModel);
            return commentModel;
        }).collect(Collectors.toList());
        return commentModelList;
    }

    @Override
    public List<CommentModel> getReply(Integer itemId, Integer commentId) throws BusinessException {
        validateService.itemValidate(itemId);
        List<CommentDO> commentDOList = commentDOMapper.listCommentAndReply(itemId,commentId);
        List<CommentModel> commentModelList = commentDOList.stream().map(commentDO -> {
            UserModel userModel = userService.getUserById(commentDO.getFromUserId());
            CommentModel commentModel = this.convertFromDO(commentDO,userModel);
            commentModel.setToUserName(userService.getUserById(commentDO.getToUserId()).getUsername());
            return commentModel;
        }).collect(Collectors.toList());
        return commentModelList;
    }

    @Override
    @Transactional
    public void addComment(CommentModel commentModel) throws BusinessException {
        validateService.itemValidate(commentModel.getItemId());
        validateService.userValidate(commentModel.getFromUserId());
        CommentDO commentDO = this.convertFromModel(commentModel);
        if(commentDO != null){
            commentDOMapper.insertSelective(commentDO);
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"留言信息错误");
        }
    }

    private CommentModel convertFromDO(CommentDO commentDO, UserModel userModel){
        if(commentDO == null){
            return null;
        }
        CommentModel commentModel = new CommentModel();
        BeanUtils.copyProperties(commentDO,commentModel);
        commentModel.setCreateTime(new DateTime(commentDO.getCreateTime()));
        commentModel.setFromUserName(userModel.getUsername());
        return commentModel;
    }

    private CommentDO convertFromModel(CommentModel commentModel){
        if(commentModel == null){
            return null;
        }
        CommentDO commentDO = new CommentDO();
        BeanUtils.copyProperties(commentModel,commentDO);
        commentDO.setCreateTime(commentModel.getCreateTime().toDate());
        return commentDO;
    }
}
