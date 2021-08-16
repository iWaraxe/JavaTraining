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

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        super.generateResponse(server.getProductsForCategory(categoryName));
        super.handle(httpExchange);
    }
}
