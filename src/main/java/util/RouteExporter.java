package util;

import model.City;
import model.TSPInstance;
import model.TSPSolution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RouteExporter {

    public static void export(
            String fileName,
            TSPInstance instance,
            TSPSolution solution
    ) {

        try {

            File dir = new File("routes");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            FileWriter writer =
                    new FileWriter(fileName);

            writer.write("order,id,x,y\n");

            int[] path =
                    solution.getPath();

            for (int i = 0;
                 i < path.length;
                 i++) {

                City city =
                        instance.getCities()
                                .get(path[i]);

                writer.write(
                        i + ","
                                + city.getId() + ","
                                + city.getX() + ","
                                + city.getY()
                                + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}