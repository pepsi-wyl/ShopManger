package Dormitory_M.Pojp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Bill {
	private Integer id;   //id 
	private String billCode; //账单编码 
	private String productName; //商品名称 
	private String productDesc; //商品描述 
	private String productUnit; //商品单位
	private BigDecimal productCount; //商品数量 
	private BigDecimal totalPrice; //总金额
	private Integer isPayment; //是否支付 
	private Integer providerId; //供应商ID 
	private Integer createdBy; //创建者
	private Date creationDate; //创建时间
	private Integer modifyBy; //更新者
	private Date modifyDate;//更新时间
	
	private String providerName;//供应商名称
	
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public BigDecimal getProductCount() {
		return productCount;
	}
	public void setProductCount(BigDecimal productCount) {
		this.productCount = productCount;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getIsPayment() {
		return isPayment;
	}
	public void setIsPayment(Integer isPayment) {
		this.isPayment = isPayment;
	}
	
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "Bill{" +
				"id=" + id +
				", billCode='" + billCode + '\'' +
				", productName='" + productName + '\'' +
				", productDesc='" + productDesc + '\'' +
				", productUnit='" + productUnit + '\'' +
				", productCount=" + productCount +
				", totalPrice=" + totalPrice +
				", isPayment=" + isPayment +
				", providerId=" + providerId +
				", createdBy=" + createdBy +
				", creationDate=" + creationDate +
				", modifyBy=" + modifyBy +
				", modifyDate=" + modifyDate +
				", providerName='" + providerName + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Bill bill = (Bill) o;
		return Objects.equals(id, bill.id) && Objects.equals(billCode, bill.billCode) && Objects.equals(productName, bill.productName) && Objects.equals(productDesc, bill.productDesc) && Objects.equals(productUnit, bill.productUnit) && Objects.equals(productCount, bill.productCount) && Objects.equals(totalPrice, bill.totalPrice) && Objects.equals(isPayment, bill.isPayment) && Objects.equals(providerId, bill.providerId) && Objects.equals(createdBy, bill.createdBy) && Objects.equals(creationDate, bill.creationDate) && Objects.equals(modifyBy, bill.modifyBy) && Objects.equals(modifyDate, bill.modifyDate) && Objects.equals(providerName, bill.providerName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, providerId, createdBy, creationDate, modifyBy, modifyDate, providerName);
	}
}
