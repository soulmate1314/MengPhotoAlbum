package com.yansir.po.mengphotoalbum;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * YANSIR
 * 2021-09-23T11:18:16.179
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Department implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 父级部门id
     */
    private Integer pid;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门code
     */
    private String departmentCode;

    /**
     * 部门描述
     */
    private String description;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 修改人
     */
    private Integer modifier;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否被删除（Y，已删除；N，未删除）
     */
    private String isDeleted;

    private static final long serialVersionUID = 1L;
}