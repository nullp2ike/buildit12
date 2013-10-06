package cs.ut.domain;
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
    private String name;

    /**
     */
    private String description;

    /**
     */
    @NotNull
    private int identifier;

    /**
     */
    private float pricePerDay;
}
