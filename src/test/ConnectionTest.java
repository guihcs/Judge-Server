package test;

import network.NetworkAdapter;
import network.NetworkMessage;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionTest {

    @Test
    public void test(){
        NetworkAdapter adapter = NetworkAdapter.getInstance();

        AtomicReference<NetworkMessage> message = new AtomicReference<>();

        adapter.onData( d -> {
            message.set(d);
        });

        while (message.get() == null) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals(message.get().getData(), "tiambobo");
        String address = message.get().getAddress();
        message.set(null);
        adapter.send(address, "import 'package:judge/network/network_provider.dart';");


        while (message.get() == null) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals("{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}", message.get().getData());
    }
}
