package com.yansir.service.user.impl;

import com.yansir.po.mengphotoalbum.User;
import com.yansir.po.mengphotoalbum.mapper.UserMapperExt;
import com.yansir.service.user.IUserAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserAsyncServiceImpl implements IUserAsyncService {

    @Autowired
    private UserMapperExt userMapperExt;


//    1. 启动类上要加上@EnableAsync 注解 ，代表开启异步。
//    2. 使用@Async注解的方法必须是public方法，使用private关键字，一定会失败。
//    3. 记带有@Async注解的方法为A类，那么调用该异步方法的方法一定需要在B类。作为开发者，最好将异步方法单独放在一个类当中，既不会出错，还便于管理。
    //多线程100w数据需要40秒,单线程需要5分钟左右
    @Async("threadPoolTaskExecutor")
    @Override
    public void insertBatchUser(List<User> userList){
        userMapperExt.insertBatchUser(userList);
        log.info("当前线程名称====={}",Thread.currentThread().getName());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
