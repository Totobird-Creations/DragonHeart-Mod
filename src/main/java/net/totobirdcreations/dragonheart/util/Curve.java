package net.totobirdcreations.dragonheart.util;

public class Curve {

    public float delta; // How extreme the curve is.
    public float mute;  // How flattened to 0.5 the values are;

    public Curve(float delta, float mute) {
        this.delta = delta;
        this.mute  = mute;
    }

    public float interpolate(float x) {
        return Curve.interpolate(this.delta, this.mute, x);
    }

    public static float interpolate(float delta, float mute, float x) {
        x        = 2.0f * x - 1.0f;
        float a  = Math.abs(x / 2.0f + 0.5f);
        float t  = x * delta - x + 2.0f * delta - 2.0f * delta * a;
        t       /= delta - 2.0f * delta * a + 1.0f;
        float u  = -t / 2.0f + 0.5f;
        float v  = u + (0.5f - u) * mute;
        return v;
    }

}
