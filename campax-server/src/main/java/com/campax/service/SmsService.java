package com.campax.service;

public interface SmsService {
	
	public void init();
	
	void sendOtp(String toPhone, String otp);
	
}