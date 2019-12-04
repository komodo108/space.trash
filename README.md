# Space.Trash
A Programming Puzzle Game· Final Project for CS4303.

### Work In Progress
Python is now fully working in its own decoupled thread, interaction happens over queues.
Now to work on the main game, bots need to be able to move and interact with other objects.

### Story
You are working in a bot factory & you come across an unusual bot which you can't work on.
You are fired from your job. Later, you find some trashed bots including the unusual bot in the trash.

They want you to help them return to their home. You go from planet to planet, programming the
the trashed bots to help you acquire the resources needed to progress.

### Features (ALL WIP)
* Integration with Python to program with
* Built-in code editor with special features (e.g. non-editable sections)
* Initial tutorial levels + tutorials throughout
* Challenging animals with AI
* Many levels where all the bots features will need to be used

### Levels
A level itself should be a winable puzzle with its own environment made from the components implemented in the game.
The player should be able to fully explore the level & interact with stuff inside the level.

#### Making levels
WIP, load levels from file...

#### Player interaction
The player needs to have functions which they can call to interact with the level.
There are 2 types of these, functions where the player directly interacts, such as moving & functions where the player queries something from the level. Both of
Both of these are allowed under the python system

#### List of bot functions
* `move(x)` - moves the player to the `x` blocks in the direction they're facing
* `face(direction)` - rotates the player to face a given direction
* `hold()` - picks up and attempts to use an item
* [?] `attack(direction)` - face direction & attack 
* `interact(direction)` - face direction & interact with whatever is there
    * [list of interactable things]
* `canMove()` - returns if the player can move
* [?] `isHold()` - returns if the item you're on can be held

### Python Integration
We use [Jython](https://www.jython.org/) for Python integration. There are 3 important parts of the integration: parsing, executing & external interaction.

#### Parsing
Jython allows the user to execute any valid program in Python 2.7, with the additional capability of being able to interact with Java objects.
To safeguard the user and functionality of the game, we do not want the user to have **FULL** control of Python 2.7, e.g. we don't want the user to write files, etc.
To do this, we parse the code and look for disallowed statements, if one is found then we don't allow the program to execute and give an error to the user.

#### Executing
Jython will execute the code using the thread it is currently on, and will execute the entirely of the code it is given.
We execute Jython on a different thread so it doesn't affect rendering on the main thread.

The user is expected to write arbitrary code, and we expect that code not to function most of the time, e.g. the user could write an infinite loop. We need a mechanism to stop execution prematurely and allow the user to try again, i.e. kill the thread.
When the user clicks a button on the main thread, we want the Python thread to stop. We can't directly kill the thread using `Thread.kill()` since this messes up the JVM.

Instead, we start by setting up a *Trace function* on the Python thread which is called between after the execution of each statement. 
When the user clicks the button, we setup an `abort` variable on the main thread. The *Trace function* will see that `abort` is set and throw an Exception which will stop Python execution.
This Exception is caught on the Python thread, and the thread concludes it's execution. (Thank you [@tkohn](https://bugs.jython.org/issue2530))

#### External Interaction
We want Jython to be able to affect things on the main thread, e.g. moving the bots around. We cannot directly call these since some data structures are not concurrent, and we want to ensure the developer knows which functions are visible to Python. 
We expose some implementation to Python, and transfer information between there and the main object using a concurrent queue. Please see the implementation for more on how this is done.

### Running the Game
WIP