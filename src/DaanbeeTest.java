import Handler.RouterOnOffHandler;
import Handler.SettingInfoHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by blogcin on 17/01/17.
 */
public class DaanbeeTest {
    private HttpServer httpServer = null;
    private final int PORT = 8000;

    private final String API_LOCATION = "/api/";
    private final String SETTING_INFO = "setting_info";
    private final String ROUTER_ON_OFF = "router_on_off";
    public DaanbeeTest() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        } catch (IOException e) {
            System.out.println("HTTP 서버 생성 실패");
            System.exit(1);
        }

        httpServer.createContext(API_LOCATION + SETTING_INFO, new SettingInfoHandler());
        httpServer.createContext(API_LOCATION + ROUTER_ON_OFF, new RouterOnOffHandler());

        httpServer.setExecutor(null);
    }

    public void start() {
        if (httpServer != null) {
            httpServer.start();
        } else {
            System.out.println("HTTP 시작에 실패하였습니다.");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        DaanbeeTest daanbeeTest = new DaanbeeTest();
        daanbeeTest.start();
    }
}
