package footballdata.environmentconfig;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public class TestConfig {

    @BeforeClass
    public static void setup() {
        // Port is not in use for the Football data API, but might be needed later for my own API
        // RestAssured.port = 8080;
        RestAssured.basePath = "/v1/";
        RestAssured.baseURI = "http://api.football-data.org";

        // todo - refactor this into a method that gets called optionally - see the Tracks code for examples
        RestAssured.proxy("localhost", 8888);


        // todo - refactor this later, probabily into its own section, and parameterise the token
        RequestSpecification requestSpecification = new RequestSpecBuilder().
                addHeader("X-Auth-Token", "fae8b099875d41f395c58dbb7f35556b").
                addHeader("X-Response-Control", "minified")
                .build();

        RestAssured.requestSpecification = requestSpecification;
    }
}
