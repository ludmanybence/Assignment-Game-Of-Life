import java.util.ArrayList;
import java.util.Scanner;

public class GOL{

    public static void main(String[] args){
        int gens = Integer.parseInt(args[0]);
        int line_nr = Integer.parseInt(args[1]);
        ArrayList<String> temp = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < line_nr; i++){
            temp.add(in.nextLine());
        }
        in.close();
        String[] dish = new String[ temp.size() ];
        dish = temp.toArray(dish);

        String[] dish_old; // needed to check for termination of the game

        // verify if input correct
        if (dish[0].length() < 2){
            System.out.println("Board is too small.\n");
            return;
        }
        for (String i : dish){
            if (i.length() != dish[0].length()) {
                System.out.println("Board is not a rectangle.\n");
                return;
            }
        }

        // simulate generations
        for(int i= 0;i < gens;i++){
            System.out.println("Generation " + (i+1) + ":");
            print(dish);
            System.out.println();
            dish_old = dish;
            dish = life(dish);
            int terminated = compare (dish_old, dish);
            if (terminated == 1) {
                print(dish);
                System.out.println("End of game, still life is reached.\n");
                return;
            }
            if (terminated == 2) {
                print(dish);
                System.out.println("End of game, all organisms are dead.\n");
                return;
            }

        }
        print(dish);
    }

    public static int compare(String[] dish_old, String[] dish_new){
        for(int i=0; i<dish_old.length; i++){
            // at least one difference in the boards
            if (!dish_old[i].equals(dish_new[i])) return 0;
        }
        // the boards are the same
        for(int i=0; i<dish_new.length; i++){
            for (char j : dish_new[i].toCharArray()){
                // the boards are the same and the new board is not empty -> still life
                if (j == 'x') return 1;
            }
        }
        // the boards are the same and they are empty -> all organisms are dead
        return 2;
    }

    public static String[] life(String[] dish){

        String[] newGen= new String[dish.length];

        //Initialize every row to empty string
        for (int i = 0; i < newGen.length; i++) {
            newGen[i] = "";
        }

        char alive = 'x';
        char dead = 'o';

        for (int i = 0; i < dish.length; i++) {
            for (int j = 0; j < dish[0].length(); j++) {

                boolean isAlive = (dish[i].charAt(j) == alive);
                int neighboursAlive = 0;

                Position cellPos = checkCoordinates(j,i, dish.length);

                //Check neighbouring coordinates
                switch(cellPos){

                    case TOPLEFT:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{1,0},
                                new int[]{0,1},
                                new int[]{1,1});
                        break;
                    case TOPRIGHT:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{j-1, 0},
                                new int[]{j,1},
                                new int[]{j-1,1});
                        break;
                    case BOTTOMLEFT:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{0,i-1},
                                new int[]{1, i},
                                new int[]{1, i-1});
                        break;
                    case BOTTOMRIGHT:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{j-1,i},
                                new int[]{j,i-1},
                                new int[]{j-1,i-1});
                        break;
                    case RIGHT:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{j,i-1},
                                new int[]{j,i+1},
                                new int[]{j-1,i-1},
                                new int[]{j-1,i+1},
                                new int[]{j-1,i});
                        break;
                    case LEFT:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{j,i-1},
                                new int[]{j,i+1},
                                new int[]{j+1,i-1},
                                new int[]{j+1,i+1},
                                new int[]{j+1,i});
                        break;
                    case TOP:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{j,i+1},
                                new int[]{j+1,i},
                                new int[]{j-1,i},
                                new int[]{j+1,i+1},
                                new int[]{j-1,i+1});
                        break;
                    case BOTTOM:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{j,i-1},
                                new int[]{j+1,i},
                                new int[]{j-1,i},
                                new int[]{j+1,i-1},
                                new int[]{j-1,i-1});
                        break;
                    case MIDDLE:
                        neighboursAlive = checkNeighbours(dish,
                                new int[]{j,i+1},
                                new int[]{j,i-1},
                                new int[]{j+1,i},
                                new int[]{j-1,i},
                                new int[]{j+1,i+1},
                                new int[]{j-1,i+1},
                                new int[]{j+1,i-1},
                                new int[]{j-1,i-1});
                        break;


                }

                //DEATH | Alive but lonely or too crowded
                if(isAlive && (neighboursAlive < 2 || neighboursAlive > 3)){
                    newGen[i]+=dead;
                }
                //BIRTH | Dead but with 3 neighbours
                else if(!isAlive && neighboursAlive == 3){
                    newGen[i]+=alive;
                }
                //STAY SAME | No change
                else {
                    newGen[i]+=dish[i].charAt(j);
                }

            }
        }

        return newGen;
    }

    //Return where in the grid a cell is (a corner, a side or the middle)
    private static Position checkCoordinates(int x, int y, int arrayLength){

        Position pos = Position.MIDDLE;
        //arrayLength-1 because we compare to indices
        arrayLength--;

        if(x == 0){
            if(y == 0){
               pos = Position.TOPLEFT;
            }
            else if (y == arrayLength){
                pos = Position.BOTTOMLEFT;
            }
            else{
                pos = Position.LEFT;
            }
        }
        else if(x==arrayLength){
            if(y==0){
                pos = Position.TOPRIGHT;
            }
            else if(y==arrayLength){
                pos = Position.BOTTOMRIGHT;
            }
            else{
                pos = Position.RIGHT;
            }
        }
        else if(y==0){
            pos = Position.TOP;
        }
        else if(y==arrayLength){
            pos = Position.BOTTOM;
        }

        return pos;

    }

    //Count the number of alive neighbours around a cell
    private static int checkNeighbours(String[] dish, int[]... coords){

        char alive = 'x';
        int neighboursAlive = 0;

        for (int[] i : coords) {
            if (dish[i[1]].charAt(i[0]) == alive) {
                neighboursAlive++;
            }
        }

        return neighboursAlive;

    }

    public static void print(String[] dish){
        for(String s : dish){
            System.out.println(s);
        }
    }

    private enum Position{
        TOPLEFT,
        TOPRIGHT,
        BOTTOMLEFT,
        BOTTOMRIGHT,
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        MIDDLE
    }

}

