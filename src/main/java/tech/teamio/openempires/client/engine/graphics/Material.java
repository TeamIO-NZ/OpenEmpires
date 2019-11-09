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
import org.lwjgl.opengl.GL13;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Material {
    private String path;
    public float width;
    public float height;
    public int textureId;

   public Material(String path) {
       this.path = path;
   }

   public void create() {
       this.textureId = GL11.glGenTextures();
       TextureLoader.Texture texture = TextureLoader.loadTexture(this.path);
       this.width = texture.getWidth();
       this.height = texture.getHeight();
       GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
       GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
       GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
       GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, texture.getWidth(), texture.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texture.getTextureBuffer());
       GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
   }
   public void destroy() {
       GL13.glDeleteTextures(this.textureId);
   }
}
