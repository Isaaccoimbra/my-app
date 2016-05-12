package com.mycompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.jooby.Jooby;
import org.jooby.Results;
import org.jooby.json.Jackson;

/**
 * @author Isaac Coimbra
 */
public class App extends Jooby {

    private ArrayList<Contact> contacts = new ArrayList<>();
    private int uId = 1;

    {
        use(new Jackson());
        use(TestRoute.class);

        get("", req -> {
            return "Agenda Isaac";
        });

        /**
         * Método utilizado para obter um contacto por id
         *
         * @param
         *
         * @return
         */
        get("/contact/:id", req -> {
            int id = Integer.parseInt(req.param("id").value()) - 1;

            int statusCode = 404;
            String message = "Contato não encontrado!";

            try {
                Contact contact = this.contacts.get(id);

                ObjectMapper mapper = new ObjectMapper();
                message = mapper.writeValueAsString(contact);
                statusCode = 200;

                return Results.with(message).status(statusCode).type("text/plain");
            } catch (IndexOutOfBoundsException e) {
                return Results.with(message).status(statusCode).type("text/plain");
            }
        });

        /**
         * Método usado para inserir um contacto
         *
         * @return
         */
        post("/contact", req -> {
            ObjectMapper mapper = new ObjectMapper();

            String jsonInString = req.body().value();
            Contact contact = mapper.readValue(jsonInString, Contact.class);

            int statusCode = 400;
            String message;

            if (contact.getName().equals("")) {
                message = "Todos os campos são obrigatórios.";
            } else if (contact.getNumber().equals("")) {
                message = "Todos os campos são obrigatórios.";
            } else {
                statusCode = 200;
                contact.setId(this.uId);
                this.uId++;
                this.contacts.add(contact);
                message = mapper.writeValueAsString(contact);
            }

            return Results.with(message).status(statusCode).type("text/plain");
        });
    }

    public static void main(final String[] args) throws Throwable {
        run(App::new, args);
    }

}
