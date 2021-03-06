package cs.ut.repository;

import cs.ut.domain.SiteEngineer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

@RooJpaRepository(domainType = SiteEngineer.class)
public interface SiteEngineerRepository {
	
	@Query("SELECT eng FROM SiteEngineer AS eng WHERE eng.email = :email")
	
	@Transactional(readOnly = true)
	SiteEngineer findSiteEngineerByEmail(@Param("email") String email);
	
}
