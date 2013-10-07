package cs.ut.domain;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/siteengineers")
@Controller
@RooWebScaffold(path = "siteengineers", formBackingObject = SiteEngineer.class)
public class SiteEngineerController {
}
