package ru.ncedu.course.service.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {

    private List<Thread> threads = new ArrayList<>();
    private BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private boolean active = true;

    private class TaskExecutorJob implements Runnable {

        @Override
        public void run() {
            while(active) {
                try {
                    Runnable task = tasks.take();
                    task.run();
                } catch (Exception e) {
                    System.out.println("Could not execute task");
                    e.printStackTrace();
                }
            }
        }
    }

    public ThreadPool(int threadsCount) {
        for(int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(new TaskExecutorJob());
            thread.start();
            threads.add(thread);
        }
    }

    public void execute(Runnable task) {
        tasks.offer(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        FutureTask<T> result = new FutureTask<>(task);
        execute(result);
        return result;
    }

    public void deactivate() {
        active = false;
        for(int i = 0; i < threads.size(); i++) {
            tasks.offer(() -> {});
        }
    }

}
