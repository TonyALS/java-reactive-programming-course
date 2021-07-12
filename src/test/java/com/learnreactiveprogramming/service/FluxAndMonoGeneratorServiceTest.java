package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoGeneratorServiceTest {
    private final FluxAndMonoGeneratorService fluxAndMonoGeneratorService =
            new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //  When
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        //  Then
        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        var stringList = new String[]{"ALEX", "BEN", "CHLOE"};

        //  When
        var namesFlux_map = fluxAndMonoGeneratorService.namesFlux_map();

        //  Then
        StepVerifier.create(namesFlux_map)
                .expectNext(stringList)
                .verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
        var stringList = new String[]{"alex", "ben", "chloe"};

        //  When
        var namesFlux_immutability = fluxAndMonoGeneratorService.namesFlux_immutability();

        //  Then
        StepVerifier.create(namesFlux_immutability)
                .expectNext(stringList)
                .verifyComplete();
    }

    @Test
    void namesFlux_filter() {
        var stringList = new String[]{"alex", "chloe"};

        //  When
        var namesFlux_filter = fluxAndMonoGeneratorService.namesFlux_filter(3);

        //  Then
        StepVerifier.create(namesFlux_filter)
                .expectNext(stringList)
                .verifyComplete();
    }

    @Test
    void testNamesFlux_flatmap() {
        //  When
        var namesFlux_flatmap = fluxAndMonoGeneratorService
                .namesFlux_flatmap(3);

        //  Then
        StepVerifier.create(namesFlux_flatmap)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        //  When
        var namesFlux_flatmap_async = fluxAndMonoGeneratorService
                .namesFlux_flatmap_async(3);

        //  Then
        StepVerifier.create(namesFlux_flatmap_async)
//                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatMap() {
        //  When
        var namesFlux_concatMap = fluxAndMonoGeneratorService
                .namesFlux_concatMap(3);

        //  Then
        StepVerifier.create(namesFlux_concatMap)
                //  Different of the flatMap, the concatMap preserves the order of stream
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesMono_flatMap() {
        //  When
        var namesMono_flatMap = fluxAndMonoGeneratorService
                .namesMono_flatMap();

        //  Then
        StepVerifier.create(namesMono_flatMap)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void namesMono_flatMapMany() {
        //  When
        var namesMono_flatMapMany = fluxAndMonoGeneratorService
                .namesMono_flatMapMany();

        //  Then
        StepVerifier.create(namesMono_flatMapMany)
                .expectNext("A", "L", "E", "X")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        //  When
        var namesFlux_transform = fluxAndMonoGeneratorService
                .namesFlux_transform(3);

        //  Then
        StepVerifier.create(namesFlux_transform)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_1() {
        //  When
        var namesFlux_transform = fluxAndMonoGeneratorService
                .namesFlux_transform(6);

        //  Then
        StepVerifier.create(namesFlux_transform)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_switchIfEmpty() {
        //  When
        var namesFlux_transform_switchIfEmpty = fluxAndMonoGeneratorService
                .namesFlux_transform_switchIfEmpty(6);

        //  Then
        StepVerifier.create(namesFlux_transform_switchIfEmpty)
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void exploreConcat() {
        var concatFlux = fluxAndMonoGeneratorService.exploreConcat();

        StepVerifier.create(concatFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void exploreConcatWith() {
        var concatWith = fluxAndMonoGeneratorService.exploreConcatWith();

        StepVerifier.create(concatWith)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void exploreConcatWith_mono() {
        var concatWith_mono = fluxAndMonoGeneratorService.exploreConcatWith_mono();

        StepVerifier.create(concatWith_mono)
                .expectNext("A","D","E","F")
                .verifyComplete();
    }
}
