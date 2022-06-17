package com.mesung.mesungspringsecurityclient.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient


@RestController
class HelloController {

    @Autowired
    private val webClient: WebClient? = null

    @GetMapping(value = ["/articles"])
    fun getArticles(
        @RegisteredOAuth2AuthorizedClient("articles-client-authorization-code") authorizedClient: OAuth2AuthorizedClient?
    ): Array<String>? {
        return webClient
            ?.get()
            ?.uri("http://127.0.0.1:8080/articles")
            ?.attributes(oauth2AuthorizedClient(authorizedClient))
            ?.retrieve()
            ?.bodyToMono(Array<String>::class.java)
            ?.block()
    }

    @GetMapping("/articles/hello")
    fun hello() : String {
        return "hello client"
    }

    @GetMapping("/articles/signup")
    fun signup() : String {
        return "signup client"
    }

}