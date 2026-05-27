package hospital.medflow.criteria;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataList<T> {
    private T data;
    private Long allElements;
    private Integer totalPages;

    public DataList(T data, Integer totalPages, Long allElements) {
        this.data = data;
        this.totalPages = totalPages;
        this.allElements = allElements;
    }
}
