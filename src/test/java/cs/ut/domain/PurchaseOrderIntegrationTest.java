package cs.ut.domain;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = PurchaseOrder.class)
public class PurchaseOrderIntegrationTest {
	
    @Test
    public void testMarkerMethod() {
    }
}
