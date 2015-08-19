package dz.sbenkhaoua.app.scrumit.core.repository;

import dz.sbenkhaoua.app.scrumit.core.persistence.ScrumItEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;



public abstract class ScrumItRepository<E extends ScrumItEntity> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private Class<E> eClazz;

	public static String DESC = "DESC";
	public static String ASC = "ASC";

	public ScrumItRepository(Class<E> eClazz) {
		this.eClazz = eClazz;
	}

	public abstract EntityManager getEntityManager();

	public String save(E entity) {
		getEntityManager().persist(entity);
		String id = entity.getId();
		getEntityManager().flush();
		return id;
	}

	public List<String> saveAll(List<E> entities) {
		List<String> ids = new ArrayList<String>();
		for (E entity : entities) {
			ids.add(save(entity));
		}
		getEntityManager().flush();
		return ids;
	}

	public String update(E entity) {
		E e = getEntityManager().merge(entity);
		getEntityManager().flush();
		return e.getId();
	}

	public boolean delete(String id) {
		boolean result = true;
		E entity = find(id);
		try {
			getEntityManager().remove(getEntityManager().merge(entity));
			getEntityManager().flush();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public boolean delete(E entity) {
		return delete(entity.getId());
	}

	public E find(String id) {
		return getEntityManager().find(eClazz, id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<E> find() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		cq.select(cq.from(eClazz));
		return getEntityManager().createQuery(cq).getResultList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int count() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		Root<E> rt = cq.from(eClazz);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<E> findRange(int[] range) {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		cq.select(cq.from(eClazz));
		Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<E> findRangeByOrder(int[] range, String order, String column) {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		cq.select(cq.from(eClazz));
		if (order.toUpperCase().equals(ScrumItRepository.DESC)) {
			cq.orderBy(getEntityManager().getCriteriaBuilder().desc(
					cq.from(eClazz).get(column)));
		}
		if (order.toUpperCase().equals(ScrumItRepository.ASC)) {
			cq.orderBy(getEntityManager().getCriteriaBuilder().asc(
					cq.from(eClazz).get(column)));
		}
		Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<E> findByParams(Map<String, Object> params, Integer maxResults,
								Integer firstResult, String orderBy, String order) {
		getEntityManager().flush();
		String query = "select DISTINCT e from " + eClazz.getName() + " e";
		if (params != null && params.size() > 0) {
			int i = 0;
			for (String string : params.keySet()) {
				if (i == 0) {
					query += " where ";
				}
				if (i != 0 && i != params.keySet().size()) {
					query += " and ";
				}
				String[]  valObj=string.split("\\.");

				query += "e." + string + " = :" + valObj[0] + " ";
				i++;
			}
		}
		if (orderBy != null) {
			if (order != null && order.toUpperCase().equals(ScrumItRepository.DESC)) {
				query += " order by e." + orderBy + " DESC";
			} else {
				query += " order by e." + orderBy + " ASC";
			}
		}

		Query q = getEntityManager().createQuery(query);
		if (params != null && params.size() > 0) {
			for (String string : params.keySet()) {
				String[]  valObj=string.split("\\.");
				q = q.setParameter(valObj[0], params.get(string));

			}
		}
		if (maxResults != null) {
			q.setMaxResults(maxResults);
		}
		if (firstResult != null) {
			q.setFirstResult(firstResult);
		}
		List<E> results = q.getResultList();

		if (results.isEmpty()) {
			return new ArrayList<>();
		} else {
			return results;
		}
	}

	public List<E> findByParams(Map<String, Object> params, String orderBy,
			String order) {
		return findByParams(params, null, null, orderBy, order);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<E> findLikeRangeWithOr(Map<String, Object> params,
			Integer maxResults, Integer firstResult, String orderBy,
			String order) {

		getEntityManager().flush();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(eClazz);
		Root<E> root = cq.from(eClazz);
		cq = cq.select(root);
		List<Predicate> ps = new ArrayList<Predicate>();

		for (String c : params.keySet()) {
			if (!c.contains(".")) {
				if (params.get(c) instanceof String) {
					Predicate a = cb.like(
							cb.upper((Expression<String>) root.get(c).as(
									String.class)), params.get(c).toString()
									.toUpperCase()
									+ "%");
					ps.add(a);
				} else {
					Predicate a = cb.equal((Expression) root.get(c),
							params.get(c));
					ps.add(a);
				}
			}
			if (c.contains(".")) {
				String[] filter = c.split("\\.");
				Path<Object> path = root.join(filter[0]).get(filter[1]);
				Predicate a = cb.like(cb.upper((Expression) path), params
						.get(c).toString().toUpperCase()
						+ "%");
				ps.add(a);
			}
			if (ps.size() > 0) {
				Predicate[] psArray = new Predicate[ps.size()];
				cq = cq.where(cb.or(ps.toArray(psArray)));
			}

		}
		if (orderBy != null) {
			String[] ords = orderBy.split(",");
			for (String ord : ords) {
				if (order != null
						&& order.toUpperCase().equals(ScrumItRepository.DESC)) {
					cq.orderBy(cb.desc(root.get(ord)));
				} else {
					cq.orderBy(cb.asc(root.get(ord)));
				}
			}

		} else {
			cq.orderBy(cb.asc(root.get("id")));
		}
		Query query = getEntityManager().createQuery(cq);
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		return query.getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<E> findLikeRange(Map<String, Object> params,
			Integer maxResults, Integer firstResult, String orderBy,
			String order) {

		getEntityManager().flush();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(eClazz);
		Root<E> root = cq.from(eClazz);
		cq = cq.select(root);
		List<Predicate> ps = new ArrayList<Predicate>();
		for (String c : params.keySet()) {
			if (!c.contains(".")) {
				if (params.get(c) instanceof String) {
					Predicate a = cb
							.like(cb.upper((Expression) root.get(c).as(
									String.class)), params.get(c).toString()
									.toUpperCase()
									+ "%");
					ps.add(a);
				} else {
					Predicate a = cb.equal((Expression) root.get(c),
							params.get(c));
					ps.add(a);
				}
			}
			if (c.contains(".")) {
				String[] filter = c.split("\\.");
				if (filter.length == 2) {
					Path<Object> path = root.join(filter[0]).get(filter[1]);
					if (params.get(c) instanceof String) {
						Predicate a = cb.like(cb.upper((Expression) path),
								params.get(c).toString().toUpperCase() + "%");
						ps.add(a);
					} else {
						Predicate a = cb.equal((Expression) root.get(c),
								params.get(c));
						ps.add(a);
					}
				}
				if (filter.length == 3) {
					Path<Object> path = root.join(filter[0]).join(filter[1])
							.get(filter[2]);
					if (params.get(c) instanceof String) {
						Predicate a = cb.like(cb.upper((Expression) path),
								params.get(c).toString().toUpperCase() + "%");
						ps.add(a);
					} else {
						Predicate a = cb.equal((Expression) root.get(c),
								params.get(c));
						ps.add(a);
					}
				}
				if (filter.length == 4) {
					Path<Object> path = root.join(filter[0]).join(filter[1])
							.join(filter[2]).get(filter[3]);
					if (params.get(c) instanceof String) {
						Predicate a = cb.like(cb.upper((Expression) path),
								params.get(c).toString().toUpperCase() + "%");
						ps.add(a);
					} else {
						Predicate a = cb.equal((Expression) root.get(c),
								params.get(c));
						ps.add(a);
					}
				}
			}
			if (ps.size() > 0) {
				Predicate[] psArray = new Predicate[ps.size()];
				cq = cq.where(cb.and(ps.toArray(psArray)));
			}

		}
		if (orderBy != null) {
			String[] ords = orderBy.split(",");
			for (String ord : ords) {
				if (order != null && order.equals(ScrumItRepository.DESC)) {
					cq.orderBy(cb.desc(root.get(ord)));
				} else {
					cq.orderBy(cb.asc(root.get(ord)));
				}
			}

		} else {
			cq.orderBy(cb.asc(root.get("id")));
		}
		Query query = getEntityManager().createQuery(cq);
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long count(Map<String, Object> params) {

		getEntityManager().flush();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<E> root = cq.from(eClazz);

		cq.select(cb.countDistinct(root));

		List<Predicate> ps = new ArrayList<Predicate>();
		for (String c : params.keySet()) {
			if (!c.contains(".")) {
				if (params.get(c) instanceof String) {
					Predicate a = cb
							.like(cb.upper((Expression) root.get(c).as(
									String.class)), params.get(c).toString()
									.toUpperCase()
									+ "%");
					ps.add(a);
				} else {
					Predicate a = cb.equal((Expression) root.get(c),
							params.get(c));
					ps.add(a);
				}
			}
			if (c.contains(".")) {
				String[] filter = c.split("\\.");
				if (filter.length == 2) {
					Path<Object> path = root.join(filter[0]).get(filter[1]);
					if (params.get(c) instanceof String) {
						Predicate a = cb.like(cb.upper((Expression) path),
								params.get(c).toString().toUpperCase() + "%");
						ps.add(a);
					} else {
						Predicate a = cb.equal((Expression) root.get(c),
								params.get(c));
						ps.add(a);
					}
				}
				if (filter.length == 3) {
					Path<Object> path = root.join(filter[0]).join(filter[1])
							.get(filter[2]);
					if (params.get(c) instanceof String) {
						Predicate a = cb.like(cb.upper((Expression) path),
								params.get(c).toString().toUpperCase() + "%");
						ps.add(a);
					} else {
						Predicate a = cb.equal((Expression) root.get(c),
								params.get(c));
						ps.add(a);
					}
				}
				if (filter.length == 4) {
					Path<Object> path = root.join(filter[0]).join(filter[1])
							.join(filter[2]).get(filter[3]);
					if (params.get(c) instanceof String) {
						Predicate a = cb.like(cb.upper((Expression) path),
								params.get(c).toString().toUpperCase() + "%");
						ps.add(a);
					} else {
						Predicate a = cb.equal((Expression) root.get(c),
								params.get(c));
						ps.add(a);
					}
				}
			}
			if (ps.size() > 0) {
				Predicate[] psArray = new Predicate[ps.size()];
				cq = cq.where(cb.and(ps.toArray(psArray)));
			}

		}
		try {
			long count = getEntityManager().createQuery(cq).getSingleResult()
					.intValue();
			return count;
		} catch (Exception e) {
			return 0L;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long countWithOr(Map<String, Object> params) {
		getEntityManager().flush();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<E> root = cq.from(eClazz);

		cq.select(cb.countDistinct(root));

		List<Predicate> ps = new ArrayList<Predicate>();
		for (String c : params.keySet()) {
			if (!c.contains(".")) {
				if (params.get(c) instanceof String) {
					Predicate a = cb
							.like(cb.upper((Expression) root.get(c).as(
									String.class)), params.get(c).toString()
									.toUpperCase()
									+ "%");
					ps.add(a);
				} else {
					Predicate a = cb.equal((Expression) root.get(c),
							params.get(c));
					ps.add(a);
				}
			}
			if (c.contains(".")) {
				String[] filter = c.split("\\.");
				Path<Object> path = root.join(filter[0]).get(filter[1]);
				Predicate a = cb.like(cb.upper((Expression) path), params
						.get(c).toString().toUpperCase()
						+ "%");
				ps.add(a);
			}
			if (ps.size() > 0) {
				Predicate[] psArray = new Predicate[ps.size()];
				cq = cq.where(cb.or(ps.toArray(psArray)));
			}

		}

		long count = getEntityManager().createQuery(cq).getSingleResult()
				.intValue();
		return count;
	}

	public List<E> findLikeRange(Map<String, Object> params) {
		getEntityManager().flush();
		return findLikeRange(params, null, null, null, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int countByFilter(List<String> columnfilter, String word) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root e = cq.from(eClazz);
		cq.select(cb.count(e));
		List criteriaList = new ArrayList();

		for (String filter : columnfilter) {
			criteriaList.add(cb.like(cb.upper(e.get(filter)),
					"%" + word.toUpperCase() + "%"));
		}
		cq.where(cb.or((Predicate[]) criteriaList.toArray(new Predicate[0])));

		Query q = getEntityManager().createQuery(cq);

		return ((Long) q.getSingleResult()).intValue();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<E> findRangeByOrderAndFilter(int[] range, String order,
			String column, List<String> columnFilter, String word) {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root e = cq.from(eClazz);
		cq.select(e);
		List criteriaList = new ArrayList();

		for (String filter : columnFilter) {
			criteriaList.add(cb.like(cb.upper(e.get(filter)),
					"%" + word.toUpperCase() + "%"));
		}
		cq.where(cb.or((Predicate[]) criteriaList.toArray(new Predicate[0])));

		if (order != null) {
			if (order.toUpperCase().equals(ScrumItRepository.DESC)) {
				cq.orderBy(cb.desc(e.get(column)));
			}
			if (order.toUpperCase().equals(ScrumItRepository.ASC)) {
				cq.orderBy(cb.asc(e.get(column)));
			}
		}

		Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public List<E> getLignes(String entity, String property,
			String subProperty, String value) {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("select e from " + entity + " e where e."
				+ property + "." + subProperty + " ='" + value + "'");

		Query query = getEntityManager().createQuery(stringBuffer.toString());
		List<E> l = query.getResultList();
		if (l == null) {
			return new ArrayList<E>();
		}
		return l;
	}

	@SuppressWarnings({ "unchecked" })
	public List<E> getLignes(String entity, String property, String value) {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("select e from " + entity + " e where e."
				+ property + "='" + value + "'");
		Query query = getEntityManager().createQuery(stringBuffer.toString());
		List<E> l = query.getResultList();

		if (l == null) {
			return new ArrayList<E>();
		}
		return l;
	}


}
