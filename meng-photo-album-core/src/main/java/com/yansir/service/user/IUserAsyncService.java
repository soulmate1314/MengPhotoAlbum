package com.yansir.service.user;


import com.yansir.po.mengphotoalbum.User;

import java.util.List;

public interface IUserAsyncService {

    void insertBatchUser(List<User> userList);
}
