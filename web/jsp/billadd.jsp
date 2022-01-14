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
            <!--div的class 为error是验证错误，ok是验证成功-->
            <input type="hidden" name="method" value="add">
            <div class="">
                <label for="billCode">订单编码：</label>
                <input type="text" name="billCode" class="text" id="billCode" value="">
                <!-- 放置提示信息 -->
                <font color="red"></font>
            </div>
            <div>
                <label for="productName">商品名称：</label>
                <input type="text" name="productName" id="productName" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="productDesc">商品分类：</label>
                <input type="text" name="productDesc" id="productDesc" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="productUnit">商品单位：</label>
                <input type="text" name="productUnit" id="productUnit" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="productCount">商品数量：</label>
                <input type="text" name="productCount" id="productCount" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="totalPrice">总金额：</label>
                <input type="text" name="totalPrice" id="totalPrice" value="">
                <font color="red"></font>
            </div>
            <div>
                <label>供应商：</label>
                <select name="providerId" id="providerId">
                </select>
                <font color="red"></font>
            </div>
            <div>
                <label>是否付款：</label>
                <input type="radio" name="isPayment" value="1" checked="checked">未付款
                <input type="radio" name="isPayment" value="2">已付款
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
    var billCode = null;
    var productName = null;
    var productUnit = null;
    var productCount = null;
    var totalPrice = null;
    var providerId = null;
    var addBtn = null;
    var backBtn = null;
    var productDesc=null;

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
        productDesc = $("#productDesc");
        productUnit = $("#productUnit");
        productCount = $("#productCount");
        totalPrice = $("#totalPrice");
        providerId = $("#providerId");
        addBtn = $("#add");
        backBtn = $("#back");
        //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
        billCode.next().html("*");
        productName.next().html("*");
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
                    $("select").html("");//通过标签选择器，得到select标签，适用于页面里只有一个select
                    let options = "<option value=\"0\">请选择</option>";
                    for (let i = 0; i < data.length; i++) {
                        options += "<option value=\"" + data[i].id + "\">" + data[i].proName + "</option>";
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

        billCode.bind("blur", function () {         //ajax后台验证--proName是否已存在
            $.ajax({
                type: "GET",  //请求类型
                url: path + "/jsp/bill.do",//请求的url
                data: {method: "ucexistBillCode", billCode: billCode.val()},       //请求参数
                dataType: "text",    //ajax接口（请求url）返回的数据类型
                success: function (data) {//data：返回数据（json对象）
                    if ("no" == data) {                  //账号已存在，错误提示
                        validateTip(billCode.next(), {"color": "red"}, imgNo + " 该编码称已存在", false);
                    } else if ("null" == data) {         //账号可用，正确提示
                        validateTip(billCode.next(), {"color": "red"}, imgNo + " 编码不能为空，请重新输入", false);
                    } else if ("yes" == data) {          //账号可用，正确提示
                        validateTip(billCode.next(), {"color": "green"}, imgYes + " 该编码称可以使用", true);
                    }
                },
                error: function (data) {//当访问时候，404，500 等非200的错误状态码
                    validateTip(billCode.next(), {"color": "red"}, imgNo + " 您访问的页面不存在", false);
                }
            });
        }).bind("focus", function () {
            validateTip(billCode.next(), {"color": "#666666"}, "* 请输入订单编码", false);   //显示友情提示
        }).focus();

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
            if (billCode.attr("validateStatus") != "true") {
                billCode.blur();
            } else if (productName.attr("validateStatus") != "true") {
                productName.blur();
            } else if (productUnit.attr("validateStatus") != "true") {
                productUnit.blur();
            } else if (providerId.attr("validateStatus") != "true") {
                providerId.blur();
            } else {
                if (confirm("是否确认提交数据")) {
                    $("#billForm").submit();
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