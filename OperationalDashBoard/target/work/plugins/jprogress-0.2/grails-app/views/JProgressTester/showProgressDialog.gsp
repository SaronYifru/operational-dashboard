<!-- jquery ui compoments -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'binbaseSchedule.css')}"/>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Shows our Dialog</title>

</head>

<body>

<g:form>
    <g:submitToRemote id="displayDialog" action="executeAction" name="displayDialog" value="start and show dialog...."/>
</g:form>




<g:jprogressDialog message="please wait for your calculation..." progressId="123" trigger="displayDialog"/>

</body>
</html>
