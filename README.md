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
A level itself should be a winnable puzzle with its own environment made from the components implemented in the game.
The player should be able to fully explore the level & interact with stuff inside the level.

#### Making levels
A level is defined by a level JSON file, which is then loaded in by the game. This level file ***must*** be in correct format for the loader to function.
An example level JSON file is given below, for it to be valid comments will need to be removed & values must be added:
```json
{
  "map": {
    // A setting from level.map.Settings - will set the background image
    "setting": "MARS",
    
    // [OPTIONAL] An array of all walls in the level, position and sizes must be given
    "walls": [
      {
        "wall": {
          "x": 1,
          "y": 1,
          "height": 1,
          "width": 1
        }
      }
    ],

    // [OPTIONAL] Location of the goal cell
    "goal": {
      "x": 14,
      "y": 14
    },

    // [OPTIONAL] An array of all items in the level, position and type must be given
    "items": [
      {
        "item" : {
          "type": "test", // This must be the prefix of a class name in level.item, e.g. a "KeyItem" would be "key"
          "x": 1,
          "y": 1
        }
      }
    ],

    // [OPTIONAL] An array of all containers in the level, position and type must be given
    "containers": [
      {
        "container": {
          "type": "test", // This must be the prefix of a class name in level.container
          "x": 34,
          "y": 24
        }
      }
    ]
  },
  
  // The win condition must be the prefix of a class name in level.win
  "win": "test",

  // The position which the bot will spawn at
  "bot": {
    "x": 1,
    "y": 1
  }
}
```

#### Player interaction
The player needs to have functions which they can call to interact with the level.
There are 2 types of these, functions where the player directly interacts, such as moving & functions where the player queries something from the level. Both of
Both of these are allowed under the python system

#### List of bot functions
* `move(x)` - moves the player to the `x` blocks in the direction they're facing
* `face(direction)` - rotates the player to face a given direction
* `hold()` - picks up and attempts to use the item the player is on top of
* [?] `attack(direction)` - face direction & attack 
* `interact()` - interact with whatever the player is on top of
    * [list of interactable things]
* `canMove(x)` - returns if the player can move `x` blocks in that direction
* [more functions]

#### Moving
The bot and all enemies in the game have collision, and will bounce off of the side of the window & walls in the level.

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
We expose some implementation to Python, and transfer information between there and the main object in one of two ways.

If the object we are updating is *not* concurrent, then we will use a concurrent action queue to transfer the call between the two threads.
If not, then Python will directly execute Java code which will update the concurrent object (please ensure you still maintain the ability to abort & sleeping loops on the Python thread).
Please see the implementation for more on how this is done.

### Running the Game
WIP