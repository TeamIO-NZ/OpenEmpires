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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import tech.teamio.openempires.client.engine.Utils;
import tech.teamio.openempires.client.engine.math.Vec3f;
import tech.teamio.openempires.client.engine.objects.GameObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class WorldLoader {

    public static void loadFromSave(String saveFile, World world) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            WorldSave save = gson.fromJson(new JsonReader(new FileReader(saveFile)), WorldSave.class);
            save.voxels.forEach(voxelData -> {
                GameObject obj = new GameObject(new Vec3f((float) Integer.parseInt(voxelData.pos.split("[,]")[0]), (float) Integer.parseInt(voxelData.pos.split("[,]")[1]), (float) Integer.parseInt(voxelData.pos.split("[,]")[2])), new Vec3f(0, 0, 0), new Vec3f(0.1f, 0.1f, 0.1f), Utils.createMesh(voxelData.texture));
                world.addObject(obj);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void randomWorld(World world, int max) {
        for(int i = 0; i < max; i++) {
            int randX = ThreadLocalRandom.current().nextInt(-50, 51);
            int randY = ThreadLocalRandom.current().nextInt(-50, 51);
            int randZ = ThreadLocalRandom.current().nextInt(-50, 51);
            world.addObject(new GameObject(new Vec3f((float)randX, (float)randY, (float)randZ), new Vec3f(0, 0, 0), new Vec3f(0.1f, 0.1f, 0.1f), Utils.createMesh("bricks")));
        }
    }

    public static void randUpdate(World world) {
        GameObject old = Utils.getRandFromList(world.objects);
        world.objects.remove(old);
        old.mesh.destroy();

        randomWorld(world, 1);
    }

    public static void loadLine(World world, int count) {
        for(int i = 0; i < count; i++) {
            world.addObject(new GameObject(new Vec3f((float)i, 0, 0), new Vec3f(0, 0, 0), new Vec3f(0.1f, 0.1f, 0.1f), Utils.createMesh("bricks")));
        }
    }

    public static void loadFloor(World world, int size) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                world.addObject(new GameObject(new Vec3f((float)i, 0, j), new Vec3f(0, 0, 0), new Vec3f(0.1f, 0.1f, 0.1f), Utils.createMesh("bricks")));
            }
        }
    }

    public static void updateRandom(World world) {
        GameObject obj = Utils.getRandFromList(world.objects);
        obj.rot.setX(obj.pos.getX() + 0.01f);
        obj.rot.setY(obj.pos.getY() + 0.01f);
        obj.rot.setZ(obj.pos.getZ() + 0.01f);
    }

    public static void spin(World world) {
        world.objects.forEach(object -> {
            object.rot.setX(object.rot.getX() + 0.13f);
            object.rot.setY(object.rot.getY() + 0.13f);
            object.rot.setZ(object.rot.getZ() + 0.13f);
        });
    }

    public static void box(World world, int size) {
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(i, 0, 0)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(i, size, 0)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(i, 0, size)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(i, size, size)));
        }

        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(0, i, 0)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(size, i, 0)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(0, i, size)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(size, i, size)));
        }

        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(0, 0, i)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(0, size, i)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(size, 0, i)));
        }
        for (int i = 1; i < size; i++) {
            world.addObject(Utils.dummyVoxel(new Vec3f(size, size, i)));
        }
    }
}
