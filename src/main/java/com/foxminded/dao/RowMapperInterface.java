package com.foxminded.dao;

import com.foxminded.schedule.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapperInterface<Schedule> {
    Schedule mapper(ResultSet resultSet, int rowNum) throws SQLException;
}
