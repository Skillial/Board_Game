import java.util.ArrayList;

public class boardPrinter {
    public static pairs checkPair(ArrayList<pairs> Occupied, int x, int y) {
        for (pairs pair : Occupied) {
            if (pair.getX() == x && pair.getY() == y) {
                return pair;
            }
        }
        return null;
    }

    public static void gridPrinter() {
        System.out.print("║     ");
    }
    public static void gridPrinter(char symbol) {
        System.out.print("║  " + symbol + "  ");
    }
    public static void gridPrinter(int symbol) {
        System.out.print("║  " + symbol + "  ");
    }
    public static void linePrinter(int row, int sizeX, int sizeY) {
        for (int i = 0; i <= sizeX; i++) {
            if (row == 0) {
                if (i == 0)
                    System.out.print("╔═════");
                else
                    System.out.print("╦═════");
                if (i == sizeX)
                    System.out.print("╗\n");
            } else if (row - 1 == sizeY) {
                if (i == 0)
                    System.out.print("╚═════");
                else
                    System.out.print("╩═════");
                if (i == sizeX)
                    System.out.print("╝\n");
            } else {
                if (i == 0)
                    System.out.print("╠═════");
                else
                    System.out.print("╬═════");
                if (i == sizeX)
                    System.out.print("╣\n");
            }

        }
    }
    public static void print(int sizeX, int sizeY, ArrayList<pairs> Occupied) {
        for (int row = 0; row <= sizeY; row++)
        {
            linePrinter(row, sizeX, sizeY);
            for (int column = 0; column <= sizeX; column++)
            {
                pairs currentPair = checkPair(Occupied, column, row);
                if (column == 0) {
                    if (row != 0)
                        gridPrinter(row);
                    else
                        gridPrinter();
                } else if (currentPair != null) {
                    gridPrinter(currentPair.getSym());
                } else if (row == 0)
                    if (column != 0)
                        gridPrinter(column);
                    else
                        gridPrinter();
                else
                    gridPrinter();
            }
            System.out.println("║");
        }
        linePrinter(sizeY + 1, sizeX, sizeY);
    }
}