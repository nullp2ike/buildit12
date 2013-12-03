package cs.ut.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;


@RooDataOnDemand(entity = Invoice.class)
public class InvoiceDataOnDemand {
	
//	@Autowired
//	private ApplicationContext applicationContext;
//	
//	@Before
//	public void setUp(){
//		setRequiredTables("SiteInv", "SupplierInv", "FirstNameInv", "LastNameInv");
//	}
//
//	SiteEngineer sE;
//	Site s;
//	Supplier sup;
//	
//	private void setRequiredTables(String siteName, String supplierName,
//			String engFirst, String engLast) {
//		sE = new SiteEngineer();
//		sE.setFirstName(engFirst);
//		sE.setLastName(engLast);
//		s = new Site();
//		s.setName(siteName);
//		sup = new Supplier();
//		sup.setName(supplierName);
//		sE.persist();
//		sE.flush();
//		s.persist();
//		s.flush();
//		sup.flush();
//		sup.persist();
//	}
//
//	private long setPlantHireRequest(int plantId, int totalCost, ApprovalStatus status, long poId, InvoiceStatus invStatus) {
//		PlantHireRequest phr = new PlantHireRequest();
//		phr.setEndDate(new Date());
//		phr.setStartDate(new Date());
//		phr.setPlantId(plantId);
//		phr.setSite(s);
//		phr.setSiteEngineer(sE);
//		phr.setStatus(status);
//		phr.setSupplier(sup);
//		phr.setTotalCost(new BigDecimal(totalCost));
//		Invoice inv = new Invoice();
//		inv.setIsPaid(false);
//		inv.setStatus(invStatus);
//		inv.setPurchaseOrderHRef("asd");
//		inv.setPurchaseOrderId(poId);
//		inv.persist();
//		phr.setInvoice(inv);
//		phr.persist();
//		phr.flush();
//		return phr.getId();
//	}
//	
//	@Test
//	public void test(){
//		setPlantHireRequest(1, 201, ApprovalStatus.APPROVED, 17, InvoiceStatus.NEEDS_APPROVAL);
//		setPlantHireRequest(1, 203, ApprovalStatus.APPROVED, 15, InvoiceStatus.NEEDS_APPROVAL);
//		setPlantHireRequest(1, 201, ApprovalStatus.APPROVED, 16, InvoiceStatus.APPROVED);
//	}
}
