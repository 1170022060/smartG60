package com.pingok.dingtalk.daemon.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页包装类
 *
 * @author chenwg
 */
@Data
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -1;
    protected List<T> records;
    protected long total;
    protected long size;
    protected long current;
    protected boolean optimizeCountSql;
    protected boolean searchCount;
    protected boolean hitCount;
    protected Long pages;
}
