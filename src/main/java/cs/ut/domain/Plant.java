package cs.ut.domain;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class Plant {

    /**
     */
    @NotNull
    private String name;

    /**
     */
    @NotNull
    private String description;

    /**
     */
    @NotNull
    private BigDecimal pricePerDay;
}
