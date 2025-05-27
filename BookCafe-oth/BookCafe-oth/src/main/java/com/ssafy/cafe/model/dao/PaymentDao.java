package com.ssafy.cafe.model.dao;

import com.ssafy.cafe.model.dto.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentDao {

    int insert(Payment payment);

    int update(@Param("tid") String tid, @Param("status") String status);
}
