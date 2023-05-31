public class pairs {
    private int x;
    private int y;
    private char sym;
    public pairs(int x, int y){
        this.x=x;
        this.y=y;
    }
    public pairs(int x, int y, char sym){
        this.x=x;
        this.y=y;
        this.sym=sym;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getSym() {
        return sym;
    }
    public void setSym(char sym) {
        this.sym = sym;
    }
}