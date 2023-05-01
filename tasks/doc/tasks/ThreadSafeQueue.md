# ThreadSafeQueue

The class [ThreadSafeQueue](../../src/main/java/fr/jhelp/tasks/ThreadSafeQueue.kt)

Its a queue , its stores elements in FIFO (First In First Out) algorithm.
Like a queue in supermarket, the first arrive, the first served.

It's a queue where access to method are thread safe.
That is to say it can be shared between several thread without any risks.

The price for thread safety is method access is slower than regular queue.
So if you don't plan to share your queue or your object that embed it, using a regular queue is
recommended.

It contains methods/parameters :

* `size` The size of the queue. In an other word, number of elements stored in the queue
* `empty` Indicated if queue is empty. That is to say, if their no element in it
* `notEmpty` Reversed of `empty`, in indicates if that at least one element in the queue
* `enque` Add an element at the end of the queue. If the queue was empty, it becomes the first of
  the queue.
* `dequeue` Take the first element of the queue. Remove it from the queue at return it.
  An `IllegalStateException` is throws if the queue is empty
