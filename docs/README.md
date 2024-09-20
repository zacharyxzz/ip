# Bob User Guide

![Product Screenshot](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ3tRjKVo9vn_08c0G5kZIHmgFWl-DZDWbo8g&s)

Bob is a task manager designed to help you stay on top of your daily tasks and expenses. It allows you to manage
various types of tasks such as to-dos, deadlines, events, and expenses.
The application provides an intuitive interface for adding, viewing, and tracking your tasks.


## Features
- **Task Management**: Manage your to-dos, deadlines, and events.
- **Expense Tracking**: Keep track of your expenses.
- **Command-Based**: Simple, fast, and efficient task management through text commands.
---
## Commands

### 1. Adding a To-Do
Adds a simple to-do task to your task list.

**Command**:
```
todo [task name]
```


**Example**:
```bash
todo buy groceries
```

**Expected Output**:
```
Got it. I've added this task:
[T][ ] buy groceries
```
---
### 2. Adding a Deadline
Adds a deadline task that needs to be completed by a specific date and time.

**Command**:
```
deadline [task name] /by [YYYY-MM-DD HHmm]
```

**Example**:
```bash
deadline submit report /by 2024-10-15 1800
```

**Expected Output**:
```
Got it. I've added this task:
[D][ ] submit report (by: Oct 15 2024 18:00)
```
---
### 3. Adding an Event
Adds an event that starts and ends at a specific date and time.

**Command**:
```
event [event name] /from [YYYY-MM-DD HHmm] /to [YYYY-MM-DD HHmm]
```

**Example**:
```bash
event team meeting /from 2024-10-20 1400 /to 2024-10-20 1600
```

**Expected Output**:
```
Got it. I've added this task:
[E][ ] team meeting (from: Oct 20 2024 14:00 to: Oct 20 2024 16:00)
```
---
### 4. Adding an Expense
Tracks your expenses.

**Command**:
```
expense [item name] [amount]
```

**Example**:
```bash
expense dinner 100
```

**Expected Output**:
```
Expense 'dinner' of amount 100.0 recorded!
```
---
### 5. Finding a task
Finds a task in your list based on a task description

**Command**:
```
find [task]
```

**Example**:
```bash
find dinner
```

**Expected Output**:
```
Here are the matching tasks in your list:
1.[Ex] dinner 100.0
```
---
### 6. Marking and unmarking finished tasks
Mark a task as done and unmark any marked tasks

**Command**:
```
mark [task number]
unmark [task number]
```

**Example**:
```bash
mark 5
unmark 5
```

**Expected Output**:
```
Nice! I've marked this task as done:
[T][X] buy groceries
```
```dtd
OK, I've marked this task as not done yet:
[T][] buy groceries
```
---
### 7. Delete a task
Deletes tasks in your list.

**Command**:
```
delete [task number]
```

**Example**:
```bash
delete 5
```

**Expected Output**:
```
Noted. I;ve removed this task:
[T][] buy groceries
Now you have 4 tasks in the list.
```
---
### 8. List down your tasks
Shows you the tasks you have in your list

**Command**:
```
list
```



**Expected Output**:
```
Here are the matching tasks in your list:
1.[Ex] dinner 100.0
2.[E][ ] team meeting (from: Oct 20 2024 14:00 to: Oct 20 2024 16:00)
3.[D][ ] submit report (by: Oct 15 2024 18:00)
```
---
### 9. Say bye to Bob
Says bye to Bob.

**Command**:
```
bye
```

**Expected Output**:
```
Bye. Hope you see you again soon!
```