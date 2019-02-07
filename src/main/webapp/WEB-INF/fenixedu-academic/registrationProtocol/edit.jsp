<!DOCTYPE html>
${portal.toolkit()}
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="backUrl" value="/registrationProtocols/"/>

<spring:url var="actionUrl" value="/registrationProtocols/${empty registrationProtocol ? 'create' : registrationProtocol.externalId}"/>

<c:set var="yes"><spring:message code="label.yes"/></c:set>
<c:set var="no"><spring:message code="label.no"/></c:set>

<div class="page-header">
    <h2>
        <spring:message code="title.manage.registrationProtocols"/>
        <small>
            <c:if test="${not empty registrationProtocol}">
                <spring:message code="label.edit"/>
            </c:if>
            <c:if test="${empty registrationProtocol}">
                <spring:message code="label.create"/>
            </c:if>
        </small>
    </h2>
</div>

<section>
    <form:form role="form" modelAttribute="bean" method="POST" class="form-horizontal" action="${actionUrl}">
        ${csrf.field()}
        <div class="form-group">
            <label for="code" class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.code" /></label>
            <div>
                <input id="code" name="code" type="text" class="col-md-5" value='<c:out value="${registrationProtocol.code}"/>' required/>
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.description" /></label>
            <div>
                <input bennu-localized-string type="text" id="description" name="description" type="text" value='<c:out value="${registrationProtocol.description.json()}"/>' required/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.enrolmentByStudent"/></label>
            <input type="checkbox" name="enrolmentByStudentAllowed" value="true" ${registrationProtocol.enrolmentByStudentAllowed == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.payGratuity"/></label>
                <input type="checkbox" name="payGratuity" value="true" ${registrationProtocol.payGratuity == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.allowsIDCard"/></label>
                <input type="checkbox" name="allowsIDCard" value="true" ${registrationProtocol.allowsIDCard == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.onlyAllowedDegreeEnrolment"/></label>
                <input type="checkbox" name="onlyAllowedDegreeEnrolment" value="true" ${registrationProtocol.onlyAllowedDegreeEnrolment == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.alien"/></label>
                <input type="checkbox" name="alien" value="true" ${registrationProtocol.alien == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.exempted"/></label>
                <input type="checkbox" name="exempted" value="true" ${registrationProtocol.exempted == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.mobility"/></label>
                <input type="checkbox" name="mobility" value="true" ${registrationProtocol.mobility == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.military"/></label>
                <input type="checkbox" name="military" value="true" ${registrationProtocol.military == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.allowDissertationCandidacyNoChecks"/></label>
                <input type="checkbox" name="allowDissertationCandidacyWithoutChecks" value="true" ${registrationProtocol.allowDissertationCandidacyWithoutChecks == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.forOfficialMobilityReporting"/></label>
                <input type="checkbox" name="forOfficialMobilityReporting" value="true" ${registrationProtocol.forOfficialMobilityReporting == true ? 'checked':''}>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.attemptAlmaMatterFromPrecedent"/></label>
                <input type="checkbox" name="attemptAlmaMatterFromPrecedent" value="true" ${registrationProtocol.attemptAlmaMatterFromPrecedent == true ? 'checked':''}>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-1">
                <a class="btn btn-default" href="${backUrl}"><spring:message code="label.cancel"/></a>
                <button type="submit" id="submitButton" class="btn btn-primary"><spring:message code="label.save"/></button>
            </div>
        </div>
    </form:form>
</section>