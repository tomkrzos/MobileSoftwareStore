package com.dataart.softwarestore.service.hibernate;

import com.dataart.softwarestore.model.domain.Program;
import com.dataart.softwarestore.service.PaginationManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HibernatePaginationManager implements PaginationManager {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Program> getPage(Integer pageNum, Integer categoryId, Integer itemsPerPage) {
        Integer firstResult = pageNum * itemsPerPage;
        Criteria criteria = session().createCriteria(Program.class);
        criteria.add(Restrictions.eq("category.id", categoryId))
                .setCacheable(true)
                .setFirstResult(firstResult).setMaxResults(itemsPerPage);
        return criteria.list();
    }
}