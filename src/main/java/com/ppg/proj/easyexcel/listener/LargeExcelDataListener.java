package com.ppg.proj.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.ppg.proj.easyexcel.model.LargeExcelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LargeExcelDataListener extends AnalysisEventListener<LargeExcelData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeExcelDataListener.class);
    private int count = 0;

    @Override
    public void invoke(LargeExcelData largeExcelData, AnalysisContext analysisContext) {
        if (count == 0) {
            LOGGER.info("开始读取的行数据: {}", JSON.toJSONString(largeExcelData));
        }
        count ++;
        if (count % 100000 == 0) {
            LOGGER.info("已经读取的行数: {}", count);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("读取的总行数为: {}", count);
    }
}
