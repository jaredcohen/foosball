<%@ page import="org.foosball.Person" %>



<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="person.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${personInstance?.name}"/>
</div>

