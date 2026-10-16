<!-- /products へ転送するだけ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
</head>
<body>
<header>
  <h1>MORI MARKET</h1>
  <nav>
    <a href="<c:url value='/products'/>">商品一覧</a> |
    <a href="<c:url value='/cart'/>">カート</a>
  </nav>
</header>

<main>
  <section>
    <h2>商品を探す</h2>
    <form action="<c:url value='/products'/>" method="get">
      <input type="text" name="q" placeholder="キーワード">
      <button type="submit">検索</button>
    </form>
  </section>

  <section>
    <h2>カテゴリから探す</h2>
    <ul>
      <li><a href="<c:url value='/products'><c:param name='category' value='豆'/></c:url>">豆</a></li>
      <li><a href="<c:url value='/products'><c:param name='category' value='器具'/></c:url>">器具</a></li>
      <li><a href="<c:url value='/products'><c:param name='category' value='カップ'/></c:url>">カップ</a></li>
      <li><a href="<c:url value='/products'><c:param name='category' value='消耗品'/></c:url>">消耗品</a></li>
    </ul>
  </section>

  <section>
    <h2>並び替えて見る</h2>
    <ul>
      <li><a href="<c:url value='/products'><c:param name='sort' value='price_asc'/></c:url>">価格の安い順</a></li>
      <li><a href="<c:url value='/products'><c:param name='sort' value='price_desc'/></c:url>">価格の高い順</a></li>
    </ul>
  </section>
</main>

<footer>
  <p>&copy; MORI MARKET</p>
</footer>
</body>
</html>