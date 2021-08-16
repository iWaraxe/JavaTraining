package by.issoft.store.httpServer;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetCategoriesHandler extends HttpStoreHandler {

    StoreHttpServer server;

    public GetCategoriesHandler(StoreHttpServer server){
        this.server = server;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        super.generateResponse(server.getCategories());
        super.handle(httpExchange);
    }
}
