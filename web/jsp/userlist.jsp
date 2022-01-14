<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面</span>
    </div>
    <div class="search">
        <form method="get" action="${pageContext.request.contextPath }/jsp/user.do">
            <input name="method" value="query" class="input-text" type="hidden">

            <span>用户名：</span>
            <input name="queryname" class="input-text" type="text" value="${queryUserName }">

            <span>用户角色：</span>
            <select name="queryUserRole">
                <c:if test="${roleList != null }">
                    <option value="0">--请选择--</option>
                    <c:forEach var="role" items="${roleList}">
                        <option
                                <c:if test="${role.id == queryUserRole }">selected="selected"</c:if>
                                value="${role.id}">${role.roleName}</option>
                    </c:forEach>
                </c:if>
            </select>

            <input type="hidden" name="pageIndex" value="1"/>
            <input value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath}/jsp/useradd.jsp">添加用户</a>
        </form>
    </div>
    <!--用户-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">用户编码</th>
            <th width="20%">用户名称</th>
            <th width="10%">性别</th>
            <th width="10%">年龄</th>
            <th width="10%">电话</th>
            <th width="10%">用户角色</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="user" items="${userList }" varStatus="status">
            <tr>
                <td>
                    <span>${user.userCode }</span>
                </td>
                <td>
                    <span>${user.userName }</span>
                </td>
                <td>
							<span>
								<c:if test="${user.gender==1}">男</c:if>
								<c:if test="${user.gender==2}">女</c:if>
							</span>
                </td>
                <td>
                    <span>${user.age}</span>
                </td>
                <td>
                    <span>${user.phone}</span>
                </td>
                <td>
                    <span>${user.userRoleName}</span>
                </td>
                <td>
                    <span><a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
                            src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
                            src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
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
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script>
    let userObj;

    //用户管理页面上点击删除按钮弹出删除框(userlist.jsp)
    function deleteUser(obj) {
        $.ajax({
            type: "GET",
            url: path + "/jsp/user.do",
            data: {method: "deluser", uid: obj.attr("userid")},
            dataType: "text",
            success: function (data) {
                if (data === "true") {//删除成功：移除删除行
                    cancleBtn();
                    obj.parents("tr").remove();
                } else if (data == "false") {//删除失败
                    //alert("对不起，删除用户【"+obj.attr("username")+"】失败");
                    changeDLGContent("对不起，删除用户【" + obj.attr("username") + "】失败");
                } else if (data == "notexist") {
                    //alert("对不起，用户【"+obj.attr("username")+"】不存在");
                    changeDLGContent("对不起，用户【" + obj.attr("username") + "】不存在");
                }
            },
            error: function (data) {
                //alert("对不起，删除失败");
                changeDLGContent("对不起，删除失败");
            }
        });
    }

    function openYesOrNoDLG() {
        $('.zhezhao').css('display', 'block');
        $('#removeUse').fadeIn();
    }

    function cancleBtn() {
        $('.zhezhao').css('display', 'none');
        $('#removeUse').fadeOut();
    }

    function changeDLGContent(contentStr) {
        let p = $(".removeMain").find("p");
        p.html(contentStr);
    }

    $(function () {
        //通过jquery的class选择器（数组）
        //对每个class为viewUser的元素进行动作绑定（click）
        /**
         * bind、live、delegate
         * on
         */
        $(".viewUser").on("click", function () {
            //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
            let obj = $(this);
            window.location.href = path + "/jsp/user.do?method=view&uid=" + obj.attr("userid");
        });

        $(".modifyUser").on("click", function () {
            let obj = $(this);
            window.location.href = path + "/jsp/user.do?method=modify&uid=" + obj.attr("userid");
        });

        $('#no').click(function () {
            cancleBtn();
        });

        $('#yes').click(function () {
            deleteUser(userObj);
        });

        $(".deleteUser").on("click", function () {
            userObj = $(this);
            changeDLGContent("你确定要删除用户【" + userObj.attr("username") + "】吗？");
            openYesOrNoDLG();
        });
    });
</script>
