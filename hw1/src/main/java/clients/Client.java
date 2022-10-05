package clients;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.*;

import parser.JsonExpression;

public interface Client {
    public enum Type {
        INDIVIDUAL {
            @Override
            public Client constructClient(HashMap<String, JsonExpression> json) {
                var inn  = (Double)json.get("inn").extract();
                var name = (String)json.get("name").extract();

                return new IndividualClient(name, inn.intValue());
            }
        },
    
        LEGAL_ENTITY {
            @Override
            public Client constructClient(HashMap<String, JsonExpression> json) {
                var inn   = (Double)json.get("inn").extract();
                var name  = (String)json.get("name").extract();
                var owner = (String)json.get("owner").extract();
                
                return new LegalEntityClient(name, inn.intValue(), owner);
            }
        },
    
        HOLDING {
            @Override
            public Client constructClient(HashMap<String, JsonExpression> json) {
                var inn  = (Double)json.get("inn").extract();
                var name = (String)json.get("name").extract();
                var parts_field = (ArrayList<JsonExpression>)json.get("holding_parts").extract();
                var parts = new ArrayList<String>();

                for (JsonExpression cur_part: parts_field) {
                    parts.add((String)cur_part.extract());
                }

                return new HoldingClient(name, inn.intValue(), parts);
            }
        };

        public abstract Client constructClient(HashMap<String, JsonExpression> json);
    }

    public static Client build(HashMap<String, JsonExpression> json) {
        var clientTypeField = json.get("clientType");
        var clientTypeName = (String)clientTypeField.extract();
        return Type.valueOf(clientTypeName).constructClient(json);
    }

    public abstract Type getType();
    public abstract void introduce();
}

//TODO: Делегирующий конструктор.
class IndividualClient implements Client {
    final String name;
    final Integer inn;

    @Override
    public Type getType() {
        return Type.INDIVIDUAL;
    }

    public IndividualClient(String name, Integer inn) {
        this.name = name;
        this.inn = inn;
    }

    @Override
    public void introduce() {
        System.out.printf("This is individual client called <<%s>>, number %d\n", name, inn);
    }
}

class LegalEntityClient implements Client {
    final String name;
    final Integer inn;
    final String owner;

    @Override
    public Type getType() {
        return Type.LEGAL_ENTITY;
    }

    public LegalEntityClient(String name, Integer inn, String owner) {
        this.name = name;
        this.inn = inn;
        this.owner = owner;
    }

    @Override
    public void introduce() {
        System.out.printf("This is Legal Entity client called <<%s>>, number %d owned by %s\n", name, inn, owner);
    }
}

class HoldingClient implements Client {
    final String name;
    final Integer inn;
    final ArrayList<String> parts;

    @Override
    public Type getType() {
        return Type.HOLDING;
    }

    public HoldingClient(String name, Integer inn, ArrayList<String> parts) {
        this.name = name;
        this.inn = inn;
        this.parts = parts;
    }

    @Override
    public void introduce() {
        System.out.printf("This is Legal Holding client called <<%s>>, number %d\n", name, inn);
        System.out.printf("Contains: \n");

        for (String part : parts ) {
            System.out.printf("\t%s\n", part);
        }
    }
}
