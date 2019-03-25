package io.richel.curso.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.richel.curso.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagementoComBoleto(PagamentoComBoleto pagamento, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		
		pagamento.setDataVencimento(cal.getTime());
	}
}
