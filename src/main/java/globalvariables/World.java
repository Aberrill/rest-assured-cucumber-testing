package globalvariables;

import io.restassured.response.Response;

public class World {
	private Response response;
	private Response givenResponse;

	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public Response getCookie() {
		return givenResponse;
	}
	public void setCookie(Response givenResponse) {
		this.givenResponse = givenResponse;
	}
}
