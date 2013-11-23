package cs.ut.domain;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WorksEngineer {
	
    /**
     */
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;
    
}
