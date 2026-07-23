# Custom Thread Pool

## Goal

The objective of this project is to understand how Java's `ExecutorService` and `ThreadPoolExecutor` work internally by implementing a custom thread pool from scratch.

Instead of relying on Java's concurrency utilities, we will build each component step by step to understand the underlying concepts.

---

# Learning Roadmap

- [x] Design the Blocking Queue
- [x] Implement the Blocking Queue
- [ ] Create Worker Threads
- [ ] Create Task Producer (`submit()`)
- [ ] Implement the Thread Pool
- [ ] Graceful Shutdown
- [ ] Compare with Java's `ThreadPoolExecutor`

---

# Step 1: Build a Blocking Queue

A thread pool requires a shared task queue where producers submit tasks and worker threads retrieve tasks for execution.

The blocking queue acts as the communication bridge between these two components.

```text
Producer
    │
    ▼
+-------------------+
|  Blocking Queue   |
+-------------------+
    ▲
    │
Worker Threads
```

---

## What is a Blocking Queue?

A **Blocking Queue** is a thread-safe queue that automatically blocks threads when an operation cannot be completed.

### Producer (`put()`)

- Inserts a task into the queue.
- If the queue is full, the producer thread waits until space becomes available.

### Consumer (`take()`)

- Removes a task from the queue.
- If the queue is empty, the consumer thread waits until a new task is added.

---

## Implementation

This implementation uses:

- Fixed-size Array
- Circular Buffer (Ring Buffer)
- `synchronized`
- `wait()`
- `notifyAll()`

instead of Java's built-in `BlockingQueue`.

---

## Why Circular Buffer?

Using an `ArrayList` would require shifting elements every time the first element is removed, making `take()` an **O(n)** operation.

A circular buffer avoids shifting by maintaining two pointers:

- `head` → Next element to remove
- `tail` → Next position to insert

Both `put()` and `take()` become **O(1)** operations.

---

## Queue Structure

```java
private final Object[] items;
private final int capacity;

private int head;
private int tail;
private int size;
```

### Field Description

| Field | Purpose |
|--------|---------|
| `items` | Stores queue elements |
| `capacity` | Maximum queue size |
| `head` | Index of the next element to remove |
| `tail` | Index where the next element will be inserted |
| `size` | Current number of elements in the queue |

---

## Synchronization

The queue is synchronized using Java's intrinsic monitor.

### `wait()`

Suspends the current thread until another thread changes the queue state.

### `notifyAll()`

Wakes all waiting threads. Each thread rechecks the queue condition before proceeding.

---

## Operations

### put(T item)

1. Wait while the queue is full.
2. Insert the item.
3. Move the `tail` pointer.
4. Increment `size`.
5. Wake waiting threads.

---

### take()

1. Wait while the queue is empty.
2. Remove the item.
3. Move the `head` pointer.
4. Decrement `size`.
5. Wake waiting threads.
6. Return the removed item.

---

# Next Step

After implementing the blocking queue, the next component is the **Worker Thread**.

Each worker continuously performs the following loop:

```java
while (true) {
    Runnable task = queue.take();
    task.run();
}
```

Multiple workers consuming tasks from the same blocking queue form the foundation of a thread pool.

        BlockingQueue ✅
                │
                ▼
        Worker Thread
                │
                ▼
        Task Interface (Runnable)
                │
                ▼
        Custom Thread Pool
                │       
                ▼
        Graceful Shutdown
                │
                ▼
        Future / Callable (Optional)
                │
                ▼
        RejectedExecutionHandler
                │
                ▼
        Dynamic Thread Pool (Advanced)