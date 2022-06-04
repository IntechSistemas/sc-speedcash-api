package br.com.intech.iseasypos.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.api.assembler.CargoIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.CargoModelAssembler;
import br.com.intech.iseasypos.api.model.CargoModel;
import br.com.intech.iseasypos.api.model.input.CargoIModel;
import br.com.intech.iseasypos.domain.exception.EmpresaNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Cargo;
import br.com.intech.iseasypos.domain.service.CargoService;

@RestController
@RequestMapping("/empresa/{empresaId}/cargos")
public class CargoController {
	@Autowired
	private CargoService cargoService;
	@Autowired
	private CargoModelAssembler cargoAssembler;
	@Autowired
	private CargoIModelDisassembler cargoDisassembler;

	@GetMapping()
	@ResponseStatus(value = HttpStatus.OK)
	public List<CargoModel> findAll(@PathVariable Integer empresaId){
		List<Cargo> cargos = cargoService.findAll(empresaId);
		return cargoAssembler.toCollectionModel(cargos); 
	}
	
	@GetMapping("/{cargoId}")
	public CargoModel findById(@PathVariable Integer empresaId, @PathVariable Integer cargoId) {
		Cargo cargo = cargoService.findById(cargoId, empresaId);
		return cargoAssembler.toModel(cargo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CargoModel save(@PathVariable Integer empresaId, @Valid @RequestBody CargoIModel cargoIModel){
		Cargo cargo = cargoDisassembler.toDomainObject(cargoIModel);
		cargo = cargoService.save(cargo, empresaId);
		return cargoAssembler.toModel(cargo);
	}
	
	@PutMapping("/{cargoId}")
	public ResponseEntity<?> update(@PathVariable Integer empresaId, @PathVariable Integer cargoId, @RequestBody CargoIModel cargoIModel){
		try {
			Cargo cargoAtual = cargoService.findById(cargoId, empresaId);
			cargoDisassembler.copyToDomainObject(cargoIModel, cargoAtual);
			return ResponseEntity.ok(cargoAssembler.toModel(cargoService.save(cargoAtual, empresaId)));
		}catch (EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{cargoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer cargoId, @PathVariable Integer empresaId) {
		cargoService.delete(cargoId, empresaId);	
	}
}
