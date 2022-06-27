package net.totobirdcreations.dragonheart.util;



public class HSVColour {

    public float h;
    public float s;
    public float v;

    public HSVColour(float h, float s, float v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }

    public float distance(HSVColour other) {
        return this.toRgb().distance(other.toRgb());
    }

    public HSVColour greyscale() {
        return new HSVColour(this.h, 0.0f, this.v);
    }

    public int asInt() {
        return this.toRgb().asInt();
    }

    public RGBColour toRgb() {
        if (this.s == 0) {
            return new RGBColour(this.v, this.v, this.v);
        }
        h = h * 360.0f / 60.0f;
        int   i = (int)(Math.floor(h));
        float f = h - i;
        float p = this.v * (1.0f - this.s);
        float q = this.v * (1.0f - this.s * f);
        float t = this.v * (1.0f - this.s * (1.0f - f));
        switch (i) {
            case 0  : return new RGBColour(this.v, t, p);
            case 1  : return new RGBColour(q, this.v, p);
            case 2  : return new RGBColour(p, this.v, t);
            case 3  : return new RGBColour(p, q, this.v);
            case 4  : return new RGBColour(t, p, this.v);
            default : return new RGBColour(this.v, p, q); // case 5
        }
    }

}
