package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import helloworld.dto.boardDTO;
import helloworld.dto.UserTest;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {
            final String pageContents = this.getPageContents("https://checkip.amazonaws.com");
            String output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents);

            UserTest userTest = UserTest.builder()
                    .id("JeongIn")
                    .name("Eddy")
                    .message("nice to meet U.")
                    .build();

            String jsonResponse = objectMapper.writeValueAsString(userTest);


            return response
                    .withStatusCode(200)
                    .withBody(jsonResponse);
        } catch (IOException e) {
            return response
                    .withBody("{}")
                    .withStatusCode(500);
        }
    }

    /**
     * board 생성
     * POST /board
     *
     * @param input
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    public APIGatewayProxyResponseEvent createBoardRequest(final APIGatewayProxyRequestEvent input, final Context context) throws JsonProcessingException {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String requestBody = input.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        boardDTO tableRequest = objectMapper.readValue(requestBody, boardDTO.class);


        try(Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
            String sql = "INSERT INTO board (title, content) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1,tableRequest.getTitle());
            stmt.setString(2,tableRequest.getContent());
            int rowInserted = stmt.executeUpdate();

            if(rowInserted>0){
                return response.withStatusCode(201).withBody("{\"message\": \"게시글이 저장되었습니다.\"}");
            }else {
                return response.withStatusCode(500).withBody("{\"error\": \"질문 저장 실패\"}");
            }


        }catch (Exception e){
            e.printStackTrace();
            return response.withStatusCode(500).withBody("{\"error\": \"DB 연결 또는 데이터 저장 실패\"}");
        }
    }

    /**
     * Q&A 이력 조회
     * GET /qna
     *
     * @param input
     * @param context
     * @return
     */
    public APIGatewayProxyResponseEvent qnaRequest(final APIGatewayProxyRequestEvent input, final Context context){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // ✅ 예제: users 테이블에서 데이터 조회
            String sql = "SELECT * FROM questions";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder("[");
            while (rs.next()) {
                result.append(String.format("{\"id\": \"%s\", \"user_id\": \"%s\",\"question\": \"%s\",\"answer\": \"%s\"},", rs.getString("id"), rs.getString("user_id")
                        ,rs.getString("question"),rs.getString("answer")));
            }
            if (result.length() > 1) result.setLength(result.length() - 1); // 마지막 쉼표 제거
            result.append("]");

            return response.withStatusCode(200).withBody(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return response.withStatusCode(500).withBody("{\"error\": \"DB 연결 실패\"}");
        }
    }

    /**
     * 사용자 정보 조회
     * GET /user
     *
     * @param input
     * @param context
     * @return
     */
    public APIGatewayProxyResponseEvent userRequest(final APIGatewayProxyRequestEvent input, final Context context){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // ✅ 예제: users 테이블에서 데이터 조회
            String sql = "SELECT id, username FROM users";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder("[");
            while (rs.next()) {
                result.append(String.format("{\"id\": \"%s\", \"username\": \"%s\"},", rs.getString("id"), rs.getString("username")));
            }
            if (result.length() > 1) result.setLength(result.length() - 1); // 마지막 쉼표 제거
            result.append("]");

            return response.withStatusCode(200).withBody(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return response.withStatusCode(500).withBody("{\"error\": \"DB 연결 실패\"}");
        }
    }

    private String getPageContents(String address) throws IOException{
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}