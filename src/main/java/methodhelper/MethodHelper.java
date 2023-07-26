package methodhelper;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MethodHelper {
	public static String getJSONData(String fileName, String jsonBody) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File("src/main/java/jsondata/" + fileName + ".json");
		return objectMapper.readTree(file).path(jsonBody).toString();
	}
}
