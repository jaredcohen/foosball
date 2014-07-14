<%@ page import="org.foosball.Result" %>



<div class="fieldcontain ${hasErrors(bean: resultInstance, field: 'winner', 'error')} required">
	<label for="winner">
		<g:message code="result.winner.label" default="Winner" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="winner" name="winner.id" from="${org.foosball.Team.findAll { sessionId == resultInstance.sessionId }}" optionKey="id" optionValue="name" required="true" value="${resultInstance?.winner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultInstance, field: 'opponent', 'error')} required">
	<label for="opponent">
		<g:message code="result.opponent.label" default="Opponent" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="opponent" name="opponent.id" from="${org.foosball.Team.findAll { sessionId == resultInstance.sessionId }}" optionKey="id" optionValue="name" required="true" value="${resultInstance?.opponent?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultInstance, field: 'sessionId', 'error')} required">
	<label for="sessionId">
		<g:message code="result.sessionId.label" default="Session Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="sessionId" type="number" value="${resultInstance.sessionId}" required=""/>
</div>

