package com.ssafy.cafe.scheduled;

import com.ssafy.cafe.model.dao.BookDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookRentalScheduler {

    private final BookDao bookDao;

    // 매일 새벽 1시에 실행 (cron: 초 분 시 일 월 요일)
    @Scheduled(cron = "0 1 * * * *")
    public void updateOverdueStatus() {
        int updatedCount = bookDao.updateOverdueRentals();
        System.out.println("Overdue 상태로 변경된 대여 건수: " + updatedCount);
    }


}