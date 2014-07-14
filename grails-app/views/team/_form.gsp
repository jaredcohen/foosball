<%@ page import="org.foosball.Team" %>



<div class="fieldcontain ${hasErrors(bean: teamInstance, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="team.members.label" default="Members" />
		
	</label>
	<g:select name="members" from="${org.foosball.Person.list()}" multiple="multiple" optionKey="id" optionValue="name" size="5" value="${teamInstance?.members*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teamInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="team.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${teamInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teamInstance, field: 'session', 'error')} required">
	<label for="session">
		<g:message code="team.session.label" default="Session" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="sessionId" type="number" value="${teamInstance.sessionId}" required=""/>
</div>

