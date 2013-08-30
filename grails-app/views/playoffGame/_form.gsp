<%@ page import="org.foosball.PlayoffGame" %>


<div class="fieldcontain ${hasErrors(bean: playoffGameInstance, field: 'winner', 'error')} required">
	<label for="winner">
		<g:message code="playoffGame.winner.label" default="Winner" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="winner" name="winner.id" from="${org.foosball.Team.list()}" optionKey="id" optionValue="name" required="true" value="${playoffGameInstance?.result?.winner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: playoffGameInstance, field: 'opponent', 'error')} required">
	<label for="opponent">
		<g:message code="playoffGame.opponent.label" default="Opponent" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="opponent" name="opponent.id" from="${org.foosball.Team.list()}" optionKey="id" optionValue="name" required="true" value="${playoffGameInstance?.result?.opponent?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: playoffGameInstance, field: 'winnerScore', 'error')} required">
	<label for="winnerScore">
		<g:message code="playoffGame.winnerScore.label" default="Winner Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="winnerScore" type="number" value="${playoffGameInstance?.winnerScore}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: playoffGameInstance, field: 'opponentScore', 'error')} required">
	<label for="opponentScore">
		<g:message code="playoffGame.opponentScore.label" default="Opponent Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="opponentScore" type="number" value="${playoffGameInstance?.opponentScore}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: playoffGameInstance, field: 'result', 'error')} required">
	<label for="result">
		<g:message code="playoffGame.result.label" default="Result" />
		<span class="required-indicator">*</span>
	</label>
	<g:hiddenField name="result.id" value="${playoffGameInstance?.result?.id}" />
	<g:textField name="result.id" size="60" value="${playoffGameInstance?.result?.description}" required="true" disabled="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: playoffGameInstance, field: 'sessionId', 'error')} required">
	<label for="sessionId">
		<g:message code="playoffGame.sessionId.label" default="Session Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:hiddenField name="sessionId" value="${playoffGameInstance?.result?.sessionId}" />
	<g:field name="sessionId" type="text" value="${playoffGameInstance?.result?.sessionId}" required="true" disabled="true"/>
</div>
