class SharedCounter{
    int counter = 0;

    public void increment(){
        System.out.println(Thread.currentThread().getName() +"Before Critical Seciton");
        synchronized (this){
            counter++;
        }
        System.out.println(Thread.currentThread().getName() +"After Critical Section");
    }

    public int getCounterValue(){
        return counter;
    }
}
/*
Now to prevent race condition: 2 methods
1. Syncronized block - syncronizing only the critical part of the race condition
2. Syncronized method - syncronizing whole method
*/