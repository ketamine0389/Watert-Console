# Left Off / Work Log
######5/20/2022
- Working on setting up `Entity` and subsequent subclasses
######5/21/2022
- Fixing up player movement
- Added `getPlayerStats()` method in `Board`
######5/22/2022
- Polishing player movement
- Added player visibility
######5/23/2022
- Begun work on Combat system
######5/24/2022
- Brainstorm day
######5/25/2022
- Continued work on Combat system
- More brainstorming documented in `#Issues`
- Begun work on Combat Detection
- Learned more about `super` and `@Override`



# Issues
- `rand()` inside of `Board.java` should be modified
  - Look into `Random.nextInt()`
- possibly too little monsters in each difficulty
- Entity - human/monster/player conversion is gonna be really whack and require some redundant code
- `movePlayer()` method inside of `Board` should begin combat upon cell being occupied
- `movePlayer()` method kinda whack
- some stuff inside of `getBoardAsString()` could be simplified
- every iteration of the game loop should check for level updates
  - if players current level is greater than average entity level, increase all entities levels & stats
- every few iterations may also cause monsters to move



# Important Links
- https://introcs.cs.princeton.edu/java/85application/jar/jar.html



# Probably Complete
- `Cell`
- `Colors`
- `Difficulty`
- `Entity`
- `EntityType`



### Version A_1.7