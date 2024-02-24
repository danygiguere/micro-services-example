package com.example.postsservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class PostsServiceApplication

fun main(args: Array<String>) {
	runApplication<PostsServiceApplication>(*args)
}
