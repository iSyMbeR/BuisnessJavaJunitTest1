package demo.task1.dao;

import demo.task1.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountDaoJpaImpl extends GenericDaoJpaImpl<Account, Long> implements AccountDao {

    EntityManager em = getEntityManager();

    @Override
    public Optional<Account> findByLogin(String login) {
        em = getEntityManager();
        TypedQuery<Account> query;
        try {
            query =
                    em.createNamedQuery("Account.findByLogin", Account.class);
            query.setParameter(1, login);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Account> findLoginLike(String login) {
        em = getEntityManager();
        TypedQuery<Account> query;
        try {
            query =
                    em.createNamedQuery("Account.findLoginLike", Account.class);
            query.setParameter(1, "%" + login + "%");
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public Optional<Account> findByLoginAndAddress(String login, String address) {
        em = getEntityManager();
        TypedQuery<Account> query;
        try {
            query =
                    em.createNamedQuery("Account.findByLoginAndAddress", Account.class);
            query.setParameter(1, login);
            query.setParameter(2, address);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Account> findByAddress(String address) {
        em = getEntityManager();
        TypedQuery<Account> query;
        try {
            query =
                    em.createNamedQuery("Account.findByAddress", Account.class);
            query.setParameter(1, address);
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<Account> findByAmount(BigDecimal bigDecimalValue) {
        em = getEntityManager();
        TypedQuery<Account> query;
        try {
            query =
                    em.createNamedQuery("Account.findByAmount", Account.class);
            query.setParameter(1, bigDecimalValue);
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<Account> findByAmountBetween(BigDecimal min, BigDecimal max) {
        em = getEntityManager();
        TypedQuery<Account> query;
        try {
            query =
                    em.createNamedQuery("Account.findByAmountBetween", Account.class);
            query.setParameter(1, min);
            query.setParameter(2, max);
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<Account> findMaxAmount() {
        em = getEntityManager();
        TypedQuery<Account> query;
        try {
            query = em.createNamedQuery("Account.findMaxAmount", Account.class);
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<Account> findMinAmount() {
        em = getEntityManager();
        TypedQuery<Account> query;
        try {
            query = em.createNamedQuery("Account.findMinAmount", Account.class);
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }
}
