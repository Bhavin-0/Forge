# Java Executor Framework

## Why was the Executor Framework introduced?

Before Java 5, every asynchronous task was typically executed by creating a new `Thread`.

```java
new Thread(task).start();
```

This works for a small number of tasks, but it becomes inefficient when the application needs to execute hundreds or thousands of tasks.

### Problems with manually creating threads

- Creating a thread is expensive.
- Destroying threads also has a cost.
- Too many threads consume large amounts of memory.
- Excessive threads increase CPU context switching.
- Thread management becomes difficult.

The **Executor Framework** solves these problems by managing threads for us.

---

# What is Executor?

`Executor` is the simplest interface in the Executor Framework.

It provides a high-level mechanism for executing tasks **without directly managing threads**.

Instead of worrying about:

- Creating threads
- Starting threads
- Managing thread lifecycle

we simply submit a task, and the Executor decides **how** it should be executed.

---

## Declaration

```java
public interface Executor {
    void execute(Runnable command);
}
```

The interface contains only one method:

```java
execute(Runnable task);
```

It accepts a `Runnable` task and executes it.

---

# How Executor Works

Instead of this:

```
Task 1 → Thread 1
Task 2 → Thread 2
Task 3 → Thread 3
...
Task 10000 → Thread 10000
```

we create a **thread pool**.

```
               Thread Pool

          Thread-1
Task ---> Thread-2
Task ---> Thread-3
Task ---> Thread-4
Task ---> Thread-5
```

Threads are **reused** instead of continuously created and destroyed.

This significantly improves performance.

---

# Executor vs ExecutorService

Think of it like this:

## Executor

Only knows how to execute a task.

```java
execute(Runnable task)
```

---

## ExecutorService

An advanced interface that extends `Executor`.

It adds features such as:

- Returning results
- Managing thread pools
- Shutting down executors
- Waiting for task completion

In real-world applications, we almost always use `ExecutorService`.

---

# Common ExecutorService Methods

## execute()

Executes a `Runnable`.

```java
executor.execute(task);
```

Returns nothing.

---

## submit()

Submits a task for execution.

```java
Future<Integer> future = executor.submit(task);
```

Unlike `execute()`, it can return a `Future`.

Use `submit()` when you want to retrieve the result later.

---

## shutdown()

Stops accepting new tasks.

Already submitted tasks continue executing until completion.

```java
executor.shutdown();
```

---

## shutdownNow()

Attempts to stop all running tasks immediately.

```java
executor.shutdownNow();
```

- Interrupts running threads.
- Tasks may or may not stop depending on whether they handle interruptions.

---

## awaitTermination()

Waits until all tasks complete after shutdown.

```java
executor.awaitTermination(timeout, unit);
```

Useful when you need to wait for graceful shutdown.

---

# Creating a Fixed Thread Pool

```java
ExecutorService executor =
        Executors.newFixedThreadPool(4);
```

This creates a pool containing **4 worker threads**.

No matter how many tasks you submit, only **4 execute simultaneously**.

Remaining tasks wait in a queue.

---

# Choosing the Right Thread Pool Size

The correct thread count depends on the type of work.

---

## CPU-Intensive Tasks

These spend almost all their time performing calculations.

Examples:

- Sorting
- Image processing
- Encryption
- Compression
- Prime number calculation
- Scientific computation

### Best Practice

```
Number of Threads ≈ Number of CPU Cores
```

Example:

8-core CPU → approximately 8 worker threads.

### Why?

Using more threads causes:

- More context switching
- Higher scheduling overhead
- Lower overall performance

Since all threads compete for the CPU, adding extra threads usually does not increase throughput.

---

## What is Context Switching?

A CPU can execute only a limited number of threads simultaneously.

When multiple threads compete for CPU time, the operating system repeatedly pauses one thread and resumes another.

This process is called **context switching**.

Too much context switching wastes CPU time because the processor spends time switching between threads instead of doing useful work.

---

## I/O-Intensive Tasks

These frequently wait for external resources.

Examples:

- Database queries
- HTTP requests
- Reading files
- Writing files
- Network communication

While one thread waits for I/O, another thread can utilize the CPU.

Therefore, using **more threads than CPU cores** often improves performance.

---

### Approximate Thread Count Formula

```
Threads = CPU Cores × (1 + Wait Time / Compute Time)
```

Example:

```
CPU Cores = 8

Wait Time = 90 ms

Compute Time = 10 ms

Threads = 8 × (1 + 90/10)
        = 80 threads
```

This is only a guideline.

Always profile and benchmark real applications.

---

# What is a CPU-Intensive Task?

A CPU-intensive task spends most of its time performing calculations.

Examples:

- Mathematical algorithms
- Image processing
- Video encoding
- Compression
- Encryption

These tasks rely primarily on the processor rather than waiting for external resources.

---

# Types of Thread Pools

## 1. Fixed Thread Pool

```java
Executors.newFixedThreadPool(n)
```

Characteristics:

- Fixed number of worker threads.
- Extra tasks wait in a queue.
- Threads are reused.
- Predictable resource usage.

Best suited for:

- CPU-intensive workloads
- Applications with controlled concurrency

---

## 2. Cached Thread Pool

```java
Executors.newCachedThreadPool()
```

Characteristics:

- Creates new threads as needed.
- Reuses idle threads.
- No fixed upper limit.
- Idle threads are removed after a period of inactivity.

Best suited for:

- Many short-lived asynchronous tasks.
- Applications with bursts of activity.

Be careful:

Since it is effectively unbounded, submitting too many long-running tasks may create a very large number of threads.

---

## 3. Single Thread Executor

```java
Executors.newSingleThreadExecutor()
```

Characteristics:

- Uses exactly one worker thread.
- Tasks execute one after another.
- Maintains task order (FIFO).
- Guarantees sequential execution.

Useful when task order matters.

Examples:

- Logging
- Writing to a file
- Processing events sequentially

---

# SingleThreadExecutor vs FixedThreadPool(1)

Both execute only one task at a time.

However, they are **not identical**.

## FixedThreadPool(1)

```java
Executors.newFixedThreadPool(1)
```

Internally uses a `ThreadPoolExecutor`.

If you have a reference to the underlying executor, its pool size can later be increased.

```java
pool.setCorePoolSize(4);
```

---

## SingleThreadExecutor

```java
Executors.newSingleThreadExecutor()
```

Always guarantees exactly one worker thread.

Its implementation intentionally prevents changing the pool size.

This makes it safer when sequential execution must never be broken.

---

# Summary

## Use Executor when

- You only need to execute tasks.

---

## Use ExecutorService when

- Managing thread pools
- Returning results
- Graceful shutdown
- Production applications

---

## Thread Pool Selection

| Thread Pool | Best For |
|-------------|----------|
| Fixed Thread Pool | CPU-intensive tasks |
| Cached Thread Pool | Many short-lived asynchronous tasks |
| Single Thread Executor | Sequential execution |

---

# Key Takeaways

- Never create thousands of threads manually.
- Prefer thread pools.
- Match the thread pool size to the workload.
- CPU-bound tasks → approximately number of CPU cores.
- I/O-bound tasks → often more threads than CPU cores.
- Always shut down an `ExecutorService` when finished.