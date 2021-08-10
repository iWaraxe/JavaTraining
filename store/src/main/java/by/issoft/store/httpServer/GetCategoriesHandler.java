package by.issoft.store.httpServer;

import by.issoft.domain.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GetCategoriesHandler implements HttpHandler {

    StoreHttpServer server;

    public GetCategoriesHandler(StoreHttpServer server){
        this.server = server;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String response = "[";

        List<Category> allCategories = server.getCategories();
        for (Category c: allCategories) {
            response += objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(c)+",";
        }
        String substring = response.substring(0, response. length() - 1);
        response =substring+  ']';

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
