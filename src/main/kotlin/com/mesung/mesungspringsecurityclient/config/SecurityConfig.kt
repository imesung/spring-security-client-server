package com.mesung.mesungspringsecurityclient.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.reactive.function.client.WebClient


@EnableWebSecurity
class SecurityConfig {

    private val WHITE_LIST_URLS = arrayOf(
        "/hello",
        "/register",
        "/verifyRegistration*",
        "/resendVerifyToken*"
    )

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http : HttpSecurity) : SecurityFilterChain {

        http
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .antMatchers("/articles/**").authenticated()
//            .antMatchers(WHITE_LIST_URLS).permitAll()
            .and()
            .oauth2Login { oauth2login -> oauth2login.loginPage("/oauth2/authorization/articles-client-oidc") }
            .oauth2Client(Customizer.withDefaults())

        return http.build()
    }







//    @Bean
//    fun clientRegistrationRepository(): ClientRegistrationRepository? {
//        return InMemoryClientRegistrationRepository()
//    }
//
//    @Bean
//    @ConditionalOnMissingBean // New
//    fun authorizedClientRepository(
//        authorizedClientService: OAuth2AuthorizedClientService?
//    ): OAuth2AuthorizedClientRepository? {
//        return AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService)
//    }
//
//    @Bean
//    fun authorizedClientService(): OAuth2AuthorizedClientService? {
//        return InMemoryOAuth2AuthorizedClientService(
//            clientRegistrationRepository()
//        )
//    }

}