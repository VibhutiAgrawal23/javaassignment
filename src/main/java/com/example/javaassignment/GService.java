package com.example.javaassignment;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GService {
    Logger log= LoggerFactory.getLogger(Controller.class);

    @Autowired
    private JdbcTemplate jdbcTemp;

    public List<goals> list() {
        String query = "SELECT * FROM goals";
        List<goals> listSale = jdbcTemp.query(query,
                BeanPropertyRowMapper.newInstance(goals.class));
        return listSale;
    }

    public void save(goals goal){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemp);
        insert.withTableName("goals").usingColumns("GoalId", "title", "details","eta","createDate","updateDate");
        BeanPropertySqlParameterSource parameter = new BeanPropertySqlParameterSource(goal);
        insert.execute(parameter);
    }

    public goals get(String GoalId) throws EmptyResultDataAccessException, GoalsNotFoundException{
        try {
            String query1 = "SELECT * FROM goals WHERE GoalId = ?";
            Object[] args = {GoalId};
            goals goal = jdbcTemp.queryForObject(query1, args,
                    BeanPropertyRowMapper.newInstance(goals.class));
            return goal;
        }
        catch (Exception e){
            log.info("Goal with id {} doesn't exists",GoalId);

            throw new GoalsNotFoundException("Goal Details Doesn't Exits");
        }
    }

    public goals delete(String GoalId) throws GoalsNotFoundException {
        try{
            String query2 = "SELECT * FROM goals WHERE GoalId = ?";
            Object[] args = {GoalId};
            goals goal = jdbcTemp.queryForObject(query2, args,
                    BeanPropertyRowMapper.newInstance(goals.class));

            String querydel = "DELETE FROM goals WHERE GoalId = ?";
            jdbcTemp.update(querydel, GoalId);
            return goal;
        }
        catch (Exception e){
            log.info("Goal with id {} doesn't exists",GoalId);
            throw new GoalsNotFoundException("Goal Details Doesn't Exits");
        }
    }
    public goals check(String GoalId) {
        try{
            String query = "SELECT * FROM goals WHERE GoalId = ?";
            Object[] args = {GoalId};
            log.info("Running sql query to check if goal exists");
            goals goal = jdbcTemp.queryForObject(query, args,
                    BeanPropertyRowMapper.newInstance(goals.class));
            return goal;
        }
        catch(Exception e){
            return null;
        }
    }

    public void update(goals goal, String GoalId) throws GoalsNotFoundException {
        try{
            String updatesql = "UPDATE goals SET title=:title, details=:details, eta=:eta, createDate=createDate, updateDate=updateDate WHERE GoalId=:GoalId";
            BeanPropertySqlParameterSource parameter = new BeanPropertySqlParameterSource(goal);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemp);
            template.update(updatesql, parameter);
        }
        catch(Exception e){
            log.info("Goal with id {} doesn't exists",GoalId);
            throw new GoalsNotFoundException("Goal Details Can't Be Updated");
        }
    }
}