package io.richel.curso.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import io.richel.curso.domain.Cliente;
import io.richel.curso.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(pedido.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + pedido.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(pedido.toString());
		
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Pedido objeto) {
		Context context = new Context();
		context.setVariable("pedido", objeto);
		
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(mm);
		}catch (Exception e) {
			sendOrderConfirmationEmail(pedido);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		
		mmh.setTo(pedido.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + pedido.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(pedido), true);
		
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPassword) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPassword);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPassword) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha ");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPassword);
		
		return sm;
	}
}
