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

package tech.teamio.openempires.client.engine.objects;

import org.lwjgl.glfw.GLFW;
import tech.teamio.openempires.client.engine.Input;
import tech.teamio.openempires.client.engine.math.Vec3f;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Camera {
    private Vec3f position, rotation;
    private float moveSpeed = 0.03f, mouseSensitivity = 0.1f;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;

    public Camera(Vec3f position, Vec3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {
        if(Input.isMouseLocked) {
            newMouseX = Input.mouseX;
            newMouseY = Input.mouseY;
        }

        float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vec3f.add(position, new Vec3f(-z, 0, x));
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vec3f.add(position, new Vec3f(z, 0, -x));
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vec3f.add(position, new Vec3f(-x, 0, -z));
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vec3f.add(position, new Vec3f(x, 0, z));
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vec3f.add(position, new Vec3f(0, moveSpeed, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vec3f.add(position, new Vec3f(0, -moveSpeed, 0));

        if(Input.isMouseLocked) {
            float dx = (float) (newMouseX - oldMouseX);
            float dy = (float) (newMouseY - oldMouseY);

            rotation = Vec3f.add(rotation, new Vec3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

            oldMouseX = newMouseX;
            oldMouseY = newMouseY;
        }
    }

    public Vec3f getPosition() {
        return position;
    }

    public Vec3f getRotation() {
        return rotation;
    }
}
