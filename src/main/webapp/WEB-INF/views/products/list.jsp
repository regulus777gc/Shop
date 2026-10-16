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
   <body id="top"
        <header class="header">
                <ul>
                    <li><a href="show.jsp">カート</a></li>
                </ul>
            </nav>
        </header>
        <!--ヘッダーここまで-->
>
<main>
<div class="items">
                <div class="item">
                    <img src="" alt="">
                    <p>
                        <form action="CartServlet" method="post">
                        <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                        <input type="hidden" name="add" value="abb">
                        <input type="hidden" name="name" value="${Product,price}">
                        <input type="hidden" name="price" value="${Product,price}">
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
                        <input type="hidden" name="name" value="${Product,price}">
                        <input type="hidden" name="price" value="${Product,price}">
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
                        <input type="hidden" name="name" value="${Product,price}">
                        <input type="hidden" name="price" value="${Product,price}">
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
                        <input type="hidden" name="name" value="${Product,price}">
                        <input type="hidden" name="price" value="${Product,price}">
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
                        <input type="hidden" name="name" value="${Product,price}">
                        <input type="hidden" name="price" value="${Product,price}">
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
                        <input type="hidden" name="name" value="${Product,price}">
                        <input type="hidden" name="price" value="${Product,price}">
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
                        <input type="hidden" name="name" value="${Product,price}">
                        <input type="hidden" name="price" value="${Product,price}">
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
                        <input type="hidden" name="name" value="${Product,price}">
                        <input type="hidden" name="price" value="${Product,price}">
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