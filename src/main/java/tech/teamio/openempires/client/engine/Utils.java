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

package tech.teamio.openempires.client.engine;


import org.apache.commons.io.IOUtils;
import tech.teamio.openempires.client.engine.graphics.Material;
import tech.teamio.openempires.client.engine.graphics.Mesh;
import tech.teamio.openempires.client.engine.graphics.Vertex;
import tech.teamio.openempires.client.engine.math.Vec2f;
import tech.teamio.openempires.client.engine.math.Vec3f;
import tech.teamio.openempires.client.engine.objects.GameObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class Utils {

    public static String resource2String(String path) {
        System.out.println(path);
        try {
            return IOUtils.toString(Utils.class.getResourceAsStream(path), Charset.defaultCharset());
        } catch (IOException e) {

            e.printStackTrace();

        }
        return "";
    }

    public static Mesh createMesh(String txt) {
        return new Mesh(new Vertex[] {
                //Back face
                new Vertex(new Vec3f(-0.5f, 0.5f, -0.5f), new Vec2f(0.0f, 0.0f)),
                new Vertex(new Vec3f(-0.5f, -0.5f, -0.5f), new Vec2f(0.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, -0.5f, -0.5f), new Vec2f(1.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, 0.5f, -0.5f), new Vec2f(1.0f, 0.0f)),

                //Front face
                new Vertex(new Vec3f(-0.5f, 0.5f, 0.5f), new Vec2f(0.0f, 0.0f)),
                new Vertex(new Vec3f(-0.5f, -0.5f, 0.5f), new Vec2f(0.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, -0.5f, 0.5f), new Vec2f(1.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, 0.5f, 0.5f), new Vec2f(1.0f, 0.0f)),

                //Right face
                new Vertex(new Vec3f(0.5f, 0.5f, -0.5f), new Vec2f(0.0f, 0.0f)),
                new Vertex(new Vec3f(0.5f, -0.5f, -0.5f), new Vec2f(0.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, -0.5f, 0.5f), new Vec2f(1.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, 0.5f, 0.5f), new Vec2f(1.0f, 0.0f)),

                //Left face
                new Vertex(new Vec3f(-0.5f, 0.5f, -0.5f), new Vec2f(0.0f, 0.0f)),
                new Vertex(new Vec3f(-0.5f, -0.5f, -0.5f), new Vec2f(0.0f, 1.0f)),
                new Vertex(new Vec3f(-0.5f, -0.5f, 0.5f), new Vec2f(1.0f, 1.0f)),
                new Vertex(new Vec3f(-0.5f, 0.5f, 0.5f), new Vec2f(1.0f, 0.0f)),

                //Top face
                new Vertex(new Vec3f(-0.5f, 0.5f, 0.5f), new Vec2f(0.0f, 0.0f)),
                new Vertex(new Vec3f(-0.5f, 0.5f, -0.5f), new Vec2f(0.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, 0.5f, -0.5f), new Vec2f(1.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, 0.5f, 0.5f), new Vec2f(1.0f, 0.0f)),

                //Bottom face
                new Vertex(new Vec3f(-0.5f, -0.5f, 0.5f), new Vec2f(0.0f, 0.0f)),
                new Vertex(new Vec3f(-0.5f, -0.5f, -0.5f), new Vec2f(0.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, -0.5f, -0.5f), new Vec2f(1.0f, 1.0f)),
                new Vertex(new Vec3f(0.5f, -0.5f, 0.5f), new Vec2f(1.0f, 0.0f)),
        }, new int[]{
                //Back face
                0, 1, 3,
                3, 1, 2,

                //Front face
                4, 5, 7,
                7, 5, 6,

                //Right face
                8, 9, 11,
                11, 9, 10,

                //Left face
                12, 13, 15,
                15, 13, 14,

                //Top face
                16, 17, 19,
                19, 17, 18,

                //Bottom face
                20, 21, 23,
                23, 21, 22
        }, new Material("textures/" + txt + ".png"));
    }

    public static <T> T getRandFromList(List<T> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public static GameObject dummyVoxel(Vec3f pos) {
        return new GameObject(pos, new Vec3f(0, 0, 0), new Vec3f(0.1f, 0.1f, 0.1f), Utils.createMesh("missing"));
    }
}
