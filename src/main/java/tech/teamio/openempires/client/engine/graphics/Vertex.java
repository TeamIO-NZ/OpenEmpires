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


import tech.teamio.openempires.client.engine.math.Vec2f;
import tech.teamio.openempires.client.engine.math.Vec3f;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Vertex {
    public Vec3f position;
    public Vec2f textureCoord;

    public Vertex(Vec3f position, Vec2f textureCoord) {
        this.position = position;
        this.textureCoord = textureCoord;
    }
}
