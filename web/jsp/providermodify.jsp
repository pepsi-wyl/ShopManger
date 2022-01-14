<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>供应商管理页面 >> 供应商修改页</span>
    </div>
    <div class="providerAdd">
        <form id="providerForm" name="providerForm" method="post"
              action="${pageContext.request.contextPath }/jsp/provider.do">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <input type="hidden" name="method" value="modified">
            <div class="">
                <label for="proCode">供应商编码：</label>
                <input type="text" name="proCode" id="proCode" value="${provider.proCode }" readonly="readonly">
            </div>
            <div>
                <label for="proName">供应商名称：</label>
                <input type="text" name="proName" id="proName" value="${provider.proName }" readonly="readonly">
                <font color="red"></font>
            </div>
            <div>
                <label for="proContact">联系人：</label>
                <input type="text" name="proContact" id="proContact" value="${provider.proContact }">
                <font color="red"></font>
            </div>
            <div>
                <label for="proPhone">联系电话：</label>
                <input type="text" name="proPhone" id="proPhone" value="${provider.proPhone }">
                <font color="red"></font>
            </div>
            <div>
                <label for="proAddress">联系地址：</label>
                <input type="text" name="proAddress" id="proAddress" value="${provider.proAddress }">
            </div>

            <div>
                <label for="proFax">传真：</label>
                <input type="text" name="proFax" id="proFax" value="${provider.proFax }">
            </div>

            <div>
                <label for="proDesc">描述：</label>
                <input type="text" name="proDesc" id="proDesc" value="${provider.proDesc }">
            </div>
            <div class="providerAddBtn">
                <input type="button" name="save" id="save" value="保存">
                <input type="button" id="back" name="back" value="返回">
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script>
    let proContact = null;
    let proPhone = null;
    let saveBtn = null;
    let backBtn = null;

    $(function () {
        proContact = $("#proContact");
        proPhone = $("#proPhone");
        saveBtn = $("#save");
        backBtn = $("#back");

        //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
        proContact.next().html("*");
        proPhone.next().html("*");

        /*
         * 验证
         * 失焦\获焦
         * jquery的方法传递
         */
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

        saveBtn.on("click", function () {
            proContact.blur();
            proPhone.blur();
            if (proContact.attr("validateStatus") == "true" &&
                proPhone.attr("validateStatus") == "true") {
                if (confirm("是否确认提交数据")) {
                    $("#providerForm").submit();
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