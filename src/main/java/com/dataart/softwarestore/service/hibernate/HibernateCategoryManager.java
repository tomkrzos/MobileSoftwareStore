package com.dataart.softwarestore.service.hibernate;

import com.dataart.softwarestore.model.domain.Category;
import com.dataart.softwarestore.service.CategoryManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HibernateCategoryManager implements CategoryManager {


    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return session().createQuery("from Category").list();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean categoryExists(String categoryName) {
        return getCategoryByName(categoryName) != null;
    }

    private Category getCategoryByName(String categoryName) {
        return (Category) session().createQuery("from Category where name=:categoryName").setParameter("categoryName", categoryName).uniqueResult();
    }

}
