package helloworld;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import helloworld.dto.UserTest;
import helloworld.dto.UsersIdAndUsername;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AppTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void successfulResponse() throws Exception {

    App app = new App();

    // 🔥 예외 발생 가능성이 있는 코드 실행
    APIGatewayProxyResponseEvent result = app.handleRequest(null, null);

    // ✅ 응답이 null이 아닌지 확인
    assertNotNull("응답이 null입니다!", result);

    // ✅ 상태 코드 출력 후 확인
    int statusCode = result.getStatusCode().intValue();
    System.out.println("응답 상태 코드: " + statusCode);
    System.out.println("응답 본문: " + result.getBody());

    // 🔥 상태 코드가 200이 아닐 경우 실패 메시지 출력
    assertEquals("서버 오류 발생! 응답 본문 확인 필요", 200, statusCode);

    // ✅ Content-Type 확인
    assertEquals("application/json", result.getHeaders().get("Content-Type"));

    // ✅ 응답 본문이 null이 아닌지 확인
    String content = result.getBody();
    assertNotNull("응답 본문이 비어 있습니다!", content);

    // ✅ JSON을 리스트(List<UserTest>)로 변환
//    List<UsersIdAndUsername> usersIdAndUsernames = objectMapper.readValue(content, new TypeReference<List<UsersIdAndUsername>>() {});

    // ✅ 데이터 검증
//    assertEquals(2, usersIdAndUsernames.size());
//    assertEquals("jeongin", usersIdAndUsernames.get(0).getId());
//    assertEquals("정인", usersIdAndUsernames.get(0).getUsername());
//    assertEquals("seohee", usersIdAndUsernames.get(1).getId());
//    assertEquals("서희", usersIdAndUsernames.get(1).getUsername());

//    assertNotNull(content);
//    assertTrue(content.contains("\"message\""));
//    assertTrue(content.contains("\"hello world\""));
//    assertTrue(content.contains("\"location\""));
//     🔥 JSON 파싱 후 검증
//    UserTest userTest = objectMapper.readValue(content, UserTest.class);
//
//    assertEquals("JeongIn", userTest.getId());
//    assertEquals("Eddy", userTest.getName());
//    assertEquals("nice to meet U.", userTest.getMessage());

  }
}
