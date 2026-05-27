package hospital.medflow.service.base;

import hospital.medflow.criteria.DataList;

import java.util.List;

/**
 * @param <D>  Response dto
 * @param <UD> Update dto
 * @param <CD> Create dto
 * @param <C>  BaseCriteria
 * @param <K>  Serializable id
 */
public interface CRUDService<D, UD, CD, C, K> {

    DataList<List<D>> getAll(C criteria);

    D get(K id);

    D create(CD dto);

    D update(K id, UD dto);

    void delete(K id) ;

}
