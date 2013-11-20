package cs.ut.domain;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.AssertTrue;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import cs.ut.repository.PlantHireRequestRepository;

@RooIntegrationTest(entity = Invoice.class)
public class InvoiceIntegrationTest {
	
	@Autowired
	PlantHireRequestRepository repository;
	
	@Before
	public void setUp(){
		setRequiredTables("SiteInv", "SupplierInv", "FirstNameInv", "LastNameInv");
	}

	SiteEngineer sE;
	Site s;
	Supplier sup;
	
	private void setRequiredTables(String siteName, String supplierName,
			String engFirst, String engLast) {
		sE = new SiteEngineer();
		sE.setFirstName(engFirst);
		sE.setLastName(engLast);
		s = new Site();
		s.setName(siteName);
		sup = new Supplier();
		sup.setName(supplierName);
		sE.persist();
		sE.flush();
		s.persist();
		s.flush();
		sup.flush();
		sup.persist();
	}

	private long setPlantHireRequest(int plantId, int totalCost, ApprovalStatus status) {
		PlantHireRequest phr = new PlantHireRequest();
		phr.setEndDate(new Date());
		phr.setStartDate(new Date());
		phr.setPlantId(plantId);
		phr.setSite(s);
		phr.setSiteEngineer(sE);
		phr.setStatus(status);
		phr.setSupplier(sup);
		phr.setTotalCost(new BigDecimal(totalCost));
		phr.persist();
		phr.flush();
		return phr.getId();
	}
	
    @Test
    public void testPlantHireRequestFinder() {
    	long phrId = setPlantHireRequest(7, 77, ApprovalStatus.APPROVED);
		Invoice inv = new Invoice();
		inv.setIsPaid(false);
		inv.setPurchaseOrderHRef("asd");
		inv.setPurchaseOrderId(123);
		inv.setNeedsApproval(false);
		inv.persist();
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(phrId);
		phr.setInvoice(inv);
		phr.persist();
		
		PlantHireRequest phr2 = repository.findByPurchaseOrderId(123);
		assertTrue(phr.getId() == phr2.getId());
    }
}
