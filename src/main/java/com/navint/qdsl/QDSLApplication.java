package com.navint.qdsl;

import java.util.List;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.navint.dsl.Customer;
import com.navint.dsl.QCustomer;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;

/**
 * 
 * Test code run query from qdsl
 * 
 * @author Rajeev Krishna Singh
 *
 */
public class QDSLApplication {
	public static void main(String[] args) {
		SQLTemplates templates = new MySQLTemplates();
		Configuration configuration = new Configuration(templates);
		SQLQueryFactory queryFactory = new SQLQueryFactory(configuration, getDataSource());

		QCustomer customer = new QCustomer("c");
		QCustomer customerBean = QCustomer.customer;

		/*List<Tuple> customers = queryFactory.select(customer.all()).from(customer)
				.where(customer.customerId.eq(Long.valueOf(9))).fetch();*/
		
		List<Customer> qCustomers = queryFactory.select(customer.all()).from(customer)
		.where(customer.customerId.eq(Long.valueOf(9))).transform(GroupBy.groupBy(customer.customerId).list(customerBean));

		for(Customer c:qCustomers) {
			System.out.println(c);
		}
	}
	
	 /**
     * Returns a DataSource object for connection to the database.
     *
     * @return a DataSource.
     */
    private static DataSource getDataSource() {
    	String user = System.getProperty("jdbc.user");
		String password = System.getProperty("jdbc.password");
		String url = System.getProperty("jdbc.url");
//		String driver = System.getProperty("jdbc.driver");
		MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        //dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
