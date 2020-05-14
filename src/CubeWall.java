public class CubeWall {
    public Color block1;
    public Color block2;
    public Color block3;
    public Color block4;
    public Color block5;
    public Color block6;
    public Color block7;
    public Color block8;
    public Color block9;
    public Color wallColor;

    public CubeWall() {}
    public CubeWall(Color block1, Color block2, Color block3, Color block4,
                    Color block5, Color block6, Color block7, Color block8, Color block9) {
        this.block1 = block1;
        this.block2 = block2;
        this.block3 = block3;
        this.block4 = block4;
        this.block5 = block5;
        this.block6 = block6;
        this.block7 = block7;
        this.block8 = block8;
        this.block9 = block9;
        this.wallColor = block5;
    }

    public void printWall() {
        System.out.println("Wall color: " + wallColor.toString());
        System.out.println("Block1: " + block1.toString());
        System.out.println("Block2: " + block2.toString());
        System.out.println("Block3: " + block3.toString());
        System.out.println("Block4: " + block4.toString());
        System.out.println("Block5: " + block5.toString());
        System.out.println("Block6: " + block6.toString());
        System.out.println("Block7: " + block7.toString());
        System.out.println("Block8: " + block8.toString());
        System.out.println("Block9: " + block9.toString());
    }
}
