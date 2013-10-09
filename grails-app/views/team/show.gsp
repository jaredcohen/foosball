
<%@ page import="org.foosball.Team" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'team.label', default: 'Team')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-team" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-team" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list team">
			
				<g:if test="${teamInstance?.members}">
				<li class="fieldcontain">
					<span id="members-label" class="property-label"><g:message code="team.members.label" default="Members" /></span>
					
						<g:each in="${teamInstance.members}" var="m">
						<span class="property-value" aria-labelledby="members-label"><g:link controller="person" action="show" id="${m.id}">${m?.name}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${teamInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="team.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${teamInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${teamInstance?.session}">
				<li class="fieldcontain">
					<span id="session-label" class="property-label"><g:message code="team.session.label" default="Session" /></span>
					
						<span class="property-value" aria-labelledby="session-label"><g:fieldValue bean="${teamInstance}" field="session"/></span>
					
				</li>
				</g:if>
				
				<li class="fieldcontain">
					<span id="session-label" class="property-label"><g:message code="team.record.label" default="Match Record" /></span>
					
						<span class="property-value" aria-labelledby="session-label">${teamInstance.getWinCount()} - ${teamInstance.getLossCount()} (${teamInstance.getWinPct()})</span>
					
				</li>
				
				<li class="fieldcontain">
					<span id="session-label" class="property-label"><g:message code="team.gameRecord.label" default="Games Record" /></span>
					
						<span class="property-value" aria-labelledby="session-label">${teamInstance.getGamesWon()} - ${teamInstance.getGamesLost()} (${teamInstance.getGamesWinPct()})</span>
					
				</li>
				
				<li class="fieldcontain">
					<span id="session-label" class="property-label"><g:message code="team.goalDiff.label" default="Goal Difference" /></span>
					
						<span class="property-value" aria-labelledby="session-label">${teamInstance.getGoalDiff()}</span>
					
				</li>

				<g:if test="${teamInstance?.getTeamsNotPlayed()}">
				<li class="fieldcontain">
					<span id="teams-not-played-label" class="property-label"><g:message code="team.teamsNotPlayed.label" default="Teams Not Played" /></span>
					<g:each in="${teamInstance.getTeamsNotPlayed()}" var="notPlayed">
						<span class="property-value" aria-labelledby="teams-label"><g:link controller="team" action="show" id="${notPlayed?.id}">${notPlayed?.name}</g:link></span>
					</g:each>
				</li>
				</g:if>

				<g:if test="${teamInstance?.getTeamsPlayed()}">
				<li class="fieldcontain">
					<span id="teams-played-label" class="property-label"><g:message code="team.teamsPlayed.label" default="Teams Played" /></span>
					<g:each in="${teamInstance.getTeamsPlayed()}" var="played">
						<span class="property-value" aria-labelledby="teams-label"><g:link controller="team" action="show" id="${played?.id}">${played?.name}</g:link></span>
					</g:each>
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${teamInstance?.id}" />
					<g:link class="edit" action="edit" id="${teamInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
