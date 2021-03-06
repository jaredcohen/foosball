<%@ page import="org.foosball.Result" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'result.label', default: 'Result')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-result" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list" id="${resultInstance.sessionId}"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create" params="['sessionId': resultInstance.sessionId]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-result" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${resultInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${resultInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${resultInstance?.id}" />
				<g:hiddenField name="version" value="${resultInstance?.version}" />
				<fieldset class="form">
					
					<g:render template="form"/>
					
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

				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
