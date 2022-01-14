<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 用户修改页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/user.do">
            <input type="hidden" name="method" value="modified">
            <input type="hidden" name="uid" value="${user.id }"/>
            <div>
                <label for="userName">用户名称：</label>
                <input type="text" name="userName" id="userName" value="${user.userName }">
                <font color="red"></font>
            </div>
            <div>
                <label>用户性别：</label>
                <select name="gender" id="gender">
                    <c:choose>
                        <c:when test="${user.gender == 1 }">
                            <option value="1" selected="selected">男</option>
                            <option value="2">女</option>
                        </c:when>
                        <c:otherwise>
                            <option value="1">男</option>
                            <option value="2" selected="selected">女</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div>
                <label for="birthday">出生日期：</label>
                <input type="text" Class="Wdate" id="birthday" name="birthday" value="${user.birthday }"
                       readonly="readonly" onclick="WdatePicker();">
                <font color="red"></font>
            </div>

            <div>
                <label for="phone">用户电话：</label>
                <input type="text" name="phone" id="phone" value="${user.phone }">
                <font color="red"></font>
            </div>
            <div>
                <label for="address">用户地址：</label>
                <input type="text" name="address" id="address" value="${user.address }">
            </div>
            <div>
                <label>用户角色：</label>
                <!-- 列出所有的角色分类 -->
                <input type="hidden" value="${user.userRole }" id="rid"/>
                <select name="userRole" id="userRole"></select>
                <font color="red"></font>
            </div>
            <div class="providerAddBtn">
                <input type="button" name="save" id="save" value="保存"/>
                <input type="button" id="back" name="back" value="返回"/>
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>


<script>
    let userName = null;
    let birthday = null;
    let phone = null;
    let userRole = null;
    let saveBtn = null;
    let backBtn = null;

    $(function () {
        userName = $("#userName");
        birthday = $("#birthday");
        phone = $("#phone");
        userRole = $("#userRole");
        saveBtn = $("#save");
        backBtn = $("#back");

        userName.next().html("*");
        birthday.next().html("*");
        phone.next().html("*");
        userRole.next().html("*");

        $.ajax({
            type: "GET",//请求类型
            url: path + "/jsp/user.do",//请求的url
            data: {method: "getrolelist"},//请求参数
            dataType: "json",//ajax接口（请求url）返回的数据类型
            success: function (data) {//data：返回数据（json对象）
                if (data != null) {
                    let rid = $("#rid").val();
                    userRole.html("");
                    let options = "<option value=\"0\">--请选择--</option>";
                    for (let i = 0; i < data.length; i++) {
                        if (rid != null && rid != undefined && data[i].id == rid) {
                            options += "<option selected=\"selected\" value=\"" + data[i].id + "\" >" + data[i].roleName + "</option>";
                        } else {
                            options += "<option value=\"" + data[i].id + "\" >" + data[i].roleName + "</option>";
                        }
                    }
                    userRole.html(options);
                }
            },
            error: function (data) {       //当访问时候，404，500 等非200的错误状态码
                validateTip(userRole.next(), {"color": "red"}, imgNo + " 获取用户角色列表error", false);
            }
        });


        userName.on("focus", function () {
            validateTip(userName.next(), {"color": "#666666"}, "* 用户名长度必须是大于1小于10的字符", false);
        }).on("blur", function () {
            if (userName.val() != null && userName.val().length > 1
                && userName.val().length < 10) {
                validateTip(userName.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(userName.next(), {"color": "red"}, imgNo + " 用户名输入的不符合规范，请重新输入", false);
            }

        });

        birthday.on("focus", function () {
            validateTip(birthday.next(), {"color": "#666666"}, "* 点击输入框，选择日期", false);
        }).on("blur", function () {
            if (birthday.val() != null && birthday.val() != "") {
                validateTip(birthday.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(birthday.next(), {"color": "red"}, imgNo + " 选择的日期不正确,请重新输入", false);
            }
        });

        phone.on("focus", function () {
            validateTip(phone.next(), {"color": "#666666"}, "* 请输入手机号", false);
        }).on("blur", function () {
            let patrn = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
            if (phone.val().match(patrn)) {
                validateTip(phone.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(phone.next(), {"color": "red"}, imgNo + " 您输入的手机号格式不正确", false);
            }
        });

        userRole.on("focus", function () {
            validateTip(userRole.next(), {"color": "#666666"}, "* 请选择用户角色", false);
        }).on("blur", function () {
            if (userRole.val() != null && userRole.val() > 0) {
                validateTip(userRole.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(userRole.next(), {"color": "red"}, imgNo + " 请重新选择用户角色", false);
            }
        });

        saveBtn.on("click", function () {
            userName.blur();
            phone.blur();
            birthday.blur();
            userRole.blur();
            if (userName.attr("validateStatus") == "true"
                && phone.attr("validateStatus") == "true"
                && birthday.attr("validateStatus") == "true"
                && userRole.attr("validateStatus") == "true") {
                if (confirm("是否确认要提交数据？")) {
                    $("#userForm").submit();
                }
            }
        });

        backBtn.on("click", function () {
            //alert("modify: "+referer);
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