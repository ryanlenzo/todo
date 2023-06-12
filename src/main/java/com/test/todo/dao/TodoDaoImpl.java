package com.test.todo.dao;

import com.test.todo.dto.TodoDTO;
import com.test.todo.entity.TodoBean;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TodoDaoImpl implements TodoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List findListByCriteria(TodoDTO todo) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(TodoBean.class);

        if (todo.getStatus() != null) {
            criteria.add(Restrictions.eq("status", todo.getStatus()));
        }
        if (todo.getName() != null) {
            criteria.add(Restrictions.eq("name", todo.getName()));
        }
        if (todo.getPriority() != null) {
            criteria.add(Restrictions.eq("priority", todo.getPriority()));
        }
        if (todo.getTag() != null) {
            criteria.add(Restrictions.eq("tag", todo.getTag()));
        }
        if (todo.getSorting() == null) {
            criteria.addOrder(Order.asc("priority"));
        } else {
            for (String sort : todo.getSorting().split(",")) {
                String columnName = sort.split("_")[0];
                String order = sort.split("_")[1];
                criteria.addOrder(order.equalsIgnoreCase("ASC") ? Order.asc(columnName) : Order.desc(columnName));
            }
        }
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }
}
