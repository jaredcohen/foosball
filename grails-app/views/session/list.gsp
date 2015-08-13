
<%@ page import="org.foosball.Person" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
		<title>Choose participating players...</title>
	</head>
	<body>
		<a href="#list-person" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="show-person" class="content scaffold-show" role="main">
			<h1>Choose participating players...</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:form action="pickTeams" >
				<fieldset class="buttons">
					<g:submitButton name="pickTeams" class="save" value="Pick teams" />
					<g:checkBox name="markUnplayedGamesAsLosses" value="${false}"/>Mark unplayed games as losses? </span>
				</fieldset>
				<g:each in="${personInstanceList}" status="i" var="personInstance">
					<fieldset class="form">
						<tr class="fieldcontain ${hasErrors(bean: personInstance, field: 'name', 'error')} ">
							<td class="property-value" aria-labelledby="name-label"><g:checkBox name="${personInstance?.id}" value="${params.get((i+1).toString())}" /> <g:fieldValue bean="${personInstance}" field="name"/></td>
						</tr>
					</fieldset>
				</g:each>
				<fieldset class="buttons">
					<g:submitButton name="pickTeams" class="save" value="Pick teams" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
