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

package tech.teamio.openempires.client.engine.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;
import tech.teamio.openempires.client.engine.Utils;
import tech.teamio.openempires.client.engine.math.Mat4f;
import tech.teamio.openempires.client.engine.math.Vec2f;
import tech.teamio.openempires.client.engine.math.Vec3f;

import java.nio.FloatBuffer;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Shader {
    private String vertexFile;
    private String fragmentFile;
    private int vertexID;
    private int fragmentID;
    private int programID;
    private static final Logger LOGGER = LogManager.getLogger("Shaders");

    public Shader(String vertexPath, String fragmentPath) {
        this.vertexFile = Utils.resource2String(vertexPath);
        this.fragmentFile = Utils.resource2String(fragmentPath);
    }

    public void create() {
        this.programID = GL20.glCreateProgram();
        this.vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        GL20.glShaderSource(this.vertexID, this.vertexFile);
        GL20.glCompileShader(this.vertexID);

        if (GL20.glGetShaderi(this.vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            LOGGER.error("Vertex Shader: " + GL20.glGetShaderInfoLog(this.vertexID));
            return;
        }

        this.fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        GL20.glShaderSource(this.fragmentID, this.fragmentFile);
        GL20.glCompileShader(this.fragmentID);

        if (GL20.glGetShaderi(this.fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            LOGGER.error("Fragment Shader: " + GL20.glGetShaderInfoLog(this.fragmentID));
            return;
        }

        GL20.glAttachShader(this.programID, this.vertexID);
        GL20.glAttachShader(this.programID, this.fragmentID);

        GL20.glLinkProgram(this.programID);
        if (GL20.glGetProgrami(this.programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Program Linking: " + GL20.glGetProgramInfoLog(this.programID));
            return;
        }

        GL20.glValidateProgram(this.programID);
        if (GL20.glGetProgrami(this.programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            LOGGER.error("Program Validation: " + GL20.glGetProgramInfoLog(this.programID));
        }
    }

    public int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(this.programID, name);
    }

    public void setUniform(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform(String name, int value) {
        GL20.glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform(String name, boolean value) {
        GL20.glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    public void setUniform(String name, Vec2f value) {
        GL20.glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }

    public void setUniform(String name, Vec3f value) {
        GL20.glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String name, Mat4f value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Mat4f.SIZE * Mat4f.SIZE);
        matrix.put(value.getAll()).flip();
        GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }

    public void bind() {
        GL20.glUseProgram(this.programID);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void destroy() {
        GL20.glDetachShader(this.programID, this.vertexID);
        GL20.glDetachShader(this.programID, this.fragmentID);
        GL20.glDeleteShader(this.vertexID);
        GL20.glDeleteShader(this.fragmentID);
        GL20.glDeleteProgram(this.programID);
    }
}
