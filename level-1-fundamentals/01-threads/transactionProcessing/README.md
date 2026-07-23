# Banking Transaction Processing System

## Overview

This project simulates a real-world banking transaction processing system using the Producer-Consumer pattern in Java.

The goal is to understand fundamental concurrency concepts such as:

* Threads
* Synchronization
* Race Conditions
* wait() / notifyAll()
* Shared Resources
* Daemon Threads
* Thread Communication

The project models how transaction requests flow through a banking system similar to those used in large financial institutions.

---

# Problem Statement

Design and implement a transaction processing system where multiple banking services generate transactions and multiple processors consume them.

A shared Buffer acts as a transaction queue between producers and consumers.

The system must ensure:

* Producers cannot add transactions when the buffer is full.
* Consumers cannot consume transactions when the buffer is empty.
* No race conditions occur.
* Background monitoring is performed using a daemon thread.

---

# System Components

## Producer

Represents transaction-generating services such as:

* ATM Service
* UPI Service
* Credit Card Service
* Net Banking Service

Responsibilities:

* Generate transactions continuously.
* Add transactions to the shared buffer.
* Wait when the buffer becomes full.

---

## Buffer

Represents the transaction queue shared by all threads.

Responsibilities:

* Store transactions.
* Maintain a fixed capacity.
* Coordinate producers and consumers.
* Prevent data corruption through synchronization.

Required Operations:

```java
put(Transaction transaction)
take()
size()
isFull()
isEmpty()
```

---

## Consumer

Represents transaction processing services.

Responsibilities:

* Retrieve transactions from the buffer.
* Process transactions.
* Wait when the buffer is empty.

Example:

```text
Processing Transaction-101
Processing Transaction-102
```

---

## Logger Daemon

A background monitoring service.

Responsibilities:

* Periodically log system activity.
* Display current buffer size.
* Display produced transaction count.
* Display consumed transaction count.

Example Output:

```text
========== SYSTEM STATS ==========
Buffer Size : 4
Produced    : 105
Consumed    : 101
==================================
```

The logger must run as a daemon thread.

---

# Functional Requirements

### FR-1

The buffer shall have a fixed capacity.

### FR-2

Producers shall add transactions to the buffer.

### FR-3

Consumers shall remove transactions from the buffer.

### FR-4

A producer shall wait when the buffer is full.

### FR-5

A consumer shall wait when the buffer is empty.

### FR-6

The system shall prevent race conditions.

### FR-7

The system shall maintain transaction statistics.

### FR-8

The daemon logger shall periodically display system metrics.

---

# Concurrency Concepts Covered

## 1. Threads

Multiple threads execute concurrently.

Examples:

* Producer Threads
* Consumer Threads
* Logger Thread

---

## 2. Critical Section

A section of code where shared data is modified.

Example:

```java
buffer.put(transaction);
buffer.take();
```

---

## 3. Mutual Exclusion

Only one thread should access shared state at a time.

Mechanism:

```java
synchronized
```

---

## 4. Thread Communication

Threads communicate using:

```java
wait()
notifyAll()
```

---

## 5. Race Condition

Occurs when multiple threads modify shared data simultaneously.

Example:

```java
counter++;
```

Without synchronization the result may be incorrect.

---

## 6. Daemon Thread

A background thread used for monitoring and logging.

Example:

```java
loggerThread.setDaemon(true);
```

---

## 7. Visibility Problem

One thread updates a variable but another thread cannot immediately observe the change.

Solution:

```java
volatile
```

---

## 8. Atomic Operations

Some operations appear simple but execute in multiple steps.

Example:

```java
counter++;
```

Possible solution:

```java
AtomicInteger
```

---

# Suggested Project Structure

```text
01-Thread/
│
├── BankingTransactionProcessing.java
├── Transaction.java
├── Buffer.java
├── Producer.java
├── Consumer.java
├── LoggerDaemon.java
└── README.md
```

---

# Future Enhancements

Version 1

* Single Producer
* Single Consumer

Version 2

* Multiple Producers
* Multiple Consumers

Version 3

* Daemon Logger

Version 4

* ReentrantLock
* Condition Variables

Version 5

* BlockingQueue Implementation

Version 6

* ExecutorService Integration

Version 7

* Graceful Shutdown Mechanism

---

# Interview Topics Demonstrated

After completing this project, you should be able to explain:

* Producer Consumer Problem
* Race Conditions
* Critical Sections
* Synchronization
* wait() vs sleep()
* notify() vs notifyAll()
* Daemon Threads
* volatile
* AtomicInteger
* ReentrantLock
* Condition
* BlockingQueue
* Thread Safety

These concepts are frequently discussed in Java backend and financial technology interviews.

