package com.jeremiasleite.osworks.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremiasleite.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.jeremiasleite.osworks.domain.exception.NegocioException;
import com.jeremiasleite.osworks.domain.model.Cliente;
import com.jeremiasleite.osworks.domain.model.Comentario;
import com.jeremiasleite.osworks.domain.model.OrdemServico;
import com.jeremiasleite.osworks.domain.model.StatusOrdemServico;
import com.jeremiasleite.osworks.domain.repository.ClienteRepository;
import com.jeremiasleite.osworks.domain.repository.ComentarioRepository;
import com.jeremiasleite.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
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
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscarId(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		return comentarioRepository.save(comentario);
	}

	private OrdemServico buscarId(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(()-> new EntidadeNaoEncontradaException("ordem de sserviço não encontrada."));
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscarId(ordemServicoId);
		ordemServico.finalizar();
		ordemServicoRepository.save(ordemServico);
	}
}
