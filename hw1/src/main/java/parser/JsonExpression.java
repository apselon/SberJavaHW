package parser;

import java.lang.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonExpression {
    final Type expr_type;
    final Object expr_obj;

    public JsonExpression(String expr) {
        expr_type = Type.classify(expr);
        expr_obj = expr_type.deserialize(expr);
    }

    public Type getType() {
        return expr_type;
    }

    public Object extract() {
        return expr_obj;
    }

    public enum Type {
        STRING {
            public Object deserialize(String expr) {
                return expr.substring(1, expr.length() - 1);
            }
        },

        ARRAY {
            public Object deserialize(String expr) {
                var regexp = "(?<=(\\[|,))\\s*((\\d+)|(\\\"[^(\\\")]*\\\")|(\\[[^\\[\\]]*\\]))\\s*(?=(\\]|,))";

                var comma_split_pattern = Pattern.compile(regexp);
                var matcher = comma_split_pattern.matcher(expr);
        
                var tokens = new ArrayList<JsonExpression>();
        
                while (matcher.find()) {
                    tokens.add(new JsonExpression(matcher.group().trim()));
                }

                return tokens;
            }
        },

        NUMBER {
            public Object deserialize(String expr) {
                return Double.parseDouble(expr);
            }
        };

        public abstract Object deserialize(String expr);

        //TODO: classify for number and exception on wrong type
        public static Type classify(String expr) {
            if (expr.startsWith("\"") || expr.startsWith("\\\"")) {
                return STRING;
            }

            if (expr.startsWith("["))
            {
                return ARRAY;
            }

            return NUMBER;
        }
    }
}
