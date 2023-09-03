package com.hacker.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hacker.excel.metadata.EasyData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *  Demo
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-08-25
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DemoExportData implements EasyData {

    @ExcelProperty(order = 1)
    private String string;

    @ExcelProperty(order = 2)
    @NotNull(message = "年龄不能为空")
    private String age;

    @ExcelProperty(order = 3)
    private String doubleData;

    @ExcelProperty(order = 4)
    private String money;

    @ExcelProperty(order = 5)
    private String localDateTime;

    private String test;

}
