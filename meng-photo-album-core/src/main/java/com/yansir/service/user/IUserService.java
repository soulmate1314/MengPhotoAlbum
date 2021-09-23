package com.yansir.service.user;



import com.yansir.exception.BusinessException;
import com.yansir.vo.BaseQeryVO;
import com.yansir.vo.UserAddVO;
import com.yansir.vo.page.BasePageResult;


public interface IUserService   {

    BasePageResult queryUserPage(BaseQeryVO baseQeryVO);


    void addUser(UserAddVO userAddVO)throws BusinessException;

    void addBatchUser(UserAddVO userAddVO)throws BusinessException;



}
