package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account account) {
        if(account.getUsername().equals("") || account.getPassword().length() < 4){
            return null;
        }
        return this.accountDAO.registerAccount(account);
    }
    
}
