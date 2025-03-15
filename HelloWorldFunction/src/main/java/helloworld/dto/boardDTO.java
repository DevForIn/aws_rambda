package helloworld.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자 추가
public class boardDTO {
    private String title;
    private String content;

    @Builder
    public boardDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
