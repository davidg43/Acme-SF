<%--
- banner.jsp
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

<div class="rounded" style="background: <acme:message code='master.banner.background'/>">
	<img src="images/banner.png" alt="<acme:message code='master.banner.alt'/>" class="img-fluid rounded"/>
<jstl:if test="${banner != null}">
    <div class="panel-body" style="margin: 1em 0em;">
        <a href="${banner.slogan}" target="_blank">
            <img src="${banner.picture}" alt="${banner.slogan}" class="img-fluid rounded" style="border-style: solid; width: 200px; height: 200px;"/>
        </a>
    </div>
</jstl:if>



</div>
