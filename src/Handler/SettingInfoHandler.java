package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * Created by blogcin on 17/01/17.
 */
public class SettingInfoHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        OutputStream responseBody = httpExchange.getResponseBody();

        String info = readRequestBody(requestBody);

        if (info != null) {
            System.out.println("읽어온 정보 : " + info);
        } else {
            System.out.println("아무 정보도 읽어 올 수 없습니다.");
        }

        JSONObject responseResult = new JSONObject();

        JSONObject dataObject = new JSONObject();
        dataObject.put("advertisement_url", "/api/advertisement_page");
        dataObject.put("refresh_time", 30);
        dataObject.put("internet_timer", 10);
        dataObject.put("mac_yn", 1);
        dataObject.put("click_btn_id", "/api/router_on_off");
        dataObject.put("router_on_off_url", "/api/router_on_off");
        dataObject.put("router_timer", 1);
        dataObject.put("host_change", "127.0.0.1:8000");
        dataObject.put("setting_info_url", "/api/setting_info");
        dataObject.put("setting_timer", 1);

        responseResult.put("code", 100);
        responseResult.put("data", dataObject);

        String result = responseResult.toJSONString();

        if (result != null) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, result.getBytes().length);
            responseBody.write(result.getBytes());
            System.out.println("다음과 같은 정보가 전송되었습니다. : ");
            System.out.println(result);
        } else {
            System.out.println("정보 전송 실패");
        }

        requestBody.close();
        responseBody.close();
        httpExchange.close();
    }

    private String readRequestBody(InputStream requestBody) throws IOException {
        byte[] buffer = new byte[requestBody.available()];
        requestBody.read(buffer);

        return new String(buffer, "utf-8");
    }
}
