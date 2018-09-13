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
    <form:form id="firstCycle" role="form" modelAttribute="firstCycleDegree" method="GET" class="form-horizontal">
        <div class="form-group">
            <label for="selectFirstCycle" class="col-sm-1 control-label"><spring:message code="scientificCouncil.choose.firstCycle" /></label>
            <div class="col-sm-11">
                <form:select path="degreesFirstCycle" class="form-control">
                    <%--<form:option label="${i18n.message('label.all')}" value="null"/>--%>
                    <form:options items="${degreesFirstCycle}"/>
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-push-1 col-sm-11">
                <button type="submit" class="btn btn-default" id="search"><spring:message code="label.search" /></button>
            </div>
        </div>
    </form:form>
</section>

<%--<hr />
<div>
<table class="table results">
    <tbody>
        <tr>
            <td>
                <spring:message code="label.firstCycle" />
            </td>
            <td>
                <form:select path="degreesFirstCycle">
                    <form:options items="${degreesFirstCycle}" />
                </form:select>
            </td>
        </tr>
    </tbody>
</table>
</div>--%>