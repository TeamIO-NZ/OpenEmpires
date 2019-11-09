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

package tech.teamio.openempires;

import org.lwjgl.glfw.GLFW;
import tech.teamio.openempires.client.engine.Input;
import tech.teamio.openempires.client.engine.graphics.Renderer;
import tech.teamio.openempires.client.engine.graphics.Shader;
import tech.teamio.openempires.client.engine.math.Vec3f;
import tech.teamio.openempires.client.engine.objects.Camera;
import tech.teamio.openempires.client.engine.window.Window;
import tech.teamio.openempires.client.engine.window.WindowSettings;
import tech.teamio.openempires.client.engine.world.World;
import tech.teamio.openempires.client.engine.world.WorldLoader;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Main implements Runnable{
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 760;

    public World world = new World();

    public Camera camera = new Camera(new Vec3f(0, 0, 1), new Vec3f(0, 0, 0));

    public void start() {
        game = new Thread(this, "client");
        game.start();
    }

    public void init() {
        window = new Window(new WindowSettings("OpenEmpires", WIDTH, HEIGHT, false));
        shader = new Shader("/assets/openempires/shaders/main.vert.glsl", "/assets/openempires/shaders/main.frag.glsl");
        renderer = new Renderer(window, shader);
        window.backgroundColor = new  Vec3f(255, 255, 255);
        window.create();
        shader.create();

        WorldLoader.loadFromSave("world_one.json", world);
        //WorldLoader.randomWorld(world, 100);
        //WorldLoader.loadLine(world, 1000);
        //WorldLoader.loadFloor(world, 100);
        //WorldLoader.box(world, 50);
        world.create();
        world.objects.get(0).rot.setY(180);
    }

    public void run() {
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.settings.isFullscreen);
            if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
                window.mouseState(!Input.isMouseLocked);
            }
        }
        close();
    }

    private void update() {
        window.update();
        camera.update();
        //WorldLoader.randUpdate(world);
        //WorldLoader.updateRandom(world);
        //WorldLoader.spin(world);
        //WorldLoader.controllerUpdate(this.world);
    }

    private void render() {
        world.render(renderer, camera);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
        world.destroy();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
