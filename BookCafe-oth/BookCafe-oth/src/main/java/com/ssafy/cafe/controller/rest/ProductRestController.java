package com.ssafy.cafe.controller.rest;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;
import com.ssafy.cafe.model.service.ProductService;

@RestController
@RequestMapping("/rest/product")
@CrossOrigin("*")
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	@Operation(summary="음식 전체 조회",
			description = "")
	public ResponseEntity<List<Product>> getProducts(){
		List<Product> products=productService.getProductList();
		
		return ResponseEntity.ok(products);
		
		
		
		
	}
	
	@GetMapping("/{productId}")
	@Operation(summary="음식 아이디로 음식 조화",
			description = "")
	public ProductWithComment selectWithComment(@PathVariable Integer productId) {
		
		ProductWithComment comment=productService.selectWithComment(productId);
		
		
		return comment;
	}
	


    
    
    

	

}