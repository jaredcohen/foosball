<%@ page import="org.foosball.Playoff" %>


<div class="fieldcontain ${hasErrors(bean: playoffInstance, field: 'finalMatch', 'error')} ">
	<label for="finalMatch">
		<g:message code="playoff.finalMatch.label" default="Final Match" />
		
	</label>
	<g:checkBox name="finalMatch" value="${playoffInstance?.finalMatch}" />
</div>

<div class="fieldcontain ${hasErrors(bean: playoffInstance, field: 'roundNum', 'error')} required">
	<label for="roundNum">
		<g:message code="playoff.roundNum.label" default="Round" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="roundNum" type="number" value="${playoffInstance.roundNum}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: playoffInstance, field: 'winner', 'error')} required">
	<label for="winner">
		<g:message code="playoff.winner.label" default="Winner" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="winner" name="winner.id" from="${org.foosball.Team.findAll { sessionId == playoffInstance.sessionId }}" optionKey="id" optionValue="name" required="true" value="${playoffInstance?.winner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: playoffInstance, field: 'opponent', 'error')} required">
	<label for="opponent">
		<g:message code="playoff.opponent.label" default="Opponent" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="opponent" name="opponent.id" from="${org.foosball.Team.findAll { sessionId == playoffInstance.sessionId }}" optionKey="id" optionValue="name" required="true" value="${playoffInstance?.opponent?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: playoffInstance, field: 'sessionId', 'error')} required">
	<label for="sessionId">
		<g:message code="playoff.sessionId.label" default="Session Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:hiddenField name="sessionId" value="${playoffInstance?.sessionId}" />
	<g:field name="sessionId" type="text" value="${playoffInstance?.sessionId}" required="true" disabled="true"/>
</div>

