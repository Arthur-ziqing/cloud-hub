package com.arthur.cloud.activity.util;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页请求结果
 *
 * @author arthur
 */
@Data
@SuppressWarnings({"rawtypes", "unchecked"})
public class PageAjax<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private int pageNo = 0;

    /**
     * 每页的数量
     */
    private int pageSize = 10;

    private List<T> list;

    private int total;
}
