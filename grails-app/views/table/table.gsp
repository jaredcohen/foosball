
<%@ page import="org.foosball.Team" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'team.label', default: 'Team')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-team" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="list" controller="team" action="list"><g:message code="default.team.label" default="All Teams" /></g:link></li>
				<li><g:link class="list" controller="person" action="list"><g:message code="default.person.label" default="All Players" /></g:link></li>
				<li><g:link class="list" controller="result" action="list"><g:message code="default.result.label" default="All Results" /></g:link></li>
			</ul>
		</div>
		<div id="list-team" class="content scaffold-list" role="main">
			<h1>Session ${sessionId} Table | <g:link controller= "playoff" action="list" id="${sessionId}">Playoffs</g:link></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:if test="${finalMatch}">
				<table id="finals">
				<thead>
					<tr>
					
						<th><g:message code="result.first.label" default="Champion" /></th>
						
						<th><g:message code="result.second.label" default="Runner-up" /></th>

						<th><g:message code="result.third.label" default="3rd Place" /></th>

					</tr>
				</thead>
				<tbody>
					<tr>
					
						<td><g:link controller= "team" action="show" id="${finalMatch?.winner?.id}">${fieldValue(bean: finalMatch, field: "winner.name")}</g:link></td>

						<td><g:link controller= "team" action="show" id="${finalMatch?.opponent?.id}">${fieldValue(bean: finalMatch, field: "opponent.name")}</g:link></td>

						<g:if test="${consolationMatch}">
							<td><g:link controller= "team" action="show" id="${consolationMatch?.winner?.id}">${fieldValue(bean: consolationMatch, field: "winner.name")}</g:link></td>
						</g:if>
						<g:else>
							<td><g:link controller= "team" action="show" id="${teamInstanceList[2].id}">${teamInstanceList[2].name}</g:link></td>
						</g:else>
					
					</tr>
				</tbody>
			</table>
			</g:if>
			<g:link controller= "table" action="table" id="${sessionInstance?.id}">${fieldValue(bean: sessionInstance, field: "id")}</g:link>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="result.rank.label" default="Rank" /></th>
						
						<th><g:message code="result.name.label" default="Name" /></th>

						<th><g:message code="result.record.label" default="Record" /></th>

						<th><g:message code="result.winPct.label" default="Winning %" /></th>
						
						<th><g:message code="result.gamesWon.label" default="Games Won" /></th>

						<th><g:message code="result.goalDiff.label" default="Goal Diff" /></th>

					</tr>
				</thead>
				<tbody>
				<g:each in="${teamInstanceList}" status="i" var="teamInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${i + 1}</td>
						
						<td><g:link controller= "team" action="show" id="${teamInstance.id}">${fieldValue(bean: teamInstance, field: "name")}</g:link></td>
					
						<td>${teamInstance.getWinCount()} - ${teamInstance.getLossCount()}</td>
						
						<td>${teamInstance.getWinPct()}</td>

						<td>${teamInstance.getGamesWon()}</td>
						
						<td>${teamInstance.getGoalDiff()}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:each in="${sessionList}" status="j" var="sessionInstance">
					<g:link controller= "table" action="table" id="${sessionInstance?.id}">${fieldValue(bean: sessionInstance, field: "id")}</g:link>
				</g:each>
			</div>
		</div>
	</body>
</html>
