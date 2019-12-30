<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<html lang="en">
<head>
    <title>Document</title>
    <script charset="utf-8" src="kindeditor-all-min.js"></script>
    <script charset="utf-8" src="lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function(K) {
             KindEditor.create('#editor_id',{
                 allowFileManager:true,
                 uploadJson:"${pageContext.request.contextPath}/kindeditor/upload",
                 filePostName:"img",
                 fileManagerJson:"${pageContext.request.contextPath}/kindeditor/getAll"
             });
        });
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/kindeditor/select" method="post">
    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
    &lt;strong&gt;HTML内容&lt;/strong&gt;
    </textarea>
    <input type="submit" placeholder="上传"/>
</form>
</body>
</html>