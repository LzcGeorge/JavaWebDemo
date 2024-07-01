/**
 * option 有如下属性：
 *  请求方式：method
 *  请求的 url ：url
 *  是否异步： asyn
 *  请求体：params
 *  回调方法：callback
 *  服务器响应数据转换成什么类型：type
 */


function ajax(option) {
    var xmlHttp = new XMLHttpRequest();
    if(!option.method) {
        option.method = "GET";
    }
    if(option.asyn == undefined) {
        option.asyn = true;
    }

    xmlHttp.open(option.method,option.url,option.asyn);

    if(option.method == "POST") {
        xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    }

    xmlHttp.send(option.params);

    xmlHttp.onreadystatechange = function () {
        if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            var data;
            if(!option.type) {//如果type没有赋值，那么默认为文本
                data = xmlHttp.responseText;
            } else if(option.type == "xml") {
                data = xmlHttp.responseXML;
            } else if(option.type == "text") {
                data = xmlHttp.responseText;
            } else if(option.type == "json") {
                var text = xmlHttp.responseText;
                data = eval("(" + text + ")");
            }

            // 调用回调方法
            option.callback(data);
        }

    }
}