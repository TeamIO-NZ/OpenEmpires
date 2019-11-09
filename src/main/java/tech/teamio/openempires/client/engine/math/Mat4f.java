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

import java.util.Arrays;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Mat4f {
    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    public static Mat4f identity() {
        Mat4f result = new Mat4f();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.set(i, j, 0);
            }
        }

        result.set(0, 0, 1);
        result.set(1, 1, 1);
        result.set(2, 2, 1);
        result.set(3, 3, 1);

        return result;
    }

    public static Mat4f translate(Vec3f translate) {
        Mat4f result = Mat4f.identity();

        result.set(3, 0, translate.getX());
        result.set(3, 1, translate.getY());
        result.set(3, 2, translate.getZ());

        return result;
    }

    public static Mat4f rotate(float angle, Vec3f axis) {
        Mat4f result = Mat4f.identity();

        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        float C = 1 - cos;

        result.set(0, 0, cos + axis.getX() * axis.getX() * C);
        result.set(0, 1, axis.getX() * axis.getY() * C - axis.getZ() * sin);
        result.set(0, 2, axis.getX() * axis.getZ() * C + axis.getY() * sin);
        result.set(1, 0, axis.getY() * axis.getX() * C + axis.getZ() * sin);
        result.set(1, 1, cos + axis.getY() * axis.getY() * C);
        result.set(1, 2, axis.getY() * axis.getZ() * C - axis.getX() * sin);
        result.set(2, 0, axis.getZ() * axis.getX() * C - axis.getY() * sin);
        result.set(2, 1, axis.getZ() * axis.getY() * C + axis.getX() * sin);
        result.set(2, 2, cos + axis.getZ() * axis.getZ() * C);

        return result;
    }

    public static Mat4f scale(Vec3f scalar) {
        Mat4f result = Mat4f.identity();

        result.set(0, 0, scalar.getX());
        result.set(1, 1, scalar.getY());
        result.set(2, 2, scalar.getZ());

        return result;
    }

    public static Mat4f transform(Vec3f position, Vec3f rotation, Vec3f scale) {
        Mat4f result = Mat4f.identity();

        Mat4f translationMatrix = Mat4f.translate(position);
        Mat4f rotXMatrix = Mat4f.rotate(rotation.getX(), new Vec3f(1, 0, 0));
        Mat4f rotYMatrix = Mat4f.rotate(rotation.getY(), new Vec3f(0, 1, 0));
        Mat4f rotZMatrix = Mat4f.rotate(rotation.getZ(), new Vec3f(0, 0, 1));
        Mat4f scaleMatrix = Mat4f.scale(scale);

        Mat4f rotationMatrix = Mat4f.multiply(rotXMatrix, Mat4f.multiply(rotYMatrix, rotZMatrix));

        result = Mat4f.multiply(translationMatrix, Mat4f.multiply(rotationMatrix, scaleMatrix));

        return result;
    }

    public static Mat4f projection(float fov, float aspect, float near, float far) {
        Mat4f result = Mat4f.identity();

        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float range = far - near;

        result.set(0, 0, 1.0f / (aspect * tanFOV));
        result.set(1, 1, 1.0f / tanFOV);
        result.set(2, 2, -((far + near) / range));
        result.set(2, 3, -1.0f);
        result.set(3, 2, -((2 * far * near) / range));
        result.set(3, 3, 0.0f);

        return result;
    }

    public static Mat4f view(Vec3f position, Vec3f rotation) {
        Mat4f result = Mat4f.identity();

        Vec3f negative = new Vec3f(-position.getX(), -position.getY(), -position.getZ());
        Mat4f translationMatrix = Mat4f.translate(negative);
        Mat4f rotXMatrix = Mat4f.rotate(rotation.getX(), new Vec3f(1, 0, 0));
        Mat4f rotYMatrix = Mat4f.rotate(rotation.getY(), new Vec3f(0, 1, 0));
        Mat4f rotZMatrix = Mat4f.rotate(rotation.getZ(), new Vec3f(0, 0, 1));

        Mat4f rotationMatrix = Mat4f.multiply(rotZMatrix, Mat4f.multiply(rotYMatrix, rotXMatrix));

        result = Mat4f.multiply(translationMatrix, rotationMatrix);

        return result;
    }

    public static Mat4f multiply(Mat4f matrix, Mat4f other) {
        Mat4f result = Mat4f.identity();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.set(i, j, matrix.get(i, 0) * other.get(0, j) +
                        matrix.get(i, 1) * other.get(1, j) +
                        matrix.get(i, 2) * other.get(2, j) +
                        matrix.get(i, 3) * other.get(3, j));
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(elements);
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
        Mat4f other = (Mat4f) obj;
        if (!Arrays.equals(elements, other.elements))
            return false;
        return true;
    }

    public float get(int x, int y) {
        return elements[y * SIZE + x];
    }

    public void set(int x, int y, float value) {
        elements[y * SIZE + x] = value;
    }

    public float[] getAll() {
        return elements;
    }
}
