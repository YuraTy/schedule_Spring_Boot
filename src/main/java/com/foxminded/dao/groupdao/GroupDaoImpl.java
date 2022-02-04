package com.foxminded.dao.groupdao;

import com.foxminded.group.Group;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class GroupDaoImpl implements GroupDao {

    private final JdbcTemplate jdbcTemplate;

    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group create(Group group) {
        String sqlInquiry = "INSERT INTO groups (name_group) VALUE (?)";
        jdbcTemplate.update(sqlInquiry, group.getNameGroup());
        return group;
    }

    @Override
    public List<Group> findAll() {
        String sqlInquiry = "SELECT name_group FROM groups";
        return jdbcTemplate.query(sqlInquiry, BeanPropertyRowMapper.newInstance(Group.class));
    }

    @Override
    public Group update(Group groupNew, Group groupOld) {
        String sqlInquiry = "UPDATE groups SET name_group = ? WHERE name_group = ?";
        jdbcTemplate.update(sqlInquiry, groupNew.getNameGroup(), groupOld.getNameGroup());
        return groupNew;
    }

    @Override
    public void delete(Group group) {
        String sqlInquiry = "DELETE FROM groups WHERE name_group = ?";
        jdbcTemplate.update(sqlInquiry, group.getNameGroup());
    }

    @PostConstruct
    public void creteTable() throws SQLException {
        DatabaseMetaData metaData = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getMetaData();
        ResultSet tablesResultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
        if (!tablesResultSet.next()) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableGroups.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        }
    }
}
