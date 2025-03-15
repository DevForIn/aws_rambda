package helloworld.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class boardDTO {
    private String title;
    private String content;

    @Builder
    public boardDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
