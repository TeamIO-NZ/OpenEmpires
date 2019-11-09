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
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class TextureLoader {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Texture loadTexture(String path) {
        ByteBuffer texture;
        int width;
        int height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            texture = STBImage.stbi_load(path, w, h, comp, 4);
            if(texture == null) {
                LOGGER.warn("Failed to load texture " + path + ". " + STBImage.stbi_failure_reason() + ".");
                texture = STBImage.stbi_load("textures/missing.png", w, h, comp, 4);
                if(texture == null) {
                    throw new RuntimeException("Failed to load texture " + path + ". " + STBImage.stbi_failure_reason() + ".");
                }
            }
            width = w.get();
            height = h.get();

            return new Texture(width, height, texture);
        }
    }

    public static class Texture {

        private ByteBuffer textureBuffer;
        private int width;
        private int height;

        public Texture(int width, int height, ByteBuffer textureBuffer) {
            this.width = width;
            this.height = height;
            this.textureBuffer = textureBuffer;
        }

        public ByteBuffer getTextureBuffer() {
            return textureBuffer;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
