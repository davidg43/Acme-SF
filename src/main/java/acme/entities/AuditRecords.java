
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class AuditRecords extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				periodInit;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				periodEnd;

	@NotNull
	private Mark				mark;

	@URL
	private String				link;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private CodeAudits			codeAudits;


	@Transient
	public Date period() {
		if (this.periodInit != null && this.periodEnd != null) {
			long diffInMillies = Math.abs(this.periodEnd.getTime() - this.periodInit.getTime());
			return new Date(diffInMillies);
		} else
			return null;
	}

}
