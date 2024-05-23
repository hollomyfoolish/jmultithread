package id.hollomyfoolish.research.jmultithread.rx;

import reactor.core.publisher.FluxSink;

import java.util.UUID;

public class MyFluxSinkConsumer extends Thread{

    private FluxSink<String> fluxSink;
    private volatile boolean stop;

    public MyFluxSinkConsumer(){
        this.stop = false;
    }

    public void setFluxSink(FluxSink<String> fluxSink){
        this.fluxSink = fluxSink;
    }

    public void stopNow(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println(Thread.currentThread().getName() + ": producing");
            fluxSink.next(UUID.randomUUID().toString());
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fluxSink.complete();
    }
}
