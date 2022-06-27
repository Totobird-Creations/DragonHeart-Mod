package net.totobirdcreations.dragonheart.util;

public class Curve {

    public float delta; // How extreme the curve is.
    public float mute;  // How flattened to 0.5 the values are;

    public Curve(float delta, float mute) {
        this.delta = delta;
        this.mute  = mute;
    }

    public float interpolate(float x) {
        x        = 2.0f * x - 1.0f;
        float a  = Math.abs(x / 2.0f + 0.5f);
        float t  = x * this.delta - x + 2.0f * this.delta - 2.0f * delta * a;
        t       /= this.delta - 2.0f * this.delta * a + 1.0f;
        float u  = -t / 2.0f + 0.5f;
        float v  = u + (0.5f - u) * this.mute;
        return v;
    }

}
