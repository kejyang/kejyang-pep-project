package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;

import java.util.List;

import DAO.AccountDAO;

public class MessageService {

    private MessageDAO messageDAO;
    private AccountDAO accountDAO;


    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public Message createMessage(Message message) {
        if(message.getMessage_text().equals("") || message.getMessage_text().length() > 255 
            || this.accountDAO.getAccountById(message.getPosted_by()) == null){

            return null;
        }
        return this.messageDAO.createMessage(message);
    }
    public List<Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return this.messageDAO.getMessageById(id);
    }
    
    public Message deleteMessageById(int id){
        return this.messageDAO.deleteMessageById(id);
    }
    
}
