package com.teamManager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamManager.model.Multa;
import com.teamManager.repository.IMultaRepository;
import com.teamManager.service.MulteService;

@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MulteController {

	@Autowired
	private IMultaRepository multaRepository;

	@Autowired
	private MulteService multeService = new MulteService();

	@RequestMapping(value = { "multa" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody Boolean add(@RequestBody Multa multa) throws Exception {
		multaRepository.save(multeService.addUpdate(multa));
		return true;
	}

	@RequestMapping(value = { "multa" }, method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody Boolean update(@RequestBody Multa multa) throws Exception {
		multaRepository.save(multeService.addUpdate(multa));
		return true;
	}

	@RequestMapping(value = { "multa" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Optional<Multa> get(@NonNull @RequestParam Long id) throws Exception {
		return multeService.getById(id);
	}

	@RequestMapping(value = { "multa/all" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Iterable<Multa> getAll() throws Exception {
		return multeService.getAllOfTeam();
	}

	@RequestMapping(value = { "multa" }, method = RequestMethod.DELETE, consumes = "application/json")
	public @ResponseBody Boolean delete(@NonNull @RequestParam Long id) {
		multaRepository.deleteById(id);
		return true;
	}
}
