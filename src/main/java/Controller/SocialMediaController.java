package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getMessagesFromAccount);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }

    }

    private void loginAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account received = accountService.loginAccount(account);
        if(received!=null){
            ctx.json(mapper.writeValueAsString(received));
        }
        else{
            ctx.status(401);
        }
    }
    
    private void createMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message received = messageService.createMessage(message);
        if(received!=null){
            ctx.json(mapper.writeValueAsString(received));
        }
        else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }
    
    private void getMessageById(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if(message!=null){
            ctx.json(message);
        }
        else{
            ctx.status(200);
        }
    }

    private void deleteMessageById(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageById(id);
        if(message!=null){
            ctx.json(message);
        }
        else{
            ctx.status(200);
        }
    }

    private void updateMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message received = messageService.updateMessageById(id, message);
        if(received != null){
            ctx.json(received);
        }
        else{
            ctx.status(400);
        }
    }
    private void getMessagesFromAccount(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> message = messageService.getMessagesFromAccount(id);
        if(message!=null){
            ctx.json(message);
        }
        else{
            ctx.status(200);
        }

    }

}