package ExecutorServiceUnderstanding;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool{
    public static void main(String[] args) {
        //cached pool is dynamic so we don't have to assign nThreads
        ExecutorService cachedPool = Executors.newCachedThreadPool();


        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of task to perform : ");
        int tasks = sc.nextInt();

        System.out.println("Submitting " + tasks + " tasks to cached pool ");

        for(int i = 0; i < tasks; i++){
            final int taskId = i + 1;
            cachedPool.execute(()->{
                System.out.println("Task : " + taskId + " , Executing by " + Thread.currentThread().getName());
                try{
                    //simulate work that takes 2 secs
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task : " + taskId + " completed by " + Thread.currentThread().getName());
            });

        }
        cachedPool.shutdown();
        System.out.println("All the tasks are submitted now Cached pool will terminate idle threads");
    }
}