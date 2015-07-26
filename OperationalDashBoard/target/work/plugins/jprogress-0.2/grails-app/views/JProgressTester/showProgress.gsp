<%--
  Created by IntelliJ IDEA.
  User: wohlgemuth
  Date: 5/9/11
  Time: 7:02 PM
  To change this template use File | Settings | File Templates.
--%>

<!-- jquery ui compoments -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'binbaseSchedule.css')}"/>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Shows our progressbar</title>

</head>
<body>

<div id="progressField">
    <g:form>
        <g:submitToRemote action="executeAction" id="progressButton" name="progressButton" value="start...."/>
    </g:form>

</div>

<g:jprogress progressId="123" trigger="progressButton"/>

</body>
</html>
