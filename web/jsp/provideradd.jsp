<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>供应商管理页面 >> 供应商添加页面</span>
    </div>
    <div class="providerAdd">
        <form id="providerForm" name="providerForm" method="post"
              action="${pageContext.request.contextPath }/jsp/provider.do">
            <input type="hidden" name="method" value="add">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <div class="">
                <label for="proCode">供应商编码：</label>
                <input type="text" name="proCode" id="proCode" value="">
                <!-- 放置提示信息 -->
                <font color="red"></font>
            </div>
            <div>
                <label for="proName">供应商名称：</label>
                <input type="text" name="proName" id="proName" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="proContact">联系人：</label>
                <input type="text" name="proContact" id="proContact" value="">
                <font color="red"></font>

            </div>
            <div>
                <label for="proPhone">联系电话：</label>
                <input type="text" name="proPhone" id="proPhone" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="proAddress">联系地址：</label>
                <input type="text" name="proAddress" id="proAddress" value="">
            </div>
            <div>
                <label for="proFax">传真：</label>
                <input type="text" name="proFax" id="proFax" value="">
            </div>
            <div>
                <label for="proDesc">描述：</label>
                <input type="text" name="proDesc" id="proDesc" value="">
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
    let proCode = null;
    let proName = null;
    let proContact = null;
    let proPhone = null;
    let addBtn = null;
    let backBtn = null;

    $(function () {
        proCode = $("#proCode");
        proName = $("#proName");
        proContact = $("#proContact");
        proPhone = $("#proPhone");
        addBtn = $("#add");
        backBtn = $("#back");
        //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
        proCode.next().html("*");
        proName.next().html("*");
        proContact.next().html("*");
        proPhone.next().html("*");

        /*
         * 验证
         * 失焦\获焦
         * jquery的方法传递
         */

        proCode.bind("blur", function () {//ajax后台验证--proCode是否已存在
            $.ajax({
                type: "GET",  //请求类型
                url: path + "/jsp/provider.do",//请求的url
                data: {method: "ucexistProCode", proCode: proCode.val()},//请求参数
                dataType: "text",    //ajax接口（请求url）返回的数据类型
                success: function (data) {//data：返回数据（json对象）
                    if ("no" == data) {               //账号已存在，错误提示
                        validateTip(proCode.next(), {"color": "red"}, imgNo + " 该供应商编码已存在", false);
                    } else if ("null" == data) {       //账号可用，正确提示
                        validateTip(proCode.next(), {"color": "red"}, imgNo + " 供应商编码不能为空，请重新输入", false);
                    } else if ("yes" == data) {       //账号可用，正确提示
                        validateTip(proCode.next(), {"color": "green"}, imgYes + " 该供应商编码可以使用", true);
                    }
                },
                error: function (data) {//当访问时候，404，500 等非200的错误状态码
                    validateTip(proCode.next(), {"color": "red"}, imgNo + " 您访问的页面不存在", false);
                }
            });
        }).bind("focus", function () {
            validateTip(proCode.next(), {"color": "#666666"}, "* 请输入供应商编码", false);//显示友情提示
        }).focus();

        proName.bind("blur", function () {         //ajax后台验证--proName是否已存在
            $.ajax({
                type: "GET",  //请求类型
                url: path + "/jsp/provider.do",//请求的url
                data: {method: "ucexistProName", proName: proName.val()},       //请求参数
                dataType: "text",    //ajax接口（请求url）返回的数据类型
                success: function (data) {//data：返回数据（json对象）
                    if ("no" == data) {               //账号已存在，错误提示
                        validateTip(proName.next(), {"color": "red"}, imgNo + " 该供应商名称已存在", false);
                    } else if ("null" == data) {       //账号可用，正确提示
                        validateTip(proName.next(), {"color": "red"}, imgNo + " 供应商名称不能为空，请重新输入", false);
                    } else if ("yes" == data) {       //账号可用，正确提示
                        validateTip(proName.next(), {"color": "green"}, imgYes + " 该供应商名称可以使用", true);
                    }
                },
                error: function (data) {//当访问时候，404，500 等非200的错误状态码
                    validateTip(proName.next(), {"color": "red"}, imgNo + " 您访问的页面不存在", false);
                }
            });
        }).bind("focus", function () {
            validateTip(proName.next(), {"color": "#666666"}, "* 请输入供应商编码", false);//显示友情提示
        }).focus();

        proContact.on("focus", function () {
            validateTip(proContact.next(), {"color": "#666666"}, "* 请输入联系人", false);
        }).on("blur", function () {
            if (proContact.val() != null && proContact.val() != "") {
                validateTip(proContact.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(proContact.next(), {"color": "red"}, imgNo + " 联系人不能为空，请重新输入", false);
            }

        });

        proPhone.on("focus", function () {
            validateTip(proPhone.next(), {"color": "#666666"}, "* 请输入手机号", false);
        }).on("blur", function () {
            let patrn = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
            if (proPhone.val().match(patrn)) {
                validateTip(proPhone.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(proPhone.next(), {"color": "red"}, imgNo + " 您输入的手机号格式不正确", false);
            }
        });

        addBtn.bind("click", function () {
            if (proCode.attr("validateStatus") != "true") {
                proCode.blur();
            } else if (proName.attr("validateStatus") != "true") {
                proName.blur();
            } else if (proContact.attr("validateStatus") != "true") {
                proContact.blur();
            } else if (proPhone.attr("validateStatus") != "true") {
                proPhone.blur();
            } else {
                if (confirm("是否确认提交数据")) {
                    $("#providerForm").submit();
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
