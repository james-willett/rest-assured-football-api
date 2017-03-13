package footballdata;

import footballdata.environmentconfig.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class ExtractResponseData extends TestConfig {

    @Test
    public void getAllPremierLeagueTeams_getResponseBodyAsString() {
        String responseBody = get("competitions/426/teams").asString();
        System.out.println(responseBody);
    }

    @Test
    public void getAllPremierLeagueTeams_getResponseAsObject_getHeaders() {
        Response response = get("competitions/426/teams");

        // TODO - refactor this elsewhere, somewhere dedicated to cookies, and set some at the start
        // From that response, you can get all the cookies and put them in a map
        Map<String, String> cookies = response.getCookies();

        // Or just get a single cookie, if you know it's name
        String myCookie = response.getCookie("cookieName");


        // You can get all the headers and put them in a Headers object
        Headers headers = response.getHeaders();

        // Or you can get an individual header
        String contentType = response.getHeader("Content-Type");
        System.out.println(contentType);
    }

    @Test
    public void getAllPremierLeagueTeams_getStatusLineAndStatusCode() {
        Response response = get("competitions/426/teams");

        // Get the status line of the response
        assertThat(response.statusLine(), equalTo("HTTP/1.1 200 OK"));
        assertThat(response.getStatusLine(), equalTo("HTTP/1.1 200 OK"));

        // Or just get the status code
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.getStatusCode(), equalTo(200));
    }

    // This will check the content type first, then extract the response
    @Test
    public void getAllPremierLeagueTeams_checkContentTypeThenExtractResponse() {
        Response response =
                when().
                        get("competitions/426/teams").
                then().
                        contentType(ContentType.JSON).
                        extract().response();

        // convert the Response to a string
        String jsonResponseAsString = response.asString();
    }

    @Test
    public void getPremierLeagueDetails_useJsonPathToGetSingleElement() {
        String caption = get("competitions/426").jsonPath().getString("caption");
        assertThat(caption, equalTo("Premier League 2016/17"));
    }

    // Todo need examples here for XML, will need by API for that
    // Todo need more complex examples, deeper in the path, will need GPath

    @Test
    public void getPremierLeagueTable_useJsonPathToGetMultipleElements() {
        Response response = get("competitions/426/leagueTable");

        // Defaults to JSONpath here because of the response, even though we only specify "path"
        List<String> teamNames = response.path("standing.team");
        assertThat(teamNames.size(), equalTo(20));

        // Print out all the teams
        for (String teamName : teamNames) {
            System.out.println(teamName);
        }
    }
}
