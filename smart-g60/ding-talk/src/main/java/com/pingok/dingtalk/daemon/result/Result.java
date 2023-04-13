package com.pingok.dingtalk.daemon.result;

import com.pingok.dingtalk.daemon.page.Page;
import lombok.Data;


import java.io.Serializable;

/**
 * 钉钉返回对象包装类
 *
 * @author chenwg
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private Boolean success;
    private Page<T> data;
}
