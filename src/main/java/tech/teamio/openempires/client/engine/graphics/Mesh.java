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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Mesh {
    public Vertex[] vertices;
    public int[] indices;
    public Material material;
    public  int vao;
    public int pbo;
    public int ibo;
    public int tbo;

    public Mesh(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    public void create() {
        this.material.create();

        this.vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(this.vao);

        FloatBuffer posBuff = MemoryUtil.memAllocFloat(this.vertices.length * 3);
        float[] posData = new float[this.vertices.length * 3];
        for (int i = 0; i < this.vertices.length; i++) {
            posData[i * 3] = this.vertices[i].position.getX();
            posData[i * 3 + 1] = this.vertices[i].position.getY();
            posData[i * 3 + 2] = this.vertices[i].position.getZ();
        }
        posBuff.put(posData).flip();

        this.pbo = this.store(posBuff, 0, 3);

        FloatBuffer textureBuff = MemoryUtil.memAllocFloat(this.vertices.length * 2);
        float[] textureData = new float[this.vertices.length * 2];
        for (int i = 0; i < this.vertices.length; i++) {
            textureData[i * 2] = this.vertices[i].textureCoord.getX();
            textureData[i * 2 + 1] = this.vertices[i].textureCoord.getY();
        }
        textureBuff.put(textureData).flip();

        this.tbo = this.store(textureBuff, 2, 2);

        IntBuffer indicesBuff = MemoryUtil.memAllocInt(this.indices.length);
        indicesBuff.put(this.indices).flip();

        this.ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuff, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private int store(FloatBuffer buff, int index, int size) {
        int buffId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buff, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return buffId;
    }

    public void destroy() {
        GL30.glDeleteVertexArrays(this.vao);
        GL15.glDeleteBuffers(this.pbo);
        GL15.glDeleteBuffers(this.ibo);
        GL15.glDeleteBuffers(this.tbo);
        this.material.destroy();
    }
}
