package com.jt.manage.pojo;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jt.common.po.BasePojo;

@Table(name = "tb_item")
public class Item extends BasePojo {// 父类序列化，子类不用再序列化
	@Id//主键
	@GeneratedValue(strategy=GenerationType.IDENTITY)//主键自增
	private Long id;// 商品id
	private String title; // 商品标题
	private String sellPoint; // 卖点信息
	private Long price;// 价格 计算速度快 精确 如果此处用数要用java提供的算术工具类进行加减运算
	private Integer num;// 商品数量
	private String barcode;// 二维码
	private String image;// 图片连接 格式：1.jpg,2.jpg 拆分用,拆分
	private Long cid;// 商品分类ID
	private Integer status;// 商品状态信息 '默认值为1，可选值：1正常，2下架，3删除'

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price + ", num=" + num
				+ ", barcode=" + barcode + ", image=" + image + ", cid=" + cid + ", status=" + status + "]/n";
	}

}
