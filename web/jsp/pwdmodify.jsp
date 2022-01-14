<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>
<script src="https://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>密码修改页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/user.do">
            <input type="hidden" name="method" value="savepwd">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <div class="info">${message}</div>

            <div class="">
                <label for="oldPassword">旧密码：</label>
                <input type="password" name="oldpassword" id="oldpassword" value="">
                <font color="red"></font>
            </div>

            <div>
                <label for="newPassword">新密码：</label>
                <input type="password" name="newpassword" id="newpassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="newPassword">确认新密码：</label>
                <input type="password" name="rnewpassword" id="rnewpassword" value="">
                <font color="red"></font>
            </div>
            <div class="providerAddBtn">
                <input type="button" name="save" id="save" value="保存" class="input-button">
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>

<script>

    let oldpassword = null;
    let newpassword = null;
    let rnewpassword = null;
    let saveBtn = null;

    $(function () {
        oldpassword = $("#oldpassword");
        newpassword = $("#newpassword");
        rnewpassword = $("#rnewpassword");
        saveBtn = $("#save");

        oldpassword.next().html("*");
        newpassword.next().html("*");
        rnewpassword.next().html("*");

        oldpassword.on("blur", function () {
            $.ajax({
                type: "GET",
                url: path + "/jsp/user.do",
                data: {method: "pwdmodify", oldpassword: oldpassword.val()},
                dataType: "text", //返回数据类型
                success: function (data) {
                    if (data == "true") {//旧密码正确
                        validateTip(oldpassword.next(), {"color": "green"}, imgYes, true);
                    } else if (data == "false") {//旧密码输入不正确
                        validateTip(oldpassword.next(), {"color": "red"}, imgNo + " 旧密码输入不正确", false);
                    } else if (data == "sessionerror") {//当前用户session过期，请重新登录
                        validateTip(oldpassword.next(), {"color": "red"}, imgNo + "当前用户session过期，请重新登录", false);
                    } else if (data == "error") {//旧密码输入为空
                        validateTip(oldpassword.next(), {"color": "red"}, imgNo + "旧密码输入为空", false);
                    }
                },
                error: function (data) {
                    //请求出错
                    validateTip(oldpassword.next(), {"color": "red"}, imgNo + "请求出错", false);
                }
            });
        }).on("focus", function () {
            validateTip(oldpassword.next(), {"color": "#666666"}, "* 请输入原密码", false);
        });

        newpassword.on("focus", function () {
            validateTip(newpassword.next(), {"color": "#666666"}, "* 密码长度必须是大于6小于20", false);
        }).on("blur", function () {
            if (newpassword.val() != null && newpassword.val().length > 5
                && newpassword.val().length < 20) {
                validateTip(newpassword.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(newpassword.next(), {"color": "red"}, imgNo + " 密码输入不符合规范，请重新输入", false);
            }
        });

        rnewpassword.on("focus", function () {
            validateTip(rnewpassword.next(), {"color": "#666666"}, "* 请输入与上面一致的密码", false);
        }).on("blur", function () {
            if (rnewpassword.val() != null && rnewpassword.val().length > 5
                && rnewpassword.val().length < 20 && newpassword.val() == rnewpassword.val()) {
                validateTip(rnewpassword.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(rnewpassword.next(), {"color": "red"}, imgNo + " 两次密码输入不一致，请重新输入", false);
            }
        });

        saveBtn.on("click", function () {
            oldpassword.blur();
            newpassword.blur();
            rnewpassword.blur();
            if (oldpassword.attr("validateStatus") == "true"
                && newpassword.attr("validateStatus") == "true"
                && rnewpassword.attr("validateStatus") == "true") {
                if (confirm("确定要修改密码？")) {
                    $("#userForm").submit();
                }
            }
        });
    });
</script>