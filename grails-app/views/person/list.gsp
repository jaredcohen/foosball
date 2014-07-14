
<%@ page import="org.foosball.Person" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-person" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-person" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="person.rank.label" default="Rank" /></th>

						<th><g:message code="person.name.label" default="Name" /></th>

						<th><g:message code="person.goalDiff.label" default="Avg Goal Diff" /></th>

						<th><g:message code="person.record.label" default="Record" /></th>

						<th><g:message code="person.winPct.label" default="Win %" /></th>
					
						<th><g:message code="person.record.label" default="Game Rec" /></th>

						<th><g:message code="person.winPct.label" default="Game %" /></th>
						
						<th><g:message code="person.rating.label" default="Rating" /></th>
						
					</tr>
				</thead>
				<tbody>
				<g:each in="${personInstanceList}" status="i" var="personInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${i + 1}</td>
						
						<td><g:link controller= "person" action="show" id="${personInstance.id}">${fieldValue(bean: personInstance, field: "name")}</g:link></td>

						<td>${personInstance.getAvgGoalDiff()}</td>

						<td>${personInstance.getWinCount()} - ${personInstance.getLossCount()}</td>
						
						<td>${personInstance.getWinPct()}</td>

						<td>${personInstance.getGamesWon()} - ${personInstance.getGamesLost()}</td>
						
						<td>${personInstance.getGamesWinPct()}</td>

						<td>${personInstance.getPlayerRating()}</td>
						
					</tr>
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
