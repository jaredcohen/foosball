<%@ page import="org.foosball.Result" %>



<div class="fieldcontain ${hasErrors(bean: resultInstance, field: 'winner', 'error')} required">
	<label for="winner">
		<g:message code="result.winner.label" default="Winner" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="winner" name="winner.id" from="${org.foosball.Team.list()}" optionKey="id" optionValue="name" required="true" value="${resultInstance?.winner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultInstance, field: 'opponent', 'error')} required">
	<label for="opponent">
		<g:message code="result.opponent.label" default="Opponent" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="opponent" name="opponent.id" from="${org.foosball.Team.list()}" optionKey="id" optionValue="name" required="true" value="${resultInstance?.opponent?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultInstance, field: 'sessionId', 'error')} required">
	<label for="sessionId">
		<g:message code="result.sessionId.label" default="Session Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="sessionId" type="number" value="${resultInstance.sessionId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultInstance, field: 'games', 'error')} ">
	<label for="games">
		<g:message code="result.games.label" default="Games" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${resultInstance?.games?}" var="g">
	<g:if test="${g?.winner.id != resultInstance?.winner.id}">
	    <li><g:link controller="game" action="show" id="${g.id}">${g?.opponentScore} - ${g?.winnerScore}</g:link></li>
	</g:if>
	<g:else>
	    <li><g:link controller="game" action="show" id="${g.id}">${g?.winnerScore} - ${g?.opponentScore}</g:link></li>
	</g:else>
</g:each>
<li class="add">
	<g:link controller="game" action="create" params="['result.id': resultInstance?.id, 'sessionId': resultInstance?.sessionId]">${message(code: 'default.add.label', args: [message(code: 'game.label', default: 'Game')])}</g:link>
</li>
</ul>

</div>

