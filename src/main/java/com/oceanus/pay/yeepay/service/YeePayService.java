package com.oceanus.pay.yeepay.service;

import java.util.Map;

public interface YeePayService {
    Map<String, Object> yeePayYop(Map<String, Object> param, String url);
}
