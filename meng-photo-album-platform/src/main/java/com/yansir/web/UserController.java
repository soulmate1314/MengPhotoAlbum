package com.yansir.web;


import com.yansir.exception.BusinessException;
import com.yansir.restful.Result;
import com.yansir.restful.ResultGenerator;
import com.yansir.service.user.IUserService;
import com.yansir.vo.BaseQeryVO;
import com.yansir.vo.UserAddVO;
import com.yansir.vo.page.BasePageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;


    /**
     * @author YANSIR
     * @Description:部门用户分页查询
     * @date 2020/7/29 15:14
     */
    @PostMapping("query/page")
    public BasePageResult queryUserPage(BaseQeryVO baseQeryVO) {
        return userService.queryUserPage(baseQeryVO);
    }


    @PostMapping(value = "add/batch")
    public Result addBatchUser(@RequestBody UserAddVO userAddVO)throws BusinessException {
        userService.addBatchUser(userAddVO);
        return ResultGenerator.genSuccessResult();
    }


}
