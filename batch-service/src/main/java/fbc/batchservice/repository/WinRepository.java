package fbc.batchservice.repository;

import fbc.batchservice.entity.WinEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.batchservice.repository
 * @fileName : WinRepository
 * @date : 24. 12. 4.
 * @description : win값이 pageable 보다 크거나 같은 Entity 조회
 * ===========================================================
 */
public interface WinRepository extends JpaRepository<WinEntity, Long> {

    Page<WinEntity> findByWinGreaterThanEqual(Long win, Pageable pageable);
}
