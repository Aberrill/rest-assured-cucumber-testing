package methodhelper;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import pojo.*;


public class MethodHelper {
	private static final Logger LOGGER = Logger.getLogger(MethodHelper.class.getName());

	public static String getJSONData(String fileName, String jsonBody) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File("src/main/java/jsondata/" + fileName + ".json");
		return objectMapper.readTree(file).path(jsonBody).toString();
	}

	public static Response chooseRequestTypeWithAuthAndBookingID(Response response, String APICall, String JSONData,
			Boolean bookingPresent, String token) {

		RequestSpecification request = RestAssured.given();
		String bookingId = "";
		if (response != null && Boolean.TRUE.equals((bookingPresent))) {
			JsonPath responseJSON = new JsonPath(response.asString());
			bookingId = "/" + responseJSON.getString("bookingid");
		}
		switch (APICall) {
		case "post":
			response = request.body(JSONData).contentType(ContentType.JSON).post();
			break;
		case "put":
			response = request.body(JSONData).header("Cookie", String.format("token=%s", token))
					.contentType(ContentType.JSON).put(bookingId);
			break;
		case "patch":
			response = request.body(JSONData).header("Cookie", String.format("token=%s", token))
					.contentType(ContentType.JSON).patch(bookingId);
			break;
		case "delete":
			response = request.delete(bookingId);
			break;
		default:
			String logMessage = "API call not recognised";
			LOGGER.log(Level.SEVERE, logMessage);
		}
		return response;
	}
	
	public static BookingRequest setPostBookingRequestWithDefaultValues() {
		BookingRequest book = new BookingRequest();
		
		BookingDates bookingDates = setPostBookingDatesWithDefaultValues();
		
		book.setFirstname("POJO");
		book.setLastname("Test");
		book.setTotalprice(123);
		book.setDepositpaid(true);
		book.setBookingdates(bookingDates);
		book.setAdditionalneeds("Optional");
		return book;
	}
	
	public static BookingDates setPostBookingDatesWithDefaultValues() {
		BookingDates dates = new BookingDates();
		
		dates.setCheckin("2024-06-06");
		dates.setCheckout("2024-06-10");
		
		return dates;
	}
	
	public static BookingResponse getPostBookingRequestWithDefaultValues() {
		BookingResponse book = new BookingResponse();		
		book.getBooking();
		return book;
	}
	
	public static void sampleRestAssured() {
		RestAssured.baseURI= "https://localhost:8080";
		
		String responseString = RestAssured.given().param("username", "admin")
							.param("password", "password123").when().post("/admin")
							.then().assertThat().statusCode(200).extract().response().asString();
		
		Assert.assertEquals("Login Successful", responseString);
	}
}