package com.retail.delight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.retail.delight.entity.Account;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SendMailImpl implements SendMail {
	

	// SendMail mm = new SendMailImpl();

	@Autowired
	EmailService emailService;
	
	private Account account;

	/* (non-Javadoc)
	 * @see com.retail.delight.service.SendMail#sendAlert(java.lang.String, java.lang.String)
	 */
	

	/* (non-Javadoc)
	 * @see com.retail.delight.service.SendMail#registeredSuccessfully(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendForgotPasswordToManager(String email, String role, String name) {
		String subject1 = "Password Recovery !! .. " + name;
		String body1 = "Hello, Manager, Your Password is " + account.MANAGER_PASSWORD
				+ " Thank You For Contacting the Team - Team Retail Delight";
		// mm.sendMail(email, subject1, body1);
		emailService.SendMail("retaildelightsmartgroceries@gmail.com", email, subject1, body1);
	}
	
	@Override
	public void sendForgotPasswordToEmployee(String email, String role, String name) {
		String subject1 = "Password Recovery !! .. " + name;
		String body1 = "Hello, Employee, Your Password is " + account.EMPLOYEE_PASSWORD
				+ " Thank You For Contacting the Team - Team Retail Delight";
		// mm.sendMail(email, subject1, body1);
		emailService.SendMail("retaildelightsmartgroceries@gmail.com", email, subject1, body1);
	}



}
