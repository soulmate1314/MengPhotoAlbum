package com.yansir.web;


import com.yansir.service.department.IDepartmentService;
import com.yansir.vo.Ztree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("department")
@Slf4j
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    /**
     * @author YANSIR
     * @Description: 加载部门树
     * @date 2020/7/27 15:43
     */
    @GetMapping("tree")
    public List<Ztree> queryDepartmentTree() {
        return departmentService.queryDepartmentTree();
    }



}
