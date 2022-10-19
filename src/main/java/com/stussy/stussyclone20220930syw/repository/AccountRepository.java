package com.stussy.stussyclone20220930syw.repository;


import com.stussy.stussyclone20220930syw.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {

    //이녀석을 가지고 xml을만듬.
    //저 쿼리가 실행되면 User을 리턴해주는것임.
    public User findUserByEmail(String email) throws Exception;
    //User이 연결된 xml이 필요하다.
    public int saveUser(User user) throws Exception;
}
