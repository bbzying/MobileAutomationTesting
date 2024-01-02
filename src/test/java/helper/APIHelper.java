package helper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class APIHelper extends BaseObject{
    private static final Logger logger = LogManager.getLogger(APIHelper.class);
    private RequestSpecBuilder builder = new RequestSpecBuilder();
    private String url;
    private String method;
    private ResponseOptions<Response> response;
    private RequestSpecification request;

    /**
     * Initial API Helper with uri / method / content type / token
     * @param uri uri needs to be requested
     * @param method constant GET / POST / DELETE / PUT
     */

    public APIHelper(String uri, String method){
        this.url = props.getProperty("baseURI") + uri;
        this.method = method;
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Authorization", "Bearer b072e38252a11dd6e84b0a1f9d654242ed9d7330638fd0aed34f6b1df0f8dd93");
        RequestSpecification requestSpec = builder.build();
        request = RestAssured.given();
        request.spec(requestSpec);
    }

    /**
     * Generic executeAPI for methods GET / POST / DELETE / PUT
     * @return response
     */

    public ResponseOptions<Response> executeAPI(){
        if (this.method.equalsIgnoreCase(APIConstant.ApiMethods.GET)){
            response = request.get(this.url);
        } else if (this.method.equalsIgnoreCase(APIConstant.ApiMethods.POST)){
            response = request.post(this.url);
        }else if (this.method.equalsIgnoreCase(APIConstant.ApiMethods.DELETE)){
            response = request.delete(this.url);
        }else if (this.method.equalsIgnoreCase(APIConstant.ApiMethods.PUT)){
            response = request.put(this.url);
        }
        try {
            logger.info("API " + this.method + " response - " + response.getBody().jsonPath().prettify());
            addResponseToTestVariables(response.getBody().jsonPath().prettify());
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Set the body for requesting
     * @param body json format string
     * @return response
     */
    public ResponseOptions<Response> executeWithBody(String body){
        request.body(jsonToHashmap(body));
        return executeAPI();
    }
}
