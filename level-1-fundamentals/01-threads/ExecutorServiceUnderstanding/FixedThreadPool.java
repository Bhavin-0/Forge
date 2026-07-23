package ExecutorServiceUnderstanding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {

    public static void main(String[] args) {
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);

        System.out.println("--- Submitting 5 task to 2 workerThreads --- ");
        int tasks = 5;

        for(int i = 1; i <= tasks; i++){
            final int taskId = i;
            fixedPool.execute(()->{
                System.out.println("Task : " + taskId + " : started by Thread" + Thread.currentThread().getName());
                try{
                    //Simulate work that takes 2 secs
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    System.out.println("ERROR: " + e.getMessage());
                }
                System.out.println("Task : " + taskId + " : completed by Thread" + Thread.currentThread().getName());
            });
        }
        fixedPool.shutdown();

    }
}