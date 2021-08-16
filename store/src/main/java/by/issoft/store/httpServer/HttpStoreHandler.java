package by.issoft.store.httpServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HttpStoreHandler implements HttpHandler {

    private String response;

    protected void generateResponse(Object object) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        response = objectMapper.writeValueAsString(object);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
