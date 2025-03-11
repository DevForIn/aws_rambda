package helloworld.dto;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class UserTest {

    private String id;
    private String name;
    private String message;
    private Timestamp now;

    // 기본 생성자
    public UserTest() {}

    @Builder
    public UserTest(String id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.now = Timestamp.valueOf(LocalDateTime.now());
    }
}
