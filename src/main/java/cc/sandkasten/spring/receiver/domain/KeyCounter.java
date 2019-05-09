package cc.sandkasten.spring.receiver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyCounter {
    private String key;
    private Integer count;
}
