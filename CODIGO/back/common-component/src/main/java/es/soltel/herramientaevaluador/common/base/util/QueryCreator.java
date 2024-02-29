 package es.soltel.herramientaevaluador.common.base.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import es.soltel.herramientaevaluador.common.base.vo.FilterVO;
import es.soltel.herramientaevaluador.common.base.vo.RequestVO;

/**
 * Class that manages the creation of queries via the <code>createTypedQuery</code> method,
 * handling the filters and sorting provided by a {@link RequestVO} object.  
 * The queries are created with {@link CriteriaBuilder}.
 * <p>
 * Also performs subqueries in order to perform complex filters that reference
 * related entites. In these cases, a list of ids are returned instead of the
 * complete object to create an {@link In} clause for the main query. 
 */
@Component
public class QueryCreator {
	
	@Autowired
	private EntityManager entityManager;
	
	/**
	 * Creates and executes a Criteria Query with the given parameters,
	 * and returns the entities found.
	 * The <code>entity</code> parameter specifies the type that will be queried,
	 * as well as the return type.
	 * 
	 * @param  page    A {@link PageRequest} object that defines the pagination options.
	 * @param  request A {@link RequestVO} object containing the filters and sort info.
	 * @param  entity  A {@link Class} object from the class being queried.
	 * @return         A {@link PageImpl} object containing the page of found objects.
	 */
	public <T> PageImpl<T> createTypedQuery(PageRequest page, RequestVO request, Class<T> entity){		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(entity);
		Root<T> root = query.from(entity);
		List<Predicate> predicate = new ArrayList<>();
		for(FilterVO filter : request.getFilters()) {
			//if(filter.getMethod() == null) {
				try {					
					// Parses the field type and casts the value to the corresponding class before adding the predicate
			
					// - Numerals (long, int): Work
					// - Boolean             : Added
					switch(entity.getDeclaredField(filter.getField()).getType().getName()) {
						case "java.lang.Boolean":
						case "boolean":								
								predicate.add(cb.equal(root.get(filter.getField()), Boolean.parseBoolean(filter.getValue())));
							break;	
						case "long":
						case "java.lang.Long":
							predicate.add(cb.equal(root.get(filter.getField()), Long.parseLong(filter.getValue())));
							break;
						case "int":
						case "java.lang.Integer":
							predicate.add(cb.equal(root.get(filter.getField()), Integer.parseInt(filter.getValue())));
							break;
						default:
							predicate.add(cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%"));
							
					}
				} catch (NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
		/*	} else {
				switch(filter.getMethod()) {
					case "like":
						predicate.add(cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%"));
						break;
					case "equal":
						predicate.add(cb.equal(root.get(filter.getField()), filter.getValue()));
						break;					
				}
				
			}
			*/
		}
		if(request.getLogic()!=null && request.getLogic().equalsIgnoreCase("or")) {
			query.where(cb.or(predicate.toArray(new Predicate[]{})));
		} else {
			query.where(cb.and(predicate.toArray(new Predicate[]{})));
		}
		if(request.getOrder() != null) {
			query.orderBy(request.getOrder().getSort().equalsIgnoreCase("asc") ? cb.asc(root.get(request.getOrder().getField())) : cb.desc(root.get(request.getOrder().getField())));
		}
		TypedQuery<T> q = entityManager.createQuery(query);
		int total = q.getResultList().size();
		q.setFirstResult(page.getPageSize()*page.getPageNumber());
		q.setMaxResults(page.getPageSize());
		return new PageImpl<T>(q.getResultList(), page, (long) total);	
	}
	
}