package demo.task1.dao;

import demo.task1.model.AccountOperation;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountOperationDaoJpaImpl extends GenericDaoJpaImpl<AccountOperation, Long> implements AccountOperationDao {
    EntityManager em = getEntityManager();

    @Override
    public List<AccountOperation> findAccountsWithOperationsBetween(Date from, Date to) {
        em = getEntityManager();
        TypedQuery<AccountOperation> query;
        try {
            query = em.createNamedQuery("AccountOperation.findAccountsWithOperationsBetween", AccountOperation.class);
            query.setParameter(1, from);
            query.setParameter(2, to);
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<AccountOperation> findAccountsWithoutOperations() {
        em = getEntityManager();
        TypedQuery<AccountOperation> query;
        try {
            query = em.createNamedQuery("AccountOperation.findAccountsWithoutOperations", AccountOperation.class);
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<Object> findAccountsWithMaxOperations() {
        em = getEntityManager();
        AtomicInteger maxStatic = new AtomicInteger();
        TypedQuery<Object[]> query;
        int index =0;
        List<Object> accountList = new ArrayList<>();
        try {
            query = em.createNamedQuery("AccountOperation.findAccountsWithMaxOperations", Object[].class);
            List<Object[]> results = query.getResultList();
            for(Object[] result : results){
                if(index++ == 0){
                    maxStatic.set(result[1].hashCode());
                }
                if(maxStatic.get() == (result[1].hashCode())){
                    accountList.add(result[0]);
                }
            }
            return accountList;
        } catch (NoResultException exception) {
            return null;
        }
    }
    @Override
    public List<Object> findMostPopularTypeOfOperation() {
        em = getEntityManager();
        AtomicInteger maxStatic = new AtomicInteger();
        TypedQuery<Object[]> query;
        int index =0;
        List<Object> typeList = new ArrayList<>();
        try {
            query = em.createNamedQuery("AccountOperation.findMostPopularTypeOfOperation", Object[].class);
            List<Object[]> results = query.getResultList();
            for(Object[] result : results){
                if(index++ == 0){
                    maxStatic.set(result[1].hashCode());
                }
                if(maxStatic.get() == (result[1].hashCode())){
                    typeList.add(result[0]);
                }
            }
            return typeList;
        } catch (NoResultException exception) {
            return null;
        }
    }

}
