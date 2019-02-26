<%@ page import="com.wangyu.wx.common.AccessTokenInfo" %><%--
  Created by IntelliJ IDEA.
  User: WangYu
  Date: 2019/2/26
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>微信开发学习</title>
  </head>
  <body>
  access_token为：<%=AccessTokenInfo.accessToken.getAccessToken()%>
  </body>
</html>
