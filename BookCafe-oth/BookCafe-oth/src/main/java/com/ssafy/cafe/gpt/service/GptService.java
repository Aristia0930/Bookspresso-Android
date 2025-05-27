package com.ssafy.cafe.gpt.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.cafe.gpt.GptConfig;
import com.ssafy.cafe.gpt.dto.GptRequest;
import com.ssafy.cafe.gpt.dto.GptResponse;
import com.ssafy.cafe.gpt.dto.RecommendationResponse;
import com.ssafy.cafe.model.dao.BookDao;
import com.ssafy.cafe.model.dao.ProductDao;
import com.ssafy.cafe.model.dao.RecommendationDao;
import com.ssafy.cafe.model.dto.Book;
import com.ssafy.cafe.model.dto.BookRecommendation;
import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
@Slf4j
@Service
public class GptService {

    private final String secretKey;
    private final WebClient webClient;
    @Autowired
    private GptConfig gptConfig;
    @Value("${gpt.model}")
    private String model;

    @Autowired
    private ProductDao productDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private RecommendationDao recommendationDao;

    public GptService(@Value("${gpt.key}") String secretKey) {
        this.secretKey = secretKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + secretKey)
                .build();
    }

    public RecommendationResponse gptApiRequest() throws JsonProcessingException {
//        String text="You are a helpful assistant that recommends one book along with a suitable drink and dessert based on the given lists.\n" +
//                "\n" +
//                "Available Books (each has: isbn, title, author, summary, status, img):\n" +gptConfig.getBookList() +
//                "\n" +
//                "Available Products (each has: id, name, type, price, img; type is 'drink' or 'dessert'):\n" +
//                gptConfig.getProducts()+
//                "Please recommend one book and one drink and one dessert that best match the book. \n" +
//                "\n" +
//                "Return the response in this exact JSON format only:\n" +
//                "\n" +
//                "{\n" +
//                "  \"book\": {\n" +
//                "    \"isbn\": \"<isbn>\",\n" +
//                "    \"title\": \"<title>\",\n" +
//                "    \"author\": \"<author>\",\n" +
//                "    \"summary\": \"<summary>\",\n" +
//                "    \"status\": \"<status>\",\n" +
//                "    \"img\": \"<img>\"\n" +
//                "  },\n" +
//                "  \"drink\": {\n" +
//                "    \"id\": <id>,\n" +
//                "    \"name\": \"<name>\",\n" +
//                "    \"type\": \"drink\",\n" +
//                "    \"price\": <price>,\n" +
//                "    \"img\": \"<img>\"\n" +
//                "  },\n" +
//                "  \"dessert\": {\n" +
//                "    \"id\": <id>,\n" +
//                "    \"name\": \"<name>\",\n" +
//                "    \"type\": \"dessert\",\n" +
//                "    \"price\": <price>,\n" +
//                "    \"img\": \"<img>\"\n" +
//                "  }\n" +
//                "}";
        String text = "당신은 책, 음료, 디저트 목록을 참고하여 책 한 권과 그에 어울리는 음료와 디저트를 추천하는 친절한 어시스턴트입니다.\n" +
                "\n" +
                "사용 가능한 책 목록 (각 항목: isbn, title, author, summary, status, img):\n" + gptConfig.getBookList() +
                "\n" +
                "사용 가능한 상품 목록 (각 항목: id, name, type, price, img; type은 'drink' 또는 'dessert'):\n" + gptConfig.getProducts() +
                "\n" +
                "책과 가장 잘 어울리는 음료 하나와 디저트 하나를 함께 추천해주세요.\n" +
                "또한, 추천한 이유를 255자 이내의 한국어로 간결하게 작성해주세요.\n" +
                "\n" +
                "다음과 같은 JSON 형식으로만 응답해주세요:\n" +
                "\n" +
                "{\n" +
                "  \"book\": {\n" +
                "    \"isbn\": \"<isbn>\",\n" +
                "    \"title\": \"<title>\",\n" +
                "    \"author\": \"<author>\",\n" +
                "    \"summary\": \"<summary>\",\n" +
                "    \"status\": \"<status>\",\n" +
                "    \"img\": \"<img>\"\n" +
                "  },\n" +
                "  \"drink\": {\n" +
                "    \"id\": <id>,\n" +
                "    \"name\": \"<name>\",\n" +
                "    \"type\": \"drink\",\n" +
                "    \"price\": <price>,\n" +
                "    \"img\": \"<img>\"\n" +
                "  },\n" +
                "  \"dessert\": {\n" +
                "    \"id\": <id>,\n" +
                "    \"name\": \"<name>\",\n" +
                "    \"type\": \"dessert\",\n" +
                "    \"price\": <price>,\n" +
                "    \"img\": \"<img>\"\n" +
                "  },\n" +
                "  \"reason\": \"<책, 음료, 디저트를 추천한 이유를 한국어로 255자 이내로 작성>\"\n" +
                "}";

        GptRequest request = new GptRequest();
        request.setModel(model);
        request.setInput(text);
        request.setTemperature(1.2);
        try {


            GptResponse rs = webClient.post()
                    .uri("v1/responses")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GptResponse.class)
                    .block();
            if (rs == null || rs.getOutput() == null || rs.getOutput().isEmpty()) {
                throw new RuntimeException("GPT 응답이 비어 있습니다.");
            }

            System.out.println(rs.getOutput());

            String jsonString = rs.getOutput().get(0).getContent().get(0).getText();


            ObjectMapper mapper = new ObjectMapper();
            RecommendationResponse response = mapper.readValue(jsonString, RecommendationResponse.class);

            return response;
        }catch (WebClientResponseException.TooManyRequests ex) {
            // OpenAI Rate Limit 예외 (HTTP 429)
            log.warn("OpenAI Rate Limit 초과: {}", ex.getMessage());
            throw new RuntimeException("AI 요청이 너무 많습니다. 잠시 후 다시 시도해주세요.");

        } catch (WebClientResponseException ex) {
            // OpenAI에서 다른 HTTP 오류 (예: 401, 500 등)
            log.error("GPT API 호출 실패: 상태 코드 {}, 응답 {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw new RuntimeException("AI 서버 오류가 발생했습니다.");

        } catch (JsonProcessingException ex) {
            log.error("GPT 응답 JSON 파싱 실패: {}", ex.getMessage());
            throw ex; // 이건 메서드에 throws 선언되어 있음

        } catch (Exception ex) {
            log.error("GPT 처리 중 예기치 못한 오류: {}", ex.getMessage(), ex);
            throw new RuntimeException("AI 추천 처리 중 오류가 발생했습니다.");
        }
    }

    //db 에 넣기
    public int gptApiInstert(RecommendationResponse response){
        BookRecommendation bookRecommendation=new BookRecommendation();

        bookRecommendation.setRecommendDate(LocalDateTime.now());
        bookRecommendation.setIsbn(response.getBook().getIsbn());
        bookRecommendation.setDrinkId(response.getDrink().getId());
        bookRecommendation.setDessertId(response.getDessert().getId());
        bookRecommendation.setReason(response.getReason());
        int rs=recommendationDao.insert(bookRecommendation);
        return rs;
    }


    //스케줄러 조회 실행
    public int addGptApi() throws JsonProcessingException {
        RecommendationResponse response= gptApiRequest();
        return gptApiInstert(response);
    }

    //조회
    public RecommendationResponse getApiRecommendation(){
        BookRecommendation bookRecommendation=recommendationDao.select();

        //책isbn
        String isbn=bookRecommendation.getIsbn();
        Book book =bookDao.getBook(isbn);
        int drinkId=bookRecommendation.getDrinkId();
        ProductWithComment productw1=productDao.selectWithInfo(drinkId);
        int dessertId=bookRecommendation.getDessertId();
        ProductWithComment productw2=productDao.selectWithInfo(dessertId);

        RecommendationResponse response=new RecommendationResponse();

        Product product1=new Product();
        product1.setId(productw1.getId());
        product1.setImg(productw1.getImg());
        product1.setName(productw1.getName());
        product1.setPrice(productw1.getPrice());
        product1.setType(productw1.getType());

        Product product2=new Product();
        product2.setId(productw2.getId());
        product2.setImg(productw2.getImg());
        product2.setName(productw2.getName());
        product2.setPrice(productw2.getPrice());
        product2.setType(productw2.getType());


//        System.out.println(bookRecommendation.toString());
        response.setBook(book);
        response.setDrink(product1);
        response.setDessert(product2);
//        System.out.println(bookRecommendation.getReason());
        response.setReason(bookRecommendation.getReason());

        return response;
    }



}
