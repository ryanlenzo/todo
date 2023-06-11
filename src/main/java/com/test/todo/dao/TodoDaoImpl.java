package com.test.todo.dao;

import com.test.todo.dto.TodoDTO;
import com.test.todo.model.Todo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TodoDaoImpl implements TodoDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List findListByCriteria(TodoDTO todo) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Todo.class);

        if(todo.getTodoStatus()!= null){
            criteria.add(Restrictions.eq("status", todo.getTodoStatus()));
        }
        if(todo.getTodoName()!= null){
            criteria.add(Restrictions.eq("name", todo.getTodoName()));
        }
        if(todo.getSorting() != null){
            for(String sort: todo.getSorting().split(",")){
                String columnName = sort.split("_")[0];
                String order = sort.split("_")[1];
                criteria.addOrder(order.equalsIgnoreCase("ASC") ? Order.asc(columnName) : Order.desc(columnName));
            }
        }

        return criteria.list();
    }
}
