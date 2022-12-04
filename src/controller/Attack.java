package controller;

public abstract class Attack {
    private static int x;
    private static int y;
    private static boolean marked;

    public void setPositionOfAttack(int x, int y){
        Attack.x = x;
        Attack.y = y;
    }

    public int getPositionX(){
        return x;
    }

    public int getPositionY(){
        return y;
    }

    public boolean getMarked(){
        return marked;
    }

    public void setMarked(boolean marked){
        Attack.marked = marked;
    }
}
