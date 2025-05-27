package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;

public interface ProductDao {
    /**
     * 모든 상품정보를 조회한다. 
     * 
     * @return
     */
    List<Product> selectAll();
    
    /**
     * ID에 해당하는 상품의 comment 갯수, 평점평균, 전체 판매량 정보를 함께 반환
     * 
     * @param productId
     * @return
     */
    ProductWithComment selectWithInfo(Integer productId);
}
