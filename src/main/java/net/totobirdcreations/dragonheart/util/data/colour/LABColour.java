package net.totobirdcreations.dragonheart.util.data.colour;



public class LABColour {

    public float l;
    public float a;
    public float b;

    public LABColour(float l, float a, float b) {
        this.l = l;
        this.a = a;
        this.b = b;
    }


    public float distance(LABColour other) {
        return (float)(Math.sqrt(
                Math.pow(other.l - this.l, 2.0f) +
                Math.pow(other.a - this.a, 2.0f) +
                Math.pow(other.b - this.b, 2.0f)
        ));
    }

}
