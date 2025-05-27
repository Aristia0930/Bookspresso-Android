package com.ssafy.cafe.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Comment;
import com.ssafy.cafe.model.service.CommentService;



@RestController
@RequestMapping("/rest/comment")
@CrossOrigin("*")
public class CommentRestController {

    @Autowired
    private CommentService service;
    
    @PostMapping("")
	@Operation(summary="평가 추가",
			description = "평가 dto를 넘기면 추가됨")
    public boolean addComment(@RequestBody Comment comment) {
    	System.out.println(comment.toString());
    	int result = service.addComment(comment);
    	if(result == 1) {
    		System.out.println();
    		return true;
    	} else {
    		return false;
    	}
    }
    

    
    @DeleteMapping("/{id}")
	@Operation(summary="평가 아이디로 삭제",
			description = "")
    public boolean removeComment(@PathVariable int id) {
    	
    	int result = service.removeComment(id);
    	if(result == 1) {

    		return true;
    	} else {
    		return false;
    	}
    }
    		
    		

    @PutMapping("")
	@Operation(summary="평가 수정",
			description = "")
    public boolean update(@RequestBody Comment comment) {
    	System.out.println(comment.toString());
    	int result = service.updateComment(comment);
    	if(result == 1) {

    		return true;
    	} else {
    		return false;
    	}

    	
    }

    


}
