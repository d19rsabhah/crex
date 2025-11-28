package com.example.crex.config;

import com.example.crex.repository.BlacklistedTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final CustomUserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String token = jwtService.extractToken(request);
//        if (token != null && jwtService.validateToken(token)) {
//            String email = jwtService.extractUsername(token);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserDetailsService userDetailsService;
//    private final BlacklistedTokenRepository blacklistedTokenRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        // If no Authorization header → continue the chain
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Extract token (remove Bearer)
//        String token = authHeader.substring(7);
//
//        // 🔒 Check if token is already blacklisted
//        if (blacklistedTokenRepository.existsByToken(token)) {
//            System.out.println("❌ Blocked request — token is blacklisted (logged out)");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Extract username/email from token
//        String username = jwtService.extractUsername(token);
//
//        // Authenticate only if user not already authenticated
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            // Validate token signature + expiry
//            if (jwtService.validateToken(token, userDetails)) {
//
//                // Create Spring Security authentication object
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // Register authentication in context
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        // Continue filter chain
//        filterChain.doFilter(request, response);
//    }
//}


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {




        String authHeader = request.getHeader("Authorization");

        // If no Authorization header → continue the chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

      //  String token = authHeader.substring(7);

        String token = authHeader.substring(7).trim(); // normalize

// 🔍 DEBUG BLOCK — PUT THIS EXACTLY HERE
        System.out.println("=============================================");
        System.out.println("🔥 FILTER DEBUG START");

// Incoming token debug
        System.out.println("🔑 Incoming token length: " + token.length());
        System.out.println("🔑 Incoming token (start): " +
                token.substring(0, Math.min(50, token.length())) + "...");
        System.out.println("🔑 Incoming token full: [" + token + "]");

// Print full blacklist table from DB
        System.out.println("🗂 Blacklist DB contents:");
        blacklistedTokenRepository.findAll().forEach(t ->
                System.out.println(" - [" + t.getToken() + "] len=" + t.getToken().length())
        );

// Test existsByToken()
        System.out.println("➡️ existsByToken(token) => " +
                blacklistedTokenRepository.existsByToken(token));

        System.out.println("🔥 FILTER DEBUG END");
        System.out.println("=============================================");


        // 🔥 1️⃣ — Check blacklist FIRST
        if (blacklistedTokenRepository.existsByToken(token)) {
            System.out.println("❌ Token is blacklisted — rejecting request");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has been logged out.");
            response.getWriter().flush();
            return;
        }

        String username = null;

        // 🔥 2️⃣ — Validate and extract username (handle expired/invalid tokens)

        try {
            username = jwtService.extractUsername(token);
        } catch (ExpiredJwtException ex) {
            System.out.println("❌ Expired token: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired. Please login again.");
            response.getWriter().flush();
            return;
        } catch (JwtException | IllegalArgumentException ex) {
            System.out.println("❌ Invalid token: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token.");
            response.getWriter().flush();
            return;
        }

        // 🔥 3️⃣ — Authenticate user if token is valid & no authentication exists
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token.");
                response.getWriter().flush();
                return;
            }
        }

        // 🔥 4️⃣ — Continue filter chain
        filterChain.doFilter(request, response);
    }
}