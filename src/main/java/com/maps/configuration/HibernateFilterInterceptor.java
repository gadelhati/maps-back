package com.maps.configuration;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HibernateFilterInterceptor implements HandlerInterceptor {

    private final EntityManager entityManager;

    public HibernateFilterInterceptor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");
        return true;
    }
}