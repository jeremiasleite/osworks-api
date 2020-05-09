package com.jeremiasleite.osworks.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremiasleite.osworks.domain.exception.NegocioException;
import com.jeremiasleite.osworks.domain.model.Cliente;
import com.jeremiasleite.osworks.domain.model.OrdemServico;
import com.jeremiasleite.osworks.domain.model.StatusOrdemServico;
import com.jeremiasleite.osworks.domain.repository.ClienteRepository;
import com.jeremiasleite.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Clente não encotrado"));
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		return ordemServicoRepository.save(ordemServico);
	}
	
	public List<OrdemServico> findAll(){
		return ordemServicoRepository.findAll();
	}
}
