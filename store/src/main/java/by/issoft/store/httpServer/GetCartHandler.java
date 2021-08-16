package by.issoft.store.httpServer;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetCartHandler extends HttpStoreHandler {

    StoreHttpServer server;

    public GetCartHandler(StoreHttpServer server){
        this.server = server;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        super.ResponseObject = server.getProductsInCart();
        super.handle(httpExchange);
    }
}
