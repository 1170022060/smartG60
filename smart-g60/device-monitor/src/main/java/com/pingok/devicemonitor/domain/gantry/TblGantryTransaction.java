package com.pingok.devicemonitor.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/5/20 17:58
 */
@Data
@Table(name = "tbl_gantry_transaction")
public class TblGantryTransaction implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** 门架编号+交易批次号+批次内流水号（6位） */
    @Id
    private String tradeId;

    /** 门架编号 */
    private String gantryId;

    /** computerOrder */
    private Integer computerOrder;

    /** 1-OBU（单片式、双片式）            2-CPC卡             */
    private Integer mediaType;

    /** 按小时自增，不能回退。例如：2019080716 */
    private String hourBatchNo;

    /** 方向（1上行，2下行）+序号（2位），例如：上行：101，102，103；下行：201，202，203 */
    private Integer gantryOrderNum;

    /** 当前门架的hex值 */
    private String gantryHex;

    /** 对向门架的hex值 */
    private String gantryHexOpposite;

    /** YYYY-MM-DDTHH:mm:ss */
    private Date transTime;

    /** fee */
    private Integer fee;

    /** 单位：分，默认为0            实收金额=应收金额-优惠金额，应收金额为优惠前金额             */
    private Integer payFee;

    /** 单位：分，默认为0            优惠金额为优惠减免金额，未优惠填0；例如消费100元，95折后，应收金额为100元，优惠金额为5元             */
    private Integer discountFee;

    /** 单位：分，默认为0            如对于储值卡采取0元扣费处理，则该字段填写0，以进行TAC验证。             */
    private Integer transFee;

    /** 单位：分，默认为0            双片式标签交易前余额              */
    private Long balanceBefore;

    /** 单位：分，默认为0            双片式标签交易后余额             */
    private Long balanceAfter;

    /** 若存在为其他收费单元代收时，应将所在的收费单元和代收的收费单元ID号均填上，并且用“|”间隔 */
    private String tollIntervalId;

    /** feeGroup */
    private String feeGroup;

    /** 单位：分，若有代收用“|”分隔 */
    private String payFeeGroup;

    /** 单位：分，若有代收用“|”分隔 */
    private String discountFeeGroup;

    /** 车牌号码+间隔符+车牌颜色            车牌颜色2位数字:            0-蓝色，1-黄色，            2-黑色，3-白色，            4- 渐变绿色            5- 黄绿双拼色            6- 蓝白渐变色            7- 临时牌照            9- 未确定            11-绿色            12-红色            例：京A12345_1            ETC交易实际车牌不做车牌格式校验             */
    private String vehiclePlate;

    /** 1-一型客车            2-二型客车            3-三型客车            4-四型客车            11-一型货车            12-二型货车            13-三型货车            14-四型货车            15-五型货车            16-六型货车            21-一型专项作业车            22-二型专项作业车            23-三型专项作业车            24-四型专项作业车            25-五型专项作业车            26-六型专项作业车             */
    private Integer vehicleType;

    /** 1-一型客车            2-二型客车            3-三型客车            4-四型客车            11-一型货车            12-二型货车            13-三型货车            14-四型货车            15-五型货车            16-六型货车            21-一型专项作业车            22-二型专项作业车            23-三型专项作业车            24-四型专项作业车            25-五型专项作业车            26-六型专项作业车             */
    private Integer identifyVehicleType;

    /** 0-普通  8-军警 10-紧急  14-车队 （35号公告已定义）21-绿通车 22-联合收割机 23-抢险救灾 24-集装箱 25-大件运输；26-应急车 */
    private Integer vehicleClass;

    /** ETC计费成功必填。 */
    private String tac;

    /** PBOC定义，如06为传统交易，09为复合交易。计费成功必填。 */
    private String transType;

    /** PSAM中0016文件中的终端机编号。计费成功必填。 */
    private String terminalNo;

    /** 不超过8位。计费成功必填。 */
    private String terminalTransNo;

    /** 双片式标签计费成功必填。 */
    private Integer transNo;

    /** 计费成功必填。1位数字：            1-公路电子收费             */
    private Integer serviceType;

    /** 计费成功必填。1位数字            1-3DEX            2-SM4             */
    private Integer algorithmIdentifier;

    /** 消费密钥版本号 */
    private String keyVersion;

    /** 为左边起第一个RSU天线头（超车道开始），其他值类推 */
    private Integer antennaId;

    /** 计费模块版本号与计费参数版本号使用“|”分隔 */
    private String rateVersion;

    /** 单位：ms,交易成功时必填 */
    private Integer consumeTime;

    /** passId */
    private String passId;

    /** 1-有入口；2-无入口 */
    private Integer passState;

    /** 1-	交易成功            2-	交易失败             */
    private Integer tradeResult;

    /** 交易特情类型 */
    private String specialType;

    /** 入口车道编号 */
    private String enTollLaneId;

    /** 例如：1101ABCD */
    private String enTollStationHex;

    /** 入口时间 */
    private Date enTime;

    /** 1-混合（ETC+MTC）            3-ETC专用            参考0019文件             */
    private String enLaneType;

    /** 上一个门架的hex编号 */
    private String lastGantryHex;

    /** 通过上一个门架的时间 */
    private Date lastGantryTime;

    /** 通行介质类型填写1时，必填。1位数字            1-单片式OBU            2-双片式OBU             */
    private Integer obuSign;

    /** OBU物理地址 */
    private String obumac;

    /** 不超过20个字符，OBU的EF01系统信息文件的合同序列号； */
    private String obusn;

    /** OBU的EF01系统信息文件的合同版本； */
    private Integer obuVersion;

    /** 单片式OBU必填。填写0-100，若无法读数或读数异常时填-1 */
    private Integer obuElectrical;

    /** 天线返回的OBU状态。 */
    private String obuState;

    /** EF01系统信息文件的发行方标识，8字节，区域代码（4字节）+运营商标识（2字节）+保留（1字节）+密钥分散标识（1字节） */
    private String obuIssueId;

    /** YYYYMMDD */
    private Integer obuStartDate;

    /** YYYYMMDD */
    private Integer obuEndDate;

    /** 标签内发行的车牌号码+间隔符+车牌颜色，间隔符”_” */
    private String obuVehiclePlate;

    /** 4X版本0015文件第43字节 */
    private Integer obuVehicleType;

    /** CPU发行的车牌号码+间隔符+车牌颜色，间隔符”_” */
    private String cpuVehiclePlate;

    /** 4X版本0015文件第43字节 */
    private Integer cpuVehicleType;

    /** YYYYMMDD */
    private Integer cpuStartDate;

    /** YYYYMMDD */
    private Integer cpuEndDate;

    /** 用户卡的0015文件中记录的卡片版本号。 */
    private Integer cpuVersion;

    /** 2字节，省级行政区划代码（1字节）+运营商序号（1字节） */
    private String cpuNetId;

    /** EF01系统信息文件的发行方标识，8字节，区域代码（4字节）+运营商标识（2字节）+保留（1字节）+密钥分散标识（1字节） */
    private String cpuIssueId;

    /** 22-储值卡            23-记账卡             */
    private Integer cpuCardType;

    /** 不超过20位 */
    private String cpuCardId;

    /** ETC应用车辆信息文件中规定 */
    private Integer vehicleUserType;

    /** ETC车辆座位数/车辆载重(单位：kg) */
    private Integer vehicleSeat;

    /** 大于等于2,货车必填 */
    private Integer axleCount;

    /** 单位：kg  */
    private Integer totalWeight;

    /** 车辆长 */
    private Integer vehicleLength;

    /** 车辆宽 */
    private Integer vehicleWidth;

    /** 车辆高 */
    private Integer vehicleHight;

    /** 0正常；其他异常，具体见计费接口错误定义 */
    private Integer feeCalcResult;

    /** 计费模块返回的信息，最大400字节 */
    private String feeInfo1;

    /** 计费模块返回的信息，最大400字节 */
    private String feeInfo2;

    /** 计费模块返回的信息，最大400字节 */
    private String feeInfo3;

    /** 1-更新成功；2-更新失败 */
    private Integer updateResult;

    /** 过站信息中上一个门架的hex编号 */
    private String lastGantryHexPass;

    /** CPC卡EF02文件第1字节 */
    private Integer gantryPassCount;

    /** 省界出口有效，最大504字节，仅CPC卡适用，EF02第5字节开始记录 */
    private String gantryPassInfo;

    /** 仅CPC卡适用，最大10字节，EF04写入前的计费信息 */
    private String feeProvInfo;

    /** 单位：分            仅CPC卡适用，其他默认0            累计前             */
    private Integer feeSumLocalBefore;

    /** 单位：分            仅CPC卡适用，其他默认0            累计后（交易成功）             */
    private Integer feeSumLocalAfter;

    /** holidayState */
    private Integer holidayState;

    /** 校验码 */
    private String verifyCode;

    /** 1 反向            2前序门架已完成交易，本排禁止交易            3前序门架复合消费失败            4前序门架更新 CPC 卡过站信息失败            -1查询共享失败             */
    private Integer shareCode;

    /** B4帧返回的错误HEX码 */
    private String b4ErrCode;

    /** B5帧返回的错误HEX码 */
    private String b5ErrCode;

    /** B7帧返回的错误HEX码 */
    private String b7ErrCode;

    /** B8帧返回的错误HEX码 */
    private String b8ErrCode;

    /** 共享检查耗时 默认值-1 */
    private Integer shareCtime;

    /** 计费耗时 默认值-1 */
    private Integer calcTime;

    /** 以“|”分隔（天线耗时） */
    private String tagTime;

    /** 软件错误码 */
    private Integer softErr;

    /** 交易重试次数 */
    private Integer retryTimes;

    /** 1 待获取用户信息            2 待计费写卡            3 待更新过站信息            4 流程结束             */
    private Integer transStep;

    /** 不超过38个字符，有匹配时填写 */
    private String vehiclePicId;

    /** 不超过38个字符，有匹配时填写 */
    private String vehicleTailPicId;

    /** 当前收费单元同一车辆产生的多条计扣费成功关联流水号，以“|”分隔，用于重复计费稽核 */
    private String relatedTradeId;

    /** 当前收费单元同一车辆产生的全部交易通行记录的关联流水号，以“|”分隔 */
    private String allRelatedTradeId;

    /** 0-未处理（默认）；1-已匹配；2-反向数据 */
    private Integer matchStatus;

    /** 0-未处理（默认）；1-有效数据；2-无效(重复)数据 */
    private Integer validStatus;

    /** 0-未处理（默认）；1-已处理 */
    private Integer dealStatus;

    /** 0-交易成功；1-交易失败 */
    private Integer tradeStatus;

    /** 门架后台入库时间 YYYY-MM-DDTHH:mm:ss */
    private Date stationDbtime;

    /** 门架后台处理时间 YYYY-MM-DDTHH:mm:ss */
    private Date stationDealTime;

    /** 门架后台去重时间 YYYY-MM-DDTHH:mm:ss */
    private Date stationValidTime;

    /** 门架后台匹配时间 YYYY-MM-DDTHH:mm:ss */
    private Date stationMatchTime;

    /** vehicleSign */
    private String vehicleSign;

    /** feeCalcSpecial */
    private Integer feeCalcSpecial;

    /** lastGantryHexFee */
    private String lastGantryHexFee;

    /** gantryType */
    private String gantryType;

    /** obufeeSumBefore */
    private Long obufeeSumBefore;

    /** obufeeSumAfter */
    private Long obufeeSumAfter;

    /** obuProvfeeSumBefore */
    private Long obuProvfeeSumBefore;

    /** obuProvfeeSumAfter */
    private Long obuProvfeeSumAfter;

    /** cardfeeSumBefore */
    private Long cardfeeSumBefore;

    /** cardfeeSumAfter */
    private Long cardfeeSumAfter;

    /** noCardTimesBefore */
    private Integer noCardTimesBefore;

    /** noCardTimesAfter */
    private Integer noCardTimesAfter;

    /** provinceNumBefore */
    private Integer provinceNumBefore;

    /** provinceNumAfter */
    private Integer provinceNumAfter;

    /** obuTradeResult */
    private Integer obuTradeResult;

    /** tradeType */
    private Integer tradeType;

    /** obuInfoTypeRead */
    private Integer obuInfoTypeRead;

    /** obuInfoTypeWrite */
    private Integer obuInfoTypeWrite;

    /** obuPassState */
    private Integer obuPassState;

    /** feeVehicleType */
    private Integer feeVehicleType;

    /** obuLastGantryHex */
    private String obuLastGantryHex;

    /** obuLastGantryTime */
    private Date obuLastGantryTime;

    /** spare1 */
    private String spare1;

    /** spare2 */
    private String spare2;

    /** spare3 */
    private String spare3;

    /** spare4 */
    private String spare4;

    /** spare5 */
    private Long spare5;

    /** spare6 */
    private Long spare6;

    /** spare7 */
    private String spare7;

    /** spare8 */
    private String spare8;

    /** obuPayFeeSumBefore */
    private Long obuPayFeeSumBefore;

    /** obuPayFeeSumAfter */
    private Long obuPayFeeSumAfter;

    /** obuDiscountFeeSumBefore */
    private Long obuDiscountFeeSumBefore;

    /** obuDiscountFeeSumAfter */
    private Long obuDiscountFeeSumAfter;

    /** obuMileageBefore */
    private Long obuMileageBefore;

    /** obuMileageAfter */
    private Long obuMileageAfter;

    /** provMinFee */
    private Long provMinFee;

    /** feeSpare1 */
    private Long feeSpare1;

    /** feeSpare2 */
    private Long feeSpare2;

    /** feeSpare3 */
    private String feeSpare3;

    /** feeProvBeginHex */
    private String feeProvBeginHex;

    /** chargeUnitId */
    private String chargeUnitId;

    /** transDate */
    private String transDate;

    /** tradeReadCiphertext */
    private String tradeReadCiphertext;

    /** tradeWriteCiphertext */
    private String tradeWriteCiphertext;

    /** readCiphertextVerify */
    private Integer readCiphertextVerify;

    /** rateCompute */
    private Integer rateCompute;

    /** rateFitCount */
    private Integer rateFitCount;

    /** versionInfo */
    private String versionInfo;

    /** 本次交易前标签累计应收金额（省内） */
    private Long obuProvPayFeeSumBefore;

    /** 本次交易后标签累计应收金额（省内） */
    private Long obuProvPayFeeSumAfter;

    /** 0-未拟合或拟合成功，1-拟合失败，默认值-1 */
    private Integer pathFitFlag;

    /** 对应计费协议中计费特情值组合 */
    private String feeCalcSpecials;

    /** ETC表示本省累计应收金额取整（含本门架应收金额）。CPC表示本省累计金额取整（含本门架计费金额），对应计费模块返回的payFeeProvSumLocal字段 */
    private Long payFeeProvSumLocal;

    /** PC-RSU接口协议版本号 */
    private Integer pcrsuVersion;

    /** 本次写入的CPC卡EF02文件中过站信息 */
    private String gantryPassInfoAfter;

    /** CPC卡计费信息文件写入结果 0-成功 1-失败，默认值-1 */
    private Integer cpcFeeTradeResult;

    /** 本次写入到CPC EF04中本省计费信息 */
    private String feeProvEf04;

    /** CPC卡是否进行全省路径拟合的标识，0-未拟合，大于0的值省内自行定义拟合方式。默认值-1 */
    private Integer fitProvFlag;

    /** 读取CPC卡EF02文件中过站信息中门架数量。默认值-1 */
    private Integer gantryPassCountBefore;

    /** 拟合后的本省省界入口ETC门架HEX码 */
    private String feeProvBeginHexFit;

    /** 拟合后的本省省界入口ETC门架的通行时间 */
    private Date feeProvBeginTimeFit;

    /** 收费单元组合处理标识 */
    private String tollIntervalSign;

    /** 本省累计通行金额计算方式 */
    private Integer provMinFeeCalcMode;

    /** 本省累计通行金额计费方式组合 */
    private String provMinFeeCalcMerge;

    /** 计费里程数 */
    private Integer feeMileage;

    /** CPC本省省界入口ETC门架通行时间 */
    private Date feeProvBeginTime;

    /** CPC本省累计拆分金额 */
    private Integer feeSumLocalAfterEf04;

    /** CPC EF02 文件中上个门架的计费金额 */
    private Integer lastGantryFeePass;

    /** CPC EF02 文件中上个门架的计费里程 */
    private Integer lastGantryMilePass;

    /** 路侧单元厂商代码 */
    private String rsuManuid;

    /** 计费协议数据结构版本号 */
    private String feeDataVersion;

    /** 配置的反向门架阈值组合 */
    private String gantryHexOppotime;

    /** 本次交易后标签累计应收金额（门架拟合前） */
    private Integer obuPayFeeSumAfterNoFit;

    /** 本次交易后标签累计实收金额（门架拟合前） 默认值-1 如有拟合，则不含拟合金额 */
    private Integer obuFeeSumAfterNoFit;

    /** 本次交易前本省标签/CPC累计里程(门架拟合前）（单位： M 即米）：默认值-1，如有拟合，则不含拟合金额 */
    private Integer obuMileageAfterNoFit;

    /** 交易前读取的本省或邻省计费起点 */
    private String feeProvBeginHexBefore;

    /** 门架拟合结果 0-拟合未正常开启 1-路径正常无需拟合 2-拟合成功计费成功 3-拟合成功计费失败 4-拟合失败 */
    private Integer gantryFitResult;

    /** 门架拟合参数版本信息 */
    private String gantryFitVersion;

    /** 门架拟合出的HEX集合 */
    private String gantryFitHexs;

    /** 门架拟合出的收费单元集合，以“|”分隔，若一个HEX存在多个代收以“_”分隔 */
    private String gantryFitTolls;

    /** 邻省拟合门架的应收金额 */
    private Integer gantryFitNprovPayfee;

    /** 邻省拟合门架的优惠金额 */
    private Integer gantryFitNprovDiscontfee;

    /** 邻省拟合门架的实收金额 */
    private Integer gantryFitNprovRealfee;

    /** 邻省拟合门架的里程 */
    private Integer gantryFitNprovMiles;

    /** 邻省拟合门架的应收金额组合 */
    private String gantryFitNprovPayfeeGroup;

    /** 拟合后邻省全省累计应收金额 */
    private Integer gantryFitNprovPayFeeSum;

    /** 拟合后邻省全省累计拆分金额 */
    private Integer gantryFitNprovRealFeeSum;

    /** 拟合后邻省全省累计里程 */
    private Integer gantryFitNprovMileSum;

    /** 邻省拟合门架的预实收金额 */
    private Integer gantryFitNprovPreRealfee;

    /** 邻省拟合门架的实收金额 */
    private String pathfitDesc;

    /** 邻省省份代码 */
    private Integer gantryFitNeighborProvCode;
}
