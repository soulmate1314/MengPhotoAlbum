package com.yansir.service.user.impl;

import com.yansir.exception.BusinessException;
import com.yansir.po.mengphotoalbum.User;
import com.yansir.po.mengphotoalbum.UserExample;
import com.yansir.po.mengphotoalbum.mapper.DepartmentMapperExt;
import com.yansir.po.mengphotoalbum.mapper.UserMapperExt;
import com.yansir.service.user.IUserAsyncService;
import com.yansir.service.user.IUserService;
import com.yansir.utils.PageUtils;
import com.yansir.utils.validation.ValidationUtils;
import com.yansir.vo.BaseQeryVO;
import com.yansir.vo.UserAddVO;
import com.yansir.vo.page.BasePageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.MapUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserMapperExt userMapperExt;


    @Autowired
    DepartmentMapperExt departmentMapperExt;

    @Autowired
    IUserAsyncService userAsyncService;


    @Override
    public BasePageResult queryUserPage(BaseQeryVO baseQeryVO) {
        PageUtils.startPage();
        List<User> departmentUserList = userMapperExt.queryUserPage(baseQeryVO);
        return PageUtils.getPageResult(departmentUserList);
    }





//    propagation：事务传播行为定义，默认为“REQUIRED”，表示Required，其值可以通过TransactionDefinition的静态传播行为变量的“PROPAGATION_”后边部分指定，如“TransactionDefinition.PROPAGATION_REQUIRED”可以使用“REQUIRED”指定；
//    isolation：事务隔离级别定义；默认为“DEFAULT”，其值可以通过TransactionDefinition的静态隔离级别变量的“ISOLATION_”后边部分指定，如“TransactionDefinition. ISOLATION_DEFAULT”可以使用“DEFAULT”指定：
//    timeout：事务超时时间设置，单位为秒，默认-1，表示事务超时将依赖于底层事务系统；
//    read-only：事务只读设置，默认为false，表示不是只读；
//    rollback-for：需要触发回滚的异常定义，以“，”分割，默认任何RuntimeException 将导致事务回滚，而任何Checked Exception 将不导致事务回滚；异常名字定义和TransactionProxyFactoryBean中含义一样
//    no-rollback-for：不被触发进行回滚的 Exception(s)；以“，”分割；异常名字定义和TransactionProxyFactoryBean中含义一样；
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            timeout = 1,
            readOnly = false,
            noRollbackFor = BusinessException.class,
            rollbackFor = Exception.class)
    public void addUser(UserAddVO userAddVO) throws BusinessException {
        ValidationUtils.checkPropertyExcludes(userAddVO, "userId");
        User user = User.builder()
                .userName(userAddVO.getUserName())
                .password(userAddVO.getPassword())
                .departmentId(userAddVO.getDepartmentId())
                .email(userAddVO.getEmail())
                .telephone(userAddVO.getTelephone())
                .status("1")
                .creator(1)
                .gmtCreate(new Date())
                .isDeleted("N")
                .build();
        userMapperExt.insertSelective(user);

    }


    @Override
    public void addBatchUser(UserAddVO userAddVO) {
        ValidationUtils.checkPropertyExcludes(userAddVO, "userId");
        List<List<User>> userLists = buildForUserList(userAddVO);
        for (List<User> userList:userLists){
            userAsyncService.insertBatchUser(userList);
        }
    }

    public List<List<User>> buildForUserList(UserAddVO userAddVO){
        List<List<User>> userLists = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        for (int i = 1; i<=10000000; i++){

            User user = User.builder()
                    .userName(userAddVO.getUserName()+i)
                    .password(userAddVO.getPassword())
                    .departmentId(userAddVO.getDepartmentId())
                    .email(userAddVO.getEmail())
                    .telephone(userAddVO.getTelephone())
                    .status("1").creator(1)
                    .gmtCreate(new Date())
                    .isDeleted("N")
                    .build();
            userList.add(user);
            if (userList.size()==1000){
                userLists.add(userList);
                if (userLists.size()==10000){
                    return userLists;
                }
                userList = new ArrayList<>();
            }
        }
        return null;
    }

}
