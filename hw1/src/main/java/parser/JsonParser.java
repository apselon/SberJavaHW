package parser;

import java.lang.*;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clients.*;

public class JsonParser {
    final Path file_path;
    final HashMap<String, JsonExpression> fields_map;

    public JsonParser(String file_name) throws Exception {
        file_path = Path.of(file_name);
        fields_map = new HashMap<String, JsonExpression>();
        parse();
    }

    public HashMap<String, JsonExpression> getJson() {
        return fields_map;
    }

    ArrayList<String> tokenize(String data) {
        /* 1. Split by comma */
        var regexp = "[^\\\\]\".*[^\\\\]\"\\s*:\\s*((\\[.*\\])|([^\\\\]\".*[^\\\\]?\")|([+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)))";
        var comma_split_pattern = Pattern.compile(regexp);
        var matcher = comma_split_pattern.matcher(data);

        /* 2. Collect and trim spaces */
        var tokens = new ArrayList<String>();

        while (matcher.find()) {
            tokens.add(matcher.group().trim());
        }

        return tokens;
    }

    public void parse() throws Exception {
        String data = Files.readString(file_path); 
        var tokens = tokenize(data);

        for (String entry: tokens) {
            var pair = entry.split(":", 2);
            var key = pair[0].trim();
            key = key.substring(1, key.length() - 1);
            fields_map.put(key, new JsonExpression(pair[1].trim()));
        }
    }
}
