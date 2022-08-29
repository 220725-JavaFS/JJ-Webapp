package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.services.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Report;

public class ReportController extends HttpServlet{

	private ReportService reportService = new ReportService();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String URI = request.getRequestURI();
		System.out.println(URI);

		String[] urlSections = URI.split("/");

		if (urlSections.length == 3) {

			List<Report> list = reportService.reportShow();

			String json = objectMapper.writeValueAsString(list);
			System.out.println(json);

			PrintWriter printWriter = response.getWriter();

			printWriter.print(json);

			response.setStatus(200);

			response.setContentType("application/json");
		} else if (urlSections.length==4) {
			try {
				int id = Integer.valueOf(urlSections[3]);
				
				Report report = reportService.getSingleReport(id);
				
				PrintWriter printWriter = response.getWriter();
				
				String json = objectMapper.writeValueAsString(report);
				
				printWriter.print(json);
				response.setStatus(200);
				response.setContentType("application/json");
		
			}catch(NumberFormatException e) {
				response.setStatus(404);
				return;
			}
		}else {
			response.setStatus(404);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		StringBuilder sb = new StringBuilder();
		
		BufferedReader reader = request.getReader();
		
		String line = reader.readLine();
		
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		
		String json = new String(sb);
		System.out.println(json);
		
		Report report = objectMapper.readValue(json, Report.class);
		
		reportService.addAccount(report);
		PrintWriter printWriter = response.getWriter();
		printWriter.print("Account was successfully added to your database!");
		response.setStatus(201);
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String URI = request.getRequestURI();
		System.out.println(URI);
		
		String[] urlSections = URI.split("/");
		if (urlSections.length==4) {
			try {
				int reportId = Integer.valueOf(urlSections[3]);
				
				reportService.deleteAccount(reportId);
				
				PrintWriter printWriter = response.getWriter();
				printWriter.print("Account#" + reportId + " was successfully deleted from your database!");
				response.setStatus(201);
				
			} catch (NumberFormatException e) {
				response.setStatus(404);
				}
				return;
			}
				
	}
	
	@Override
	 protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			
		 StringBuilder sb = new StringBuilder();
			
			BufferedReader reader = request.getReader();
		
			String line = reader.readLine();
			
			while(line!=null) {
				sb.append(line);
				line=reader.readLine();
			}
			
			String json = new String(sb);
			
			System.out.println(json);
			
			Report report = objectMapper.readValue(json, Report.class);
			
		    int reportId = report.getAccountNumber();
		    int reportGrade = report.getAccountGrade();
			
			reportService.updateAccount(reportId, reportGrade);
			
			PrintWriter printWriter = response.getWriter();
			printWriter.print("Account#" + reportId + " was successfully updated!");
			response.setStatus(201);
			
			
	 }
	
}














