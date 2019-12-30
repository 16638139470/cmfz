<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<script>
        $(function () {
                $('#t1').jqGrid({
                    //加入bootstrap属性
                    styleUI:'Bootstrap',
                    url:'${pageContext.request.contextPath}/b/showAll',//加载数据表格的路径
                    datatype:'json',
                    colNames:['编号','名称','图片','描述','状态','日期'],
                    editurl:'${pageContext.request.contextPath}/b/updateBanner',//编辑修改路径
                    colModel:[
                        {name:'id',align:'center',key:true},
                        {name:'name',align:'center',editable:true},
                        {name:'cover',align:'center',editable:true,
                            edittype: 'file',
                            formatter: function (value, options, row) {
                                return '<img style="height: 50px;" src="${pageContext.request.contextPath}/' + 'image/' + row.cover + '"/>';
                            }
                        },
                        {name:'description',align:'center',editable:true},
                        { name: 'status', align: 'center', editable: true,
                            edittype:'select',
                            editoptions:{value:"开启:开启;停用:停用"}
                        },
                        {name:'create_date',align:'center',
                            editable:false,
                            formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}
                        }
                    ],
                    autowidth:true,
                    //加入分页
                    pager:'#pg1',
                    rowList:[3,5,7],
                    rowNum:3,
                    page:1,
                    viewrecords:true,
                    mtype:'GET',
                    caption:'轮播图表',
                    rownumbers:true,
                    height:200,
                    toolbar:['true','both']
                }).navGrid('#pg1',
                    {// 控制是否显示指定工具栏
                        add:true,edit:true,del:true,search:true,refresh:false
                    },
                    {//控制修改操作
                        closeAfterEdit: true,
                        beforeShowForm:function(frm){
                            frm.find("#cover").attr("disabled",true);
                        }
                    },
                    { ///控制添加操作
                        closeAfterAdd: true,
                        afterSubmit: function (response) {
                            var id = response.responseJSON.data;
                            var code = response.responseJSON.status;
                            console.log(code)
                            if (code == "addOk") {
                                $.ajaxFileUpload({
                                    url: "/cmfz/b/upload",//用于文件上传的服务器端请求地址
                                    fileElementId: 'cover',
                                    type:'post',
                                    data: {id: id},
                                    success: function () {
                                        $("#t1").trigger("reloadGrid");
                                    }
                                })
                                return "true";
                            }
                        }
                    },
                    {
                    },// 控制删除操作
                    { // 控制搜索
                        closeAfterSearch:true
                    }
                );
        })
    </script>
    <style>
        th{
            text-align: center;
        }
    </style>

<div class="panel-body">
    <table class="table table-hover" id="t1"></table>
    <!--分页-->
    <div style="text-align: center;height: 40px" id="pg1"></div>
</div>
