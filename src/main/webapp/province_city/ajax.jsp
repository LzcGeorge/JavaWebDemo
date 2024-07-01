<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ajax</title>
    <script type="text/javascript">
        window.onload = function () {
            // 查询所有省
            fetch('<c:url value='/ProvinceServlet'/>')
                .then(response => response.json())
                .then(allProvince => {
                    for (const province of allProvince) {
                        // 创建 <option> 元素
                        var optionEle = document.createElement("option");
                        optionEle.value = province.pid; // 实际值
                        optionEle.innerHTML = province.name; // 显示值

                        // 添加到下拉框中
                        document.getElementById("province").appendChild(optionEle);
                    }
                });

            // 给 province 添加 onchange 监听

            document.getElementById("province").onchange = function () {

                fetch('<c:url value='/CityServlet'/>', {
                        method: "POST",
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded;'
                        },
                        body: "pid=" + this.value,
                    })
                    .then(response => response.json())
                    .then(citys => {
                        // 清空原有的 city

                        var cityList = document.getElementById("city");
                        // var cityOptions = cityList.getElementsByTagName("option");
                        // while(cityOptions.length > 1) {
                        //     cityList.removeChild(cityOptions[1]);
                        // }
                        cityList.innerHTML = cityList.options[0].outerHTML;
                        for (const city of citys) {
                            // 创建 <option> 元素
                            var optionEle = document.createElement("option");
                            optionEle.value = city.cid; // 实际值
                            optionEle.innerHTML = city.name; // 显示值

                            // 添加到下拉框中
                            cityList.appendChild(optionEle);
                        }
                    });
            };
        }
    </script>
</head>
<body>
<h1>省市联动</h1>
省：<select name="province" id="province">
    <option value="">==请选择==</option>
</select>
市：<select name="city" id="city">
    <option value="">==请选择==</option>
</select>
</body>
</html>
