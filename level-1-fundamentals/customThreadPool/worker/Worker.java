package worker;

import queue.MyBlockingQueue;

public class Worker extends Thread {
    private final MyBlockingQueue<Runnable> queue;

    public Worker(MyBlockingQueue<Runnable> queue){
        this.queue = queue;
    }

    @Override
    public void run(){
        while(true){
           try{
               Runnable task = queue.take();
               //Make sure the thread does not die due to interruption
               try{
                   task.run();
               }catch (Exception e){
                   System.out.println("Task Execution Failed : " + e.getMessage());
                   e.printStackTrace();
               }
           }catch (InterruptedException e){
               //Restores the interrupted status and terminate the worker
               Thread.currentThread().interrupt();
               break;
           }
        }
    }
}
