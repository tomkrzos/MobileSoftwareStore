package com.dataart.softwarestore.service.hibernate;

import com.dataart.softwarestore.model.domain.Program;
import com.dataart.softwarestore.model.dto.ProgramBasicInfoDto;
import com.dataart.softwarestore.service.MostPopularManager;
import com.dataart.softwarestore.service.QueryResultsOrder;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HibernateMostPopularManager implements MostPopularManager {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<ProgramBasicInfoDto> getTopPrograms(Integer limit, QueryResultsOrder downloadOrder, QueryResultsOrder
            timeUploadedOrder) {
        String query = "from Program p order by p.statistics.downloads " + downloadOrder.value() + ", p.statistics" +
                ".timeUploaded " + timeUploadedOrder.value();
        List<Program> programs = session().createQuery(query).setMaxResults(limit).setCacheable(true).list();

        programs.stream().forEach(program -> {
            Hibernate.initialize(program.getCategory());
            Hibernate.initialize(program.getStatistics());
        });
        return programs.stream().map(program -> new ProgramBasicInfoDto(program.getId(), program.getName(), program
                .getDescription(), program.getImg128(), program.getImg512(), program.getCategory().getName(), program
                .getStatistics().getDownloads()))
                .collect(Collectors.toList());
    }

}


