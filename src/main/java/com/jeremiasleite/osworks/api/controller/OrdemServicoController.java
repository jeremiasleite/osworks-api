package com.jeremiasleite.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jeremiasleite.osworks.api.model.OrdemServicoInputModel;
import com.jeremiasleite.osworks.api.model.OrdemServicoModel;
import com.jeremiasleite.osworks.domain.model.OrdemServico;
import com.jeremiasleite.osworks.domain.repository.OrdemServicoRepository;
import com.jeremiasleite.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<OrdemServicoModel> listar(){
		return toCollectionModel(gestaoOrdemServico.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long id){
		Optional<OrdemServico> ordemServico= ordemServicoRepository.findById(id);
		if(ordemServico.isPresent()) {
			OrdemServicoModel model = toModel(ordemServico.get());
			return ResponseEntity.ok(model);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInputModel ordemServicoInput) {
		return toModel(gestaoOrdemServico.criar(toEntity(ordemServicoInput)));
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.finalizar(ordemServicoId);
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		OrdemServicoModel ordemServicoModel = modelMapper.map(ordemServico, OrdemServicoModel.class);
		return ordemServicoModel;
	}
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInputModel ordemServicoInput) {
		OrdemServico ordemServico = modelMapper.map(ordemServicoInput, OrdemServico.class);
		return ordemServico;
	}
	
	
}
