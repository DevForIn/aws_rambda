package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

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

/**
 * Handler for requests to Lambda function.
 */
public class AppUserId implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DB_URL = "";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context){
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

//
//    public APIGatewayProxyResponseEvent testRequest(final APIGatewayProxyRequestEvent input, final Context context) {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        headers.put("X-Custom-Header", "application/json");
//
//        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
//                .withHeaders(headers);
//        try {
//            final String pageContents = this.getPageContents("https://checkip.amazonaws.com");
////            String output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents);
//
//            UserTest userTest = UserTest.builder()
//                    .id("JeongIn")
//                    .name("Eddy")
//                    .message("nice to meet U.")
//                    .build();
//
//            String jsonResponse = objectMapper.writeValueAsString(userTest);
//
//
//            return response
//                    .withStatusCode(200)
//                    .withBody(jsonResponse);
//        } catch (IOException e) {
//            return response
//                    .withBody("{}")
//                    .withStatusCode(500);
//        }
//    }

    private String getPageContents(String address) throws IOException{
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
