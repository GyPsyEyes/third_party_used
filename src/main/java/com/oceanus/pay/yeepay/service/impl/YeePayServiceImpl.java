package com.oceanus.pay.yeepay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.oceanus.pay.constants.YopConst;
import com.oceanus.pay.yeepay.service.YeePayService;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import com.yeepay.g3.sdk.yop.client.YopRsaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class YeePayServiceImpl implements YeePayService {

    @Value("${yeepay.sanbox.customer-number}")
    private String customerNumber;

    @Value("${yeepay.sanbox.group-number}")
    private String groupNumber;

    @Value("${yeepay.sanbox.private-key}")
    private String privateKey;

    @Value("${yeepay.sanbox.public-key}")
    private String publicKey;

    @Value("${yeepay.sanbox.transfer-query-url}")
    private String transferQueryUrl;

    @Value("${yeepay.sanbox.transfer-send-url}")
    private String transferSendUrl;

    @Value("${yeepay.sanbox.transfer-batch-send-url}")
    private String transferBatchSendUrl;

    @Value("${yeepay.sanbox.customer-amount-query-url}")
    private String customerAmountQueryUrl;

    @Value("${yeepay.sanbox.remit-day-download-url}")
    private String remitDayDownloadUrl;

    @Override
    public Map<String, Object> yeePayYop(Map<String, Object> param, String url) {

        Map<String, Object> result = new HashMap<>();

        // 封装请求参数
        YopRequest yopRequest = new YopRequest(customerNumber);
        if (!CollectionUtils.isEmpty(param)) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                yopRequest.addParam(entry.getKey(), entry.getValue());
            }
        }

        // YOP请求
        YopResponse yopResponse = null;
        try {
            yopResponse = YopRsaClient.post(url, yopRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 处理失败结果
        if (YopConst.FAILURE.equals(yopResponse.getState()) && yopResponse.getError() != null) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("errorCode", yopResponse.getError().getCode());
            errorMap.put("erorMsg", yopResponse.getError().getMessage());

            if (!StringUtils.isEmpty(yopResponse.getError().getSubCode())) {
                errorMap.put("errorSubCode", yopResponse.getError().getSubCode());
                errorMap.put("errorSubMsg", yopResponse.getError().getSubMessage());
            } else {
                errorMap.put("errorSubCode", null);
                errorMap.put("errorSubMsg", null);
            }

            result.put("code", "-1");
            result.put("msg", errorMap);
        }

        // 处理成功结果
        if (!StringUtils.isEmpty(yopResponse.getStringResult())) {
            TreeMap<String, Object> treeMap = JSON.parseObject(yopResponse.getStringResult(), new TypeReference<TreeMap<String, Object>>() {
            });
            result.put("code", "0");
            result.put("msg", treeMap);
        }
        return result;
    }
}
