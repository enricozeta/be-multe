package com.teamManager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamManager.dto.MultaTypeDTO;
import com.teamManager.service.MulteTypeService;

/**
 * The Class MulteTypeController.
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MulteTypeController {

	@Autowired
	private MulteTypeService multeTypeService;

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
	public @ResponseBody MultaTypeDTO add(@RequestBody MultaTypeDTO multaType) throws Exception {
		return multeTypeService.add(multaType);
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
	public @ResponseBody MultaTypeDTO update(@RequestBody MultaTypeDTO multaType) throws Exception {
		return multeTypeService.add(multaType);
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
	public @ResponseBody MultaTypeDTO get(@NonNull @RequestParam Long id) throws Exception {
		return multeTypeService.get(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "multaType/all" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Iterable<MultaTypeDTO> getAll() throws Exception {
		return multeTypeService.getAll();
	}

	@RequestMapping(value = { "multaType/enabled/all" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Iterable<MultaTypeDTO> getAllEnabled() throws Exception {
		List<MultaTypeDTO> result = new ArrayList<>();
		multeTypeService.getAll().forEach((multaType -> {
			if (multaType.isEnabled()) {
				result.add(multaType);
			}
		}));
		return result;
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "admin/multaType" }, method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Boolean delete(@NonNull @RequestParam Long id) throws Exception {
		return multeTypeService.disable(id);
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
	@RequestMapping(value = { "admin/multaType/disable" }, method = RequestMethod.DELETE, consumes = "application/json")
	public @ResponseBody Boolean disable(@NonNull @RequestParam Long id) throws Exception {
		return multeTypeService.disable(id);
	}
}
