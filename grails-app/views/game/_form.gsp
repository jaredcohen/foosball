<%@ page import="org.foosball.Game" %>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'winner', 'error')} required">
	<label for="winner">
		<g:message code="game.winner.label" default="Winner" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="winner" name="winner.id" from="${org.foosball.Team.list()}" optionKey="id" optionValue="name" required="true" value="${gameInstance?.result?.winner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'opponent', 'error')} required">
	<label for="opponent">
		<g:message code="game.opponent.label" default="Opponent" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="opponent" name="opponent.id" from="${org.foosball.Team.list()}" optionKey="id" optionValue="name" required="true" value="${gameInstance?.result?.opponent?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'winnerScore', 'error')} required">
	<label for="winnerScore">
		<g:message code="game.winnerScore.label" default="Winner Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="winnerScore" type="number" value="${gameInstance?.winnerScore}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'opponentScore', 'error')} required">
	<label for="opponentScore">
		<g:message code="game.opponentScore.label" default="Opponent Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="opponentScore" type="number" value="${gameInstance?.opponentScore}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'result', 'error')} required">
	<label for="result">
		<g:message code="game.result.label" default="Result" />
		<span class="required-indicator">*</span>
	</label>
	<g:hiddenField name="result.id" value="${gameInstance?.result?.id}" />
	<g:textField name="result.id" size="60" value="${gameInstance?.result?.description}" required="true" disabled="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'sessionId', 'error')} required">
	<label for="sessionId">
		<g:message code="game.sessionId.label" default="Session Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:hiddenField name="sessionId" value="${gameInstance?.result?.sessionId}" />
	<g:field name="sessionId" type="text" value="${gameInstance?.result?.sessionId}" required="true" disabled="true"/>
</div>

