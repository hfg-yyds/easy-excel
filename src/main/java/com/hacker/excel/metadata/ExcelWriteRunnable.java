package com.hacker.excel.metadata;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-08-25
 */
@FunctionalInterface
public interface ExcelWriteRunnable {

    /**
     * run
     */
    IPage<?> run();

}
