package demo.task1.dao.jdbc;

import demo.task1.dao.AccountDao;
import demo.task1.dao.AccountOperationDao;
import demo.task1.exception.DataAccessException;
import demo.task1.model.Account;
import demo.task1.model.AccountOperation;
import demo.task1.util.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountOperationDaoJdbcImpl implements AccountOperationDao {
    AccountDao accountDao = new AccountDaoJdbcImpl();

    @Override
    public void deposit(AccountOperation accountOperation) {
        final String SQL = "insert into account_operation values (DEFAULT,?,?,?,?,?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, accountOperation.getSource().getId());
            statement.setObject(2, accountOperation.getDestination().getId());
            statement.setBigDecimal(3, accountOperation.getAmount());
            statement.setString(4, accountOperation.getType().toString());
            statement.setBoolean(5, accountOperation.isOperationStatus());
            Optional<Account> acc = accountDao.findById(accountOperation.getDestination().getId());
            if(acc.isPresent()){
                Account account = acc.get();
                account.setAmount(account.getAmount().add(accountOperation.getAmount()));
                accountDao.update(account);
                accountOperation.setOperationStatus(true);
                statement.setBoolean(5, accountOperation.isOperationStatus());
            }
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    accountOperation.setId(resultSet.getLong(1));
                }
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void withdraw(AccountOperation accountOperation) {

    }

    @Override
    public void transfer(AccountOperation accountOperation) {

    }

    @Override
    public BigDecimal getBalance(AccountOperation accountOperation) {
        return null;
    }
}
