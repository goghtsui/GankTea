package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * Created by wangbei on 2015/12/4.
 * 支付参数
 */
public class VipInfoEntity extends  HiveviewBaseEntity {
     /* * 对应一下参数
     *  productName=name;
     *  productType=categoryId;
     *  iconUrl=pic;
     *  pkgType=1;
     *  partnerId=p151203151651821;
     *  productId=id;
     */

    /**
     * 商户id
     */
    private String partnerId;
    /**
     *   付费为1
     */
    private String pkgType;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品序列叿
     */
    private String productSerial;
    /**
     * 产品类型
     */
    private String productType;

    /* 传过来的包名，主要用来区分极清和极清首映 */
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPkgType() {
        return pkgType;
    }

    public void setPkgType(String pkgType) {
        this.pkgType = pkgType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSerial() {
        return productSerial;
    }

    public void setProductSerial(String productSerial) {
        this.productSerial = productSerial;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
