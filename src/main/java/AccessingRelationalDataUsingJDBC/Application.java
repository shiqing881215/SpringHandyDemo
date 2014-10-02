package AccessingRelationalDataUsingJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class Application {
	public static void main(String[] args) {
		// A simple database used by JDBC for test 
		// Define driver as jdbc and also the url (here use the h2 in-memory database  http://www.h2database.com/html/main.html)
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUsername("sa");
		dataSource.setUrl("jdbc:h2:~/mem");  // Set an absolute path of database like ~/mem
		dataSource.setPassword("");
		
		// Class for JDBC, this class can manipulate data in the db
		// and also take good care of other stuff, like exception, db connection 
		// Just focus on the application logic and that's enough
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		// Create table first
		System.out.println("Creating table");
		jdbcTemplate.execute("drop table customers if exists");
		jdbcTemplate.execute("create table customers(id serial, first_name varchar(255), last_name varchar(255))");
		
		// Update the table
		String[] names = {"LeBron James", "Kobe Bryant", "Kevin Love", "Kobe Bryant2"};
		for (String fullname : names) {
			String[] name = fullname.split(" ");
            System.out.printf("Inserting customer record for %s %s\n", name[0], name[1]);
			jdbcTemplate.update("INSERT INTO customers(first_name, last_name) values(?,?)", name[0], name[1]);
		}
		
		// Query the table
        System.out.println("Querying for customer records where first_name = 'Kobe':");
        List<Customer> customers = jdbcTemplate.query("select * from customers where first_name=?", 
        		new Object[]{"Kobe"}, 
        		new RowMapper<Customer>() {
					public Customer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"));
					}
        });
        
        for (Customer customer : customers) {
        	System.out.println(customer);
        }
	}
}
