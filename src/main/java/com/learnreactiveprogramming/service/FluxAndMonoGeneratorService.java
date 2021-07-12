package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        //  db or remote service call:
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .log();
    }

    public Flux<String> namesFlux_filter(int stringLength) {
        //  filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .filter(s -> s.length() > stringLength)
                .log();
    }

    public Flux<String> namesFlux_flatmap(int stringLength) {
        //  filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                //  ALEX, CHLOE -> A,L,E,X,C,H,L,O,E
                .flatMap(this::splitString)
                .log();
    }

    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        //  filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                //  ALEX, CHLOE -> A,L,E,X,C,H,L,O,E
                .flatMap(this::splitString_withDelay)
                .log();
    }

    //  The concatMap preserves the order in the reactive stream
    public Flux<String> namesFlux_concatMap(int stringLength) {
        //  filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                //  ALEX, CHLOE -> A,L,E,X,C,H,L,O,E
                .concatMap(this::splitString_withDelay)
                .log();
    }

    public Flux<String> splitString(String name) {
        return Flux.fromArray(name.split(""));
    }

    public Flux<String> splitString_withDelay(String name) {
        var delay = 1000;
        return Flux.fromArray(name.split(""))
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> namesFlux_map() {
        //  db or remote service call:
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFlux_immutability() {
        //  db or remote service call:
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Mono<List<String>> namesMono_flatMap() {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .flatMap(this::splitStringMono)
                .log();
    }

    public Flux<String> namesMono_flatMapMany() {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .flatMapMany(this::splitString)
                .log();
    }

    public Mono<List<String>> splitStringMono(String name) {
        var charArray = name.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public Mono<String> nameMono() {
        //  db or remote service call:
        return Mono.just("alex");
    }

    public Flux<String> namesFlux_transform(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength);

        //  filter the string whose length is greater than stringLength
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                //  ALEX, CHLOE -> A,L,E,X,C,H,L,O,E
                .flatMap(this::splitString)
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength)
                        .flatMap(this::splitString);

        var switchIfEmpty = Flux.just("default")
                .transform(filterMap);

        //  filter the string whose length is greater than stringLength
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                //  ALEX, CHLOE -> A,L,E,X,C,H,L,O,E
                .flatMap(this::splitString)
                .switchIfEmpty(switchIfEmpty)
                .log();
    }

    public Flux<String> exploreConcat() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> exploreConcatWith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.concatWith(defFlux).log();
    }

    public Flux<String> exploreConcatWith_mono() {
        var abcFlux = Mono.just("A");
        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.concatWith(defFlux).log();
    }

    public static void main(String[] args) {
        var fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(System.out::println);

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(System.out::println);

        fluxAndMonoGeneratorService.namesFlux_map()
                .subscribe(System.out::println);

        fluxAndMonoGeneratorService.namesFlux_immutability()
                .subscribe(System.out::println);
    }
}
