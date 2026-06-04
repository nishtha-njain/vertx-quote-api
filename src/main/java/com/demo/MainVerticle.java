package com.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;

import java.util.List;
import java.util.Random;

public class MainVerticle extends AbstractVerticle {

    private final List<String> quotes = List.of(
            "Done is better than perfect.",
            "Small steps every day compound over time.",
            "Consistency beats intensity.",
            "Progress, not perfection.",
            "Start before you're ready."
    );

    private final Random random = new Random();

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new MainVerticle());
    }

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.route().handler(
                CorsHandler.create()
                        .addOrigin("*")
                        .allowedMethod(io.vertx.core.http.HttpMethod.GET)
                        .allowedHeader("*")
        );

        router.get("/").handler(ctx ->
                ctx.response().end("Quote API is running!")
        );

        router.get("/quote").handler(ctx -> {

            String quote =
                    quotes.get(random.nextInt(quotes.size()));

            System.out.println("Quote requested");

            ctx.response()
                    .putHeader("content-type", "application/json")
                    .end("""
                        {
                          "quote":"%s"
                        }
                        """.formatted(quote));
        });

        router.get("/health").handler(ctx ->
                ctx.response()
                        .putHeader("content-type", "application/json")
                        .end("""
                            {
                              "status":"UP"
                            }
                            """)
        );

        router.get("/version").handler(ctx -> {

            String version =
                    System.getenv().getOrDefault(
                            "APP_VERSION",
                            "local"
                    );

            ctx.response()
                    .putHeader("content-type", "application/json")
                    .end("""
                        {
                          "version":"%s"
                        }
                        """.formatted(version));
        });

        int port = Integer.parseInt(
                System.getenv().getOrDefault(
                        "PORT",
                        "8080"
                )
        );

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(port)
                .onSuccess(server ->
                        System.out.println(
                                "Server started on port " + port
                        )
                );
    }
}