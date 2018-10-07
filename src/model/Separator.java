package model;

public class Separator extends Line{
    public Separator(int pageWidth) {
        super(pageWidth);
    }

    public void print(){
        for (int i = 0; i < super.getPageWidth(); i++){
            System.out.print("-");
        }
        System.out.print("\n");
    }
}
