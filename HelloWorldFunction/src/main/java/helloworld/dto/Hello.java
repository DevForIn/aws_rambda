package helloworld.dto;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Hello {

    private String id;
    private String name;
    private String message;
    private Timestamp now;

    // 기본 생성자
    public Hello() {}

    @Builder
    public Hello(String id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.now = Timestamp.valueOf(LocalDateTime.now());
    }
}
