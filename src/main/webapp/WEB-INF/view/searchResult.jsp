<%@ page import="com.liang.batchOrder.bean.SearchResponse" %>
<%@ page import="com.liang.batchOrder.bean.SearchResultBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SearchResponse searchResponse = (SearchResponse) request.getAttribute("searchResponse");
%>

<html>
<head>
</head>
<body>
<h2>搜索页面</h2>

<form action="/batchOrder">
    <table>
        <tr>第一航段航班信息</tr>
        <tr>
            <th>选择</th>
            <th>航线</th>
            <th>航班号</th>
            <th>起飞</th>
            <th>到达</th>
            <th>总限额</th>
            <th>剩余限额</th>
            <th>直接成交额</th>
            <th>直接成交额限制</th>
        </tr>
        <%
            for (SearchResultBean searchResultBean : searchResponse.getFirstSearchResultBeanList()) {
        %>
        <tr>
            <td><input name="secondResult" type="radio"/></td>
            <td><%=searchResultBean.getFlightData()%></td>
            <td><%=searchResultBean.getFlightData()%></td>
            <td><%=searchResultBean.getFlightNo()%></td>
            <td><%=searchResultBean.getDepTime()%></td>
            <td><%=searchResultBean.getArrTime()%></td>
            <td><%=searchResultBean.getAllLimitMoney()%></td>
            <td><%=searchResultBean.getLeftLimitMoney()%></td>
            <td><%=searchResultBean.getPayMoney()%></td>
        <tr/>
        <%
            }
        %>
    </table>
    <table>
        <tr>第二航段航班信息</tr>
        <tr>
            <th>选择</th>
            <th>航线</th>
            <th>航班号</th>
            <th>起飞</th>
            <th>到达</th>
            <th>总限额</th>
            <th>剩余限额</th>
            <th>直接成交额</th>
            <th>直接成交额限制</th>
        </tr>
        <%
            for (SearchResultBean searchResultBean : searchResponse.getSecondSearchResultBeanList()) {
        %>
        <tr>
            <td><input name="firstResult" type="radio"/></td>
            <td><%=searchResultBean.getFlightData()%></td>
            <td><%=searchResultBean.getFlightNo()%></td>
            <td><%=searchResultBean.getDepTime()%></td>
            <td><%=searchResultBean.getArrTime()%></td>
            <td><%=searchResultBean.getAllLimitMoney()%></td>
            <td><%=searchResultBean.getLeftLimitMoney()%></td>
            <td><%=searchResultBean.getPayMoney()%></td>
        <tr/>
        <%
            }
        %>
    </table>
    <table>
        <tr>
            <td>批量预定天数</td>
            <td><input name="dayNum" id="dayNum" type="text" value=""/></td>
        </tr>
        <tr>
            <td>手机号</td>
            <td><input name="phone" id="phone" type="text" value=""/></td>
        </tr>
    </table>
    <input type="submit"/>
</form>
</body>
</html>
