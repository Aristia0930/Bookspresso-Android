package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;

public interface ProductService {
    /**
     * 모든 상품 정보를 반환한다.
     * @return
     */
    List<Product> getProductList();
    
    /**
     * backend 관통 과정에서 추가됨
     * 상품의 정보, 판매량, 평점 정보를 함께 반환
     * @param productId
     * @return
     */
    ProductWithComment selectWithComment(Integer productId);
    
}
