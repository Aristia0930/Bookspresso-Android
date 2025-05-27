package com.ssafy.cafe.model.dao;

import com.ssafy.cafe.model.dto.User;

public interface UserDao {
    /**
     * 사용자 정보를 추가한다.
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 사용자의 Stamp 정보를 수정한다.
     * @param user
     * @return
     */
    int updateStamp(User user);
    
    /**
     * 사용자 정보를 조회한다.
     * @param userId
     * @return
     */
    User selectById(String userId);

    /**
     * 사용자 정보를 조회한다.
     * @param user
     * @return
     */
    User selectByUser(User user);
    
}
