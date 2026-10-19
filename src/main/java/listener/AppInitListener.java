package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import dao.Database;

/** アプリ起動時に1回だけ、データベースを準備する。 */
@WebListener
public class AppInitListener implements ServletContextListener {

    // ~ はH2が「ユーザーのホームフォルダ」に読み替える
    private static final String DEFAULT_URL = "jdbc:h2:tcp://10.23.104.249/~/User";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String url = sce.getServletContext().getInitParameter("db.url");
        if (url == null || url.trim().isEmpty()) {
            url = DEFAULT_URL;
        }
        Database.init(url);
    }
}
