import static java.lang.StrictMath.sqrt;

public class Planet {

    private static final double G = 6.67e-11;
    public double xxPos, yyPos, xxVel, yyVel, mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet b) {
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(Planet b) {
        double dist;
        dist = (b.xxPos - this.xxPos) * (b.xxPos - this.xxPos) + (b.yyPos - this.yyPos) * (b.yyPos - this.yyPos);
        dist = sqrt(dist);
        return dist;
    }

    public double calcForceExertedBy(Planet b) {
        double force;
        double distance = this.calcDistance(b);
        force = G * this.mass * b.mass / (distance * distance);
        return force;
    }

    public double calcForceExertedByX(Planet b) {
        double force = this.calcForceExertedBy(b);
        double disX = b.xxPos - this.xxPos;
        double distance = this.calcDistance(b);
        force = force * disX / distance;
        return force;
    }


    public double calcForceExertedByY(Planet b) {
        double force = this.calcForceExertedBy(b);
        double disY = b.yyPos - this.yyPos;
        double distance = this.calcDistance(b);
        force = force * disY / distance;
        return force;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        int j = allPlanets.length;
        int i;
        double NetForceX = 0.0;
        for (i = 0; i < j; i++)
            if (!this.equals(allPlanets[i]))
                NetForceX += this.calcForceExertedByX(allPlanets[i]);
        return NetForceX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        int j = allPlanets.length;
        int i;
        double NetForceY = 0.0;
        for (i = 0; i < j; i++)
            if (!this.equals(allPlanets[i]))
                NetForceY += this.calcForceExertedByY(allPlanets[i]);
        return NetForceY;
    }

    public void update(double dt, double fx, double fy) {
        this.xxVel += fx / this.mass * dt;
        this.yyVel += fy / this.mass * dt;
        this.xxPos += xxVel * dt;
        this.yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "src/images/" + imgFileName);
    }

}
