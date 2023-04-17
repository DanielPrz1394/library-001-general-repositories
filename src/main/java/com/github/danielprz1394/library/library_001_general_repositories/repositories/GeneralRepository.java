package com.github.danielprz1394.library.library_001_general_repositories.repositories;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class GeneralRepository {
    private static final Logger logger = LoggerFactory.getLogger(GeneralRepository.class);
    private static final String PROCEDURE_MSG = "Executing procedure: {}";
    private static final String PARAMS_MSG = "Parameter {} = {}";
    private static final int PARAMS_SIZE = 100;

    private final JdbcTemplate jdbcTemplate;

    private void printParameters(Map<String, ?> params) {
        params.forEach((key, value) -> {
            String temp = String.valueOf(value);
            logger.info(PARAMS_MSG, key, temp.length() > PARAMS_SIZE ? temp.substring(0, PARAMS_SIZE) + "..." : temp);
        });
    }

    private void printStore(String procedureName) {
        logger.info(PROCEDURE_MSG, procedureName);
    }

    public <T> List<T> runProcedure(String procedureName, Class<T> returnType) {
        this.printStore(procedureName);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withProcedureName(procedureName).returningResultSet("RS", BeanPropertyRowMapper.newInstance(returnType));
        return (List<T>) simpleJdbcCall.execute().get("RS");
    }

    public <T> List<T> runProcedure(String procedureName, Map<String, ?> params, Class<T> returnType) {
        this.printStore(procedureName);
        this.printParameters(params);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withProcedureName(procedureName).returningResultSet("RS", BeanPropertyRowMapper.newInstance(returnType));
        return (List<T>) simpleJdbcCall.execute(params).get("RS");
    }

    public Map<String, Object> runProcedure(String procedureName) {
        this.printStore(procedureName);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withProcedureName(procedureName);
        return simpleJdbcCall.execute();
    }

    public Map<String, Object> runProcedure(String procedureName, Map<String, ?> params) {
        this.printStore(procedureName);
        this.printParameters(params);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withProcedureName(procedureName);
        return simpleJdbcCall.execute(params);
    }
}
