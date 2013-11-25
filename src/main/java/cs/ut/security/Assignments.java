package cs.ut.security;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Assignments {
	
	@ManyToOne
	@NotNull
	private Users userBuildit;
	@ManyToOne
	@NotNull
	private Authorities authority;
}
