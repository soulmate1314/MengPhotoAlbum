package com.yansir.vo.page;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class BasePageResult {
    private int code;
    private long total;
    private List<?> rows;

    public BasePageResult() {
    }

    public BasePageResult(List<?> rows) {
        this.code = 0;
        this.total = new PageInfo(rows).getTotal();
        this.rows = rows;
    }
}
