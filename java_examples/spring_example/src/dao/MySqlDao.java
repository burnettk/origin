package dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import dao.mapper.PersonRowMapper;
import domainmodel.Person;

public class MySqlDao implements iDao {
	private DataSource dataSource;

	public void setDataSource(DataSource ds) {
		
		//Something wrong here...
		dataSource = ds;
		
		System.out.println("...setDataSource() = " + ds);
	}

	public void create(String firstName, String lastName) {
		JdbcTemplate insert = new JdbcTemplate(dataSource);
		insert.update("INSERT INTO PERSON (FIRSTNAME, LASTNAME) VALUES(?,?)",
				new Object[] { firstName, lastName });
	}

	public List<Person> select(String firstname, String lastname) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select
				.query("select  FIRSTNAME, LASTNAME from PERSON where FIRSTNAME = ? AND LASTNAME= ?",
						new Object[] { firstname, lastname },
						new PersonRowMapper());
	}

	public List<Person> selectAll() {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query("select FIRSTNAME, LASTNAME from PERSON",
				new PersonRowMapper());
	}

	public void deleteAll() {
		JdbcTemplate delete = new JdbcTemplate(dataSource);
		delete.update("DELETE from PERSON");
	}

	public void delete(String firstName, String lastName) {
		JdbcTemplate delete = new JdbcTemplate(dataSource);
		delete.update("DELETE from PERSON where FIRSTNAME= ? AND LASTNAME = ?",
				new Object[] { firstName, lastName });
	}

}
