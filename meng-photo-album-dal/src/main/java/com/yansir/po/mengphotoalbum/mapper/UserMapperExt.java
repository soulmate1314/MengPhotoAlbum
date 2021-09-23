package com.yansir.po.mengphotoalbum.mapper;

import com.yansir.po.mengphotoalbum.User;
import com.yansir.vo.BaseQeryVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapperExt extends UserMapper {

    List<User> queryUserPage(BaseQeryVO baseQeryVO);
    void insertBatchUser(List<User> userList);
}