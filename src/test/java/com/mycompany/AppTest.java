package com.mycompany;

import org.junit.Test;

/**
 * @author Isaac Coimbra
 */
public class AppTest extends BaseTest {

    static String JSON_TO_INSERT = "{\"name\": \"Isaac\", \"number\": \"82 988538465\"}";
    static String JSON_EXPECTED
        = "{\"id\":1,\"name\":\"Isaac\",\"number\":\"82 988538465\"}";

    @Test
    public void retrieveContact() throws Exception {
        server.post("/contact").body(JSON_TO_INSERT, "application/json");
        server.get("/contact/1").expect(JSON_EXPECTED).expect(200);
    }

    @Test
    public void insertcontact() throws Exception {
        server.post("/contact")
            .body(JSON_TO_INSERT, "application/json")
            .expect(JSON_EXPECTED)
            .expect(200);
    }

}
