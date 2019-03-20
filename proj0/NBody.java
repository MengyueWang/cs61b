import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NBody {

    public static double readRadius(String fname) {
        double radius = 0.0;
        try {
            Scanner scan = new Scanner(new File(fname));
            scan.nextDouble();
            radius = scan.nextDouble();
            scan.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return radius;
    }

    public static Planet[] readPlanets(String fname) {
        Scanner scan;
        ArrayList<Planet> allBodiesArrList = new ArrayList<Planet>();
        try {
            scan = new Scanner(new File(fname));
            int numPlanets = scan.nextInt();
            scan.nextDouble();
            for (int i = 0; i < numPlanets; i++) {
                double xp = scan.nextDouble();
                double yp = scan.nextDouble();
                double xv = scan.nextDouble();
                double yv = scan.nextDouble();
                double mass = scan.nextDouble();
                String filename = scan.next();
                Planet b1 = new Planet(xp, yp, xv, yv, mass, filename);
                Planet b2 = new Planet(b1);
                allBodiesArrList.add(b2);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Planet[] allBodiesArr = new Planet[allBodiesArrList.size()];
        allBodiesArr = allBodiesArrList.toArray(allBodiesArr);
        return allBodiesArr;
    }

    public static void main(String[] args) {
        double totalTime = 1E9;
        double dt = 1E6;
        String pfile = "src/data/planets.txt";
        if (args.length > 2) {
            totalTime = Double.parseDouble(args[0]);
            dt = Double.parseDouble(args[1]);
            pfile = args[2];
        }

        // Read simulation data from file
        Planet[] planets = readPlanets(pfile); // readBodies(pfile);
        double radius = readRadius(pfile); // readRadius(pfile);

        // TODO Draw the background
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "src/images/starfield.jpg");
        for (int i = 0; i < planets.length; i++) {
            planets[i].draw();
        }

        // TODO Animate the simulation from time 0 until totalTime

        for (double time = 0; time < totalTime; time += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int k = 0; k < planets.length; k++) {
                xForces[k] = planets[k].calcNetForceExertedByX(planets);
                yForces[k] = planets[k].calcNetForceExertedByY(planets);
            }
            for (int m = 0; m < planets.length; m++) {
                planets[m].update(dt, xForces[m], yForces[m]);
            }
            StdDraw.setScale(-radius, radius);
            StdDraw.picture(0, 0, "src/images/starfield.jpg");
            for (int n = 0; n < planets.length; n++) {
                planets[n].draw();
            }
            StdDraw.show(10);

        }

        // Print final positions of planets
        System.out.printf("%d\n", planets.length);
        System.out.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos,
                    planets[i].xxVel, planets[i].yyVel,
                    planets[i].mass, planets[i].imgFileName);
        }
    }
}
