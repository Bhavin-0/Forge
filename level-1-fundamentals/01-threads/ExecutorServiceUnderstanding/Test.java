package ExecutorServiceUnderstanding;

public class Test {

}

class PrintTask extends Thread{
    private String name;

    public PrintTask(String name){
        this.name = name;
    }

    @Override
    public void run(){
        System.out.println("Task Started");
        for(int i = 0; i < 10; i++){
            System.out.println("Hey! " + name + " I am doing task #" + i);
        }
        System.out.println("Task Completed");
    }
}