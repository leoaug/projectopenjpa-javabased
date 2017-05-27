package projectopenjpa.repository;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import bossanovadata.model.persistence.dao.GenericDAOImpl;
import bossanovadata.model.persistence.dao.InterfaceRepository;
import bossanovadata.model.persistence.query.BOSSAQuery;
import bossanovadata.util.ObjectUtil;

@Transactional
@EnableTransactionManagement
@Configuration
public class YourJpaRepository  <T extends Serializable> extends GenericDAOImpl<T> implements InterfaceRepository<T> {

	@PersistenceContext(unitName = "bossanovadata")
	private EntityManager entityManager;
	
	@Autowired
	private BOSSAQuery<T> BOSSAQuery;
	
	@PostConstruct
	public void init(){
		super.setEntityManager(this.entityManager);
	}

	@Override
	public List<T> findByEntityList(T entity) throws Exception {
		return super.findByEntityList(entity, initialize());
	}

	@Override
	public T getEntity(Long id) throws Exception {
		return super.getEntity(id, Class.forName(initialize().getCanonicalName()));
	}

	
	@SuppressWarnings("unchecked")
	public BOSSAQuery<T> initialize() throws Exception {
		try {
			BOSSAQuery.setGenericDAOImpl((T) this);
			BOSSAQuery.addBaseEntity(ObjectUtil.getCanonicalNameForTypeGenerics(super.getClass()));
			return BOSSAQuery;
		} catch (Exception e) {
			throw e;
		}
	}

	
}
