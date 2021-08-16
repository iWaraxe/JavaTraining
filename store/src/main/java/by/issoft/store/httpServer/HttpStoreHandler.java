package by.issoft.store.httpServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HttpStoreHandler implements HttpHandler {

    public Object ResponseObject;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(ResponseObject);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
