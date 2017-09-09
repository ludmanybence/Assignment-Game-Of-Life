import java.util.*;

public class GOL{

    public static void main(String[] args){
        int gens = Integer.parseInt(args[0]);
        int line_nr = Integer.parseInt(args[1]);
        List<String> temp = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < line_nr; i++){
            temp.add(in.nextLine());
        }
        in.close();
        String[] dish = new String[ temp.size() ];
        temp.toArray( dish );

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
            //System.out.println("Generation " + i + ":");
            //print(dish);
            dish_old = dish;
            dish = life(dish);
            int terminated = compare (dish_old, dish);
            if (terminated == 1) {
                print(dish);
                //System.out.println("End of game, still life is reached.\n");
                return;
            }
            if (terminated == 2) {
                print(dish);
                //System.out.println("End of game, all organisms are dead.\n");
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

        //TODO: implement this function

        return newGen;
    }

    public static void print(String[] dish){
        for(String s: dish){
            System.out.println(s);
        }
    }

}