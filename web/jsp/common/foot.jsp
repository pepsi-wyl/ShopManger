<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<footer class="footer">
    <div>
        超市订单管理系统<br/>
        © 2021-2022.&nbsp;&nbsp;&nbsp;© <a href="#">锋芒工作室</a>.&nbsp;&nbsp;&nbsp;Designed by @wyl
    </div>
</footer>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/calendar/WdatePicker.js"></script>

<script>
    //时间
    function fn() {
        var time = new Date();
        var str = "";
        var div = document.getElementById("time");
//    console.log(time);
        var year = time.getFullYear();
        var mon = time.getMonth() + 1;
        var day = time.getDate();
        var h = time.getHours();
        var m = time.getMinutes();
        var s = time.getSeconds();
        var week = time.getDay();
        switch (week) {
            case 0:
                week = "SUN";
                break;
            case 1:
                week = "MON";
                break;
            case 2:
                week = "TUE";
                break;
            case 3:
                week = "WED";
                break;
            case 4:
                week = "THU";
                break;
            case 5:
                week = "FRI";
                break;
            case 6:
                week = "SAT";
                break;
        }
        str = year + "-" + totwo(mon) + "-" + totwo(day) + "-" + "&nbsp;" + totwo(h) + ":" + totwo(m) + ":" + totwo(s) + "&nbsp;" + "&nbsp" + week;
        div.innerHTML = str;
    }

    fn();
    setInterval(fn, 1000);

    function totwo(n) {
        if (n <= 9) {
            return n = "0" + n;
        } else {
            return n = "" + n;
        }
    }
</script>

</body>
</html>