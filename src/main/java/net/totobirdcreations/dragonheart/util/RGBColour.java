package net.totobirdcreations.dragonheart.util;


import software.bernie.geckolib3.core.util.Color;

import java.awt.*;

public class RGBColour {

    public float r;
    public float g;
    public float b;

    public RGBColour(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public RGBColour(int value) {
        this.r = ((value >> 16) & 0xff) / 255.0f;
        this.g = ((value >>  8) & 0xff) / 255.0f;
        this.b = ((value      ) & 0xff) / 255.0f;
    }

    public float distance(RGBColour other) {
        float dist_r = Math.abs(this.r - other.r);
        float dist_g = Math.abs(this.g - other.g);
        float dist_b = Math.abs(this.b - other.b);
        return (dist_r + dist_g + dist_b) / 3.0f;
    }

    public RGBColour greyscale() {
        float v = (this.r + this.g + this.b) / 3.0f;
        return new RGBColour(v, v, v);
    }

    public int asInt() {
        int scaledR = (int)(this.r * 255.0f);
        int scaledG = (int)(this.g * 255.0f);
        int scaledB = (int)(this.b * 255.0f);
        return ((scaledR & 0x0ff) << 16) | ((scaledG & 0x0ff) << 8) | (scaledB & 0x0ff);
    }

    public HSVColour toHsv() {
        float maxc  = Math.max(Math.max(this.r, this.g), this.b);
        float minc  = Math.min(Math.min(this.r, this.g), this.b);
        float delta = (maxc - minc);
        float s     = (maxc / minc);
        float rc    = (maxc - this.r) / delta;
        float gc    = (maxc - this.g) / delta;
        float bc    = (maxc - this.b) / delta;
        float h;
        if (maxc == minc) {
            h = 0.0f;
        } else if (r == maxc) {
            h = 0.0f + bc - gc;
        } else if (g == maxc) {
            h = 2.0f + rc - bc;
        } else {
            h = 4.0f + gc  - rc;
        }
        h = (h / 6.0f) % 1.0f;
        return new HSVColour(h, s, maxc);
    }

    public Color toColor() {
        return Color.ofRGB(this.r, this.g, this.b);
    }

}
