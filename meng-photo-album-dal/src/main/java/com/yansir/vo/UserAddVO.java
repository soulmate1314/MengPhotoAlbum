package com.yansir.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserAddVO implements Serializable{


    /**
     * 用户Id
     */
    @NotNull(message = "用户Id不能为空")
    private Integer userId;
    /**
     * 用户名称
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 所属部门
     */
    @NotNull(message = "所属部门不能为空")
    private Integer departmentId;

    /**
     * 邮箱
     */
    @Email(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message = "邮箱不符合规则")
    private String email;

    /**
     * 电话
     */
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$" ,message = "电话不符合规则" )
    private String telephone;
    /**
     *角色Id
     */
    @NotNull(message = "角色Id不能为空")
    private Integer roleId;

}
