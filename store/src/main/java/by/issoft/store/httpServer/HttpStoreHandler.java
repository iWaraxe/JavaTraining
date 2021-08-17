package by.issoft.store.httpServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HttpStoreHandler implements HttpHandler {

    protected void handle(HttpExchange httpExchange, Object responseObject) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(responseObject);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Not implemented here, use method handle(HttpExchange httpExchange, Object object)");
    }
}
