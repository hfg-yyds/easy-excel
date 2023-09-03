package com.hacker.excel.util;

import com.alibaba.excel.EasyExcel;
import com.hacker.excel.metadata.ExcelExportRunnable;
import com.hacker.excel.listener.BaseDataListener;
import com.hacker.excel.metadata.EasyData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * <p>
 * Excel操作工具类
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-08-25
 */
public class ExcelUtils {

    /**
     * 读取第一个Sheet 文件流自动关闭
     *
     * @param filePath 文件路径
     * @param head     头
     * @param runnable 实体操作函数
     */
    public static void importExcel(String filePath, Class<? extends EasyData> head, ExcelExportRunnable runnable) {
        EasyExcel.read(filePath, head, new BaseDataListener(runnable)).sheet().doRead();
    }

    /**
     * 读取第一个Sheet 文件流自动关闭
     *
     * @param filePath      文件路径
     * @param head          头
     * @param headRowNumber row起始行数
     * @param runnable      实体操作函数
     */
    public static void importExcel(String filePath, Class<? extends EasyData> head, Integer headRowNumber, ExcelExportRunnable runnable) {
        EasyExcel.read(filePath, head, new BaseDataListener(runnable)).sheet().headRowNumber(headRowNumber).doRead();
    }

    /**
     * 导出
     * @param response
     * @param filename
     * @param sheetName
     * @param head
     * @param supplier
     * @param <T>
     * @throws IOException
     */
    public static <T> void write(HttpServletResponse response, String filename,
                                 Class<T> head, Supplier<Collection<?>> supplier) {

    }


}
