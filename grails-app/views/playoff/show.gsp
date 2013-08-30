
<%@ page import="org.foosball.Playoff" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'playoff.label', default: 'Playoff')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-playoff" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list" id="${playoffInstance.sessionId}"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create" id="${playoffInstance.sessionId}"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-playoff" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list playoff">
			
				<g:if test="${playoffInstance?.sessionId}">
				<li class="fieldcontain">
					<span id="session-label" class="property-label"><g:message code="playoff.session.label" default="Session" /></span>
					
						<span class="property-value" aria-labelledby="session-label"><g:fieldValue bean="${playoffInstance}" field="sessionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffInstance?.roundNum}">
				<li class="fieldcontain">
					<span id="roundNum-label" class="property-label"><g:message code="playoff.roundNum.label" default="Round" /></span>
					
						<span class="property-value" aria-labelledby="roundNum-label"><g:fieldValue bean="${playoffInstance}" field="roundNum"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffInstance?.finalMatch}">
				<li class="fieldcontain">
					<span id="finalMatch-label" class="property-label"><g:message code="playoff.finalMatch.label" default="Is Final?" /></span>
					
						<span class="property-value" aria-labelledby="finalMatch-label"><g:formatBoolean boolean="${playoffInstance?.finalMatch}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffInstance?.winner}">
				<li class="fieldcontain">
					<span id="winner-label" class="property-label"><g:message code="playoff.winner.label" default="Winner" /></span>
					
						<span class="property-value" aria-labelledby="winner-label"><g:link controller="team" action="show" id="${playoffInstance?.winner?.id}">${playoffInstance?.winner?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffInstance?.opponent}">
				<li class="fieldcontain">
					<span id="opponent-label" class="property-label"><g:message code="playoff.opponent.label" default="Opponent" /></span>
					
						<span class="property-value" aria-labelledby="opponent-label"><g:link controller="team" action="show" id="${playoffInstance?.opponent?.id}">${playoffInstance?.opponent?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${playoffInstance?.games}">
				<li class="fieldcontain">
					<span id="games-label" class="property-label"><g:message code="playoff.games.label" default="Games" /></span>
					
						<g:each in="${playoffInstance.games}" var="g">
							<g:if test="${g?.winner.id != playoffInstance?.winner.id}">
								<span class="property-value" aria-labelledby="games-label"><g:link controller="playoffGame" action="show" id="${g.id}">${g?.opponentScore} - ${g?.winnerScore}</g:link></span>
							</g:if>
							<g:else>
								<span class="property-value" aria-labelledby="games-label"><g:link controller="playoffGame" action="show" id="${g.id}">${g?.winnerScore} - ${g?.opponentScore}</g:link></span>
							</g:else>
						</g:each>
					
					<span class="property-value" aria-labelledby="games-label"><g:link controller="playoffGame" action="create" params="['result.id': playoffInstance?.id, 'sessionId': playoffInstance?.sessionId]">${message(code: 'default.add.label', args: [message(code: 'playoffGame.label', default: 'Game')])}</g:link></span>
				</li>
				</g:if>
				<g:else>
					<li class="fieldcontain">
						<span class="property-value" aria-labelledby="games-label"><g:link controller="playoffGame" action="create" params="['result.id': playoffInstance?.id, 'sessionId': playoffInstance?.sessionId]">${message(code: 'default.add.label', args: [message(code: 'playoffGame.label', default: 'Game')])}</g:link></span>
					</li>
				</g:else>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${playoffInstance?.id}" />
					<g:link class="edit" action="edit" id="${playoffInstance?.id}" sessionId="${sessionId}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
