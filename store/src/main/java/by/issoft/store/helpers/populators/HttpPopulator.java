package by.issoft.store.helpers.populators;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import by.issoft.store.httpServer.StoreHttpServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.List;

public class HttpPopulator implements IHttpPopulator {

    public static final String DEFAULT_USERNAME = "Username";
    public static final String DEFAULT_PASSWORD = "Password";

    StoreHttpServer server;
    HttpClient client;
    ObjectMapper objectMapper = new ObjectMapper();

    public HttpPopulator() {
        server = new StoreHttpServer();
        createClient();
    }

    @Override
    public List<Category> getCategories() {

        try {

            HttpGet request = new HttpGet(server.GET_CATEGORIES_URL);
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            List<Category> list = objectMapper.readValue(result, new TypeReference<List<Category>>() {});

            return list;

        } catch (IOException e) {
            System.out.println("Error when getting all categories via HTTP : " + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<Product> getProductsForCategory(CategoryEnum category) {

        try {
            HttpGet request = new HttpGet(String.format(server.GET_PRODUCTS_FOR_CATEGORY_URL + "/%s", category.name()));
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            List<Product> list = objectMapper.readValue(result, new TypeReference<List<Product>>() {});

            return list;

        } catch (IOException e) {
            System.out.println(String.format("Error when getting products for category '%S' via HTTP : ", category.name()) + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void addToCart(String productName) {

        HttpPost httppost = new HttpPost(String.format(server.CART_URL));

        try {
            httppost.setEntity(new StringEntity(objectMapper.writeValueAsString(server.addProductToCart(productName))));

        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            System.out.println("Error when adding product to cart via HTTP : " + e.getLocalizedMessage());
        }
    }

    @Override
    public List<Product> getProductsInCart() {

        try {
            HttpGet request = new HttpGet(String.format(server.CART_URL));
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            List<Product> list = objectMapper.readValue(result, new TypeReference<List<Product>>() {
            });

            return list;

        } catch (IOException e) {
            System.out.println("Error when getting all products from the cart via HTTP : " + e.getLocalizedMessage());
        }

        return null;
    }

    private void createClient(){
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        provider.setCredentials(AuthScope.ANY, credentials);

        client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
    }
}

