
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "contract_id"), @Index(columnList = "recordId")
})
public class ProgressLog extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}")
	@Column(unique = true)
	private String				recordId;

	@NotNull
	@Positive
	@Range(min = 0, max = 100)
	private Double				completeness;

	@NotBlank
	@Length(max = 100)
	private String				comment;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	private Date				registrationMoment;

	@NotBlank
	@Length(max = 75)
	private String				reponsiblePerson;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Contract			contract;

	private boolean				isDraft;

}
