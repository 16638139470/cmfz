<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#album-table").jqGrid(
            {
                url: '${pageContext.request.contextPath}/album/showAll',
                datatype: "json",
                height: 190,
                editurl:'${pageContext.request.contextPath}/album/edit',
                colNames: ['编号', '标题', '封面', '作者', '播音', '分数', '集数','简介','时间'],
                colModel: [
                    {name: 'id',align:'center',key:true},
                    {name: 'title',align:'center',editable:true},
                    {name: 'cover',align:'center',editable:true,
                        edittype: 'file',
                        formatter: function (value, options, row) {
                            return '<img style="height: 50px;" src="${pageContext.request.contextPath}/' + 'image/' + row.cover + '"/>';
                        }
                    },
                    {name: 'author',align:'center',editable:true},
                    {name: 'beam',align:'center',editable:true},
                    {name: 'score',align:'center',editable:true,editrules:{number:true}},
                    {name: 'count',align:'center'},
                    {name: 'intro',align:'center',editable:true},
                    {name: 'create_date',align:'center'}
                ],
                styleUI:"Bootstrap",
                height:200,
                autowidth:true,
                rowNum: 2,
                rowList: [2, 5, 10, 20],
                pager: '#album-pager',
                viewrecords: true,
                multiselect: false,
                subGrid: true,
                caption: "展示专辑",
                subGridRowExpanded : function(subgrid_id, row_id) {//1.当前父容器的id   2.当前专辑的id
                    var subgrid_table_id = subgrid_id + "_t";
                    var pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                        "<div id='" + pager_id + "' class='scroll'></div>");
                    $("#" + subgrid_table_id).jqGrid(
                        {
                            url : '${pageContext.request.contextPath}/chapter/showAll?id='+row_id,
                            datatype : "json",
                            colNames : [ '编号', '标题', '大小', '时长','文件名','创建时间','在线播放' ],
                            editurl:'${pageContext.request.contextPath}/chapter/update?album_id='+row_id,
                            colModel : [
                                {name : "id",align:'center',key:true},
                                {name : "title",align:'center',editable:true},
                                {name : "size",align:'center'},
                                {name : "duration",align:'center'},
                                {name : "cover",align:'center',editable:true,edittype:'file'},
                                {name : "create_date",align:'center'},
                                {name : "aaa",width:400,formatter:function (value,options,rows) {
                                        return "<audio controls loop>\n" +
                                            "  <source src='${pageContext.request.contextPath}/album/music/"+rows.cover+"' type=\"audio/ogg\">\n" +
                                            "</audio>"
                                    }},
                            ],
                            styleUI:"Bootstrap",
                            autowidth:true,
                            rowNum : 2,
                            rowList: [2, 5, 10, 20],
                            pager : pager_id,
                            height : '100%'
                        }).jqGrid('navGrid',
                        "#" + pager_id, {
                            edit : true,
                            add : true,
                            del : false
                        },{
                            //控制章节修改
                            closeAfterEdit:true,
                            afterSubmit:function (response) {
                                var id=response.responseJSON.data;
                                $.ajaxFileUpload( {
                                    url : "${pageContext.request.contextPath}/chapter/upload",//用于文件上传的服务器端请求地址
                                    fileElementId :'cover',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                                    // dataType : 'json',       //返回值类型 一般设置为json
                                    type:'post',
                                    data:{id:id},
                                    success : function() {
                                        $("#" + subgrid_table_id).trigger("reloadGrid");
                                        $("#album-table").trigger("reloadGrid");
                                    }
                                });
                                return "true";
                            }
                        },{
                            //控制添加的相关操作
                            closeAfterAdd:true,
                            afterSubmit:function (response) {
                                var id = response.responseJSON.message;
                                console.log(id);
                                $.ajaxFileUpload( {
                                    url : "${pageContext.request.contextPath}/chapter/upload?album_id="+row_id,//用于文件上传的服务器端请求地址
                                    fileElementId:'cover',   //文件上传空间的id属性  <input type="file" id="file" name="file" />
                                    // dataType : 'json',       //返回值类型 一般设置为json
                                    type:'post',
                                    data:{id:id},
                                    success : function() {
                                        $("#" + subgrid_table_id).trigger("reloadGrid");
                                        $("#album-table").trigger("reloadGrid");
                                    }
                                });
                                return "true";
                            }
                        });
                },
            }).navGrid("#album-pager", {edit : true,add : true,del : true,search:false},{
            //控制修改的相关操作
            closeAfterEdit:close,
            beforeShowForm:function (frm) {
                frm.find("#cover").attr("disabled",true);
            }
        },{
            //控制添加的相关操作
            closeAfterAdd:true,
            afterSubmit:function (response) {
                var status=response.responseJSON.status;
                var id=response.responseJSON.message;
                console.log(id);
                if(status){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        fileElementId:'cover',
                        type:'post',
                        data: {id: id},
                        success:function () {
                            $("#album-table").trigger("reloadGrid");
                        }
                    })
                }
                return "true";
            }

        });
    })
</script>
<style>
    th{
        text-align: center;
    }
</style>
<div class="page-header">
    <h2>展示专辑</h2>
</div>
<table id="album-table"></table>
<div id="album-pager" style="height: 40px"></div>
