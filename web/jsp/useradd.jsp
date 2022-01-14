<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 用户添加页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/user.do">
            <input type="hidden" name="method" value="add">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <div>
                <label for="userCode">用户编码：</label>
                <input type="text" name="userCode" id="userCode" value="">
                <!-- 放置提示信息 -->
                <font color="red"></font>
            </div>
            <div>
                <label for="userName">用户名称：</label>
                <input type="text" name="userName" id="userName" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="userPassword">用户密码：</label>
                <input type="password" name="userPassword" id="userPassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="ruserPassword">确认密码：</label>
                <input type="password" name="ruserPassword" id="ruserPassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label>用户性别：</label>
                <select name="gender" id="gender">
                    <option value="1" selected="selected">男</option>
                    <option value="2">女</option>
                </select>
            </div>
            <div>
                <label for="birthday">出生日期：</label>
                <input type="text" Class="Wdate" id="birthday" name="birthday"
                       readonly="readonly" onclick="WdatePicker();">
                <font color="red"></font>
            </div>
            <div>
                <label for="phone">用户电话：</label>
                <input type="text" name="phone" id="phone" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="address">用户地址：</label>
                <input name="address" id="address" value="">
            </div>
            <div>
                <label>用户角色：</label>
                <!-- 列出所有的角色分类 -->
                <select name="userRole" id="userRole" style="text-align:center;text-align-last:center">

                </select>
                <font color="red"></font>
            </div>
            <div class="providerAddBtn">
                <input type="button" name="add" id="add" value="保存">
                <input type="button" id="back" name="back" value="返回">
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>

<script>
    let userCode = null;
    let userName = null;
    let userPassword = null;
    let ruserPassword = null;
    let phone = null;
    let birthday = null;
    let userRole = null;
    let addBtn = null;
    let backBtn = null;


    $(function () {
        userCode = $("#userCode");
        userName = $("#userName");
        userPassword = $("#userPassword");
        ruserPassword = $("#ruserPassword");
        phone = $("#phone");
        birthday = $("#birthday");
        userRole = $("#userRole");
        addBtn = $("#add");
        backBtn = $("#back");
        //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
        userCode.next().html("*");
        userName.next().html("*");
        userPassword.next().html("*");
        ruserPassword.next().html("*");
        phone.next().html("*");
        birthday.next().html("*");
        userRole.next().html("*");

        $.ajax({
            type: "GET",//请求类型
            url: path + "/jsp/user.do",//请求的url
            data: {method: "getrolelist"},//请求参数
            dataType: "json",//ajax接口（请求url）返回的数据类型
            success: function (data) {//data：返回数据（json对象）
                if (data != null) {
                    userRole.html("");
                    let options = "<option value=\"0\" style='text-align: center'>--请选择--</option>";
                    for (let i = 0; i < data.length; i++) {
                        options += "<option value=\"" + data[i].id + "\" style='text-align: center'>" + data[i].roleName + "</option>";
                    }
                    userRole.html(options);
                }
            },
            error: function (data) {//当访问时候，404，500 等非200的错误状态码
                validateTip(userRole.next(), {"color": "red"}, imgNo + " 获取用户角色列表error", false);
            }
        });

        /*
         * 验证
         * 失焦\获焦
         * jquery的方法传递
         */
        userCode.bind("blur", function () {
            //ajax后台验证--userCode是否已存在
            //user.do?method=ucexist&userCode=**
            $.ajax({
                type: "GET",//请求类型
                url: path + "/jsp/user.do",//请求的url
                data: {method: "ucexist", userCode: userCode.val()},//请求参数
                dataType: "text",    //ajax接口（请求url）返回的数据类型
                success: function (data) {//data：返回数据（json对象）
                    if ("no" == data) {               //账号已存在，错误提示
                        validateTip(userCode.next(), {"color": "red"}, imgNo + " 该用户账号已存在", false);
                    } else if ("null" == data) {       //账号可用，正确提示
                        validateTip(userCode.next(), {"color": "red"}, imgNo + " 该账号输入为空", false);
                    } else if ("yes" == data) {       //账号可用，正确提示
                        validateTip(userCode.next(), {"color": "green"}, imgYes + " 该账号可以使用", true);
                    }
                },
                error: function (data) {//当访问时候，404，500 等非200的错误状态码
                    validateTip(userCode.next(), {"color": "red"}, imgNo + " 您访问的页面不存在", false);
                }
            });
        }).bind("focus", function () {
            validateTip(userCode.next(), {"color": "#666666"}, "* 用户编码是您登录系统的账号", false);//显示友情提示
        }).focus();

        userName.bind("focus", function () {
            validateTip(userName.next(), {"color": "#666666"}, "* 用户名长度必须是大于1小于10的字符", false);
        }).bind("blur", function () {
            if (userName.val() != null && userName.val().length > 1
                && userName.val().length < 10) {
                validateTip(userName.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(userName.next(), {"color": "red"}, imgNo + " 用户名输入的不符合规范，请重新输入", false);
            }
        });

        userPassword.bind("focus", function () {
            validateTip(userPassword.next(), {"color": "#666666"}, "* 密码长度必须是大于6小于20", false);
        }).bind("blur", function () {
            if (userPassword.val() != null && userPassword.val().length > 5
                && userPassword.val().length < 20) {
                validateTip(userPassword.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(userPassword.next(), {"color": "red"}, imgNo + " 密码输入不符合规范，请重新输入", false);
            }
        });

        ruserPassword.bind("focus", function () {
            validateTip(ruserPassword.next(), {"color": "#666666"}, "* 请输入与上面一致的密码", false);
        }).bind("blur", function () {
            if (ruserPassword.val() != null && ruserPassword.val().length > 5
                && ruserPassword.val().length < 20 && userPassword.val() == ruserPassword.val()) {
                validateTip(ruserPassword.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(ruserPassword.next(), {"color": "red"}, imgNo + " 两次密码输入不一致，请重新输入", false);
            }
        });


        birthday.bind("focus", function () {
            validateTip(birthday.next(), {"color": "#666666"}, "* 点击输入框，选择日期", false);
        }).bind("blur", function () {
            if (birthday.val() != null && birthday.val() != "") {
                validateTip(birthday.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(birthday.next(), {"color": "red"}, imgNo + " 选择的日期不正确,请重新输入", false);
            }
        });

        phone.bind("focus", function () {
            validateTip(phone.next(), {"color": "#666666"}, "* 请输入手机号", false);
        }).bind("blur", function () {
            let patrn = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
            if (phone.val().match(patrn)) {
                validateTip(phone.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(phone.next(), {"color": "red"}, imgNo + " 您输入的手机号格式不正确", false);
            }
        });

        userRole.bind("focus", function () {
            validateTip(userRole.next(), {"color": "#666666"}, "* 请选择用户角色", false);
        }).bind("blur", function () {
            if (userRole.val() != null && userRole.val() > 0) {
                validateTip(userRole.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(userRole.next(), {"color": "red"}, imgNo + " 请重新选择用户角色", false);
            }
        });

        addBtn.bind("click", function () {
            if (userCode.attr("validateStatus") != "true") {
                userCode.blur();
            } else if (userName.attr("validateStatus") != "true") {
                userName.blur();
            } else if (userPassword.attr("validateStatus") != "true") {
                userPassword.blur();
            } else if (ruserPassword.attr("validateStatus") != "true") {
                ruserPassword.blur();
            } else if (birthday.attr("validateStatus") != "true") {
                birthday.blur();
            } else if (phone.attr("validateStatus") != "true") {
                phone.blur();
            } else if (userRole.attr("validateStatus") != "true") {
                userRole.blur();
            } else {
                if (confirm("是否确认提交数据")) {
                    $("#userForm").submit();
                }
            }
        });

        backBtn.on("click", function () {
            if (referer != undefined
                && null != referer
                && "" != referer
                && "null" != referer
                && referer.length > 4) {
                window.location.href = referer;
            } else {
                history.back(-1);
            }
        });
    });
</script>
