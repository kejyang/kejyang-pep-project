package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account account) {
        if(account.getUsername().equals("") || account.getPassword().length() < 4){
            return null;
        }
        return this.accountDAO.registerAccount(account);
    }

    public Account loginAccount(Account account){
        return this.accountDAO.loginAccount(account);
    }

    public Account getAccountById(int id){
        return this.accountDAO.getAccountById(id);
    }
    
}
