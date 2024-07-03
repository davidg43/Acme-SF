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
	<acme:input-textbox code="any.claim.form.label.code" path="code" />
	<acme:input-moment code="any.claim.form.label.instantiationMoment"
		path="instantiationMoment" readonly="true" />
	<acme:input-textbox code="any.claim.form.label.heading" path="heading" />
	<acme:input-textarea code="any.claim.form.label.description"
		path="description" />
	<acme:input-textbox code="any.claim.form.label.department"
		path="department" />
	<acme:input-email code="any.claim.form.label.emailAddress"
		path="emailAddress" />
	<acme:input-url code="any.claim.form.label.link" path="link" />
	<jstl:if test="${_command == 'create'}">
		<acme:input-checkbox code="any.claim.form.label.publish"
			path="publish" />
	</jstl:if>




	<jstl:if test="${_command == 'create'}">
		<acme:submit code="any.claim.form.button.create"
			action="/any/claim/create" />
	</jstl:if>

</acme:form>