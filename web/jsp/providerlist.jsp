<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>供应商管理页面</span>
    </div>
    <div class="search">
        <form method="get" action="${pageContext.request.contextPath }/jsp/provider.do">
            <input name="method" value="query" type="hidden">

            <span>供应商编码：</span>
            <input name="queryProCode" type="text" value="${queryProCode }">

            <span>供应商名称：</span>
            <input name="queryProName" type="text" value="${queryProName }">

            <input type="hidden" name="pageIndex" value="1"/>
            <input value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath }/jsp/provideradd.jsp">添加供应商</a>
        </form>
    </div>
    <!--供应商操作表格-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">供应商编码</th>
            <th width="20%">供应商名称</th>
            <th width="10%">联系人</th>
            <th width="10%">联系电话</th>
            <th width="10%">传真</th>
            <th width="10%">创建时间</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="provider" items="${providerList }" varStatus="status">
            <tr>
                <td>
                    <span>${provider.proCode }</span>
                </td>
                <td>
                    <span>${provider.proName }</span>
                </td>
                <td>
                    <span>${provider.proContact}</span>
                </td>
                <td>
                    <span>${provider.proPhone}</span>
                </td>
                <td>
                    <span>${provider.proFax}</span>
                </td>
                <td>
					<span>
					<fmt:formatDate value="${provider.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
                </td>
                <td>
                    <span><a class="viewProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img
                            src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img
                            src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img
                            src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
    <c:import url="rollpage.jsp">
        <c:param name="totalCount" value="${totalCount}"/>
        <c:param name="currentPageNo" value="${currentPageNo}"/>
        <c:param name="totalPageCount" value="${totalPageCount}"/>
    </c:import>
</div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该供应商吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script>
    let providerObj;
    //供应商管理页面上点击删除按钮弹出删除框(providerlist.jsp)
    function deleteProvider(obj) {
        $.ajax({
            type: "GET",
            url: path + "/jsp/provider.do",
            data: {method: "delprovider", proid: obj.attr("proid")},
            dataType: "text",
            success: function (data) {
                if (data == "true") {           //删除成功：移除删除行
                    cancleBtn();
                    obj.parents("tr").remove();
                } else if (data == "false") {   //删除失败
                    changeDLGContent("对不起，删除供应商【" + obj.attr("proname") + "】失败");
                } else if (data == "notexist") {
                    changeDLGContent("对不起，供应商【" + obj.attr("proname") + "】不存在");
                } else if (data == "exist"){
                    changeDLGContent("对不起，该供应商【" + obj.attr("proname") + "】下有订单，不能删除");
                }
            },
            error: function (data) {
                changeDLGContent("对不起，删除失败");
            }
        });
    }

    function openYesOrNoDLG() {
        $('.zhezhao').css('display', 'block');
        $('#removeProv').fadeIn();
    }

    function cancleBtn() {
        $('.zhezhao').css('display', 'none');
        $('#removeProv').fadeOut();
    }

    function changeDLGContent(contentStr) {
        let p = $(".removeMain").find("p");
        p.html(contentStr);
    }

    $(function () {
        $(".viewProvider").on("click", function () {
            //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
            var obj = $(this);
            window.location.href = path + "/jsp/provider.do?method=view&proid=" + obj.attr("proid");
        });

        $(".modifyProvider").on("click", function () {
            var obj = $(this);
            window.location.href = path + "/jsp/provider.do?method=modify&proid=" + obj.attr("proid");
        });

        $('#no').click(function () {
            cancleBtn();
        });

        $('#yes').click(function () {
            deleteProvider(providerObj);
        });

        $(".deleteProvider").on("click", function () {
            providerObj = $(this);
            changeDLGContent("你确定要删除供应商【" + providerObj.attr("proname") + "】吗？");
            openYesOrNoDLG();
        });
    });
</script>
