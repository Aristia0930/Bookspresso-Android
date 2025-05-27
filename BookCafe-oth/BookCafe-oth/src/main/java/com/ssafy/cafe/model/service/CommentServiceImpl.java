package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.CommentDao;
import com.ssafy.cafe.model.dto.Comment;
import com.ssafy.cafe.model.dto.CommentInfo;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
    @Override
    public int addComment(Comment comment) {
        // TODO Auto-generated method stub
    	System.out.println("서비스 " + comment.toString());
        return commentDao.insert(comment);
    }

    @Override
    public Comment selectComment(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int removeComment(Integer id) {
        // TODO Auto-generated method stub
        return commentDao.delete(id);
    }

    @Override
    public int updateComment(Comment comment) {
        // TODO Auto-generated method stub
        return commentDao.update(comment);
    }

    @Override
    public List<CommentInfo> selectByProduct(Integer productId) {
        // TODO Auto-generated method stub
        return null;
    }


}
