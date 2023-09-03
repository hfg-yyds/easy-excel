package com.hacker.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hacker.excel.entity.DemoExportData;
import com.hacker.excel.mapper.TestMapper;
import com.hacker.excel.metadata.EasyData;
import com.hacker.excel.po.TestPo;
import com.hacker.excel.util.ExcelUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-08-25
 */
public class TestCase {

    @Test
    public void test() {
        AtomicInteger integer = new AtomicInteger(0);
        ExcelUtils.importExcel("E:\\github\\easy-excel\\excel\\test.xlsx", DemoExportData.class, 1, (list -> {
            for (EasyData easyExportData : list) {
                integer.incrementAndGet();
                System.out.println(easyExportData);
            }
        }));
        System.out.println(integer.incrementAndGet());
    }

    @Autowired
    private TestMapper mapper;

    @Test
    public void test2() {
        // 方法1: 如果写到同一个sheet
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write("filename", DemoExportData.class).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
            List<TestPo> records = null;
            Page<TestPo> page1 = new Page<>(1, 500);
            do {
                IPage<TestPo> page = mapper.selectPage(page1, null);
                records = page.getRecords();
                excelWriter.write(records, writeSheet);
            } while (records.size() != 0);
        }
    }


}
