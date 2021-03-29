package demo.task1.dao.jdbc;

import demo.task1.dao.AccountDao;
import demo.task1.exception.DataAccessException;
import demo.task1.model.Account;
import demo.task1.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoJdbcImpl implements AccountDao {
    @Override
    public void save(Account account) {
        final String SQL = "insert into account values (DEFAULT,?,?,?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, account.getAddress());
            statement.setBigDecimal(3, account.getAmount());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    account.setId(resultSet.getLong(1));
                }
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void update(Account account) {
        final String SQL = "update account set login = ?, address = ?, amount = ? where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, account.getAddress());
            statement.setBigDecimal(3, account.getAmount());
            statement.setLong(4, account.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public void delete(Account account) {
        final String SQL = "delete account where id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, account.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public Optional<Account> findById(Long id) {
        final String SQL = "select * from account where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = Account.builder()
                            .id(resultSet.getLong("id"))
                            .login(resultSet.getString("login"))
                            .address(resultSet.getString("address"))
                            .amount(resultSet.getBigDecimal("amount"))
                            .build();
                    return Optional.of(account);
                }
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        final String SQL = "select * from account";
        List<Account> result = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                result.add(new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("login"),
                        resultSet.getString("address"),
                        resultSet.getBigDecimal("amount"))
                );
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return result;

    }
}
