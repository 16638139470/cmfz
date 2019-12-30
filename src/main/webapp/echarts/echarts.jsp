<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<html lang="en">
<head>
    <title>Document</title>
    <!-- 引入 ECharts 文件 -->
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js"></script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '持明法洲用户注册近几月的人数'
        },
        tooltip: {},//展示对应光标移入的详细信息
        legend: {
            data:['人数']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '注册量',
            type: 'bar'
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    var months=[];
    var counts=[];
    $.ajax({
        url:"${pageContext.request.contextPath}/user/showZhuce",
        dataType:"json",
        type:"get",
        success:function (data) {
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    months.push(data[i].month);
                }
                for (var i = 0; i < data.length; i++) {
                    counts.push(data[i].count);
                }
                myChart.setOption({
                    xAxis: {
                        data: months
                    },
                    series: [{
                        data:counts
                    }]
                });
            }
        }
    })
</script>
</body>
</html>