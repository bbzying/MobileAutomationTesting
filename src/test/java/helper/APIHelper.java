package helper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

public class APIHelper extends BaseObject{
    private static final Logger logger = LogManager.getLogger(APIHelper.class);
    public static RequestSpecification request;
    public APIHelper(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(props.getProperty("baseURI"));
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Authorization", "Bearer b072e38252a11dd6e84b0a1f9d654242ed9d7330638fd0aed34f6b1df0f8dd93");
        var requestSpec = builder.build();
        request = RestAssured.given().spec(requestSpec);

    }
    public static Response get(String url) {
        try {
            Response response = request.get(new URI(url));
            logger.info("API GET response - " + response.jsonPath().prettify());
            addResponseToTestVariables(response.jsonPath().prettify());
            response.statusCode();
            return response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response post(String url, String payload){
        try {
            request.body(jsonToHashmap(payload));
            Response response = request.post(url);
            logger.info("API POST response - " + response.jsonPath().prettify());
            addResponseToTestVariables(response.jsonPath().prettify());
            return response;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Response delete(String url){
        try {
            Response response = request.delete(url);
            logger.info("API DELETE response - " + response.asPrettyString());
            return response;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Response put(String url, String payload){
        try {
            request.body(jsonToHashmap(payload));
            Response response = request.put(url);
            logger.info("API PUT response - " + response.jsonPath().prettify());
            addResponseToTestVariables(response.jsonPath().prettify());
            return response;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
