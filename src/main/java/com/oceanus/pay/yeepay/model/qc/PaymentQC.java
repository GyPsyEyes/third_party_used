package com.oceanus.pay.yeepay.model.qc;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class PaymentQC {

    /**
     * 批次号 必须唯一，长度15-20位之间，仅数字
     */
    private String batchNo;

    /**
     * 订单号 必须唯一，最长50位，允许数字、字母
     */
    private String orderID;

    /**
     * 出款金额 单位：元，非负浮点数，保留2位小数
     */
    private String amount;

    /**
     * 是否加急
     */
    private String urgency;

    /**
     * 账户名 收款帐户的开户名称
     */
    private String accountName;

    /**
     * 账户号 收款帐户的卡号
     */
    private String accountNumber;

    /**
     * 银行支行名称
     */
    private String bankBranch;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 手续费方式
     */
    private String feeType;

    /**
     * 留言
     */
    private String leaveWord;
}
