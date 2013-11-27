package cs.ut.domain;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class SiteEngineer {

    /**
     */
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;

    @NotNull
    @Column(unique=true)
    private String email;
}
