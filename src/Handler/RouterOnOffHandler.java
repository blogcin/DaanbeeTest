package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by blogcin on 17/01/17.
 */
public class RouterOnOffHandler implements HttpHandler {

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
        dataObject.put("setting_yn", 1);

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
