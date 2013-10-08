package cs.ut.domain;
import java.math.BigDecimal;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
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
