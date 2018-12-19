<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="backUrl" value="/academic-administration/academic-administration/registrationProtocols/"/>

<spring:url var="actionUrl" value="/academic-administration/academic-administration/registrationProtocols/${empty registrationProtocol ? 'create' : registrationProtocol.externalId}"/>

<c:set var="yes"><spring:message code="label.yes"/></c:set>
<c:set var="no"><spring:message code="label.no"/></c:set>

<style>
    .form-control.bennu-localized-string-input {
        width: 300px;
    }
</style>

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
    <form:form role="form" method="POST" class="form-horizontal" action="${actionUrl}">
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
                <input id="description" name="description" type="text" class="bennu-localized-string-input col-md-5" value='<c:out value="${registrationProtocol.description.json()}"/>' bennu-localized-string required/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.enrolmentByStudent"/></label>
            <div class="col-sm-2">
                <input type="radio" name="enrolmentByStudent" value="true"> yes<br>
                <input type="radio" name="enrolmentByStudent" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.payGratuity"/></label>
            <div class="col-sm-2">
                <input type="radio" name="payGratuity" value="true"> yes<br>
                <input type="radio" name="payGratuity" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.allowsIDCard"/></label>
            <div class="col-sm-2">
                <input type="radio" name="allowsIDCard" value="true"> yes<br>
                <input type="radio" name="allowsIDCard" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.onlyAllowedDegreeEnrolment"/></label>
            <div class="col-sm-2">
                <input type="radio" name="onlyAllowedDegreeEnrolment" value="true"> yes<br>
                <input type="radio" name="onlyAllowedDegreeEnrolment" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.alien"/></label>
            <div class="col-sm-2">
                <input type="radio" name="alien" value="true"> yes<br>
                <input type="radio" name="alien" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.exempted"/></label>
            <div class="col-sm-2">
                <input type="radio" name="exempted" value="true"> yes<br>
                <input type="radio" name="exempted" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.mobility"/></label>
            <div class="col-sm-2">
                <input type="radio" name="mobility" value="true"> yes<br>
                <input type="radio" name="mobility" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.military"/></label>
            <div class="col-sm-2">
                <input type="radio" name="military" value="true"> yes<br>
                <input type="radio" name="military" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.allowDissertationCandidacyNoChecks"/></label>
            <div class="col-sm-2">
                <input type="radio" name="allowDissertationCandidacyWithoutChecks" value="true"> yes<br>
                <input type="radio" name="allowDissertationCandidacyWithoutChecks" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.forOfficialMobilityReporting"/></label>
            <div class="col-sm-2">
                <input type="radio" name="forOfficialMobilityReporting" value="true"> yes<br>
                <input type="radio" name="forOfficialMobilityReporting" value="false"> no<br>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><spring:message code="label.registrationProtocol.attemptAlmaMatterFromPrecedent"/></label>
            <div class="col-sm-2">
                <input type="radio" name="attemptAlmaMatterFromPrecedent" value="true"> yes<br>
                <input type="radio" name="attemptAlmaMatterFromPrecedent" value="false"> no<br>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-1">
                <a class="btn btn-default" href="${backUrl}"><spring:message code="label.cancel"/></a>
                <button type="submit" id="submitButton" class="btn btn-primary"><spring:message code="label.save"/></button>
            </div>
        </div>
    </form:form>
</section>