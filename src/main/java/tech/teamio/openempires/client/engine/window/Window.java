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

package tech.teamio.openempires.client.engine.window;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import tech.teamio.openempires.client.engine.Input;
import tech.teamio.openempires.client.engine.math.Mat4f;
import tech.teamio.openempires.client.engine.math.Vec3f;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Window {
    public WindowSettings settings;
    private long handle;
    private int frames;
    private static long time;
    private Input input;
    public Vec3f backgroundColor = new Vec3f(0, 0, 0);
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private int[] windowPosX = new int[1];
    private int[] windowPosY = new int[1];
    private static final Logger LOGGER = LogManager.getLogger("GLFW");
    public Mat4f projection;

   public Window(WindowSettings settings) {
       this.settings = settings;
       this.projection = Mat4f.projection(70.0f, (float)this.settings.width / (float)this.settings.height, 0.1f, 1000.0f);
   }

   public void create() {
       if(!GLFW.glfwInit()) {
            LOGGER.error("Failed to initialize GLFW.");
            return;
       }

       this.input = new Input();
       this.handle = GLFW.glfwCreateWindow(this.settings.width, this.settings.height, this.settings.title, this.settings.isFullscreen ? GLFW.glfwGetPrimaryMonitor() : MemoryUtil.NULL, MemoryUtil.NULL);

       if(this.handle == MemoryUtil.NULL) {
           LOGGER.error("Failed to create GLFW window.");
           return;
       }

       GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
       windowPosX[0] = (vidMode.width() - this.settings.width) / 2;
       windowPosY[0] = (vidMode.height() - this.settings.height) / 2;
       GLFW.glfwSetWindowPos(this.handle, this.windowPosX[0], this.windowPosY[0]);
       GLFW.glfwMakeContextCurrent(this.handle);
       GL.createCapabilities();
       GL11.glEnable(GL11.GL_DEPTH_TEST);

       initCallbacks();

       GLFW.glfwShowWindow(this.handle);
       GLFW.glfwSwapInterval(1);
       time = System.currentTimeMillis();
   }

   private void initCallbacks() {
       this.sizeCallback = new GLFWWindowSizeCallback() {
           @Override
           public void invoke(long window, int width, int height) {
               settings.width = width;
               settings.height = height;
               isResized = true;
           }
       };
       GLFW.glfwSetWindowSizeCallback(this.handle, this.sizeCallback);
       GLFW.glfwSetKeyCallback(this.handle, this.input.keyboard);
       GLFW.glfwSetCursorPosCallback(this.handle, this.input.mouseMove);
       GLFW.glfwSetMouseButtonCallback(this.handle, this.input.mouseButtons);
       GLFW.glfwSetScrollCallback(this.handle, this.input.mouseScroll);
   }

   public void update() {
        if(this.isResized) {
            GL11.glViewport(0, 0, this.settings.width, this.settings.height);
            this.isResized = false;
        }
        GL11.glClearColor(this.backgroundColor.getX(), this.backgroundColor.getY(), this.backgroundColor.getZ(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
        frames++;
        if(System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(this.handle, this.settings.title + " | FPS: " + this.frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
   }

    public void destroy() {
        this.input.destroy();
        this.sizeCallback.free();
        GLFW.glfwWindowShouldClose(this.handle);
        GLFW.glfwDestroyWindow(this.handle);
        GLFW.glfwTerminate();
    }

    public void mouseState(boolean lock) {
        GLFW.glfwSetInputMode(this.handle, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
        Input.isMouseLocked = lock;
    }

   public void swapBuffers() {
       GLFW.glfwSwapBuffers(this.handle);
   }

   public boolean shouldClose() {
       return GLFW.glfwWindowShouldClose(this.handle);
   }

   public void setFullscreen(boolean value) {
       this.settings.isFullscreen = value;
       this.isResized = true;
       if(this.settings.isFullscreen) {
           GLFW.glfwGetWindowPos(this.handle, this.windowPosX, this.windowPosY);
           GLFW.glfwSetWindowMonitor(this.handle, GLFW.glfwGetPrimaryMonitor(), 0, 0, this.settings.width, this.settings.height, 0);
       } else {
           GLFW.glfwSetWindowMonitor(this.handle, 0, this.windowPosX[0], this.windowPosY[0], this.settings.width, this.settings.height, 0);
       }
   }
}
