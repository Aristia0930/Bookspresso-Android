package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Comment;
import com.ssafy.cafe.model.dto.CommentInfo;

public interface CommentDao {
    /**
     * comment 입력
     * @param comment
     * @return
     */
    int insert(Comment comment);

    /**
     * comment 수정
     * @param comment
     * @return
     */
    int update(Comment comment);

    /**
     * comment 삭제
     * @param comment
     * @return
     */
    int delete(Integer commentId);

    /**
     * comment 단건 조회.
     * @param commentId
     * @return
     */
    Comment select(Integer commentId);

    
    /**
     * product에 작성된 전체 comment를 리턴한다. 
     * comment 정보와 작성자 이름이 포함된 CommentInfo 객체를 리턴한다.
     *  
     * @param productId
     * @return
     */
    List<CommentInfo> selectByProduct(Integer productId);
}
