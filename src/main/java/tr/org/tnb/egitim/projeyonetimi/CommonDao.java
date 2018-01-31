package tr.org.tnb.egitim.projeyonetimi;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface CommonDao {
	
	public void save(BaseEntity entity);
	public void update(BaseEntity entity);
	public void delete(BaseEntity entity);
	public BaseEntity getById(Long id, Class clazz);
	public List<BaseEntity> getAll(Class clazz);
}	
