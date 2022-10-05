import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import clients.*;
import parser.*;

public class Tester {
    @Test
    public void testIndividual() throws Exception {
        var parser = new JsonParser("./app/src/test/java/resources/testIndividual.json");
        var client = Client.build(parser.getJson());

        Assertions.assertEquals(client.getType(), Client.Type.INDIVIDUAL);
    }

    @Test
    public void testLegalEntity() throws Exception {
        var parser = new JsonParser("./app/src/test/java/resources/testIndividual.json");
        var client = Client.build(parser.getJson());
        Assertions.assertEquals(client.getType(), Client.Type.LEGAL_ENTITY);
    }

    @Test
    public void testHolding() throws Exception {
        var parser = new JsonParser("./app/src/test/java/resources/testIndividual.json");
        var client = Client.build(parser.getJson());
        Assertions.assertEquals(client.getType(), Client.Type.HOLDING);
    }
}
