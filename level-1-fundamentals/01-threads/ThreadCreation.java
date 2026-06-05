import java.util.Random;

//using Thread class
class ThreadCreation extends Thread{
    @Override
    public void run(){
        System.out.println("Thread is starting through Thread class... ");
    }
}

//Using runnable interface
class RunnableThread implements Runnable{
    private final Runnable task;

    /*
    * create constructor, so it can accept lambda
    */
    
    public RunnableThread(Runnable task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println(
                Thread.currentThread().getName()
               + " - Started"
        );

        //include this: as the thread has to threat the lamabda expression as another thread and then excute it
        task.run();
    }
}
