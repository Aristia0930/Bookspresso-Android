package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.CommentDao;
import com.ssafy.cafe.model.dao.ProductDao;
import com.ssafy.cafe.model.dto.CommentInfo;
import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;


@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductDao pDao;

    @Autowired
    private CommentDao cDao;
    
    
    @Override
    public List<Product> getProductList() {
        return pDao.selectAll();
    }

    @Override
    public ProductWithComment selectWithComment(Integer productId) {
    	ProductWithComment productWithComment = pDao.selectWithInfo(productId);
    	List<CommentInfo> comments=cDao.selectByProduct(productId);
    	productWithComment.setComments(comments);
        return productWithComment;
    }

}
;