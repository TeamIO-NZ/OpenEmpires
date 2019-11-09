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

package tech.teamio.openempires.client.engine.world;

import tech.teamio.openempires.client.engine.graphics.Renderer;
import tech.teamio.openempires.client.engine.objects.Camera;
import tech.teamio.openempires.client.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class World {
    public List<GameObject> objects = new ArrayList<>();

    public void addObject(GameObject object) {
        this.objects.add(object);
    }

    public void create() {
        objects.forEach(object -> {
            object.mesh.create();
        });
    }

    public void destroy() {
        objects.forEach(object -> object.mesh.destroy());
    }

    public void render(Renderer renderer, Camera camera) {
        objects.forEach(object -> renderer.renderMesh(object, camera));
    }
}
