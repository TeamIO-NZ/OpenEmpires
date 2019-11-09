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
import org.lwjgl.glfw.GLFWJoystickCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Controller {
    public static GLFWJoystickCallback joystickCallback;
    private static Map<Integer, Boolean> joysticks = new HashMap<>();

    public static final int AXIS_1 = 0;
    public static final int AXIS_2 = 1;

    public static void init() {
        joystickCallback = new GLFWJoystickCallback() {
            @Override
            public void invoke(int jid, int event) {
                if(event == GLFW.GLFW_CONNECTED) joysticks.put(jid, true);
                if(event == GLFW.GLFW_DISCONNECTED) joysticks.put(jid, false);
            }
        };
    }

    public static void destroy() {
        joystickCallback.free();
    }

    public static boolean isJoyConnected(int jid) {
        if(joysticks.containsKey(jid)) {
            return joysticks.get(jid);
        }
        return false;
    }

    public static float getAxis(int jid, int aid) {
        if(isJoyConnected(jid)) {
            return GLFW.glfwGetJoystickAxes(jid).get(aid);
        }
        return 0.0f;
    }
}
