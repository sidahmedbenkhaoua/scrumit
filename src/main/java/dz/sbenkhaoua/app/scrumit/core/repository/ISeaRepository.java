package dz.sbenkhaoua.app.scrumit.core.repository;



import dz.sbenkhaoua.app.scrumit.core.persistence.ScrumItEntity;

import java.util.List;
import java.util.Map;

public interface ISeaRepository<E extends ScrumItEntity> {

    public void save(E entity);

    public void saveAll(List<E> entities);

    public void update(E entity);

    public void delete(String id);

    public void delete(E entity);

    public E find(String id);

    public List<E> find();

    public List<E> findRange(int[] range);

    public List<E> findRangeByOrder(int[] range, String order, String column);

    public int count();

    public List<E> findByParams(Map<String, Object> params, Integer maxResults,
                                Integer firstResult, String orderBy, String order);

    public List<E> findByParams(Map<String, Object> params, String orderBy,
                                String order);

    public List<E> findLikRangeWithOR(Map<String, Object> params,
                                      Integer maxResults, Integer firstResult, String orderBy,
                                      String order);

    public List<E> findLikRange(Map<String, Object> params, Integer maxResults,
                                Integer firstResult, String orderBy, String order);

    public Long count(Map<String, Object> params);

    public Long countWithOR(Map<String, Object> params);

    public List<E> findLikRange(Map<String, Object> params);

    public int countByFilter(List<String> columnfilter, String word);

    public List<E> findRangeByOrderAndFilter(int[] range, String order,
                                             String column, List<String> columnfilter, String word);

}