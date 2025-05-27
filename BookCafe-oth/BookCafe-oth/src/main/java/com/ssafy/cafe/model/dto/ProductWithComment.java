package com.ssafy.cafe.model.dto;

import java.util.List;

public class ProductWithComment {
    private Integer id;
    private String name;
    private String type;
    private Integer price;
    private String img;
    
    private int commentCount;
	private int totalSells;
    private double averageStars;
    
    private List<CommentInfo> comments;
    
   
	public ProductWithComment(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.type = product.getType();
        this.price = product.getPrice();
        this.img = product.getImg();
    }
    
    public ProductWithComment() {}
    
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

    public List<CommentInfo> getComments() {
		return comments;
	}

	public void setComments(List<CommentInfo> comments) {
		this.comments = comments;
	}

    public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getTotalSells() {
		return totalSells;
	}

	public void setTotalSells(int totalSells) {
		this.totalSells = totalSells;
	}

	public double getAverageStars() {
		return averageStars;
	}

	public void setAverageStars(double averageStars) {
		this.averageStars = averageStars;
	}


	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + ", img=" + img + "]";
	}
    
    
    
    
}
