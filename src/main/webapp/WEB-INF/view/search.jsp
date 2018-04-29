<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Boolean loginStatus = (Boolean) request.getAttribute("loginStatus");
%>
<html>
<head>
    <script type="application/javascript" src="../../js/jquery.js"></script>
</head>
<body>
<h2>搜索页面</h2>
<h2><span id="title" style="color: red"></span></h2>
<form id="loginForm" action="/search">
    <table>
        <tr>
            <td>去程：</td>
        </tr>
        <tr>
            <td>出发</td>
            <td><input name="searchBeanList[0].depCity" id="go_dep" type="text" value="天津"/></td>
        </tr>
        <tr>
            <td>到达</td>
            <td><input name="searchBeanList[0].arrCity" id="go_arr" type="text" value="大连"/></td>
        </tr>
        <tr>
            <td>起飞日期</td>
            <td><input name="searchBeanList[0].depDate" id="go_depDate" type="text" value="2018-05-03"/></td>
        </tr>
        <tr>
            <td>返程：</td>
        </tr>
        <tr>
            <td>出发</td>
            <td><input name="searchBeanList[1].depCity" id="return_dep" type="text" value="大连"/></td>
        </tr>
        <tr>
            <td>到达</td>
            <td><input name="searchBeanList[1].arrCity" id="return_arr" type="text" value="天津"/></td>
        </tr>
        <tr>
            <td>起飞日期</td>
            <td><input name="searchBeanList[1].depDate" id="return_depDate" type="text" value="2018-05-10"/></td>
        </tr>
    </table>
    <input type="submit"/>
</form>
</body>
<script type="application/javascript">
    $(function () {
        var loginStatus = <%=loginStatus%>;
        if(!loginStatus) {
            $('#loginForm').hide();
            $("#title").text("登录失败请返回重试");
        }
    });

</script>
</html>
