# Mutex

The class [fr.jhelp.tasks.Mutex](../../src/main/java/fr/jhelp/tasks/Mutex.kt)

Permits do do some atomic operations.
Operations does with same mutex can't be done in same time.

Image a group of value you don't want that several thread change them in same time.
Mutex permit to create a safe environment called critical section where you can do operations that
change the group safely.

For example the classic bank account issue :

Peter have a bank account, he receive his pay in it and his credit card take money from it for pay.

We can represents the bank account by a class :

```kotlin
class BankAccount
{
    private var money: Int = 0

    fun receiveMoney(amount: Int)
    {
        this.money += amount
    }

    fun takeMoney(amount: Int)
    {
        this.money -= amount
    }
}
```

It looks great but you have to know that  :

Each methods work with its own memory stack.
And remember that your code is compile in byte code (Instructions understandable by the JVM)

`receiveMoney` code is reality is (R stands for `receiveMoney`) :

* (R1) Get `this.money` value and push it in `receiveMoney`'s stack
* (R2) Get `amount` value and push it in `receiveMoney`'s stack
* (R3) Pop from `receiveMoney`'s stack two last pushed valued, add them and push the result on stack
* (R4) Pop from `receiveMoney`'s stack the last pushed and assign it to `this.money`

`takeMoney` code is reality is (T stands for `takeMoney`) :

* (T1) Get `this.money` value and push it in `takeMoney`'s stack
* (T2) Get `amount` value and push it in `takeMoney`'s stack
* (T3) Pop from `takeMoney`'s stack two last pushed valued, subtract them and push the result on
  stack
* (T4) Pop from `takeMoney`'s stack the last pushed and assign it to `this.money`

If `receiveMoney` and `takeMoney`are called in same time (for example Peter buy something in same
time he received is pay)
One of method is called first, but can be interrupt by any moment by the other to take the hand
later.

A bad scenario can be : (T1) (T2) (R1) (R2) (R3) (R4) (T3) (T4)
If things happen in this order it is a bad new for Peter. It is like he never received his pay.

Explanation on imagine he have at start 500, want buy something at 100 and should receive 1000. We
expect to have at end 1400.
But not the case. Here we note each value in table step by step to understand what happen.
Stack are note left to right separate by a semicolon. Left is the top of the stack.

|  Step   | this.money | `receiveMoney`'s stack | `takeMoney`'s stack |
|:-------:|:----------:|:-----------------------|:--------------------|
| Initial |    500     |                        |                     |
|   T1    |    500     |                        | 500                 |
|   T2    |    500     |                        | 100;500             |
|   R1    |    500     | 500                    | 100;500             |
|   R2    |    500     | 1000;500               | 100;500             |
|   R3    |    500     | 1500                   | 100;500             |
|   R4    |    1500    |                        | 100;500             |
|   T3    |    1500    |                        | 400                 |
|   T4    |    400     |                        |                     |

For this never happen we want be sure that each method is completely done before be able do the
other one. That's the role of `Mutex`

In our example :

```kotlin
import fr.jhelp.tasks.Mutex

class BankAccount
{
    private var money: Int = 0
    private val mutex = Mutex()

    fun receiveMoney(amount: Int)
    {
        this.mutex {
            this.money += amount
        }
    }

    fun takeMoney(amount: Int)
    {
        this.mutex {
            this.money -= amount
        }
    }
}
```

When a thread enter in the `Mutex` open curly it lock the mutex,
so that other thread that what to enter have to wait that the thread finish its work and pass by the
close curly to un lock the door for the next thread.

Just remember not to do long operation in critical section. 
Make it shorter as possible to not experiment some slowdown or in worst scenario memory full. 
