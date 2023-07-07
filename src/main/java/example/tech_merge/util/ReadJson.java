package example.tech_merge.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Component
public class ReadJson {
    public static JSONObject JsonToString(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Reader reader = new FileReader(path);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        return jsonObject;
    }

}
