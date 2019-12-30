<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
<script>
    $(function () {
        $('#article-table').jqGrid({
            //加入bootstrap属性
            styleUI:'Bootstrap',
            url:'${pageContext.request.contextPath}/article/showAll',//加载数据表格的路径
            datatype:'json',
            colNames:['编号','标题','内容','作者','日期','操作'],
            editurl:'${pageContext.request.contextPath}/article/update',//编辑修改路径
            colModel:[
                {name:'id',align:'center',key:true},
                {name:'title',align:'center',editable:true},
                {name:'content',align:'center',editable:true,
                },
                {name:'author',align:'center',editable:true},
                {name:'create_date',align:'center'
                },
                {name : 'handle',formatter:function (value,options,rows) {
            return "<a class='btn btn-success' onclick=\"openModal('edit','"+rows.id+"')\" >文章详情</a>"
        }}
            ],
            autowidth:true,
            //加入分页
            pager:'#article-pager',
            rowList:[3,5,7],
            rowNum:3,
            page:1,
            viewrecords:true,
            mtype:'GET',
            caption:'文章图表',
            rownumbers:true,
            height:200,
            toolbar:['true','both']
        }).navGrid('#article-pager',
            {// 控制是否显示指定工具栏
                add:false,edit:false,del:true,search:true,refresh:false
            },{},{},{
                //控制添加的相关操作
                closeAfterDel:true,
                afterSubmit:function (response) {
                    var status=response.responseJSON.status;
                    if(status){
                        $("#article-table").trigger("reloadGrid");
                    }
                    return "true";
                }
            },{}
        );
    })
    function openModal(oper,id) {
        KindEditor.html("#editor_id","");
        var article=$("#article-table").jqGrid("getRowData",id);
        //文章的数据赋值给form表单
        $("#article-title").val(article.title);
        $("#article-author").val(article.author);
        $("#article-id").val(article.id);
        KindEditor.html("#editor_id",article.content);
        $("#article-oper").val(oper);
        $("#article-modal").modal("show");
    }
    KindEditor.create("#editor_id",{
        allowFileManager:true,
        uploadJson:"${pageContext.request.contextPath}/kindeditor/upload",
        filePostName:"img",
        fileManagerJson:"${pageContext.request.contextPath}/kindeditor/getAll",
        resizeType:1,
        //将文本域中的值同步到form当中
        afterBlur:function () {
            this.sync();
        }
    });
    function dealSave() {
        $.ajax({
            url:"${pageContext.request.contextPath}/article/update",
            type:"post",
            data:{
                id:$("#article-id").val(),
                oper:$("#article-oper").val(),
                title:$("#article-title").val(),
                author:$("#article-author").val(),
                content:$(document.getElementsByTagName('iframe')[0].contentWindow.document.body).html()
            },
            dataType:"json",
            success:function () {
                $("#article-modal").modal("hide");
                $("#article-table").trigger("reloadGrid");
            }
        })
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
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">展示文章</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" onclick="openModal('add')" data-toggle="tab">添加文章</a></li>
    </ul>
</div>

<table class="table table-hover" id="article-table"></table>
    <!--分页-->
<div style="text-align: center;height: 40px" id="article-pager"></div>
<%--模态框--%>
<div class="modal fade" id="article-modal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 702px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">请输入文章信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="article-form">
                    <input id="article-id" type="hidden" name="id">
                    <input id="article-oper" type="hidden" name="oper">
                    <div class="form-group">
                        <label for="article-title" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="article-title" placeholder="title">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-2 control-label">作者</label>
                        <div class="col-sm-10">
                            <input type="text" name="author" class="form-control" id="article-author" placeholder="author">
                        </div>
                    </div>
                    <div class="form-group">
                         <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                         </textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="dealSave()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
