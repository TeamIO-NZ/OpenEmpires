/*
 * Copyright 2019 Team io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.teamio.openempires.client.engine.math;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Vec2f {
    private float x, y;

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2f add(Vec2f vector1, Vec2f vector2) {
        return new Vec2f(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    public static Vec2f subtract(Vec2f vector1, Vec2f vector2) {
        return new Vec2f(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
    }

    public static Vec2f multiply(Vec2f vector1, Vec2f vector2) {
        return new Vec2f(vector1.getX() * vector2.getX(), vector1.getY() * vector2.getY());
    }

    public static Vec2f divide(Vec2f vector1, Vec2f vector2) {
        return new Vec2f(vector1.getX() / vector2.getX(), vector1.getY() / vector2.getY());
    }

    public static float length(Vec2f vector) {
        return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
    }

    public static Vec2f normalize(Vec2f vector) {
        float len = Vec2f.length(vector);
        return Vec2f.divide(vector, new Vec2f(len, len));
    }

    public static float dot(Vec2f vector1, Vec2f vector2) {
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vec2f other = (Vec2f) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
