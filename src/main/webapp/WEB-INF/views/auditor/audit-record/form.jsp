<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
<acme:input-textbox code="auditor.audit-record.form.label.code" path="code" readonly="true" />
	<acme:input-textbox code="auditor.audit-record.form.label.code-audit" path="codeAudit.correctiveActions" readonly="true" />
	<acme:input-moment code="auditor.audit-record.form.label.period-init" path="periodInit" readonly="true" />
	<acme:input-moment code="auditor.audit-record.form.label.period-end" path="periodEnd" readonly="true" />
	<acme:input-textbox code="auditor.audit-record.form.label.mark" path="mark" readonly="true" />
	<acme:input-url code="auditor.audit-record.form.label.link" path="link" readonly="true" />
</acme:form>

<acme:button code="auditor.audit-record.button.form.update" action="/auditor/audit-record/update"/>
<acme:button code="auditor.audit-record.button.form.delete" action="/auditor/audit-record/delete"/>