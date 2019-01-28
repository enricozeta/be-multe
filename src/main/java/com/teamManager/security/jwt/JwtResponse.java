package com.teamManager.security.jwt;

/**
 * The Class JwtResponse.
 */
public class JwtResponse {
	private String token;
	private String type = "Bearer";

	/**
	 * Instantiates a new jwt response.
	 *
	 * @param accessToken
	 *            the access token
	 */
	public JwtResponse(String accessToken) {
		this.token = accessToken;
	}

	/**
	 * Gets the access token.
	 *
	 * @return the access token
	 */
	public String getAccessToken() {
		return token;
	}

	/**
	 * Sets the access token.
	 *
	 * @param accessToken
	 *            the new access token
	 */
	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	/**
	 * Gets the token type.
	 *
	 * @return the token type
	 */
	public String getTokenType() {
		return type;
	}

	/**
	 * Sets the token type.
	 *
	 * @param tokenType
	 *            the new token type
	 */
	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
}
