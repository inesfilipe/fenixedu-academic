<%--

    Copyright © 2017 Instituto Superior Técnico

    This file is part of FenixEdu Spaces.

    FenixEdu Spaces is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu Spaces is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu Spaces.  If not, see <http://www.gnu.org/licenses/>.

--%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="createUrl" value=""/>
<spring:url var="baseUrl" value="/degree-affinity-manager/"/>
<spring:url var="logsUrl" value="/degree-affinity-manager/logs"/>

<div class="page-header">
    <h2>
        <spring:message code="title.manage.degreeCurricularPlans.affinity"/>
    </h2>
</div>

<div class="btn-group">
    <a class="btn btn-default" href="${logsUrl}"><spring:message code="label.show.logs"/></a>
</div>

<table class="table results">
    <thead>
    <th><spring:message code="label.interestRate.start" /></th>
    <th><spring:message code="label.interestRate.end" /></th>
    <th><spring:message code="label.interestRate.value"/></th>
    </thead>
    <tbody>
        <tr>
            <td>
                <spring:message code="label.firstCycle" />
            </td>
            <td>
                <form:select path="firstCycleDegree">
                    <form:option value="NONE" label="label.affinityCycles.chooseFirstCycle"/>
                    <form:options items="${degreesFirstCycle}" />
                </form:select>
            </td>
            <td>
                <c:out value="${interestRate.value} %"/>
            </td>
            <td>
                <a href="${baseUrl}${interestRate.externalId}" class="btn btn-default"><spring:message code="label.edit"/></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>