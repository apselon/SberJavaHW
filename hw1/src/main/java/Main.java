import parser.JsonParser;
import clients.Client;

class Main {
    public static void main(String args[]) throws Exception {
        if ( args.length < 1 ) {
            System.out.println("Please provide json file name as command line argument");
            return;
        }

        var parser = new JsonParser(args[0]);
        var client = Client.build(parser.getJson());
        client.introduce();
    }
}
