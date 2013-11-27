package cs.ut.domain;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/invoices")
@Controller
@RooWebScaffold(path = "invoices", formBackingObject = Invoice.class)
public class InvoiceController {
}
