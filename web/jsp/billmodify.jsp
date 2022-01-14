<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>订单管理页面 >> 订单添加页面</span>
    </div>
    <div class="providerAdd">
        <form id="billForm" name="billForm" method="post" action="${pageContext.request.contextPath }/jsp/bill.do">
            <input type="hidden" name="method" value="modifysave">
            <input type="hidden" name="id" value="${bill.id }">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <div class="">
                <label for="billCode">订单编码：</label>
                <input type="text" name="billCode" id="billCode" value="${bill.billCode }" readonly="readonly">
            </div>
            <div>
                <label for="productName">商品名称：</label>
                <input type="text" name="productName" id="productName" value="${bill.productName }">
                <font color="red"></font>
            </div>
            <div>
                <label for="productDesc">商品分类：</label>
                <input type="text" name="productDesc" id="productDesc" value="${bill.productDesc }">
                <font color="red"></font>
            </div>
            <div>
                <label for="productUnit">商品单位：</label>
                <input type="text" name="productUnit" id="productUnit" value="${bill.productUnit }">
                <font color="red"></font>
            </div>
            <div>
                <label for="productCount">商品数量：</label>
                <input type="text" name="productCount" id="productCount" value="${bill.productCount }">
                <font color="red"></font>
            </div>
            <div>
                <label for="totalPrice">总金额：</label>
                <input type="text" name="totalPrice" id="totalPrice" value="${bill.totalPrice }">
                <font color="red"></font>
            </div>
            <div>
                <label for="providerId">供应商：</label>
                <input type="hidden" value="${bill.providerId }" id="pid"/>
                <select name="providerId" id="providerId">
                </select>
                <font color="red"></font>
            </div>
            <div>
                <label>是否付款：</label>
                <c:if test="${bill.isPayment == 1 }">
                    <input type="radio" name="isPayment" value="1" checked="checked">未付款
                    <input type="radio" name="isPayment" value="2">已付款
                </c:if>
                <c:if test="${bill.isPayment == 2 }">
                    <input type="radio" name="isPayment" value="1">未付款
                    <input type="radio" name="isPayment" value="2" checked="checked">已付款
                </c:if>
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
    var billCode = null;
    var productName = null;
    var productUnit = null;
    var productCount = null;
    var totalPrice = null;
    var providerId = null;
    var saveBtn = null;
    var backBtn = null;
    var productDesc = null;

    function priceReg(value) {
        value = value.replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
        value = value.replace(/^\./g, "");  //验证第一个字符是数字而不是.
        value = value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
        value = value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");//去掉特殊符号￥
        if (value.indexOf(".") > 0) {
            value = value.substring(0, value.indexOf(".") + 3);
        }
        return value;
    }


    $(function () {
        billCode = $("#billCode");
        productName = $("#productName");
        productUnit = $("#productUnit");
        productDesc = $("#productDesc");
        productCount = $("#productCount");
        totalPrice = $("#totalPrice");
        providerId = $("#providerId");
        addBtn = $("#save");
        backBtn = $("#back");

        //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
        billCode.next().html("*");
        productName.next().html("*");
        productDesc.next().html("*");
        productUnit.next().html("*");
        productCount.next().html("*");
        totalPrice.next().html("*");
        providerId.next().html("*");

        $.ajax({
            type: "GET",//请求类型
            url: path + "/jsp/bill.do",//请求的url
            data: {method: "getproviderlist"},//请求参数
            dataType: "json",//ajax接口（请求url）返回的数据类型
            success: function (data) {//data：返回数据（json对象）
                if (data != null) {
                    var pid = $("#pid").val();
                    $("select").html("");//通过标签选择器，得到select标签，适用于页面里只有一个select
                    var options = "<option value=\"0\">请选择</option>";
                    for (var i = 0; i < data.length; i++) {
                        //alert(data[i].id);
                        //alert(data[i].proName);
                        if (pid != null && pid != undefined && data[i].id == pid) {
                            options += "<option selected=\"selected\" value=\"" + data[i].id + "\" >" + data[i].proName + "</option>";
                        } else {
                            options += "<option value=\"" + data[i].id + "\" >" + data[i].proName + "</option>";
                        }
                    }
                    $("select").html(options);
                }
            },
            error: function (data) {//当访问时候，404，500 等非200的错误状态码
                validateTip(providerId.next(), {"color": "red"}, imgNo + " 获取供应商列表error", false);
            }
        });

        /*
         * 验证
         * 失焦\获焦
         * jquery的方法传递
         */

        productName.on("focus", function () {
            validateTip(productName.next(), {"color": "#666666"}, "* 请输入商品名称", false);
        }).on("blur", function () {
            if (productName.val() != null && productName.val() != "") {
                validateTip(productName.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(productName.next(), {"color": "red"}, imgNo + " 商品名称不能为空，请重新输入", false);
            }
        });

        productDesc.on("focus", function () {
            validateTip(productDesc.next(), {"color": "#666666"}, "* 请输入商品分类", false);
        }).on("blur", function () {
            if (productDesc.val() != null && productDesc.val() != "") {
                validateTip(productDesc.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(productDesc.next(), {"color": "red"}, imgNo + " 商品分类不能为空，请重新输入", false);
            }
        });

        productUnit.on("focus", function () {
            validateTip(productUnit.next(), {"color": "#666666"}, "* 请输入商品单位", false);
        }).on("blur", function () {
            if (productUnit.val() != null && productUnit.val() != "") {
                validateTip(productUnit.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(productUnit.next(), {"color": "red"}, imgNo + " 单位不能为空，请重新输入", false);
            }
        });

        providerId.on("focus", function () {
            validateTip(providerId.next(), {"color": "#666666"}, "* 请选择供应商", false);
        }).on("blur", function () {
            if (providerId.val() != null && providerId.val() != "" && providerId.val() != 0) {
                validateTip(providerId.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(providerId.next(), {"color": "red"}, imgNo + " 供应商不能为空，请选择", false);
            }
        });

        productCount.on("focus", function () {
            validateTip(productCount.next(), {"color": "#666666"}, "* 请输入大于0的正自然数，小数点后保留2位", false);
        }).on("keyup", function () {
            this.value = priceReg(this.value);
        }).on("blur", function () {
            this.value = priceReg(this.value);
        });

        totalPrice.on("focus", function () {
            validateTip(totalPrice.next(), {"color": "#666666"}, "* 请输入大于0的正自然数，小数点后保留2位", false);
        }).on("keyup", function () {
            this.value = priceReg(this.value);
        }).on("blur", function () {
            this.value = priceReg(this.value);
        });

        addBtn.on("click", function () {
            productName.blur();
            productUnit.blur();
            providerId.blur();
            productDesc.blur();
            if (productName.attr("validateStatus") == "true"
                && productUnit.attr("validateStatus") == "true"
                && providerId.attr("validateStatus") == "true"
                && productDesc.attr("validateStatus") == "true") {
                if (confirm("是否确认提交数据")) {
                    $("#billForm").submit();
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