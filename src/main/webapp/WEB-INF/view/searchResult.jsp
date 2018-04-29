<%@ page import="com.liang.batchOrder.bean.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SearchResponse searchResponse = (SearchResponse) request.getAttribute("searchResponse");
    SearchRequest searchRequest = (SearchRequest) request.getAttribute("searchRequest");
    ReferencePriceBean referencePriceBean = searchResponse.getReferencePriceBean();
    SearchBean firstSearchBean = searchRequest.getSearchBeanList().get(0);
    SearchBean secondSearchBean = searchRequest.getSearchBeanList().get(0);
%>

<html>
<head>
    <script type="application/javascript" src="../../js/jquery.js"></script>
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
            <td><input id="firstResult"  name="firstResult" type="radio" onclick="chooseSecond(this)"/></td>
            <td><%=searchResultBean.getFlightData()%></td>
            <td name="flightNo"><%=searchResultBean.getFlightNo()%></td>
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
            <td><input id="secondResult" name="secondResult" type="radio" onclick="chooseSecond(this)"/></td>
            <td><%=searchResultBean.getFlightData()%></td>
            <td name="flightNo"><%=searchResultBean.getFlightNo()%></td>
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
        <tr>参考价格</tr>
        <tr>
            <th>承运人</th>
            <th>航班搭配</th>
            <th>班期搭配</th>
            <th>间隔</th>
            <th>航班日期</th>
            <th>参考价</th>
            <th>代理费</th>
            <th>人数</th>
            <th>旅客</th>
        </tr>
        <tr>
            <td><%=referencePriceBean.getRealCarrier()%></td>
            <td name="flightNo"><%=referencePriceBean.getFlightMatch()%></td>
            <td><%=referencePriceBean.getFlightDateMatch()%></td>
            <td><%=referencePriceBean.getSpan()%></td>
            <td><%=referencePriceBean.getFlightDate()%></td>
            <td><%=referencePriceBean.getReferencePrice()%></td>
            <td><%=referencePriceBean.getAgencyFee()%></td>
            <td><%=referencePriceBean.getPersonNum()%></td>
            <td><%=referencePriceBean.getTraveler()%></td>
        <tr/>
    </table>
    <table>
        <tr>
            <td>批量预定天数</td>
            <td><input name="days" id="days" type="number" value="10"/></td>
        </tr>
        <tr>
            <td>作为数量</td>
            <td><input name="days" id="seatNum" type="number" value="10"/></td>
        </tr>
        <tr>
            <td>手机号</td>
            <td><input name="mobile" id="mobile" type="text" value="13299978758"/></td>
        </tr>
        <input name="firstFlightName" id="firstFlightName" type="hidden" value=""/>
        <input name="secondFlightName" id="secondFlightName" type="hidden" value=""/>
        <input name="goDate" id="goDate" type="hidden" value="<%=firstSearchBean.getDepDate()%>"/>
        <input name="backDate" id="backDate" type="hidden" value="<%=secondSearchBean.getDepDate()%>"/>
    </table>
    <input type="submit"/>
</form>
</body>
<script type="application/javascript">
    function chooseFirst(radio) {
        setValue(radio, 'firstFlightName');
    }

    function chooseSecond(radio) {
        setValue(radio, 'secondFlightName');
    }

    function setValue(radio, setInputId) {
        var $radio=$(radio);
        var $tr = $radio.parent().parent();

        $tr.children().each(function(i){                   // 遍历 tr
            var name = $(this).attr("name");
            if(name == "flightNo") {
                $("#" + setInputId).val($(this).text());
            }
        });
    }
</script>
</html>
