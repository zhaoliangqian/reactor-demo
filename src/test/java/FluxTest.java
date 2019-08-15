import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @author qianzhaoliang
 * @since 2019/8/12
 */
public class FluxTest {

    @Test
    public void test_1() {
        Flux.just("a").subscribe(System.out::println);
        Flux.fromIterable(Arrays.asList("hello","world")).subscribe(System.out::println);
        Flux.range(0, 10).subscribe(System.out::print);
    }

    @Test
    public void test_interval() {
        Flux.interval(Duration.ofSeconds(1)).subscribe(value-> System.out.println(value),
                error -> System.out.println(error), () -> System.out.println("done"));
    }
    @Test
    public void test_onComplete() {
        Flux.range(1,3)
                .subscribe(integer -> System.out.println(integer), error -> System.err.println(error), () -> System.out.println("done"));
    }
    @Test
    public void test_baseSubscriber() {
        Flux.range(1,3)
                .subscribe(new BaseSubscriber() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        //super.hookOnSubscribe(subscription);
                        System.out.println("on subscriber");
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Object value) {
                        super.hookOnNext(value);
                        System.out.println("value: " + value);
                        request(1);
                    }

                    @Override
                    protected void hookOnComplete() {
                        super.hookOnComplete();
                        System.out.println("done");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }
                });
    }

}
