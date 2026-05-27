package hospital.medflow.criteria;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseCriteria {
    private String search;
    private Integer page;
    private Integer size;
}
