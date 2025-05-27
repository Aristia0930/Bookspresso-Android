package com.ssafy.cafe.model.dto;

public class OrderDetailInfo {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    
    private String img; // 상품이미지
	private String name;// 상품명
    private String type; // 상품 종류
    private String unitPrice; //상품가격
    private int sumPrice; // quantity * 상품가격
    
    public OrderDetailInfo(Integer id, Integer orderId, Integer productId, Integer quantity) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
    
    public OrderDetailInfo(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    
    public OrderDetailInfo(Integer orderId, Integer productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
    
    public OrderDetailInfo() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
    public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}

    
}
