/*
 * AuditorCodeAuditPublishService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeAudit.AuditRecord;
import acme.entities.codeAudit.CodeAudit;
import acme.entities.codeAudit.Mark;
import acme.entities.codeAudit.Type;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		CodeAudit codeAudit;
		Auditor auditor;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && codeAudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		Date currentMoment = MomentHelper.getCurrentMoment();
		Date creationMoment = new Date(currentMoment.getTime() - 6000);

		List<Mark> marks = this.repository.findManyMarksByCodeAuditId(object.getId());
		Mark modeMark = this.repository.getMode(marks);

		super.bind(object, "code", "execution", "type", "correctiveActions", "mark", "link");
		object.setProject(project);
		object.setExecution(creationMoment);
		object.setMark(modeMark);

	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findOneCodeAuditByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "auditor.code-audit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("mark")) {

			List<Mark> marks = this.repository.findManyMarksByCodeAuditId(object.getId());
			Mark modeMark = this.repository.getMode(marks);
			super.state(modeMark != Mark.F_MINUS && modeMark != Mark.F, "mark", "auditor.code-audit.form.error.not-enought-mark");
		}

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			Project project = object.getProject();
			super.state(!project.isDraft(), "project", "auditor.code-audit.form.error.invalid-project");
		}

		if (!super.getBuffer().getErrors().hasErrors("*")) {
			Collection<AuditRecord> auditRecords = this.repository.findManyAuditRecordsByCodeAuditId(object.getId());

			super.state(!auditRecords.isEmpty(), "*", "auditor.code-audit.form.error.empty-audit-record");
		}

		if (!super.getBuffer().getErrors().hasErrors("*")) {
			Collection<AuditRecord> auditRecords = this.repository.findManyAuditRecordsByCodeAuditId(object.getId());

			super.state(!auditRecords.stream().anyMatch(ar -> ar.getIsDraftMode()), "*", "auditor.code-audit.form.error.no-published-audit-record");
		}

	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;
		SelectChoices choicesType;
		SelectChoices choicesMark;

		projects = this.repository.findAllPublishedProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());
		choicesType = SelectChoices.from(Type.class, object.getType());
		choicesMark = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "mark", "link", "draftMode", "project");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("types", choicesType);
		dataset.put("marks", choicesMark);

		super.getResponse().addData(dataset);
	}

}
