package tomcat;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class SpringBootApplication {
    public static void run() {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        try {
            //contextPath：访问应用程序的url路径；docBase：应用的本地路径
            tomcat.addWebapp("/", new File("").getCanonicalPath());
            //启动tomcat
            tomcat.start();
            //设置阻塞，否则会立刻执行并且关闭tomcat
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
