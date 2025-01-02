import java.util.concurrent.*;

public class test {


    //    编写一个Java函数，通过调用AService.get()、BService.get()、CService.get()三个接口，获取三个整数，然后将这三个整数累加，最终返回累加的值。要求：
//
//            1.调用三个接口的操作需要并行执行，以提高效率；
//
//            2.累加操作需要在获取三个整数的操作完成后进行，因此需要保证三个整数均已获取后才能进行累加操作；
//
//            3.考虑多线程安全问题。
    public static void main(String[] args) {


//        //创建线程池
//       ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
//                3,
//                6,
//                3,
//                TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>(10),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());
//
//        //把三个service 放入线程池中
//        CompletableFuture<Integer> completableFutureA = CompletableFuture.supplyAsync(AService::get, poolExecutor);
//        CompletableFuture<Integer> completableFutureB = CompletableFuture.supplyAsync(BService::get, poolExecutor);
//        CompletableFuture<Integer> completableFutureC = CompletableFuture.supplyAsync(CService::get, poolExecutor);
//
//
//        //获取线程池中的service 执行累加 D = A + B + C
//        CompletableFuture<Integer> completableFutureD = completableFutureA
//                .thenCombine(completableFutureB, (a, b) -> a + b)
//                .thenCombine(completableFutureC, (c, d) -> c + d);
//
//        //获取service 的计算结果
//        try {
//            int result = completableFutureD.get();
//            System.out.println("service 计算结果：" + result);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }



    }





}


class AService extends  Thread{
    public static Integer get() {
        return 10;
    }
}

class BService {
    public static int get() {
        return 11;
    }
}

class CService {
    public static int get() {
        return 12;
    }
}
