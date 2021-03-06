package com.injian.dao;

import com.injian.dataobject.CommentDO;

import java.util.List;

public interface CommentDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun May 12 15:33:17 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun May 12 15:33:17 CST 2019
     */
    int insert(CommentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun May 12 15:33:17 CST 2019
     */
    int insertSelective(CommentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun May 12 15:33:17 CST 2019
     */
    CommentDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun May 12 15:33:17 CST 2019
     */
    int updateByPrimaryKeySelective(CommentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun May 12 15:33:17 CST 2019
     */
    int updateByPrimaryKey(CommentDO record);
    //得到商品的父评论
    List<CommentDO> selectItemComment(Integer itemId);
    //得到商品的所有评论和回复
    List<CommentDO> listCommentAndReply(Integer itemId,Integer parentId);
}