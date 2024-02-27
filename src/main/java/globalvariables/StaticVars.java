package globalvariables;

public class StaticVars {
	private static String url = "https://restful-booker.herokuapp.com";
	private static String bookingUrl = "https://restful-booker.herokuapp.com/booking/";

	public static String getUrl() {
		return url;
	}
	public static String getBookingUrl() {
		return bookingUrl;
	}
	public static void setUrl(String url) {
		StaticVars.url = url;
	}
	public static void setBookingUrl(String bookingUrl) {
		StaticVars.bookingUrl = bookingUrl;
	}
}
