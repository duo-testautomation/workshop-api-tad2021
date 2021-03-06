import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class Escapi {


    //check the README for useful information about Rest Assured

    @Test
    public void ecapeTheApi() {

    }


    @Test
    public void example(){
        // sets a baseuri for every request in this run
        baseURI = "https://randomuser.me";
        Response exampleRequest =
                given().
                        // logs the request in the console
                        log().all().
                        // adds a queryparam to the request
                        queryParam("color", "red").
                when().
                        // combines the baseURI with /api and makes a GET request
                        get("/api").
                then().
                        // logs the response in the console
                        log().all().
                        // validates that that the statuscode from the respons is 200
                        statusCode(200).
                        // checks if the body contains a gender node with value female
                        body("results[0].color", equalTo("red")).
                extract()
                        // this will save the response in the exampleRequest variable
                        .response();
        // extract a single value from the response
        String lastName = exampleRequest.jsonPath().get("results[0].name.last");
        // prints output to the console
        System.out.println("The lastname found in the extracted response = " + lastName);
    }


}
