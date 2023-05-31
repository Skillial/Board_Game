import java.util.*;
public class main{
    public static int[] R = {1, 2, 3, 4, 5, 6, 7};
    public static int[] C = {1, 2, 3, 4, 5};
    public static ArrayList<pairs> E = new ArrayList<>();
    public static ArrayList<pairs> Alpha = new ArrayList<>();
    public static ArrayList<pairs> Y = new ArrayList<>();
    public static ArrayList<pairs> Beta = new ArrayList<>();
    public static ArrayList<pairs> P = new ArrayList<>();       //Cross product of R and C
    public static ArrayList<pairs> S = new ArrayList<>();       //x,y is member of P where x %2 = y %2  -> where the pieces start
    public static ArrayList<pairs> Free = new ArrayList<>();    //spaces that are free
    public static ArrayList<pairs> Occupied = new ArrayList<>();//spaces that are occupied
    public static boolean aTurn = true, ok = false;
    public static final Scanner inputManager = new Scanner(System.in);


    //Method(aka function) descriptions are in the submitted write-up
    public static int findIndex(ArrayList<pairs>Search, int a, int b){
        int i=0;
        for(pairs pairs: Search){
            if(pairs.getX() == a && pairs.getY() == b){
                return i;
            }
            i++;
        }
        return -1;
    }
    public static boolean checkContain(ArrayList<pairs> Search, int x, int y, char sym){
        for(pairs pairs: Search){
            if(pairs.getX() == x && pairs.getY() == y && pairs.getSym() == sym){
                return true;
            }
        }
        return false;
    }
    public static pairs checkPair(ArrayList<pairs>List, int x, int y) {
        for (pairs pair : List) {
            if (pair.getX() == x && pair.getY() == y) {
                return pair;
            }
        }
        return null;
    }

    public static boolean GameOver (boolean over) {
        int remainingA=Alpha.size(), remainingB=Beta.size();
        for (int j=Alpha.size()-1;j>=0;j--)
            for (pairs pairs : Y)
                if (Alpha.get(j).getX() == pairs.getX() && Alpha.get(j).getY() == pairs.getY())
                    remainingA--;
        for (int j=Beta.size()-1;j>=0;j--)
            for (pairs pairs : E)
                if (Beta.get(j).getX() == pairs.getX() && Beta.get(j).getY() == pairs.getY())
                    remainingB--;

        if (Beta.size()==0 || Alpha.size()>0 && remainingA==0) {
            System.out.println("result = Alpha Wins");
            return true;
        }
        if (Alpha.size()==0 || (Beta.size()>0 && remainingB==0)) {
            System.out.println("result = Beta Wins");
            return true;
        }
        return false;
    }

    public static void NextPlayerMove(pairs Previous, pairs Next) {
        int a = Previous.getX(), b = Previous.getY(), c = Next.getX(), d = Next.getY();
        if (aTurn && checkContain(Alpha, Previous.getX(), Previous.getY(), 'A') && a == c + 1 && (d == b || d == b + 1 || b == d + 1)){
            ok = !ok;
        }
        if (!aTurn && checkContain(Beta, Previous.getX(), Previous.getY(), 'B') && c == a + 1 && (d == b || d == b + 1 || b == d + 1)){
            ok = !ok;
        }
        if (ok && aTurn && checkPair(Free, c, d) != null) {
            Alpha.remove(findIndex(Alpha, a, b));
            Alpha.add(Next);
            aTurn = !aTurn;
            ok = !ok;
        }
        if (ok && !aTurn && checkPair(Free, c, d) != null) {
            Beta.remove(findIndex(Beta, a, b));
            Beta.add(Next);
            aTurn = !aTurn;
            ok = !ok;
        }
        if (ok && aTurn && checkContain(Beta, Next.getX(), Next.getY(), 'B') && !checkContain(S, Next.getX(), Next.getY(), Character.MIN_VALUE)) {
            ok = !ok;
        }
        if (ok && aTurn && checkContain(Beta, Next.getX(), Next.getY(), 'B') && checkContain(S, Next.getX(), Next.getY(), Character.MIN_VALUE)) {
            Beta.remove(findIndex(Beta, c, d));
            Alpha.remove(findIndex(Alpha, a, b));
            Alpha.add(Next);
            aTurn = !aTurn;
            ok = !ok;
        }
        if (ok && !aTurn && checkContain(Alpha, Next.getX(), Next.getY(), 'A') && !checkContain(S, Next.getX(), Next.getY(), Character.MIN_VALUE)) {
            ok = !ok;
        }
        if (ok && !aTurn && checkContain(Alpha, Next.getX(), Next.getY(), 'A') && checkContain(S, Next.getX(), Next.getY(), Character.MIN_VALUE)){
            Alpha.remove(findIndex(Alpha, c, d));
            Beta.remove(findIndex(Beta, a, b));
            Beta.add(Next);
            aTurn = !aTurn;
            ok = !ok;
        }
    }

    public static void redrawGrid() {
        Occupied.clear();
        Occupied.addAll(Alpha);
        Occupied.addAll(Beta);
        boardPrinter.print(7, 5, Occupied);
    }

    //main driver of the program
    public static void main(String[] args) {
        //Preliminary initializations

        //Cross product of R and C
        for (int i : R)
            for (int j : C)
                P.add(new pairs(i, j));

        for (pairs pairs : P)
            if (pairs.getX() % 2 == pairs.getY() % 2)
                S.add(new pairs(pairs.getX(), pairs.getY()));    //x,y is member of P where x %2 = y %2  -> where the pieces start

        for (pairs pairs : S)
            if (pairs.getX() <= 2)
                Y.add(new pairs(pairs.getX(), pairs.getY(), 'B')); //: {(x, y) ∈ S | x ≤ 2} --> Beta's starting pieces' positions

        for (pairs pairs : S)
            if (pairs.getX() >= 6)
                E.add(new pairs(pairs.getX(), pairs.getY(), 'A')); //: {(x, y) ∈ S | x >= 6} --> Alpha's starting pieces' positions

        //Free represents the empty spaces, which are empty at the start of the game
        Free=new ArrayList<>(P);
        for (pairs pairs : E)
            for (int j = Free.size()-1; j >= 0; j--)
                if (pairs.getX() == Free.get(j).getX() && pairs.getY() == Free.get(j).getY())
                    Free.remove(j);
        for (pairs pairs : Y)
            for (int j = Free.size()-1; j >=0; j--)
                if (pairs.getX() == Free.get(j).getX() && pairs.getY() == Free.get(j).getY())
                    Free.remove(j);
        boolean[] B = {true, false}; //boolean B;

        //System Initialization
        boolean over = false;

        //gets the starting positions of Alpha and Beta's pieces
        Alpha.addAll(E);
        Beta.addAll(Y);

        //Prints out the board
        redrawGrid();
        System.out.println();

        //loops the game while neither side has won
        do {
            int x, y;
            char sym;
            pairs previous, next;
            if (aTurn) {
                System.out.println("It's Alpha's turn");
                sym = 'A';
            }
            else{
                System.out.println("It's Beta's turn");
                sym = 'B';
            }
            System.out.print("Please input current piece X: ");
            x = inputManager.nextInt();
            System.out.print("Please input current piece Y: ");
            y = inputManager.nextInt();
            previous = new pairs(x,y);

            System.out.print("Please input next move X: ");
            x = inputManager.nextInt();
            System.out.print("Please input next move Y: ");
            y = inputManager.nextInt();
            inputManager.nextLine();
            next = new pairs(x,y, sym);
            NextPlayerMove(previous, next);
            Free=new ArrayList<>(P);
            for (pairs pairs : Alpha)
                for (int j = Free.size()-1; j >=0; j--)
                    if (pairs.getX() == Free.get(j).getX() && pairs.getY() == Free.get(j).getY())
                        Free.remove(j);
            for (pairs pairs : Beta)
                for (int j = Free.size()-1; j >=0; j--)
                    if (pairs.getX() == Free.get(j).getX() && pairs.getY() == Free.get(j).getY())
                        Free.remove(j);
            redrawGrid();
            over = GameOver(over);
        } while (!over);
        System.out.println("GG, Game Over!");
    }
}
