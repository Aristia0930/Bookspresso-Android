package com.ssafy.cafe.model.service;

import com.ssafy.cafe.model.dto.User;
import com.ssafy.cafe.model.dto.UserInfo;


public interface UserService {
    /**
     * 사용자 정보를 DB에 저장하고 저장된 건수를 리턴한다. 
     * 
     * @param user
     */
    public int join(User user);

    /**
     * id, pass에 해당하는 User 정보를 반환한다.
     * 
     * @param id
     * @param pass
     * @return
     * 조회된 User 정보를 반환한다.
     */
    public User login(String id, String pass);
    
    
    /**
     * 해당 아이디가 이미 사용 중인지를 반환한다.
     * @param id
     * @return
     */
    public boolean isUsedId(String id);

    /**
     * id 에 해당하는 User 정보를 반환한다.
     * 
     * @param id
     * @return
     * 조회된 User 정보를 반환한다.
     */
    public User selectUser(String id);

    
    public UserInfo userInfo(String id);

}
