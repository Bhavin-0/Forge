public class Main{
    public static void main(String[] args) {
//        ThreadCreation t1 = new ThreadCreation();
//        t1.start();
//
//        RunnableThread r1 = new RunnableThread();
//        //initializing thread object
//        Thread t2 = new Thread(r1);
//        t2.start();
//
//        //tell main thread to wait till both the threads complete
//        try{
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //practice : shared counter instance
        //creating race condition
        SharedCounter obj = new SharedCounter();

        createMultipleThreads(obj);
        System.out.println("Final Counter value : " + obj.getCounterValue());

    }

    //creating multiple threads to access the counter
    private static void createMultipleThreads(SharedCounter obj){
        RunnableThread t1 = new RunnableThread(()->{
            for(int i = 0; i < 1000; i++){
                obj.increment();
            }
        });

        RunnableThread t2 = new RunnableThread(()->{
            for(int i =0; i < 1000; i++){
                obj.increment();
            }
        });

        //intialize the thread object because using Runnable Interface
        Thread thread1 = new Thread(t1, "Thread-1");
        Thread thread2 = new Thread(t2, "Thread-2");

        thread1.start();
        thread2.start();

        //telling currentThread (Main thread ) to wait for the excution of both the threads
        try{
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}