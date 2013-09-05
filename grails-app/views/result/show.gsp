
<%@ page import="org.foosball.Result" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'result.label', default: 'Result')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-result" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list" id="${resultInstance.sessionId}"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create" params="['sessionId': resultInstance.sessionId]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-result" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list result">
				
				<g:if test="${resultInstance?.winner}">
				<li class="fieldcontain">
					<span id="winner-label" class="property-label"><g:message code="result.winner.label" default="Winner" /></span>
					
						<span class="property-value" aria-labelledby="winner-label"><g:link controller="team" action="show" id="${resultInstance?.winner?.id}">${resultInstance?.winner?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${resultInstance?.opponent}">
				<li class="fieldcontain">
					<span id="opponent-label" class="property-label"><g:message code="result.opponent.label" default="Opponent" /></span>
					
						<span class="property-value" aria-labelledby="opponent-label"><g:link controller="team" action="show" id="${resultInstance?.opponent?.id}">${resultInstance?.opponent?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${resultInstance?.games}">
				<li class="fieldcontain">
					<span id="games-label" class="property-label"><g:message code="result.games.label" default="Games" /></span>
					
						<g:each in="${resultInstance.games}" var="g">
							<g:if test="${g?.winner.id != resultInstance?.winner.id}">
								<span class="property-value" aria-labelledby="games-label"><g:link controller="game" action="show" id="${g.id}">${g?.opponentScore} - ${g?.winnerScore}</g:link></span>
							</g:if>
							<g:else>
								<span class="property-value" aria-labelledby="games-label"><g:link controller="game" action="show" id="${g.id}">${g?.winnerScore} - ${g?.opponentScore}</g:link></span>
							</g:else>
						</g:each>
					
					<span class="property-value" aria-labelledby="games-label"><g:link controller="game" action="create" params="['result.id': resultInstance?.id, 'sessionId': resultInstance?.sessionId]">${message(code: 'default.add.label', args: [message(code: 'game.label', default: 'Game')])}</g:link></span>
				</li>
				</g:if>
				<g:else>
					<li class="fieldcontain">
						<span class="property-value" aria-labelledby="games-label"><g:link controller="game" action="create" params="['result.id': resultInstance?.id, 'sessionId': resultInstance?.sessionId]">${message(code: 'default.add.label', args: [message(code: 'game.label', default: 'Game')])}</g:link></span>
					</li>
				</g:else>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${resultInstance?.id}" />
					<g:link class="edit" action="edit" id="${resultInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
