package com.ssafy.cafe.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.cafe.gpt.service.GptService;
import com.ssafy.cafe.model.dao.BookDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class GptUpdate {

    @Autowired
    private GptService gptService;


    @Scheduled(cron = "0 0,30 * * * *")
    public void updateTable() throws JsonProcessingException {
        gptService.addGptApi();
    }
}