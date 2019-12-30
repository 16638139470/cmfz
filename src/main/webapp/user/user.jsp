<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $('#user-table').jqGrid({
            //加入bootstrap属性
            styleUI:'Bootstrap',
            url:'${pageContext.request.contextPath}/user/showAll',//加载数据表格的路径
            datatype:'json',
            colNames:['编号','用户名','法号','省份','城市','签名','性别','头像','状态','电话','上师名','创建日期'],
            editurl:'${pageContext.request.contextPath}/user/update',//编辑修改路径
            colModel:[
                {name:'id',align:'center',key:true},
                {name:'username',align:'center'},
                {name:'dharma',align:'center'},
                {name:'province',align:'center'},
                {name:'city',align:'center'},
                {name:'sign',align:'center'},
                {name:'sex',align:'center'},
                {name:'photo',align:'center', edittype: 'file',
                    formatter: function (value, options, row) {
                        return '<img style="height: 50px;" src="${pageContext.request.contextPath}/' + 'image/' + row.photo + '"/>';
                    }},
                {name:'status',align:'center',editable:true,edittype:'select',
                        editoptions:{value:"激活:激活;冻结:冻结"}
                },
                {name:'phone',align:'center'},
                {name:'guruname',align:'center'},
                {name:'create_date',align:'center'}
            ],
            autowidth:true,
            //加入分页
            pager:'#user-pager',
            rowList:[3,5,7],
            rowNum:3,
            page:1,
            viewrecords:true,
            mtype:'GET',
            caption:'用户图表',
            rownumbers:true,
            height:200,
            toolbar:['true','both']
        }).navGrid('#user-pager',
            {// 控制是否显示指定工具栏
                add:true,edit:true,del:true,search:true,refresh:false
            },
            {//控制修改操作
                closeAfterEdit: true,
            },
            { ///控制添加操作
                closeAfterAdd: true,
            },
            {},// 控制删除操作
            { // 控制搜索
                closeAfterSearch:true
            }
        );
    })
    function UserOut() {
        window.location.href="${pageContext.request.contextPath}/user/userOut"
    }
    function WordOut() {
        window.location.href="${pageContext.request.contextPath}/user/wordOut"
    }
    function PDFOut() {
        window.location.href="${pageContext.request.contextPath}/user/pdfOut"
    }
</script>
<style>
    th{
        text-align: center;
    }
</style>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">展示用户</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" onclick="UserOut()" data-toggle="tab">Excel导出</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" onclick="WordOut()" data-toggle="tab">Word导出</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" onclick="PDFOut()" data-toggle="tab">PDF导出</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/echarts/echarts.jsp" >用户月份注册走势图</a></li>
    </ul>
</div>
<table class="table table-hover" id="user-table"></table>
    <!--分页-->
<div style="text-align: center;height: 40px" id="user-pager"></div>
