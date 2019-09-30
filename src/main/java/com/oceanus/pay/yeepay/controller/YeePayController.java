package com.oceanus.pay.yeepay.controller;

import com.oceanus.pay.yeepay.service.YeePayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "1 - 代付代发接口")
@RestController
@RequestMapping("/yeepay")
public class YeePayController {

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

    @Autowired
    private YeePayService yeePayService;

    @ApiOperation("出款查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNo",
                    value = "批次号",
                    required = true),
            @ApiImplicitParam(name = "orderId",
                    value = "请求号-不输入则查询该批次全部打款明细；" +
                            "若输入则查询该批次某笔或某几笔订单，多笔订单号以逗号分割，最多可查询30笔"),
            @ApiImplicitParam(name = "product",
                    value = "业务类型-WTJS：代付代发；" +
                            "RJT：日结通" +
                            "ALL：查询所有业务类型" +
                            "为空时，默认值：WTJS",
                    defaultValue = "WTJS"),
            @ApiImplicitParam(name = "pageNo",
                    value = "页码-从1开始，依次递增，默认值：1",
                    defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",
                    value = "每页数目-默认值：100，区间：1-1000",
                    defaultValue = "100")
    })
    @PostMapping("/transfer-query")
    public ResponseEntity transferQuery(@RequestParam(value = "batchNo") String batchNo,
                                        @RequestParam(value = "orderId", required = false) String orderId,
                                        @RequestParam(value = "product", required = false, defaultValue = "WTJS") String product,
                                        @RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "100") String pageSize) {
        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("batchNo", batchNo);
                put("orderId", orderId);
                put("product", product);
                put("pageNo", pageNo);
                put("pageSize", pageSize);
            }
        };
        Map<String, Object> result = yeePayService.yeePayYop(param, transferQueryUrl);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("单笔出款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNo",
                    value = "批次号-必须唯一，长度15-20位之间，仅数字",
                    required = true),
            @ApiImplicitParam(name = "orderId",
                    value = "订单号-必须唯一，最长50位，允许数字、字母",
                    required = true),
            @ApiImplicitParam(name = "amount",
                    value = "出款金额-单位：元，非负浮点数，保留2位小数",
                    required = true),
            @ApiImplicitParam(name = "accountName",
                    value = "账户名-收款帐户的开户名称",
                    required = true),
            @ApiImplicitParam(name = "accountNumber",
                    value = "卡号-收款帐户的卡号",
                    required = true),
            @ApiImplicitParam(name = "bankCode",
                    value = "银行编码",
                    required = true),
            @ApiImplicitParam(name = "bankName",
                    value = "银行名称"),
            @ApiImplicitParam(name = "bankBranchName",
                    value = "支行名称"),
            @ApiImplicitParam(name = "provinceCode",
                    value = "省编码"),
            @ApiImplicitParam(name = "cityCode",
                    value = "市编码"),
            @ApiImplicitParam(name = "abstractInfo",
                    value = "额外信息-预留参数，无需传参"),
            @ApiImplicitParam(name = "product",
                    value = "业务类型-WTJS：代付代发；" +
                            "RJT：日结通；AUTO：自动路由；" +
                            "为空时，默认值：WTJS；" +
                            "为AUTO时优先使用代付代发，若代付代发未开通或可用余额不足，则使用日结通出款",
                    defaultValue = "WTJS"),
            @ApiImplicitParam(name = "feeType",
                    value = "手续费方式-SOURCE：商户承担，手续费从商户账户里扣除，出款金额即为实际打款金额；" +
                            "TARGET：用户承担，手续费从出款金额中扣除，实际打款金额为出款金额扣除手续费后的金额；" +
                            "为空时，默认值：SOURCE",
                    defaultValue = "SOURCE"),
            @ApiImplicitParam(name = "urgency",
                    value = "是否加急出款-0：非加急；" +
                            "1：加急；" +
                            "为空时，默认值：1",
                    defaultValue = "1"),
            @ApiImplicitParam(name = "desc",
                    value = "描述"),
            @ApiImplicitParam(name = "leaveWord",
                    value = "留言-给收款人银行备注；支持备注银行详见：留言字数限制")

    })
    @PostMapping("/transfer-send")
    public ResponseEntity transferSend(@RequestParam(value = "batchNo") String batchNo,
                                       @RequestParam(value = "orderId") String orderId,
                                       @RequestParam(value = "amount") String amount,
                                       @RequestParam(value = "accountName") String accountName,
                                       @RequestParam(value = "accountNumber") String accountNumber,
                                       @RequestParam(value = "bankCode") String bankCode,
                                       @RequestParam(value = "bankName", required = false) String bankName,
                                       @RequestParam(value = "bankBranchName", required = false) String bankBranchName,
                                       @RequestParam(value = "provinceCode", required = false) String provinceCode,
                                       @RequestParam(value = "cityCode", required = false) String cityCode,
                                       @RequestParam(value = "abstractInfo", required = false) String abstractInfo,
                                       @RequestParam(value = "product", required = false, defaultValue = "WTJS") String product,
                                       @RequestParam(value = "feeType", required = false, defaultValue = "SOURCE") String feeType,
                                       @RequestParam(value = "urgency", required = false, defaultValue = "1") String urgency,
                                       @RequestParam(value = "desc", required = false) String desc,
                                       @RequestParam(value = "leaveWord", required = false) String leaveWord) {
        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("batchNo", batchNo);
                put("orderId", orderId);
                put("amount", amount);
                put("accountName", accountName);
                put("accountNumber", accountNumber);
                put("bankCode", bankCode);
                put("bankName", bankName);
                put("bankBranchName", bankBranchName);
                put("provinceCode", provinceCode);
                put("cityCode", cityCode);
                put("abstractInfo", abstractInfo);
                put("product", product);
                put("feeType", feeType);
                put("urgency", urgency);
                put("desc", desc);
                put("leaveWord", leaveWord);
            }
        };
        Map<String, Object> result = yeePayService.yeePayYop(param, transferSendUrl);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("批次出款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupNumber",
                    value = "集团商户编号-单商户时为商户编号",
                    required = true),
            @ApiImplicitParam(name = "product",
                    value = "业务类型-WTJS：" +
                            "代付代发；" +
                            "RJT：日结通；AUTO：自动路由；" +
                            "为空时，默认值：WTJS；" +
                            "为AUTO时优先使用代付代发，若代付代发未开通或可用余额不足，则使用日结通出款",
                    required = true,
                    defaultValue = "WTJS"),
            @ApiImplicitParam(name = "totalAmount",
                    value = "出款总金额-非负浮点数，以元为单位，小数点保留2位",
                    required = true),
            @ApiImplicitParam(name = "totalCount",
                    value = "出款总笔数-每批次最多500笔",
                    required = true),
            @ApiImplicitParam(name = "detailJson",
                    value = "打款明细json串-示例：" +
                            "[{\"batchNo\":\"2018604263123456789\",\"orderID\":\"11222223\",\"amount\":\"0.01\",\"urgency\":\"1\",\"accountName\":\"张三\",\"accountNumber\":\"6225757522000999\",\"bankCode\":\"CCB\",\"feeType\":\"SOURCE\"},\n" +
                            "{\"batchNo\":\"2018604263123456789\",\"orderID\":\"1222222222456\",\"amount\":\"1.10\",\"urgency\":\"1\",\"accountName\":\"张三\",\"accountNumber\":\"6225757522000999\",\"bankCode\":\"CCB\",\"feeType\":\"SOURCE\"}]",
                    required = true)
    })
    @PostMapping("/transfer-batch-send")
    public ResponseEntity transferBatchSend(@RequestParam(value = "groupNumber") String groupNumber,
                                            @RequestParam(value = "product") String product,
                                            @RequestParam(value = "totalAmount") String totalAmount,
                                            @RequestParam(value = "totalCount") String totalCount,
                                            @RequestParam(value = "detailJson") String detailJson) {
        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("groupNumber", groupNumber);
                put("product", product);
                put("totalAmount", totalAmount);
                put("totalCount", totalCount);
                put("detailJson", detailJson);
            }
        };
        Map<String, Object> result = yeePayService.yeePayYop(param, transferBatchSendUrl);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("可用余额查询")
    @PostMapping("/customer-amount-query")
    public ResponseEntity customerAmountQuery() {
        Map<String, Object> result = yeePayService.yeePayYop(null, customerAmountQueryUrl);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("出款日对账下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantNo", value = "子商户编号"),
            @ApiImplicitParam(name = "dayString", value = "日期"),
            @ApiImplicitParam(name = "dataType", value = "数据类型-数据类型： trade:按交易流水 success:按银行返回成功状态")
    })
    @PostMapping("/remit-day-download")
    public ResponseEntity remitDayDownload(@RequestParam(value = "merchantNo", required = false) String merchantNo,
                                           @RequestParam(value = "dayString", required = false) String dayString,
                                           @RequestParam(value = "dataType", required = false) String dataType) {
        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("merchantNo", merchantNo);
                put("dayString", dayString);
                put("dataType", dataType);
            }
        };
        Map<String, Object> result = yeePayService.yeePayYop(param, remitDayDownloadUrl);
        return ResponseEntity.ok(result);
    }

}
