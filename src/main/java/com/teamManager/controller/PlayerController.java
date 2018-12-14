package com.teamManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamManager.dto.PlayerDTO;
import com.teamManager.service.PlayerService;

/**
 * The Class PlayerController.
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	/**
	 * Adds the.
	 *
	 * @param player
	 *            the player
	 * @return the http status entry point
	 * @throws Exception
	 */
	@RequestMapping(value = { "admin/player" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody PlayerDTO add(@RequestBody PlayerDTO player) throws Exception {
		return playerService.save(player);
	}

	/**
	 * Update.
	 *
	 * @param player
	 *            the player
	 * @return the http status entry point
	 * @throws Exception
	 */
	@RequestMapping(value = { "admin/player" }, method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody PlayerDTO update(@RequestBody PlayerDTO player) throws Exception {
		return playerService.save(player);
	}

	/**
	 * Gets the.
	 *
	 * @param id
	 *            the id
	 * @return the player
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "player" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody PlayerDTO get(@NonNull @RequestParam Long id) throws Exception {
		return playerService.getPlayerById(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "player/all" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<PlayerDTO> getAll() throws Exception {
		return playerService.getAllPlayer();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the http status entry point
	 */
	@RequestMapping(value = { "admin/player" }, method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Boolean delete(@NonNull @RequestParam Long id) {
		try {
			playerService.delete(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
