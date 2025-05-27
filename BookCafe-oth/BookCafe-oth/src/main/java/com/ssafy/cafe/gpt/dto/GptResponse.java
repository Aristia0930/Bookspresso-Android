package com.ssafy.cafe.gpt.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GptResponse {
    private String id;
    private String object;
    private long createdAt;
    private String status;
    private String model;
    private List<Output> output;
    private Usage usage;

    // getters/setters
    @Data
    public static class Output {
        private String id;
        private String type;
        private List<Content> content;
        private String role;
        // getters/setters
        @Data
        public static class Content {
            private String type;
            private String text;
            // getters/setters
        }
    }
    @Data
    public static class Usage {
        private int inputTokens;
        private int outputTokens;
        private int totalTokens;
        // getters/setters
    }
}
