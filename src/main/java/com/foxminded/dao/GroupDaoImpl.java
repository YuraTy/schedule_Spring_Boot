package com.foxminded.dao;

import com.foxminded.model.Group;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Group create(Group group) {
        String sqlInquiry = "INSERT INTO groups (name_group) VALUES (?)";
        jdbcTemplate.update(sqlInquiry, group.getNameGroup());
        return group;
    }

    @Override
    public List<Group> findAll() {
        String sqlInquiry = "SELECT name_group,id FROM groups";
        return jdbcTemplate.query(sqlInquiry, new RowMapper<Group>() {
            @Override
            public Group mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
                return Group.builder()
                        .groupId(rs.getInt("id"))
                        .nameGroup(rs.getString("name_group"))
                        .build();
            }
        });
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
    public void creteTable() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableGroups.sql"));
        resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
    }
}
