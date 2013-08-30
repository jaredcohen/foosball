<%@ page import="org.foosball.PlayoffGame" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'playoffGame.label', default: 'PlayoffGame')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-playoffGame" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" controller="playoff" action="show" id="${playoffGameInstance?.result?.id}">Back to result</g:link></li>
				<li><g:link class="create" action="create" params="['result.id': playoffGameInstance?.result?.id, 'sessionId': playoffGameInstance?.result?.sessionId]">${message(code: 'default.add.label', args: [message(code: 'playoffGame.label', default: 'Game')])}</g:link></li>
			</ul>
		</div>
		<div id="create-playoffGame" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${playoffGameInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${playoffGameInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
