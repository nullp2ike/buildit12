// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.service;

import cs.ut.service.InvoiceResource;
import java.math.BigDecimal;

privileged aspect InvoiceResource_Roo_JavaBean {
    
    public String InvoiceResource.getPurchaseOrderHRef() {
        return this.purchaseOrderHRef;
    }
    
    public void InvoiceResource.setPurchaseOrderHRef(String purchaseOrderHRef) {
        this.purchaseOrderHRef = purchaseOrderHRef;
    }
    
    public BigDecimal InvoiceResource.getTotal() {
        return this.total;
    }
    
    public void InvoiceResource.setTotal(BigDecimal total) {
        this.total = total;
    }
    
}
