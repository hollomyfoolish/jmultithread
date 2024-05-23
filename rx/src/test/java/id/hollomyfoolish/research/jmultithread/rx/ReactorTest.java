package id.hollomyfoolish.research.jmultithread.rx;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReactorTest {

    public static void main(String[] args) {
//        testIteratorFlux();
        testCustomizedEmitter();
    }

    private static void testCustomizedEmitter() {
        MyFluxSinkConsumer myFluxSinkConsumer = new MyFluxSinkConsumer();
        Flux.<String>create(t -> myFluxSinkConsumer.setFluxSink(t))
//                .sort()
//                .distinct()
                .subscribe(t -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + t);
                });

        myFluxSinkConsumer.start();
        try {
            int r = System.in.read();
            System.out.println(r);
            System.out.println("stopping");
            myFluxSinkConsumer.stopNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testIteratorFlux() {
        System.setProperty("reactor.trace.operatorStacktrace", "true");
        List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
        Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
//                .flatMap(word -> Mono.just(word))
//                .map(word -> word + "s")
                .concatWith(Mono.just("s"))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE), (s, c) -> String.format("%2d. %s", c, s))
                .subscribe(System.out::println)
        ;
    }

}
