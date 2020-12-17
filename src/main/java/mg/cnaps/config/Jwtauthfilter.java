package mg.cnaps.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class Jwtauthfilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Fetching the authorization header from the request.
		String authenticationHeader = request.getHeader(Iconstants.HEADER);

		try {
			SecurityContext context = SecurityContextHolder.getContext();

			if (authenticationHeader != null && authenticationHeader.startsWith("Bearer")) {

				final String bearerTkn = authenticationHeader.replaceAll(Iconstants.BEARER_TOKEN, "");

				try {
					// Parsing the jwt token.
					Jws<Claims> claims = Jwts.parser().requireIssuer(Iconstants.ISSUER)
							.setSigningKey(Iconstants.SECRET_KEY).parseClaimsJws(bearerTkn);

					// Obtaining the claims from the parsed jwt token.
					String user = (String) claims.getBody().get("usr");
					Date expiration = claims.getBody().getExpiration();
					System.out.println(expiration.toString());
					if (expiration.before(new Date())) {
						throw new ServletException("Il faut se connecter");
					}
					if (expiration.after(new Date())) {
						Map<String, Object> claimss = new HashMap<String, Object>();
						claimss.put("usr", user);
						claimss.put("sub", "Authentication token");
						claimss.put("iss", Iconstants.ISSUER);
						Jwts.builder().setClaims(claimss).signWith(SignatureAlgorithm.HS512, Iconstants.SECRET_KEY)
						.setExpiration(Date.from(LocalDateTime.now().plusMinutes(60).toInstant(ZoneOffset.UTC))).compact();
					}

					// Creating an authentication object using the claims.
					Myauthtoken authenticationTkn = new Myauthtoken(user, null, null);
					// Storing the authentication object in the security context.
					context.setAuthentication(authenticationTkn);
				} catch (SignatureException e) {
					throw new ServletException("Invalid token.");
				}
			}
			else throw new ServletException("No token.");
			filterChain.doFilter(request, response);
			context.setAuthentication(null);
		} catch (AuthenticationException ex) {
			throw new ServletException("Authentication exception.");
		}
	}
}