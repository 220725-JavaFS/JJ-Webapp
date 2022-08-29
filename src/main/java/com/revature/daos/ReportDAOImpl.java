package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.models.Report;
import com.revature.utils.ConnectionUtil;

public class ReportDAOImpl implements ReportDAO {

	@Override
	public Report getReportById(int id) {
		//WE ARE CONNECTING AND GETTING A STATEMENT FOR ID IN THE DATABASE
				try (Connection conn = ConnectionUtil.getConnection()) {
					String sql = "SELECT * FROM report WHERE account_number = " + id + ";";
					Statement statement = conn.createStatement();
					ResultSet result = statement.executeQuery(sql); //WE ARE HOLDING THE RESULT
					
					if (result.next()) { //USING IF SINCE WE ONLY NEED TO SELECT ONE QUERY
						Report report = new Report(
								result.getInt("account_number"),
								result.getString("account_first_name"),
								result.getString("account_last_name"),
								result.getString("account_year"),
								result.getInt("account_grade")
								);
							return report;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
	}
	
	@Override
	public List<Report> getAllAccounts() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM report";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql); //WE ARE HOLDING THE RESULT
			
			List<Report> reportList = new LinkedList<Report>();
			
			while (result.next()) { //BECAUSE WE AREN'T SLECTING ONE QUERY BUT SET OF QUERIES
				Report report = new Report(
						result.getInt("account_number"),
						result.getString("account_first_name"),
						result.getString("account_last_name"),
						result.getString("account_year"),
						result.getInt("account_grade")
					);
				
				reportList.add(report);
			}
			
			return reportList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insertAccount(Report report) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO report (account_first_name, account_last_name, account_year, account_grade)"
					+ "VALUES (?, ?, ?, ?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int count = 0;
			statement.setString(++count, report.getAccountFirstName());
			statement.setString(++count, report.getAccountLastName());
			statement.setString(++count, report.getAccountYear());
			statement.setInt(++count, report.getAccountGrade());
			
			statement.execute();
			
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void deleteAccount(int id) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM report WHERE account_number = " + id + ";";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.execute();
			
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateAccount(int id, int grade) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql =  "UPDATE report SET account_grade = " + grade + " WHERE account_number = " + id + ";";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.execute();
			
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
	}
	
	

}
