
<%@ page import="org.foosball.Game" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'game.label', default: 'Game')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-game" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" controller="result" action="show" id="${gameInstance?.result?.id}">Back to result</g:link></li>
			</ul>
		</div>
		<div id="show-game" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list game">
			
				<g:if test="${gameInstance?.winner}">
				<li class="fieldcontain">
					<span id="winner-label" class="property-label"><g:message code="game.winner.label" default="Winner" /></span>
					
						<span class="property-value" aria-labelledby="winner-label"><g:link controller="team" action="show" id="${gameInstance?.winner?.id}">${gameInstance?.winner?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${gameInstance?.opponent}">
				<li class="fieldcontain">
					<span id="opponent-label" class="property-label"><g:message code="game.opponent.label" default="Opponent" /></span>
					
						<span class="property-value" aria-labelledby="opponent-label"><g:link controller="team" action="show" id="${gameInstance?.opponent?.id}">${gameInstance?.opponent?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${gameInstance?.winnerScore}">
				<li class="fieldcontain">
					<span id="winnerScore-label" class="property-label"><g:message code="game.winnerScore.label" default="Winner Score" /></span>
					
						<span class="property-value" aria-labelledby="winnerScore-label"><g:fieldValue bean="${gameInstance}" field="winnerScore"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${gameInstance?.opponentScore}">
				<li class="fieldcontain">
					<span id="opponentScore-label" class="property-label"><g:message code="game.opponentScore.label" default="Opponent Score" /></span>
					
						<span class="property-value" aria-labelledby="opponentScore-label"><g:fieldValue bean="${gameInstance}" field="opponentScore"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${gameInstance?.result}">
				<li class="fieldcontain">
					<span id="result-label" class="property-label"><g:message code="game.result.label" default="Result" /></span>
					
						<span class="property-value" aria-labelledby="result-label"><g:link controller="result" action="show" id="${gameInstance?.result?.id}">${gameInstance?.result?.id}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${gameInstance?.sessionId}">
				<li class="fieldcontain">
					<span id="sessionId-label" class="property-label"><g:message code="game.sessionId.label" default="Session Id" /></span>
					
						<span class="property-value" aria-labelledby="sessionId-label"><g:fieldValue bean="${gameInstance}" field="sessionId"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${gameInstance?.id}" />
					<g:link class="edit" action="edit" id="${gameInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
