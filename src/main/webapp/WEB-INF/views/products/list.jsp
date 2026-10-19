<<<<<<< HEAD
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<section class="intro">
  <h1>焙煎したての豆と、毎朝使う道具。</h1>
  <p>注文の流れを一通り試せる、ECサイトの練習用ページです。</p>
</section>

<section class="tools" aria-label="絞り込みと並べ替え">
  <nav class="chips" aria-label="カテゴリ">
    <c:url var="allUrl" value="/products">
      <c:param name="q" value="${q}"/>
      <c:param name="sort" value="${sort}"/>
    </c:url>
    <a class="chip" href="<c:out value='${allUrl}'/>" aria-current="${empty category}">すべて</a>
    <c:forEach var="cat" items="${categories}">
      <c:url var="catUrl" value="/products">
        <c:param name="category" value="${cat}"/>
        <c:param name="q" value="${q}"/>
        <c:param name="sort" value="${sort}"/>
      </c:url>
      <a class="chip" href="<c:out value='${catUrl}'/>" aria-current="${cat eq category}"><c:out value="${cat}"/></a>
    </c:forEach>
  </nav>

  <form class="field" method="get" action="${ctx}/products">
    <c:if test="${not empty category}"><input type="hidden" name="category" value="<c:out value='${category}'/>"></c:if>
    <input type="search" name="q" value="<c:out value='${q}'/>" placeholder="商品名で探す" aria-label="商品名で探す">
    <select name="sort" aria-label="並べ替え" onchange="this.form.submit()">
      <option value="def" ${sort eq 'def' ? 'selected' : ''}>おすすめ順</option>
      <option value="low" ${sort eq 'low' ? 'selected' : ''}>価格が安い順</option>
      <option value="high" ${sort eq 'high' ? 'selected' : ''}>価格が高い順</option>
    </select>
    <button class="btn" type="submit">検索</button>
  </form>
</section>

<c:choose>
  <c:when test="${not empty products}">
    <section class="grid">
      <c:forEach var="p" items="${products}">
        <article class="card">
          <a class="tile t${p.tone}" href="${ctx}/product?id=${p.id}" aria-label="<c:out value='${p.name}'/>の詳細"><t:icon kind="${p.kind}"/></a>
          <div class="info">
            <span class="cat"><c:out value="${p.category}"/></span>
            <a class="name" href="${ctx}/product?id=${p.id}"><c:out value="${p.name}"/></a>
            <span class="spec"><c:out value="${p.spec}"/></span>
            <div class="buy">
              <span class="price"><t:yen value="${p.price}"/><small>税込</small></span>
              <form method="post" action="${ctx}/cart">
                <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="${p.id}">
                <button class="btn primary" type="submit">カートに入れる</button>
              </form>
            </div>
          </div>
        </article>
      </c:forEach>
    </section>
  </c:when>
  <c:otherwise>
    <p class="empty">条件に合う商品がありません。絞り込みを変えてみてください。</p>
  </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
=======
<!-- 商品一覧 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
</head>
 <!--　ヘッダー　-->
   <body id="top">
        <header class="header">
                <ul>
                    <li><a href="show.jsp">カート</a></li>
                </ul>
        </header>
        <!--ヘッダーここまで-->
    </body>
<main>

<div class="items">
                <div class="item">
                    <img src="" alt="">
                    <p>
                        <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="add" value="abb">
                        <input type="hidden" name="name" value="${Product,name}">
                        <input type="hidden" name="price" value="${Product,price}">
                        <input type="hidden" name="id" value="${Product,id}"
                        <input type="submit" value="カートに追加">カートに追加する<br>
                        </form>
                    </p>
                </div>
                
                <div class="item">
                    <img src="" alt="">
                    <p>
                        <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="add" value="abb">
                        <input type="hidden" name="name" value="${Product,name}">
                        <input type="hidden" name="price" value="${Product,price}">
                        <input type="hidden" name="id" value="${Product,id}"
                        <input type="submit" value="カートに追加">カートに追加する<br>
                        </form>
                    </p>
                </div>
                
                <div class="item">
                    <img src="" alt="">
                    <p>
                        <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="name" value="${Product,name}">
                        <input type="hidden" name="price" value="${Product,price}">
                        <input type="hidden" name="id" value="${Product,id}"
                        <input type="submit" value="カートに追加">カートに追加する<br>
                        </form>
                    </p>
                </div>
                
                <div class="item">
                    <img src="" alt="">
                    <p>
                        <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="name" value="${Product,name}">
                        <input type="hidden" name="price" value="${Product,price}">
                        <input type="hidden" name="id" value="${Product,id}"
                        <input type="submit" value="カートに追加">カートに追加する<br>
                        </form>
                    </p>
                </div>
                
                <div class="item">
                    <img src="" alt="">
                    <p>
                        <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="add" value="abb">
                        <input type="hidden" name="name" value="${Product,name}">
                        <input type="hidden" name="price" value="${Product,price}">
                        <input type="hidden" name="id" value="${Product,id}"
                        <input type="submit" value="カートに追加">カートに追加する<br>
                        </form>
                    </p>
                </div>
                
                <div class="item">
                    <img src="" alt="">
                    <p>
                        <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="add" value="abb">
                        <input type="hidden" name="name" value="${Product,name}">
                        <input type="hidden" name="price" value="${Product,price}">
                        <input type="hidden" name="id" value="${Product,id}"
                        <input type="submit" value="カートに追加">カートに追加する<br>
                        </form>
                    </p>
                </div>
                
                <div class="item">
                    <img src="" alt="">
                    <p>
                       <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="add" value="abb">
                        <input type="hidden" name="name" value="${Product,name}">
                        <input type="hidden" name="price" value="${Product,price}">
                        <input type="hidden" name="id" value="${Product,id}"
                        <input type="submit" value="カートに追加">カートに追加する<br>
                        </form>
                    </p>
                </div>
                
                <div class="item">
                    <img src="" alt="">
                    <p>
                        <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="add" value="abb">
                        <input type="hidden" name="name" value="${Product,name}">
                        <input type="hidden" name="price" value="${Product,price}">
                        <input type="hidden" name="id" value="${Product,id}"
                        <input type="submit" value="カートに追加">カートに追加する<br>
                        </form>
                    </p>
                </div>
</div>

</main>

        <footer>
            <div class="gotop">
                <a href="#top"><img src="" alt="ページトップへ戻る"></a>
            </div>
            <p class="">&copy; </p>
        
        </footer>
</body>

</html>
>>>>>>> branch 'master' of https://github.com/regulus777gc/Shop.git
