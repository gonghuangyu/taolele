package com.jt.common.pojo;

import java.util.Date;

public class Seckill {
	private Long seckillId;
	private String name;
	private Integer number;
	private Long InitialPrice;
	private Long seckillPrice;
	private String sellPoint;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	public Long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Long getInitialPrice() {
		return InitialPrice;
	}
	public void setInitialPrice(Long initialPrice) {
		InitialPrice = initialPrice;
	}
	public Long getSeckillPrice() {
		return seckillPrice;
	}
	public void setSeckillPrice(Long seckillPrice) {
		this.seckillPrice = seckillPrice;
	}
	
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//get方法决定了json转化过程中,展示的属性值
	public Long getStartTime() {
		return startTime.getTime();
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime.getTime();
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
