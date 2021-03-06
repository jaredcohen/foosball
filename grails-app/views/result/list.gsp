
<%@ page import="org.foosball.Result" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'result.label', default: 'Result')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-result" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create" params="['sessionId': sessionId]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-result" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="id" title="${message(code: 'result.id.label', default: 'ID')}" />
					
						<th><g:message code="result.winner.label" default="Winner" /></th>
					
						<th><g:message code="result.opponent.label" default="Opponent" /></th>

						<th><g:message code="result.goalDiff.label" default="Goal Diff" /></th>

						<g:sortableColumn property="sessionId" title="${message(code: 'result.sessionId.label', default: 'Session Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${resultInstanceList}" status="i" var="resultInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${resultInstance.id}">${fieldValue(bean: resultInstance, field: "id")}</g:link></td>
					
						<td>${fieldValue(bean: resultInstance, field: "winner.name")}</td>
					
						<td>${fieldValue(bean: resultInstance, field: "opponent.name")}</td>

						<td>${resultInstance.getGoalDiff()}</td>
					
						<td>${fieldValue(bean: resultInstance, field: "sessionId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:each in="${sessionList}" status="j" var="sessionInstance">
					<g:link action="list" id="${sessionInstance?.id}">${fieldValue(bean: sessionInstance, field: "id")}</g:link>
				</g:each>
			</div>
		</div>
	</body>
</html>
