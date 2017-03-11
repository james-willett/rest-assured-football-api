package footballdata;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ParametersExamples {

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

    @Test
    public void getTeamPlayers_UsingPathParams() {

        // GET http://api.football-data.org/v1/teams/66/players

        given().
                pathParam("teamId", 66).
        when().
                get("teams/{teamId}/players").
        then().
                statusCode(200);
    }

    @Test
    public void getTeamPlayers_UsingVariables() {

        // GET http://api.football-data.org/v1/teams/66/players

        String svc = "teams";
        int id = 66;

        given().
        when().
              get("{service}/{teamId}/players", svc, id).
        then().
              statusCode(200);
    }

    @Test
    public void getTeamPlayers_UsingMixOfNamedAndUnnamedPathParameters() {

        // GET http://api.football-data.org/v1/teams/66/players

        given().
                pathParam("service", "teams").
        when().
            get("{service}/{teamId}/players", 66).
                then().statusCode(200);
    }

    @Test
    public void getAllCompetitionsForSeason_UsingQueryParameters() {

        // GET http://api.football-data.org/v1/competitions/?season=2015

        given().
                queryParam("season", 2015).
        when().
                get("competitions/").
        then().
                statusCode(200);
    }

    // here we don't specify the parameter type (query/form) - it defaults to QUERY because its a GET request
    @Test
    public void getAllCompeitionsForSeason_UsingUnspecifiedParameters() {

        // GET http://api.football-data.org/v1/competitions/?season=2015

        given().
                param("season", 2015).
        when().
                get("competitions/").
        then().
                statusCode(200);
    }

    // todo - need an example with form parameters - it needs to be a POST so would have to be in my own API
    // todo - cover Multipart Form Mapping - https://github.com/rest-assured/rest-assured/wiki/Usage#multi-part-form-data
}
