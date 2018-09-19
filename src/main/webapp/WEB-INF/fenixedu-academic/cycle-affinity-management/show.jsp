<%--

    Copyright © 2018 Instituto Superior Técnico

    This file is part of FenixEdu Academic.

    FenixEdu Academic is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu Academic is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.

--%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type='text/javascript'>

    // $.fn.dataTableExt.afnFiltering.push(
    // 	    function( oSettings, aData, iDataIndex ) {
    // 	    	return true;
    // 	    }
    // 	);

    $(document).ready(function() {
        $("form#search select").change(function() {
            $("form#search").submit();
        });

        $("button#search").click(function(el) {
            $("form#search").attr('action', "${searchUrl}");
        });
    });

</script>



<script type='text/javascript'>

    $(document).ready(function() {

        $("form#firstCycleDegree select").change(function() {
            $("form#firstCycleDegree").submit();
        });

        <%--$("button#search").click(function(el) {--%>
            <%--$("form#search").attr('action', "${searchUrl}");--%>
        <%--});--%>

    });

</script>

<div class="page-header">
<h1>
    <spring:message code="title.manage.degreeCurricularPlans.affinity"/>
    <small><spring:message code="label.listing" /></small>
</h1>
</div>
<div class="btn-group">
    <a class="btn btn-default" href="${logsUrl}"><spring:message code="label.show.logs"/></a>
</div>
<section>
    <form:form id="firstCycleDegree" role="form" modelAttribute="firstCycleDegree" method="GET" class="form-horizontal">
        <div class="form-group">
            <label for="selectFirstCycle" class="col-sm-1 control-label"><spring:message code="label.firstCycle" /></label>
            <div class="col-sm-11">
                <form:select path="degree" id="selectFirstCycle" items="${degreesFirstCycle}" class="form-control" itemLabel="presentationName" itemValue="externalId"/>
            </div>
        </div>
    </form:form>
</section>
<hr />
<section>
    <h4>
        <spring:message code="teacher.professorships.subtitle.departments.all" arguments="${firstCycleDegree.degree.presentationName}"/>
    </h4>

    <table class="table" id="affinities">
        <thead>
        <th><spring:message code="label.affinities"/></th>
        <th></th>
        </thead>
        <tbody>
        <c:forEach var="affinity" items="${affinities}">
            <tr class="authorization" id="affinity-${affinity.externalId}">
                <td><c:out value="${affinity.name} - ${affinity.parentDegreeCurricularPlan.presentationName}" /></td>
                <td><button class="btn btn-default"><spring:message code="label.delete"/></button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>