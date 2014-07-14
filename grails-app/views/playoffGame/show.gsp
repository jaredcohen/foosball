
<%@ page import="org.foosball.PlayoffGame" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'playoffGame.label', default: 'PlayoffGame')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-playoffGame" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" controller="playoff" action="show" id="${playoffGameInstance?.result?.id}">Back to result</g:link></li>
			</ul>
		</div>
		<div id="show-playoffGame" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list playoffGame">
			
				<g:if test="${playoffGameInstance?.result}">
				<li class="fieldcontain">
					<span id="result-label" class="property-label"><g:message code="playoffGame.result.label" default="Result" /></span>
					
						<span class="property-value" aria-labelledby="result-label"><g:link controller="playoff" action="show" id="${playoffGameInstance?.result?.id}">${playoffGameInstance?.result?.id}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffGameInstance?.sessionId}">
				<li class="fieldcontain">
					<span id="sessionId-label" class="property-label"><g:message code="playoffGame.sessionId.label" default="Session Id" /></span>
					
						<span class="property-value" aria-labelledby="sessionId-label"><g:fieldValue bean="${playoffGameInstance}" field="sessionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffGameInstance?.winner}">
				<li class="fieldcontain">
					<span id="winner-label" class="property-label"><g:message code="playoffGame.winner.label" default="Winner" /></span>
					
						<span class="property-value" aria-labelledby="winner-label"><g:link controller="team" action="show" id="${playoffGameInstance?.winner?.id}">${playoffGameInstance?.winner?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffGameInstance?.opponent}">
				<li class="fieldcontain">
					<span id="opponent-label" class="property-label"><g:message code="playoffGame.opponent.label" default="Opponent" /></span>
					
						<span class="property-value" aria-labelledby="opponent-label"><g:link controller="team" action="show" id="${playoffGameInstance?.opponent?.id}">${playoffGameInstance?.opponent?.name}</g:link></span>
					
				</li>
				</g:if>
				
				<g:if test="${playoffGameInstance?.winnerScore}">
				<li class="fieldcontain">
					<span id="winnerScore-label" class="property-label"><g:message code="playoffGame.winnerScore.label" default="Winner Score" /></span>
					
						<span class="property-value" aria-labelledby="winnerScore-label"><g:fieldValue bean="${playoffGameInstance}" field="winnerScore"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffGameInstance?.opponentScore}">
				<li class="fieldcontain">
					<span id="opponentScore-label" class="property-label"><g:message code="playoffGame.opponentScore.label" default="Opponent Score" /></span>
					
						<span class="property-value" aria-labelledby="opponentScore-label"><g:fieldValue bean="${playoffGameInstance}" field="opponentScore"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${playoffGameInstance?.id}" />
					<g:link class="edit" action="edit" id="${playoffGameInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
