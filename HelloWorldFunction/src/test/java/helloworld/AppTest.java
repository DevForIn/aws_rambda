package helloworld;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

  private final App app = new App();

  @Test
  public void testHandleRequest() throws Exception {
    APIGatewayProxyResponseEvent result = app.handleRequest(null, null);

    // 응답 값 찍기
    System.out.println("handleRequest 응답: " + result.getBody());

    // 응답이 null이 아닌지 확인
    assertNotNull(result, "handleRequest 응답이 null입니다!");

    int statusCode = result.getStatusCode().intValue();
    // 상태 코드가 200인지 확인
    assertEquals(200, statusCode, "서버 오류 발생! 응답 본문 확인 필요");
    // Content-Type이 application/json인지 확인
    assertEquals("application/json", result.getHeaders().get("Content-Type"));

    // 응답 본문이 비어 있지 않은지 확인
    assertNotNull(result.getBody(), "응답 본문이 비어 있습니다!");
  }

  @Test
  public void testQnaRequest() throws Exception {
    APIGatewayProxyResponseEvent result = app.qnaRequest(null, null);

    // 응답 값 찍기
    System.out.println("handleRequest 응답: " + result.getBody());

    // 응답이 null이 아닌지 확인
    assertNotNull(result, "handleRequest 응답이 null입니다!");

    int statusCode = result.getStatusCode().intValue();
    // 상태 코드가 200인지 확인
    assertEquals(200, statusCode, "서버 오류 발생! 응답 본문 확인 필요");
    // Content-Type이 application/json인지 확인
    assertEquals("application/json", result.getHeaders().get("Content-Type"));

    // 응답 본문이 비어 있지 않은지 확인
    assertNotNull(result.getBody(), "응답 본문이 비어 있습니다!");
  }

  @Test
  public void testUserRequest() throws Exception {
    APIGatewayProxyResponseEvent resultUser = app.userRequest(null, null);

    // 응답 값 찍기
    System.out.println("userRequest 응답: " + resultUser.getBody());

    // 응답이 null이 아닌지 확인
    assertNotNull(resultUser, "userRequest 응답이 null입니다!");

    int userStatusCode = resultUser.getStatusCode().intValue();
    // 상태 코드가 200인지 확인
    assertEquals(200, userStatusCode, "서버 오류 발생! 응답 본문 확인 필요");
    // Content-Type이 application/json인지 확인
    assertEquals("application/json", resultUser.getHeaders().get("Content-Type"));

    // 응답 본문이 비어 있지 않은지 확인
    assertNotNull(resultUser.getBody(), "응답 본문이 비어 있습니다!");
  }
}
