<%--
  Created by IntelliJ IDEA.
  User: wangliang.wang
  Date: 2018/4/23
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>批量预定页面</title>
</head>
<body>
<form action="/search">
    <table>
        <tr>
            <td>去程：</td>
        </tr>
        <tr>
            <td>出发</td>
            <td><input name="searchBeanList[0].depCity" id="go_dep" type="text" value="北京"/></td>
        </tr>
        <tr>
            <td>到达</td>
            <td><input name="searchBeanList[0].arrCity" id="go_arr" type="text" value="上海"/></td>
        </tr>
        <tr>
            <td>起飞日期</td>
            <td><input name="searchBeanList[0].depDate" id="go_depDate" type="text" value="2018-05-30"/></td>
        </tr>
        <tr>
            <td>返程：</td>
        </tr>
        <tr>
            <td>出发</td>
            <td><input name="searchBeanList[1].depCity" id="return_dep" type="text" value="北京"/></td>
        </tr>
        <tr>
            <td>到达</td>
            <td><input name="searchBeanList[1].arrCity" id="return_arr" type="text" value="上海"/></td>
        </tr>
        <tr>
            <td>起飞日期</td>
            <td><input name="searchBeanList[1].depDate" id="return_depDate" type="text" value="2018-05-30"/></td>
        </tr>
    </table>
    <input type="submit"/>
</form>
</body>
</html>
