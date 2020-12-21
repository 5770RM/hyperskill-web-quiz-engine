package engine.repository;

import engine.model.CompletionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionRepository extends PagingAndSortingRepository<CompletionEntity, Long> {
    Page<CompletionEntity> findAllByEmail(String email, Pageable pageable);
}
