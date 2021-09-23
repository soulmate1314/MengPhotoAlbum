package com.yansir.po.mengphotoalbum;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * YANSIR
 * 2021-09-23T11:18:16.170
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 所属部门
     */
    private Integer departmentId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 状态
     */
    private String status;

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