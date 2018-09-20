package com.teamManager.controller;

import java.util.List;
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

import com.teamManager.model.MulteType;
import com.teamManager.model.Team;
import com.teamManager.repository.IMulteTypeRepository;
import com.teamManager.service.TeamService;

/**
 * The Class MulteTypeController.
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MulteTypeController {

	@Autowired
	private IMulteTypeRepository multeTypeRepository;

	@Autowired
	private TeamService teamService;

	/**
	 * Adds the.
	 *
	 * @param multaType
	 *            the multa type
	 * @return the boolean
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/multaType" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody Boolean add(@RequestBody MulteType multaType) throws Exception {
		multaType.setTeam(teamService.getCurrentTeam());
		multeTypeRepository.save(multaType);
		return true;
	}

	/**
	 * Update.
	 *
	 * @param multaType
	 *            the multa type
	 * @return the boolean
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/multaType" }, method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody Boolean update(@RequestBody MulteType multaType) throws Exception {
		multaType.setTeam(teamService.getCurrentTeam());
		multeTypeRepository.save(multaType);
		return true;
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
	@RequestMapping(value = { "multaType" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Optional<MulteType> get(@NonNull @RequestParam Long id) throws Exception {
		Optional<MulteType> findById = multeTypeRepository.findById(id);
		if (findById != null) {
			teamService.checkTeam(findById.get().getTeam());
			return findById;
		}
		return null;
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "multaType/all" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Iterable<MulteType> getAll() throws Exception {
		return teamService.getCurrentTeam().getMulteTypes();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "admin/multaType" }, method = RequestMethod.DELETE, consumes = "application/json")
	public @ResponseBody Boolean delete(@NonNull @RequestParam Long id) throws Exception {
		List<MulteType> multeTypes = teamService.getCurrentTeam().getMulteTypes();
		Optional<MulteType> findById = multeTypeRepository.findById(id);
		if (findById.isPresent() && multeTypes.contains(findById.get())) {
			multeTypeRepository.deleteById(id);
		}
		return true;
	}
}
