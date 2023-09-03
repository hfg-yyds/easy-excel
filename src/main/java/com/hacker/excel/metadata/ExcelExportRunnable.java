package com.hacker.excel.metadata;

import java.util.List;

/**
 * <p>
 * Excel导入函数表达式
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-08-25
 */
@FunctionalInterface
public interface ExcelExportRunnable {

    /**
     * run
     *
     * @param list list
     */
    void run(List<EasyData> list);

}
