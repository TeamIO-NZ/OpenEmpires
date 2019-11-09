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


import tech.teamio.openempires.client.engine.graphics.Mesh;
import tech.teamio.openempires.client.engine.math.Vec3f;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class GameObject {
    public Vec3f pos;
    public Vec3f rot;
    public Vec3f scale;
    public Mesh mesh;

    public GameObject(Vec3f pos, Vec3f rot, Vec3f scale, Mesh mesh) {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
        this.mesh = mesh;
    }
}
