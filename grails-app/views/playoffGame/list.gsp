
<%@ page import="org.foosball.PlayoffGame" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'playoffGame.label', default: 'PlayoffGame')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-playoffGame" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-playoffGame" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="winnerScore" title="${message(code: 'playoffGame.winnerScore.label', default: 'Winner Score')}" />
					
						<g:sortableColumn property="opponentScore" title="${message(code: 'playoffGame.opponentScore.label', default: 'Opponent Score')}" />
					
						<th><g:message code="playoffGame.winner.label" default="Winner" /></th>
					
						<th><g:message code="playoffGame.opponent.label" default="Opponent" /></th>
					
						<th><g:message code="playoffGame.result.label" default="Result" /></th>
					
						<g:sortableColumn property="sessionId" title="${message(code: 'playoffGame.sessionId.label', default: 'Session Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${playoffGameInstanceList}" status="i" var="playoffGameInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${playoffGameInstance.id}">${fieldValue(bean: playoffGameInstance, field: "winnerScore")}</g:link></td>
					
						<td>${fieldValue(bean: playoffGameInstance, field: "opponentScore")}</td>
					
						<td>${fieldValue(bean: playoffGameInstance, field: "winner")}</td>
					
						<td>${fieldValue(bean: playoffGameInstance, field: "opponent")}</td>
					
						<td>${fieldValue(bean: playoffGameInstance, field: "result")}</td>
					
						<td>${fieldValue(bean: playoffGameInstance, field: "sessionId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${playoffGameInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
