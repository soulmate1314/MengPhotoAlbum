package com.yansir.utils;

import com.github.pagehelper.PageHelper;
import com.yansir.constant.Constants;
import com.yansir.vo.page.BasePageResult;
import com.yansir.vo.page.PageDomain;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author YANSIR
 * @Description:分页数据处理
 * @date 2020/3/24 15:30
 */
public class PageUtils {

    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = PageUtils.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (pageNum == null && pageSize == null) {
            PageHelper.startPage(Constants.PAGE_NUM_DEFAULT, Constants.PAGE_SIZE_DEFAULT);
        } else {
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    /**
     * 封装分页对象
     */
    static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(getParameterToInt(Constants.PAGE_SIZE));
        return pageDomain;
    }

    static PageDomain buildPageRequest() {
        return getPageDomain();
    }

    /**
     * 获取Integer参数
     */
    static Integer getParameterToInt(String name) {
        String parameter = ServletUtils.getRequest().getParameter(name);
        if (StringUtils.isEmpty(parameter)) {
            return null;
        }
        return Integer.parseInt(parameter);
    }


    public static BasePageResult getPageResult(List<?> list) {
        return new BasePageResult(list);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }


}
