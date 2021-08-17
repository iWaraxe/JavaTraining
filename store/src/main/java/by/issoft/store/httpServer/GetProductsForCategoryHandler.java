package by.issoft.store.httpServer;

import by.issoft.domain.categories.CategoryEnum;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetProductsForCategoryHandler extends HttpStoreHandler {

    CategoryEnum categoryName;
    StoreHttpServer server;

    public GetProductsForCategoryHandler(CategoryEnum category, StoreHttpServer server){

        categoryName = category;
        this.server = server;
    }

    public void handle(HttpExchange httpExchange) throws IOException {

        super.handle(httpExchange, server.getProductsForCategory(categoryName));
    }
}
