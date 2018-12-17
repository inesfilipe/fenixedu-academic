<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="createUrl" value="/registrationProtocols/create"/>
<spring:url var="baseUrl" value="/registrationProtocols/"/>
<spring:url var="logsUrl" value="/registrationProtocols/logs"/>

<c:set var="yes"><spring:message code="label.yes"/></c:set>
<c:set var="no"><spring:message code="label.no"/></c:set>

<div class="page-header">
    <h2>
        <spring:message code="title.manage.registrationProtocols"/>
    </h2>
</div>

<div class="btn-group">
    <a class="btn btn-default" href="${createUrl}"><spring:message code="label.create"/></a>
    <a class="btn btn-default" href="${logsUrl}"><spring:message code="label.show.logs"/></a>
</div>

<table class="table results">
    <thead>
    <th><spring:message code="label.registrationProtocol.code" /></th>
    <th><spring:message code="label.registrationProtocol.description" /></th>
    <th></th>
    </thead>
    <tbody>
    <c:forEach var="registrationProtocol" items="${registrationProtocols}">
        <tr>
            <td>
                <c:out value="${registrationProtocol.code}"/>
            </td>
            <td>
                <c:out value="${registrationProtocol.description}"/>
            </td>
            <td>
                <%--<a href="${baseUrl}${interestRate.externalId}" class="btn btn-default"><spring:message code="label.edit"/></a>--%>
                <%-- TO DO: add dynamic hide and show of details, change the width somehow, change labels--%>
            </td>
        </tr>
        <tr>
            <td></td>
            <td colspan="1">
            <table class="table" colspan="2">
                <tr>
                <th><spring:message code="label.registrationProtocol.enrolmentByStudent"/></th>
                    <td><c:out value="${registrationProtocol.enrolmentByStudentAllowed ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.payGratuity"/></th>
                    <td><c:out value="${registrationProtocol.payGratuity ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.allowsIDCard"/></th>
                    <td><c:out value="${registrationProtocol.allowsIDCard ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.onlyAllowedDegreeEnrolment"/></th>
                    <td><c:out value="${registrationProtocol.onlyAllowedDegreeEnrolment ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.alien"/></th>
                    <td><c:out value="${registrationProtocol.alien ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.exempted"/></th>
                    <td><c:out value="${registrationProtocol.exempted ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.mobility"/></th>
                    <td><c:out value="${registrationProtocol.mobility ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.military"/></th>
                    <td><c:out value="${registrationProtocol.military ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.allowDissertationCandidacyNoChecks"/></th>
                    <td><c:out value="${registrationProtocol.allowDissertationCandidacyWithoutChecks ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.forOfficialMobilityReporting"/></th>
                    <td><c:out value="${registrationProtocol.forOfficialMobilityReporting ? yes : no}" /></td>
                </tr>
                <tr>
                    <th><spring:message code="label.registrationProtocol.attemptAlmaMatterFromPrecedent"/></th>
                    <td><c:out value="${registrationProtocol.attemptAlmaMatterFromPrecedent ? yes : no}" /></td>
                </tr>
            </table>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>