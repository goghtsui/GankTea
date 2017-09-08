/**
 * 产品详情
 *
 * @Title ProductInfoEntity.java
 * @Package com.hiveview.pay.entity
 * @author 郭松�?
 * @date 2014-6-24 下午3:41:54
 * @Description TODO
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * 产品详情
 */

public class VipProductInfoEntity extends HiveviewBaseEntity {

    /**
     * @Fields serialVersionUID:TODO
     */
    private static final long serialVersionUID = 1L;

    // ID
    private Integer id;
    // 计费包名称
    private String name;
    // 0未发布 1已发布
    private Integer isEffective;
    // 顺序
    private Integer seq;
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;

    private VipPackageListEntity vipPackageContentPriceVo;

    public VipPackageListEntity getVipPackageContentPriceVo() {
        return vipPackageContentPriceVo;
    }

    public void setVipPackageContentPriceVo(VipPackageListEntity vipPackageContentPriceVo) {
        this.vipPackageContentPriceVo = vipPackageContentPriceVo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VipProductInfoEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isEffective=" + isEffective +
                ", seq=" + seq +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", vipPackageContentPriceVo=" + vipPackageContentPriceVo +
                '}';
    }
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
     * 付费为1
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
