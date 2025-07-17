package ram0973.blog.common.mappers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import ram0973.blog.common.entity.BaseEntity;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING
)
@RequiredArgsConstructor
public abstract class ReferenceMapper {
    @PersistenceContext
    private EntityManager entityManager;

    public ReferenceMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T extends BaseEntity> T toEntity(Long id, @TargetType Class<T> entityClass) {
        return id != null ? entityManager.find(entityClass, id) : null;
    }
}
