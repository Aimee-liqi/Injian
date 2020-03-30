package com.injian.controller;

import com.injian.error.BusinessException;
import com.injian.response.CommonReturnType;
import com.injian.service.CommentService;
import com.injian.service.model.CommentModel;
import com.injian.service.model.UserModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("comment")
@RequestMapping("/comment")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class CommentController extends BaseController{
    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/listComment",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType commentList(@RequestParam(name = "itemId") Integer itemId) throws BusinessException {

        return CommonReturnType.create(commentService.listItemComments(itemId));
    }

    @RequestMapping(value = "/listReply",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType replyList(@RequestParam(name = "itemId") Integer itemId,
                                      @RequestParam(name = "commentId") Integer commentId) throws BusinessException {
        return CommonReturnType.create(commentService.getReply(itemId,commentId));
    }

    @RequestMapping(value = "/addComments",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addComment(@RequestParam(name = "itemId") Integer itemId,
                                       @RequestParam(name = "content") String content) throws BusinessException {
        UserModel userModel = validateUserLogin();
        CommentModel commentModel = new CommentModel();
        commentModel.setFromUserId(userModel.getId());
        commentModel.setToUserId(0);
        commentModel.setParentId(0);
        commentModel.setContent(content);
        commentModel.setItemId(itemId);
        commentModel.setCreateTime(new DateTime());
        commentService.addComment(commentModel);
        return CommonReturnType.create(null);
    }
}
