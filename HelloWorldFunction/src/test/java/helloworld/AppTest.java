package helloworld;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import helloworld.dto.UserTest;
import org.junit.Test;

public class AppTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void successfulResponse() throws Exception {
    App app = new App();
    APIGatewayProxyResponseEvent result = app.handleRequest(null, null);
    assertEquals(200, result.getStatusCode().intValue());
    assertEquals("application/json", result.getHeaders().get("Content-Type"));
    String content = result.getBody();
    assertNotNull(content);
//    assertTrue(content.contains("\"message\""));
//    assertTrue(content.contains("\"hello world\""));
//    assertTrue(content.contains("\"location\""));
    // 🔥 JSON 파싱 후 검증
    UserTest userTest = objectMapper.readValue(content, UserTest.class);

    assertEquals("JeongIn", userTest.getId());
    assertEquals("Eddy", userTest.getName());
    assertEquals("nice to meet U.", userTest.getMessage());

  }
}
