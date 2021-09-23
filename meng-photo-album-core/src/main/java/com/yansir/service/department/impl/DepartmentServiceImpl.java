package com.yansir.service.department.impl;

import com.yansir.po.mengphotoalbum.Department;
import com.yansir.po.mengphotoalbum.DepartmentExample;
import com.yansir.po.mengphotoalbum.mapper.DepartmentMapperExt;
import com.yansir.service.department.IDepartmentService;
import com.yansir.vo.Ztree;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    DepartmentMapperExt departmentMapperExt;


    @Override
    public List<Ztree> queryDepartmentTree() {
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.setOrderByClause("ID ASC");
        List<Department> departments = departmentMapperExt.selectByExample(departmentExample);
        List<Ztree> ztrees = initDepartmentZtree(departments);
        return ztrees;
    }

    private List<Ztree> initDepartmentZtree(List<Department> departments) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (Department department : departments) {
            Ztree ztree = new Ztree();
            if (ObjectUtils.isNotEmpty(department.getId())) {
                ztree.setId(Long.parseLong(department.getId().toString()));
            }
            if (ObjectUtils.isNotEmpty(department.getPid())) {
                ztree.setPId(Long.parseLong(department.getPid().toString()));
            }

            ztree.setName(department.getDepartmentName());
            ztree.setTitle(department.getDepartmentName());
            ztrees.add(ztree);
        }
        return ztrees;
    }




}
