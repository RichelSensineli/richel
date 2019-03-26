package io.richel.curso.services;

import org.springframework.mail.SimpleMailMessage;

import io.richel.curso.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
