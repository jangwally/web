<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 公用的分页按钮   ，需要每个使用的方法都要返回  pageList      共${pageList.totalPage}页， -->
<div class="panelBar">
		<div class="pages" style="width: 250px;height:25px">
			<span style="height:25px;display:inline-block">
			<!-- 当前第${pageList.currentPage}页,显示第 ${pageList.currentPage==1 ? 1 : ((pageList.currentPage-1)*pageList.numPerPage+1)}至 ${pageList.currentPage*pageList.numPerPage < pageList.totalCount ? (pageList.currentPage*pageList.numPerPage) : pageList.totalCount}项结果,共 ${pageList.totalCount }条数据, -->
			<p style="float:left;height:25px;line-height:21px"><c:if test="${pageList.totalCount!=0 }">
			      共 ${pageList.totalPage}页
			</c:if>
			每页显示条数</p><select  style="width: 60px;float:left;height: 21px"  name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10" ${pageList.numPerPage==10?'selected':''}>10</option>
				<option value="20" ${pageList.numPerPage==20?'selected':''}>20</option>
				<option value="50" ${pageList.numPerPage==50?'selected':''}>50</option>
				<option value="300" ${pageList.numPerPage==300?'selected':''}>300</option>
				<option value="500" ${pageList.numPerPage==500?'selected':''}>500</option>
			</select>
			共${pageList.totalCount }条数据
			</span>   
		</div>
		<div class="pagination"  targetType="navTab" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage }" pageNumShown="10" currentPage="${pageList.currentPage}"></div>

	</div>
