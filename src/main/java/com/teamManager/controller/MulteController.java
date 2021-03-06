package com.teamManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamManager.dto.MultaDTO;
import com.teamManager.service.MulteService;

/**
 * The Class MulteController.
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MulteController {

	@Autowired
	private MulteService multeService = new MulteService();

	/**
	 * Adds the.
	 *
	 * @param multa
	 *            the multa
	 * @return the boolean
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/multa" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody MultaDTO add(@RequestBody MultaDTO multa) throws Exception {
		return multeService.addUpdate(multa);
	}

	/**
	 * Update.
	 *
	 * @param multa
	 *            the multa
	 * @return the boolean
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/multa" }, method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody MultaDTO update(@RequestBody MultaDTO multa) throws Exception {
		return multeService.addUpdate(multa);
	}

	/**
	 * Gets the.
	 *
	 * @param id
	 *            the id
	 * @return the optional
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "multa" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody MultaDTO get(@NonNull @RequestParam Long id) throws Exception {
		return multeService.getById(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "multa/all" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Iterable<MultaDTO> getAll() throws Exception {
		return multeService.getAllOfTeam();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "admin/multa" }, method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Boolean delete(@NonNull @RequestParam Long id) throws Exception {
		return multeService.delete(id);
	}
}
