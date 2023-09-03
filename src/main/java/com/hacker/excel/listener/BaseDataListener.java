package com.hacker.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.hacker.excel.metadata.ExcelExportRunnable;
import com.hacker.excel.metadata.EasyData;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 通用数据监听器
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-08-25
 */
@Slf4j
public class BaseDataListener implements ReadListener<EasyData> {

    /**
     * 导入对象1000条
     */
    private static final int BATCH_COUNT = 1000;

    /**
     * 缓存的数据
     */
    private List<EasyData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 存储对象函数表达式
     */
    private final ExcelExportRunnable runnable;

    /**
     * 手动校验
     */
    private final Validator validator;

    /**
     * 构造器
     *
     * @param runnable runnable
     */
    public BaseDataListener(ExcelExportRunnable runnable) {
        this.runnable = runnable;
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public void onException(Exception e, AnalysisContext context) throws Exception {
        int row = context.readRowHolder().getRowIndex() + 1;
        String.format("第{%s}行参数校验失败,原因:" + e.getMessage(), row);
        log.error("Excel解析失败:",e);
    }

    @Override
    public void invoke(EasyData data, AnalysisContext context) {
        //当前总行数
        Integer rowNumber = context.readSheetHolder().getApproximateTotalRowNumber();
        int row = context.readRowHolder().getRowIndex() + 1;
        //校验
        Set<ConstraintViolation<EasyData>> set = validator.validate(data);
        for (ConstraintViolation<EasyData> constraintViolation : set) {
            throw new IllegalArgumentException(constraintViolation.getMessage());
        }
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            runnable.run(cachedDataList);
            //存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //这里也要保存数据,确保最后遗留的数据也存储到数据库
        runnable.run(cachedDataList);
        cachedDataList.clear();
    }

}
