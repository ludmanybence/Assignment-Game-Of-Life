Conway's Game of Life is a popular challenge among intermediate programmers. It isn't really a game more like an experiment,
and of course it's way less entertaining because our implementation has no color or animation. But the point is, it works!

The rules:

• Death. If an occupied cell has 0, 1, 4, 5, 6, 7, or 8 occupied neighbors, the organism dies (0 or 1
of loneliness; 4 thru 8 of overcrowding).

• Survival. If an occupied cell has two or three neighbors, the organism survives to the next
generation.

• Birth. If an unoccupied cell has precisely three occupied neighbors, it becomes occupied by a
new organism.


The game is over if still life occours (boards stays the same forever) or every organism dies.
We do not account for oscillating states. Read more: https://en.wikipedia.org/wiki/Conway's_Game_of_Life
