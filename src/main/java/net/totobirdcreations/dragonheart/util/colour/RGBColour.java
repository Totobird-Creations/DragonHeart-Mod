package net.totobirdcreations.dragonheart.util.colour;


import software.bernie.geckolib3.core.util.Color;

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
        int[] lab1 = this.toLab();
        int[] lab2 = other.toLab();
        return (float)(Math.sqrt(
                Math.pow(lab2[0] - lab1[0], 2.0f) +
                Math.pow(lab2[1] - lab1[1], 2.0f) +
                Math.pow(lab2[2] - lab1[2], 2.0f)
        ));
    }

    public RGBColour greyscale() {
        float v = (this.r + this.g + this.b) / 3.0f;
        return new RGBColour(v, v, v);
    }

    public int[] toLab() {
        float eps = 216.0f / 24389.0f;
        float k   = 24389.0f / 27.0f;
        float Xr  = 0.964221f;
        float Yr  = 1.0f;
        float Zr  = 0.825211f;
        float r   = (float)(this.r <= 0.04045 ? this.r / 12.0f : Math.pow((this.r + 0.055f) / 1.055f, 2.4f));
        float g   = (float)(this.g <= 0.04045 ? this.g / 12.0f : Math.pow((this.g + 0.055f) / 1.055f, 2.4f));
        float b   = (float)(this.b <= 0.04045 ? this.b / 12.0f : Math.pow((this.b + 0.055f) / 1.055f, 2.4f));
        float X = 0.436052025f * r + 0.385081593f * g + 0.143087414f * b;
        float Y = 0.222491598f * r + 0.71688606f  * g + 0.060621486f * b;
        float Z = 0.013929122f * r + 0.097097002f * g + 0.71418547f  * b;
        float xr = X / Xr;
        float yr = Y / Yr;
        float zr = Z / Zr;
        float fx = (float)(xr > eps ? Math.pow(xr, 1.0f / 3.0f) : (k * xr + 16.0f) / 116.0f);
        float fy = (float)(yr > eps ? Math.pow(yr, 1.0f / 3.0f) : (k * yr + 16.0f) / 116.0f);
        float fz = (float)(zr > eps ? Math.pow(zr, 1.0f / 3.0f) : (k * zr + 16.0f) / 116.0f);
        float Ls = (116.0f * fy) - 16.0f;
        float as = 500.0f * (fx - fy);
        float bs = 200.0f * (fy - fz);
        return new int[]{
                (int)(2.55f * Ls + 0.5f),
                (int)(as + 0.5f),
                (int)(bs + 0.5f)
        };
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
