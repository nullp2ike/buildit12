package cs.ut.repository;

import java.util.List;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.InvoiceStatus;
import cs.ut.domain.PlantHireRequest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

@RooJpaRepository(domainType = PlantHireRequest.class)
public interface PlantHireRequestRepository {
	
	@Query("SELECT phr FROM PlantHireRequest AS phr WHERE phr.invoice = (SELECT inv FROM Invoice AS inv WHERE inv.purchaseOrderId = :purchaseOrderId)")
	
	@Transactional(readOnly = true)
	PlantHireRequest findByPurchaseOrderId(@Param("purchaseOrderId") long purchaseOrderId);
	
	@Query("SELECT phr FROM PlantHireRequest AS phr WHERE phr.invoice IN (SELECT inv.id FROM Invoice AS inv WHERE inv.status = :status)")
	
	@Transactional(readOnly = true)
	List<PlantHireRequest> findRequestsByInvoiceStatus(@Param("status") InvoiceStatus status);
	
	@Query("SELECT phr FROM PlantHireRequest AS phr WHERE phr.status = :status")
	
	@Transactional(readOnly = true)
	List<PlantHireRequest> findRequestsByApprovalStatus(@Param("status") ApprovalStatus status);
	
}
